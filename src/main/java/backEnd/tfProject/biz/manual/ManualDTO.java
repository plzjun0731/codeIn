package backEnd.tfProject.biz.manual;

import lombok.Data;

@Data
public class ManualDTO {

  private String manualId;
  private String manualTitle;
  private String manualDate;
  private String manualScript;
  private String manualCheckList;
  private String manualRemarks;
  private String memberId;

  private String searchCondition;

}
