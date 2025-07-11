package codein.back.biz.domain.member;

import lombok.Data;

@Data
public class MemberResponseDTO {
    private String memberId;
    private String memberName;
    private String memberEmail;
    private String memberPhone;
    private int memberRole;
}
