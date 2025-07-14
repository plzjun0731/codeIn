package codein.back.biz.dao.partner;

import codein.back.biz.domain.partner.PartnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository("partnerDAO")
public class PartnerDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECTALL = "SELECT * FROM PARTNER " +
            "ORDER BY PARTNER_ID DESC";

    private static final String SELECTONE = " ";

    private static final String INSERT = "INSERT INTO PARTNER (" +
            "PARTNER_NAME," +
            "PARTNER_UNIT," +
            "PARTNER_MANAGER," +
            "NOTICE_DATE_1," +
            "NOTICE_DATE_2," +
            "NOTICE_DATE_3," +
            "TARGET_VALUE )" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE = "UPDATE PARTNER SET " +
            "PARTNER_NAME = ?, " +
            "PARTNER_UNIT = ?, " +
            "PARTNER_MANAGER = ?, " +
            "NOTICE_DATE_1 = ?, " +
            "NOTICE_IMG_1 = ?, " +
            "NOTICE_DATE_2 = ?, " +
            "NOTICE_IMG_2 = ?, " +
            "NOTICE_DATE_3 = ?, " +
            "NOTICE_IMG_3=?, " +
            "TARGET_VALUE = ?, " +
            "LAST_UPDATED = CURRENT_TIMESTAMP " +
            "WHERE PARTNER_ID = ?";

    private static final String DELETE = "DELETE FROM PARTNER WHERE PARTNER_ID = ? ";

    public List<PartnerDTO> selectAll(PartnerDTO partnerDTO) {
        List<PartnerDTO> result;
        System.out.println("PartnerDAO In로그(selectAll) = [" + partnerDTO + "]");
        try {
            result = jdbcTemplate.query(SELECTALL, new SelectAllRowMapper());
            System.out.println("PartnerDAO Out로그 (selectAll)");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PartnerDTO selectOne(PartnerDTO partnerDTO) {
        return null;
    }

    public boolean insert(PartnerDTO partnerDTO) {
        try{
            System.out.println("PartnerDAO In로그(insert) = [" + partnerDTO + "]");
            Object[] args = {partnerDTO.getPartnerName(),partnerDTO.getPartnerUnit(), partnerDTO.getPartnerManager(),partnerDTO.getNoticeDate1(),partnerDTO.getNoticeDate2(),partnerDTO.getNoticeDate3(),partnerDTO.getTargetValue()};
            int rows = jdbcTemplate.update(INSERT,args);
            System.out.println("PartnerDAO Out로그(insert)");
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(PartnerDTO partnerDTO) {
        try{
            System.out.println("PartnerDAO In로그(update) = [" + partnerDTO + "]");

            // 날짜 포맷터 생성
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // 각 날짜를 yyyy-MM-dd 형식으로 변환
            Date noticeDate1 = partnerDTO.getNoticeDate1() != null ?
                    Date.valueOf(dateFormat.format(partnerDTO.getNoticeDate1())) : null;
            Date noticeDate2 = partnerDTO.getNoticeDate2() != null ?
                    Date.valueOf(dateFormat.format(partnerDTO.getNoticeDate2())) : null;
            Date noticeDate3 = partnerDTO.getNoticeDate3() != null ?
                    Date.valueOf(dateFormat.format(partnerDTO.getNoticeDate3())) : null;

            Object[] args = {
                    partnerDTO.getPartnerName(),
                    partnerDTO.getPartnerUnit(),
                    partnerDTO.getPartnerManager(),
                    noticeDate1,
                    partnerDTO.getNoticeImg1(),
                    noticeDate2,
                    partnerDTO.getNoticeImg2(),
                    noticeDate3,
                    partnerDTO.getNoticeImg3(),
                    partnerDTO.getTargetValue(),
                    partnerDTO.getPartnerId()  // WHERE 절의 조건
            };

            int rows = jdbcTemplate.update(UPDATE,args);
            System.out.println("PartnerDAO Out로그(update)");
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(PartnerDTO partnerDTO) {
        try{
            System.out.println("PartnerDAO In로그(delete) = ["+partnerDTO+"]");
            Object[] args = {partnerDTO.getPartnerId()};
            int rows = jdbcTemplate.update(DELETE,args);
            System.out.println("PartnerDAO Out로그(update)");
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

class SelectAllRowMapper implements RowMapper<PartnerDTO> {
    @Override
    public  PartnerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        PartnerDTO partnerDTO = new PartnerDTO();
        partnerDTO.setPartnerId(rs.getInt("PARTNER_ID"));
        partnerDTO.setPartnerName(rs.getString("PARTNER_NAME"));
        partnerDTO.setPartnerUnit(rs.getString("PARTNER_UNIT"));
        partnerDTO.setPartnerManager(rs.getString("PARTNER_MANAGER"));
        partnerDTO.setNoticeDate1(rs.getDate("NOTICE_DATE_1"));
        partnerDTO.setNoticeImg1(rs.getString("NOTICE_IMG_1"));
        partnerDTO.setNoticeDate2(rs.getDate("NOTICE_DATE_2"));
        partnerDTO.setNoticeImg2(rs.getString("NOTICE_IMG_2"));
        partnerDTO.setNoticeDate3(rs.getDate("NOTICE_DATE_3"));
        partnerDTO.setNoticeImg3(rs.getString("NOTICE_IMG_3"));
        partnerDTO.setTargetValue(rs.getInt("TARGET_VALUE"));
        partnerDTO.setLastUpdate(rs.getDate("LAST_UPDATED"));

        return partnerDTO;
    }

}
