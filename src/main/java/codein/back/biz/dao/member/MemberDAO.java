package codein.back.biz.dao.member;

import codein.back.biz.domain.member.MemberDTO;
import codein.back.biz.domain.member.MemberResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("memberDAO")
public class MemberDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String SELECTONE_LOGIN =   "SELECT * FROM MEMBER " +
            "WHERE MEMBER_ID = ?";

    private static final String INSERT_MEMBER = "INSERT INTO MEMBER " +
            "(MEMBER_ID, MEMBER_PW, MEMBER_NAME, MEMBER_EMAIL, MEMBER_PHONE, MEMBER_ROLE) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    public List<MemberResponseDTO> selectAll(){
        String sql = "SELECT * FROM MEMBER";
        List<MemberDTO> members = jdbcTemplate.query(sql, new LoginRowMapper());

        return members.stream().map(member -> {
            MemberResponseDTO dto = new MemberResponseDTO();
            dto.setMemberId(member.getMemberId());
            dto.setMemberName(member.getMemberName());
            dto.setMemberEmail(member.getMemberEmail());
            dto.setMemberPhone(member.getMemberPhone());
            dto.setMemberRole(member.getMemberRole());
            return dto;
        }).toList();
    }

    @Transactional
    public MemberDTO selectOne(MemberDTO memberDTO){
        MemberDTO result=null;
        System.out.println("MemberDAO(selectOne) In로그 =["+memberDTO+"]");
        try {
            if(memberDTO.getSearchCondition().equals("login")){
                Object[] args = {memberDTO.getMemberId()};
                result = jdbcTemplate.queryForObject(SELECTONE_LOGIN, args, new LoginRowMapper());
                if(bCryptPasswordEncoder.matches(memberDTO.getMemberPw(), result.getMemberPw())){
                    System.out.println("MemberDAO(selectOne) Out로그 =["+result+"]");
                    return result;
                } else {
                    System.out.println("MemberDAO(selectOne) Out로그 =[" + result + "]");
                    return result;
                }
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
        // 서버 로그 로컬 로그 모두 기록하기로 합니다.
        return false;
    }

}

class LoginRowMapper implements RowMapper<MemberDTO> {
    @Override
    public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(rs.getString("MEMBER_ID"));
        memberDTO.setMemberPw(rs.getString("MEMBER_PW"));
        memberDTO.setMemberName(rs.getString("MEMBER_NAME"));
        memberDTO.setMemberEmail(rs.getString("MEMBER_EMAIL"));
        memberDTO.setMemberPhone(rs.getString("MEMBER_PHONE"));
        memberDTO.setMemberRole(rs.getInt("MEMBER_ROLE"));

        return memberDTO;
    }
}
