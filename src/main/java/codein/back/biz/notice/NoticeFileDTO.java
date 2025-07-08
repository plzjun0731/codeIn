package codein.back.biz.notice;

import lombok.Data;

@Data
public class NoticeFileDTO {

    private Integer fileId;  //첨부파일 아이디
    private String fileName; //첨부파일 이름
    private String filePath; //첨부파일 경로
    private Long fileSize; //첨부파일 사이즈
}
