package codein.back.biz.service.common;

import codein.back.biz.dao.member.MemberDAO;
import codein.back.biz.domain.member.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("signUpService")
public class SignUpService {

    @Autowired
    private MemberDAO memberDAO;

    public Map<String, Object> signUp(MemberDTO memberDTO){
        Map<String, Object> response = new HashMap<>();

        System.out.println("SignUpService In 로그 =[" + memberDTO + "]");

        memberDTO.setSearchCondition("signup");

        boolean result = memberDAO.insert(memberDTO); // 정보 삽입은 성공 혹은 실패로만 출력함

        if (result) {
            response.put("status",0);

            System.out.println("SignUpService Out 로그 (회원가입 성공) =[" + result + "]");

            return response;
        }

        response.put("status",1);

        System.out.println("SignUpService Out 로그 (회원가입 실패) =[" + result + "]");

        return response;
    }

}
