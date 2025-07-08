package codein.back.biz.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberService")
public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    public List<MemberDTO> selectAll(MemberDTO memberDTO){
        return memberDAO.selectAll(memberDTO);
    }

    public MemberDTO selectOne(MemberDTO memberDTO){
        return memberDAO.selectOne(memberDTO);
    }

    public boolean insert(MemberDTO memberDTO){
        return memberDAO.insert(memberDTO);
    }

    public boolean update(MemberDTO memberDTO){
        return memberDAO.update(memberDTO);
    }

    public boolean delete(MemberDTO memberDTO){
        return memberDAO.delete(memberDTO);
    }

}
