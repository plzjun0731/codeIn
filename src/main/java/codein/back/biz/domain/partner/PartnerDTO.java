package codein.back.biz.domain.partner;

import lombok.Data;

import java.util.Date;

@Data
public class PartnerDTO {

    private int partnerId; // 제휴처명
    private String partnerName; // 단위
    private String partnerUnit; // 담당자명
    private String partnerManager;
    private Date noticeDate1; // 1차 공지
    private String noticeImg1;
    private Date noticeDate2; // 2차 공지
    private String noticeImg2;
    private Date noticeDate3; // 3차 공지
    private String noticeImg3;
    private int targetValue;
    private Date lastUpdate;

    private String searchCondition;
}
