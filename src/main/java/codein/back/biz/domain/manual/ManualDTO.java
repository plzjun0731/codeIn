package codein.back.biz.domain.manual;

import lombok.Data;

@Data
public class ManualDTO {

  private String manualId; // 업무 메뉴얼 아이디
  private String manualScript; // 상담 멘트
  private String manualCheckList; // 상담 체크 사항
  private String manualEtc; // 특이 사항

  private String searchCondition;

}
