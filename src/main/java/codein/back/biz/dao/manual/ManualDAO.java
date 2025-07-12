package codein.back.biz.dao.manual;

import codein.back.biz.domain.manual.ManualDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("manualDAO")
public class ManualDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECTALL = " ";

    private static final String SELECTONE = " SELECT * FROM BOARD_MANUAL " +
            "WHERE MANUAL_ID = ( SELECT MAX(MANUAL_ID) FROM BOARD_MANUAL)";

    private static final String INSERT = " ";

    private static final String UPDATE = " ";

    private static final String DELETE = " ";

    public List<ManualDTO> selectAll(ManualDTO manualDTO) {
        return null;
    }

    @Transactional
    public ManualDTO selectOne(ManualDTO manualDTO) { // 텍스트 에리어에 투명하게 출력해줄 때 사용
        ManualDTO result;
        System.out.println("ManualDAO IN로그 (selectOne) = ["+manualDTO+"]");
        try{
            result=jdbcTemplate.queryForObject(SELECTONE,new SelectOneRowMapper());
            System.out.println("ManualDAO Out로그 (selectOne)");
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean insert(ManualDTO manualDTO){
        return false;
    }

    public boolean update(ManualDTO manualDTO){
        return false;
    }

    public boolean delete(ManualDTO manualDTO){
        return false;
    }

}

class SelectOneRowMapper implements RowMapper<ManualDTO> {
    @Override
    public ManualDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ManualDTO manualDTO = new ManualDTO();
        manualDTO.setManualId(rs.getString("MANUAL_ID"));
        manualDTO.setManualScript(rs.getString("MANUAL_SCRIPT"));
        manualDTO.setManualCheckList(rs.getString("MANUAL_CHECKLIST")); // MANUAL_CHECK_LIST가 아닌 MANUAL_CHECKLIST로 수정
        manualDTO.setManualEtc(rs.getString("MANUAL_ETC"));
        return manualDTO;

    }
}
