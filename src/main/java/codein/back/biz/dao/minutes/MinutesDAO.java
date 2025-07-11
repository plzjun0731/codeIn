package codein.back.biz.dao.minutes;

import codein.back.biz.domain.minutes.MinutesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("minutesDAO")
public class MinutesDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //회의록 목록
    private static final String LIST_MINUTES=
            "SELECT MINUTES_TITLE, MINUTES_WRITER, MINUTES_ID"+
                    "FROM BOARD_MINUTES"+
                    "ORDER BY MINUTES_DATE";

    // 회의록 등록
    private static final String INSERT_MINUTES =
            "INSERT INTO BOARD_MINUTES(MINUTES_TITLE, MINUTES_DATE, MINUTES_WRITER, MINUTES_SUBJECT, " +
                    "MINUTES_PLACE, MINUTES_ABSENTEE, MINUTES_JOINER, MINUTES_CONTENT, " +
                    "MINUTES_RESULT, MINUTES_NEXT, MINUTES_WorkDate) " +
                    "VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // 팔로우업 등록
    private static final String INSERT_FOLLOWUP =
            "INSERT INTO BOARD_FOLLOWUP(FOLLOWUP_WORK, FOLLOWUP_PERFORMER, FOLLOWUP_DEADLINE, MINUTES_ID) " +
                    "VALUES (?, ?, ?, ?)";

    // 회의록 단건 조회
    private static final String SELECT_MINUTES =
            "SELECT * FROM BOARD_MINUTES WHERE MINUTES_ID = ?";

    // 팔로우업 목록 조회
    private static final String SELECT_FOLLOWUPS =
            "SELECT * FROM BOARD_FOLLOWUP WHERE MINUTES_ID = ? ORDER BY FOLLOWUP_ID ASC";


    // 회의록 수정
    private static final String UPDATE_MINUTES =
            "UPDATE BOARD_MINUTES SET " +
                    "MINUTES_TITLE = ?, MINUTES_SUBJECT = ?, MINUTES_PLACE = ?, " +
                    "MINUTES_ABSENTEE = ?, MINUTES_JOINER = ?, MINUTES_CONTENT = ?, " +
                    "MINUTES_RESULT = ?, MINUTES_NEXT = ?, MINUTES_WORKDATE = ?, MINUTES_DATE = NOW() " +
                    "WHERE MINUTES_ID = ?";

    // 기존 팔로우업 전체 삭제
    private static final String DELETE_FOLLOWUP_BY_MINUTES_ID =
            "DELETE FROM BOARD_FOLLOWUP WHERE MINUTES_ID = ?";

    // 회의록 삭제
    private static final String DELETE_MINUTES =
            "DELETE FROM BOARD_MINUTES WHERE MINUTES_ID = ?";

