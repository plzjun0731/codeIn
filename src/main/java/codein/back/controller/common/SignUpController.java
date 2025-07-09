package codein.back.controller.common;

import codein.back.biz.domain.member.MemberDTO;
import codein.back.biz.service.common.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody MemberDTO memberDTO){
        Map<String, Object> response = signUpService.signUp(memberDTO);
        return ResponseEntity.ok(response);
    }

}
