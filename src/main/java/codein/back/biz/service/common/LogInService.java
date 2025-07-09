package codein.back.biz.service.common;

import codein.back.biz.dao.member.MemberDAO;
import codein.back.biz.domain.member.MemberDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("logInService")
public class LogInService {

    @Autowired
    private MemberDAO memberDAO;

    public Map<String, Object> login(MemberDTO memberDTO, HttpSession session){
        Map<String, Object> response = new HashMap<>();

        System.out.println("LogInService In 로그 =[" + memberDTO + "]");

        memberDTO.setSearchCondition("login");

        MemberDTO result = memberDAO.selectOne(memberDTO);

        // 페이지에서 게스트 혹은 관리자 로그인을 선택
        // 아이디 비번이 잘못 되었을 때는 0 반환 "아이디 혹은 비번이 틀렸습니다."로 안내
        // 게스트가 관리자 로그인 선택시 "관리자 권한이 없습니다"로 안내
        // 게스트가 게스트 로그인 선택시 로그인 정상 진행 후 게스트 페이지로 이동
        // 관리자가 관리자 로그인 선택시 로그인 정상 진행 후 관리자 페이지로 이동

        // 0 : 아이디 혹은 비번이 틀렸습니다.
        // 1 : 관리자 로그인 성공
        // 2 : 게스트 로그인 성공

        if (result == null) {
            response.put("status", 0);
            return response;
        }

        System.out.println("LogInService Out 로그 =[" + memberDTO + "]");

        setUserSession(session, result);
        return createLogInResponse(result);
    }

    private Map<String, Object> createLogInResponse(MemberDTO member) {
        Map<String, Object> response = new HashMap<>();
        if (member.getMemberRole() <= 1) {
            response.put("status", 1); // 관리자
        } else {
            response.put("status", 2); // 게스트
        }
        response.put("member", member);
        return response;
    }

    private void setUserSession(HttpSession session, MemberDTO member) {
        session.setAttribute("memberId", member.getMemberId());
        session.setAttribute("memberName", member.getMemberName());
        session.setAttribute("memberEmail", member.getMemberEmail());
        session.setAttribute("memberPhone", member.getMemberPhone());
        session.setAttribute("memberGrade", member.getMemberRole());
    }

    // 로그아웃 기능 추가
    public void logout(HttpSession session) {

        System.out.println("LogOutService In 로그 =[" + session.getAttribute("memberId") + "]");

        session.invalidate();

        System.out.println("LogInService Out 로그");
    }

}
