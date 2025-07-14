package codein.back.biz.dao.notice;

import codein.back.biz.domain.notice.NoticeDTO;
import codein.back.biz.domain.notice.NoticeFileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("noticeDAO")
public class NoticeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //공지 사항 제목+ 날짜 + 작성자 이름 목록
    private static final String SELECTALL="SELECT n.NOTICE_ID, n.NOTICE_TITLE, n.NOTICE_DATE, m.MEMBER_NAME " +
            "FROM BOARD_NOTICE n " +
            "JOIN MEMBER m ON n.MEMBER_ID = m.MEMBER_ID " +
            "ORDER BY n.NOTICE_DATE DESC";

    //공지 사항 단건 조회
    private static final String SELECTONE= "SELECT n.NOTICE_ID, n.NOTICE_DATE, n.NOTICE_TITLE, n.NOTICE_CONTENT, " +
            "f.FILE_ID, f.FILE_NAME, f.FILE_PATH, f.FILE_SIZE " +
            "FROM BOARD_NOTICE n LEFT JOIN BOARD_FILE f ON n.NOTICE_ID = f.NOTICE_ID " +
            "WHERE n.NOTICE_ID = ?";

    //공지 등록
    private static final String INSERT_NOTICE= "INSERT INTO BOARD_NOTICE(NOTICE_DATE, MEMBER_ID, NOTICE_TITLE, NOTICE_CONTENT) " +
            "VALUES (?, ?, ?, ?)";

    //첨부파일 등록
    private static final String INSERT_FILE= "INSERT INTO BOARD_FILE(NOTICE_ID, FILE_NAME, FILE_PATH, FILE_SIZE) " +
            "VALUES (?, ?, ?, ?)";

    //제목 검색
    private static final String SEARCH_NOTICE= "SELECT n.NOTICE_TITLE, m.MEMBER_NAME " +
            "FROM BOARD_NOTICE n " +
            "JOIN MEMBER m ON n.MEMBER_ID = m.MEMBER_ID " +
            "WHERE n.NOTICE_TITLE LIKE CONCAT('%', ?, '%') " +
            "ORDER BY n.NOTICE_DATE DESC";

    // 공지사항 수정
    private static final String UPDATE_NOTICE =
            "UPDATE BOARD_NOTICE SET NOTICE_TITLE = ?, NOTICE_CONTENT = ?, NOTICE_DATE = NOW() WHERE NOTICE_ID = ?";

    // 공지사항 삭제
    private static final String DELETE_NOTICE =
            "DELETE FROM BOARD_NOTICE WHERE NOTICE_ID = ?";

    // 기존 파일 삭제
    private static final String DELETE_FILE =
            "DELETE FROM BOARD_FILE WHERE NOTICE_ID = ?";

    public List<NoticeDTO> selectAll(NoticeDTO noticeDTO){
        List<NoticeDTO> result = null;
        System.out.println("NoticeDAO In로그(selectAll) =["+noticeDTO+"]");
        try {
            Object[] args = {noticeDTO.getNoticeId()};
            result = jdbcTemplate.query(SELECTALL, args, new NoticeListRowMapper());
            System.out.println("NoticeDAO Out로그(selectAll) =["+result+"]");
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<NoticeDTO> searchNotice(String keyword){
        return jdbcTemplate.query(SEARCH_NOTICE, new SelectListRowMapper(),keyword);
    }

    @Transactional
    public NoticeDTO selectOne(NoticeDTO noticeDTO) {
        try {
            return jdbcTemplate.queryForObject(SELECTONE, new SelectOneRowMapper(),noticeDTO.getNoticeId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Transactional
    public boolean insert(NoticeDTO noticeDTO){
        try {
            jdbcTemplate.update(INSERT_NOTICE,
                    noticeDTO.getNoticeDate(),
                    noticeDTO.getMemberId(),
                    noticeDTO.getNoticeTitle(),
                    noticeDTO.getNoticeContent());

            Integer newNoticeId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

            //파일이 있다면 첨부
            if (noticeDTO.getFile() != null) {
                jdbcTemplate.update(INSERT_FILE,
                        newNoticeId,
                        noticeDTO.getFile().getFileName(),
                        noticeDTO.getFile().getFilePath(),
                        noticeDTO.getFile().getFileSize());
            }
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean update(NoticeDTO noticeDTO){
        try {
            // 공지사항 수정
            int noticeResult = jdbcTemplate.update(UPDATE_NOTICE,
                    noticeDTO.getNoticeTitle(),
                    noticeDTO.getNoticeContent(),
                    noticeDTO.getNoticeId());

            // 파일 정보가 있으면 기존 파일 삭제 후 새 파일 추가
            if (noticeDTO.getFile() != null) {
                jdbcTemplate.update(DELETE_FILE, noticeDTO.getNoticeId());

                jdbcTemplate.update(INSERT_FILE,
                        noticeDTO.getNoticeId(),
                        noticeDTO.getFile().getFileName(),
                        noticeDTO.getFile().getFilePath(),
                        noticeDTO.getFile().getFileSize());
            }
            return noticeResult > 0;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean delete(NoticeDTO noticeDTO){
        try {
            int result = jdbcTemplate.update(DELETE_NOTICE, noticeDTO.getNoticeId());
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

class NoticeListRowMapper implements RowMapper<NoticeDTO>{
    @Override
    public NoticeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeId(rs.getInt("NOTICE_ID"));
        noticeDTO.setNoticeTitle(rs.getString("NOTICE_TITLE"));
        noticeDTO.setNoticeContent(rs.getString("NOTICE_CONTENT"));
        noticeDTO.setNoticeDate(rs.getString("NOTICE_DATE"));

        return noticeDTO;
    }
}


class SelectOneRowMapper implements RowMapper<NoticeDTO> {
    public NoticeDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        NoticeDTO dto= new NoticeDTO();
        dto.setNoticeId(rs.getInt("NOTICE_ID"));
        dto.setNoticeDate(rs.getString("NOTICE_DATE"));
        dto.setNoticeTitle(rs.getString("NOTICE_TITLE"));
        dto.setNoticeContent(rs.getString("NOTICE_CONTENT"));

        if (rs.getString("FILE_NAME") != null) {
            NoticeFileDTO file= new NoticeFileDTO();
            file.setFileId(rs.getInt("FILE_ID"));
            file.setFileName(rs.getString("FILE_NAME"));
            file.setFilePath(rs.getString("FILE_PATH"));
            file.setFileSize(rs.getLong("FILE_SIZE"));
            dto.setFile(file);
        }
        return dto;
    }
}

class SelectListRowMapper implements RowMapper<NoticeDTO> {
    @Override
    public NoticeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        NoticeDTO dto = new NoticeDTO();
        dto.setNoticeTitle(rs.getString("NOTICE_TITLE"));
        dto.setNoticeDate(rs.getString("NOTICE_DATE"));
        dto.setMemberName(rs.getString("MEMBER_NAME"));
        return dto;
    }
}
