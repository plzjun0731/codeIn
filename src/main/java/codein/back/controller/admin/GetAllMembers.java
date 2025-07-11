package codein.back.controller.admin;

import codein.back.biz.dao.member.MemberDAO;
import codein.back.biz.domain.member.MemberDTO;
import codein.back.biz.domain.member.MemberResponseDTO;
import codein.back.biz.service.common.GetAllMembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetAllMembers {
    @Autowired
    private GetAllMembersService getAllMembersService;

    //계정 생성 창에서 표 안에 들어갈 정보 불러오는 컨트롤러
    //프론트 측에서 처음 페이지 로딩 시, 계정 생성 시에 해당 컨트롤러 불러올 것
    @GetMapping("/api/admin/members")
    public ResponseEntity<List<MemberResponseDTO>> getAllMembers() {
        List<MemberResponseDTO> response = getAllMembersService.getAllMembers();
        return ResponseEntity.ok(response);
    }
}
