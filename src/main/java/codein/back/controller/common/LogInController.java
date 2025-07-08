package codein.back.controller.common;

import codein.back.biz.member.MemberDTO;
import codein.back.biz.member.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LogInController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody MemberDTO memberDTO, HttpSession session) {

        System.out.println("LogInController In 로그 =[" + memberDTO + "]");

        memberDTO.setSearchCondition("login");

        Map<String, Object > response = new HashMap<>();

        MemberDTO result = memberService.selectOne(memberDTO);

        // 페이지에서 게스트 혹은 관리자 로그인을 선택
        // 아이디 비번이 잘못 되었을 때는 0 반환 "아이디 혹은 비번이 틀렸습니다."로 안내
        // 게스트가 관리자 로그인 선택시 "관리자 권한이 없습니다"로 안내
        // 게스트가 게스트 로그인 선택시 로그인 정상 진행 후 게스트 페이지로 이동
        // 관리자가 관리자 로그인 선택시 로그인 정상 진행 후 관리자 페이지로 이동

        // 0 : 아이디 혹은 비번이 틀렸습니다.
        // 1 : 관리자 로그인 성공
        // 2 : 게스트 로그인 성공

        if (result == null) {
            // 아이디 비번을 찾지 못했을 때
            // 로그인 실패시 0 반환
            // "아이디 혹은 비번이 틀렸습니다."로 안내
            response.put("status",0);
            return ResponseEntity.ok(response);
        }

        session.setAttribute("memberId", result.getMemberId());
        session.setAttribute("memberName", result.getMemberName());
        session.setAttribute("memberEmail", result.getMemberEmail());
        session.setAttribute("memberPhone", result.getMemberPhone());
        session.setAttribute("memberGrade", result.getMemberGrade());

        if(result.getMemberGrade()==1){
            // 회원이 관리자일 경우 1의 상태로 로그인 성공을 보내줌
            // 프론트에서 관리자 페이지로 이동 시킴
            response.put("status",1);
            response.put("member",result);
            return ResponseEntity.ok(response);
        }

        // 이외의 경우 모두 게스트 로그인 이므로
        // 2의 상태로 로그인 성공을 보내줌
        // 프론트에서 게스트 페이지로 이동 시킴
        response.put("status",2);
        response.put("member",result);
        return ResponseEntity.ok(response);
    }
}
