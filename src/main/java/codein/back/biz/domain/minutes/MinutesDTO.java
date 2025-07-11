package codein.back.biz.domain.minutes;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MinutesDTO {

    private String minutesId; // 회의록 아이디
    private String minutesTitle; // 회의록 제목
    private String minutesDate; // 회의록 작성일
    private String minutesWriter; // 회의록 작성자
    private String minutesSubject; // 회의 주제
    private String minutesPlace; // 회의 장소
    private String minutesAbsentee; // 회의 결석자
    private String minutesContent; // 회의 내용
    private String minutesResult; // 회의 결론
    private String minutesNext; // 다음 단계
    private String minutesWorkDay; // 회의 일시
    private ArrayList<String> followUpWork; // 팔로우업 작업
    private ArrayList<String> followUpPerformer; // 팔로우업 작업자
    private ArrayList<String> followUpDeadLine; // 팔로우업 마감일

    private String searchCondition;

}
