package codein.back.controller.admin;

import codein.back.biz.member.MemberDTO;
import codein.back.biz.member.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tf")
public class SignupController {
  @Autowired
  private MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<Void> signup(@RequestBody MemberDTO memberDTO) {
    boolean success = memberService.insert(memberDTO);
    if(success) {
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
