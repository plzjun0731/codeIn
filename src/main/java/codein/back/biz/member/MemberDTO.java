package codein.back.biz.member;

import lombok.Data;

@Data
public class MemberDTO {

    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberEmail;
    private String memberPhone;
    private int memberRole;

    private String searchCondition;

}
