package codein.back.biz.service.common;

import codein.back.biz.dao.member.MemberDAO;
import codein.back.biz.domain.member.MemberDTO;
import codein.back.biz.domain.member.MemberResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service("signUpService")
public class SignUpService {

    @Autowired
    private MemberDAO memberDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Map<String, Object> signUp(MemberDTO memberDTO){
        Map<String, Object> response = new HashMap<>();

        System.out.println("SignUpService In 로그 =[" + memberDTO + "]");

        memberDTO.setSearchCondition("signup");

        memberDTO.setMemberPw(bCryptPasswordEncoder.encode(memberDTO.getMemberPw()));

        boolean result = memberDAO.insert(memberDTO); // 정보 삽입은 성공 혹은 실패로만 출력함

        if (result) {
            MemberResponseDTO memberResponse = new MemberResponseDTO();
            memberResponse.setMemberId(memberDTO.getMemberId());
            memberResponse.setMemberName(memberDTO.getMemberName());
            memberResponse.setMemberEmail(memberDTO.getMemberEmail());
            memberResponse.setMemberPhone(memberDTO.getMemberPhone());
            memberResponse.setMemberRole(memberDTO.getMemberRole());

            response.put("status", 1);
            response.put("data", memberResponse);

            System.out.println("SignUpService Out 로그 (회원가입 성공) =[" + result + "]");

            return response;
        }

        response.put("status",0);

        System.out.println("SignUpService Out 로그 (회원가입 실패) =[" + result + "]");

        return response;
    }
}

