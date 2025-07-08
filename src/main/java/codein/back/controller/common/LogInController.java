package codein.back.controller.common;

import codein.back.biz.domain.member.MemberDTO;
import codein.back.biz.service.common.LogInService;
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
    private LogInService logInService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody MemberDTO memberDTO, HttpSession session) {
        Map<String, Object> response = logInService.login(memberDTO, session);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        logInService.logout(session);
        return ResponseEntity.ok().build();
    }


}
