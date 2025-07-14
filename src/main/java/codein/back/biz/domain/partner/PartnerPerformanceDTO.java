    package codein.back.biz.domain.partner;

    import lombok.Data;

    @Data
    public class PartnerPerformanceDTO {

        private int performanceId;
        private int partnerId;
        private int monthlyDb; // 월별 DB수
        private int monthlyTest; // 월별 검사 수
        private int monthlySurgery; // 월별 수술 수
        private int performanceYear; // 연도
        private int performanceMonth; // 월 1~12

        private String searchCondition;
    }