// 회의록 목록 조회
    @Transactional
    public List<MinutesDTO> selectAllMinutes() {
        try {
            List<MinutesDTO> list= jdbcTemplate.query(LIST_MINUTES, new MinutesRowMapper());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Transactional
    public boolean insert(MinutesDTO dto) {
        try{

            //회의록 저장
            jdbcTemplate.update(INSERT_MINUTES,
                    dto.getMinutesTitle(),
                    dto.getMinutesSubject(),
                    dto.getMinutesPlace(),
                    dto.getMinutesAbsentee(),
                    dto.getMinutesJoiner(),
                    dto.getMinutesContent(),
                    dto.getMinutesResult(),
                    dto.getMinutesNext(),
                    dto.getMinutesWorkDate());

            Integer newMinutesId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);


            // 팔로우업 저장
            if (dto.getFollowUpWork() != null) {
                for (int i = 0; i < dto.getFollowUpWork().size(); i++) {
                    jdbcTemplate.update(INSERT_FOLLOWUP,
                            dto.getFollowUpWork().get(i),
                            dto.getFollowUpPerformer().get(i),
                            dto.getFollowUpDeadLine().get(i),
                            newMinutesId);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //회의록 단건 조회
    @Transactional
    public MinutesDTO selectMinutes(MinutesDTO dto) {
        try {
            MinutesDTO result = jdbcTemplate.queryForObject(SELECT_MINUTES, new MinutesRowMapper(), dto.getMinutesId());
            List<FollowUpRow> followUps = jdbcTemplate.query(SELECT_FOLLOWUPS, new FollowUpRowMapper(), dto.getMinutesId());

            ArrayList<String> works = new ArrayList<>();
            ArrayList<String> performers = new ArrayList<>();
            ArrayList<String> deadlines = new ArrayList<>();

            for (FollowUpRow row : followUps) {
                works.add(row.work);
                performers.add(row.performer);
                deadlines.add(row.deadline);
            }

            result.setFollowUpWork(works);
            result.setFollowUpPerformer(performers);
            result.setFollowUpDeadLine(deadlines);
            return dto;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //회의록 수정
    @Transactional
    public boolean updateMinutes(MinutesDTO dto) {
        try {
            int updated = jdbcTemplate.update(UPDATE_MINUTES,
                    dto.getMinutesTitle(),
                    dto.getMinutesSubject(),
                    dto.getMinutesPlace(),
                    dto.getMinutesAbsentee(),
                    dto.getMinutesJoiner(),
                    dto.getMinutesContent(),
                    dto.getMinutesResult(),
                    dto.getMinutesNext(),
                    dto.getMinutesWorkDate());

            // 기존 팔로우업 삭제
            jdbcTemplate.update(DELETE_FOLLOWUP_BY_MINUTES_ID, dto.getMinutesId());

            // 새 팔로우업 삽입
            if (dto.getFollowUpWork() != null) {
                for (int i = 0; i < dto.getFollowUpWork().size(); i++) {
                    jdbcTemplate.update(INSERT_FOLLOWUP,
                            dto.getFollowUpWork().get(i),
                            dto.getFollowUpPerformer().get(i),
                            dto.getFollowUpDeadLine().get(i),
                            dto.getMinutesId());
                }
            }

            return updated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //회의록 삭제
    @Transactional
    public boolean deleteMinutes(MinutesDTO dto) {
        try {
            int id = dto.getMinutesId();

            // 팔로우업 먼저 삭제
            jdbcTemplate.update(DELETE_FOLLOWUP_BY_MINUTES_ID, id);

            // 회의록 삭제
            int deleted = jdbcTemplate.update(DELETE_MINUTES, id);

            return deleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}


class MinutesRowMapper implements RowMapper<MinutesDTO> {
    @Override
    public MinutesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MinutesDTO dto = new MinutesDTO();
        dto.setMinutesId(rs.getInt("MINUTES_ID"));
        dto.setMinutesTitle(rs.getString("MINUTES_TITLE"));
        dto.setMinutesDate(rs.getString("MINUTES_DATE"));
        dto.setMinutesWriter(rs.getString("MINUTES_WRITER"));
        dto.setMinutesSubject(rs.getString("MINUTES_SUBJECT"));
        dto.setMinutesPlace(rs.getString("MINUTES_PLACE"));
        dto.setMinutesAbsentee(rs.getString("MINUTES_ABSENTEE"));
        dto.setMinutesJoiner(rs.getString("MINUTES_JOINER"));
        dto.setMinutesContent(rs.getString("MINUTES_CONTENT"));
        dto.setMinutesResult(rs.getString("MINUTES_RESULT"));
        dto.setMinutesNext(rs.getString("MINUTES_NEXT"));
        dto.setMinutesWorkDate(rs.getString("MINUTES_WORKDATE"));

        return dto;
    }
}

class FollowUpRowMapper implements RowMapper<FollowUpRow> {
    @Override
    public FollowUpRow mapRow(ResultSet rs, int rowNum) throws SQLException {
        FollowUpRow row = new FollowUpRow();
        row.work = rs.getString("FOLLOWUP_WORK");
        row.performer = rs.getString("FOLLOWUP_PERFORMER");
        row.deadline = rs.getString("FOLLOWUP_DEADLINE");
        return row;
    }
}

class FollowUpRow {
    String work;
    String performer;
    String deadline;
}

