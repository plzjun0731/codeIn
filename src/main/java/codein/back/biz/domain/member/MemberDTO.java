package codein.back.biz.domain.member;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class MemberDTO {

    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberEmail;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(
            regexp = "^01[016789]-\\d{4}-\\d{4}$",
            message = "전화번호 형식은 반드시 010-1234-5678처럼 '-'가 포함된 형식이어야 합니다."
    )
    private String memberPhone;
    private int memberRole;

    private String searchCondition;

}