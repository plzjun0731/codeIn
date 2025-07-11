package codein.back.biz.service.common;

import codein.back.biz.dao.member.MemberDAO;
import codein.back.biz.domain.member.MemberResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllMembersService {
    @Autowired
    private MemberDAO memberDAO;

    public List<MemberResponseDTO> getAllMembers() {
        List<MemberResponseDTO> response = memberDAO.selectAll();
        return response;
    }
}

