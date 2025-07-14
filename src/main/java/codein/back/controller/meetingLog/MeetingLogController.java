package codein.back.controller.meetingLog;
import codein.back.biz.domain.meetingLog.MeetingLogDTO;
import codein.back.biz.service.meetingLog.MeetingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/meetingLog")
public class MeetingLogController {

    @Autowired
    private MeetingLogService meetingLogService;

    public Map<String, Object> listMeetingLog() {
        System.out.println("목록 출력");
        return meetingLogService.selectAllMeetingLog();
    }

    // 회의록 등록
    @PostMapping("/insert")
    public Map<String, Object> insertMeetingLog(@RequestBody MeetingLogDTO dto) {
        return meetingLogService.insertMeetingLog(dto);
    }

    // 회의록 단건 조회
    @PostMapping("/select")
    public Map<String, Object> selectMeetingLog(@RequestBody MeetingLogDTO dto) {
        return meetingLogService.selectMeetingLog(dto);
    }

    // 회의록 수정
    @PostMapping("/update")
    public Map<String, Object> updateMeetingLog(@RequestBody MeetingLogDTO dto) {
        return meetingLogService.updateMeetingLog(dto);
    }

    // 회의록 삭제
    @PostMapping("/delete")
    public Map<String, Object> deleteMeetingLog(@RequestBody MeetingLogDTO dto) {
        return meetingLogService.deleteMeetingLog(dto);
    }
}

