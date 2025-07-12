package codein.back.biz.domain.notice;

import lombok.Data;

@Data
public class NoticeDTO {

    private Integer noticeId; // 공지사항 아이디
    private String noticeTitle; // 공지사항 제목
    private String noticeDate; // 공지사항 작성일
    private String noticeContent; // 공지사항 내용
    private String memberId;   // 작성자 회원 ID
    private String memberName;  // 작성자 이름

    private String searchCondition;
    private NoticeFileDTO file;

}
