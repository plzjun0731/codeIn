package codein.back.biz.domain.meetingLog;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MeetingLogDTO {

    private Integer meetingLogId; // 회의록 아이디
    private String meetingLogTitle; // 회의록 제목
    private String meetingLogDate; // 회의록 작성일
    private String meetingLogWriter; // 회의록 작성자
    private String meetingLogSubject; // 회의 주제
    private String meetingLogPlace; // 회의 장소
    private String meetingLogAbsentee; // 회의 결석자
    private String meetingLogJoiner; // 회의 참여자
    private String meetingLogContent; // 회의 내용
    private String meetingLogResult; // 회의 결론
    private String meetingLogNext; // 다음 단계
    private String meetingLogWorkDate; // 회의 일시
    private ArrayList<String> followUpWork; // 팔로우업 작업
    private ArrayList<String> followUpPerformer; // 팔로우업 작업자
    private ArrayList<String> followUpDeadLine; // 팔로우업 마감일

    private String searchCondition;

}
