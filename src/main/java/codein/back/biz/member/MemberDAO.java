package codein.back.biz.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("memberDAO")
public class MemberDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECTONE_LOGIN =   "SELECT * FROM MEMBER " +
                                                    "WHERE MEMBER_ID = ? AND " +
                                                    "MEMBER_PW = ?";

    private static final String INSERT_MEMBER = "INSERT INTO MEMBER " +
        "(MEMBER_ID, MEMBER_PW, MEMBER_NAME, MEMBER_EMAIL, MEMBER_PHONE, MEMBER_ROLE) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    public List<MemberDTO> selectAll(MemberDTO memberDTO){
        return null;
    }

    @Transactional
    public MemberDTO selectOne(MemberDTO memberDTO){
        MemberDTO result=null;
        System.out.println("MemberDAO(selectOne) In로그 =["+memberDTO+"]");
        try {
            if(memberDTO.getSearchCondition().equals("login")){
                Object[] args = {memberDTO.getMemberId(),memberDTO.getMemberPw()};
                result = jdbcTemplate.queryForObject(SELECTONE_LOGIN, args, new LoginRowMapper());
                System.out.println("MemberDAO(selectOne) Out로그 =["+result+"]");
                return result;
            }
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Transactional
    public boolean insert(MemberDTO memberDTO){
        try {
            if(memberDTO.getSearchCondition().equals("signup")){
                Object[] args = {memberDTO.getMemberId(),memberDTO.getMemberPw(),
                    memberDTO.getMemberName(),memberDTO.getMemberEmail(),
                    memberDTO.getMemberPhone(),memberDTO.getMemberRole()};

                int rows = jdbcTemplate.update(INSERT_MEMBER, args);
                return rows > 0;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
      return false;
    }

    public boolean update(MemberDTO memberDTO){
        // 회원 정보를 변경 시킬 때 사용합니다.
        return false;
    }

    public boolean delete(MemberDTO memberDTO){
        // 멤버 삭제는 없습니다. 퇴사한 회원은 등급을 3으로 변경해 관리합니다. (update 기능으로 넣겠습니다)
        // 모든 접근 기록은 로그로 기록합니다.
        return false;
    }

}

class LoginRowMapper implements RowMapper<MemberDTO> {
    @Override
    public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(rs.getString("MEMBER_ID"));
        memberDTO.setMemberPw(rs.getString("MEMBER_PW"));
        return memberDTO;
    }
}
