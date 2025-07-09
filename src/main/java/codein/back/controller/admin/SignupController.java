package codein.back.controller.admin;

import codein.back.biz.domain.member.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tf")
public class SignupController {

//  @Autowired
//  private MemberService memberService;
//
//  @PostMapping("/signup")
//  public ResponseEntity<Void> signup(@RequestBody MemberDTO memberDTO) {
//    boolean success = memberService.insert(memberDTO);
//    if(success) {
//      return ResponseEntity.status(HttpStatus.CREATED).build();
//    } else {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }
//  }
}
