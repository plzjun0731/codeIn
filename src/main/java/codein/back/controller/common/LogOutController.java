package codein.back.controller.common;

import codein.back.biz.member.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tf")
public class LogOutController {

  @PostMapping("/logout")
  public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
    Map<String, Object> response = new HashMap<>();
    session.invalidate(); // 세션 정보 모두 삭제
    response.put("status","success");
    return ResponseEntity.ok(response);
  }
}
