package backEnd.tfProject.biz.notice;

import lombok.Data;

@Data
public class NoticeDTO {

    private String noticeId; // 공지사항 아이디
    private String noticeTitle; // 공지사항 제목
    private String noticeDate; // 공지사항 작성일
    private String noticeWriter; // 공지사항 작성자

    private String searchCondition;

}
