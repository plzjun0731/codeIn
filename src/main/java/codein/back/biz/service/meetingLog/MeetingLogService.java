package codein.back.biz.service.meetingLog;

import codein.back.biz.dao.meetingLog.MeetingLogDAO;
import codein.back.biz.domain.meetingLog.MeetingLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Service("meetingLogService")
public class MeetingLogService {

    @Autowired
    private MeetingLogDAO meetingLogDAO;

    // 회의록 전체 목록 조회
    public Map<String, Object> selectAllMeetingLog() {
        Map<String, Object> response = new HashMap<>();

        List<MeetingLogDTO> list = meetingLogDAO.selectAllMeetingLog();

        if (list != null && !list.isEmpty()) {
            response.put("status", 0);
            response.put("list", list);
            response.put("message", "회의록 목록 조회 성공");
        } else {
            response.put("status", 1);
            response.put("message", "회의록이 존재하지 않습니다.");
        }

        return response;
    }


    // 회의록 등록
    public Map<String, Object> insertMeetingLog(MeetingLogDTO dto) {
        Map<String, Object> response = new HashMap<>();
        boolean result = meetingLogDAO.insert(dto);

        if (result) {
            response.put("status", 0); // 성공
            response.put("message", "회의록이 등록되었습니다.");
        } else {
            response.put("status", 1); // 실패
            response.put("message", "회의록 등록에 실패했습니다.");
        }
        return response;
    }

    // 회의록 조회
    public Map<String, Object> selectMeetingLog(MeetingLogDTO dto) {
        Map<String, Object> response = new HashMap<>();
        MeetingLogDTO result = meetingLogDAO.selectMeetingLog(dto);

        if (result != null) {
            response.put("status", 0);
            response.put("data", result);
        } else {
            response.put("status", 1);
            response.put("message", "회의록을 찾을 수 없습니다.");
        }
        return response;
    }

    // 회의록 수정
    public Map<String, Object> updateMeetingLog(MeetingLogDTO dto) {
        Map<String, Object> response = new HashMap<>();
        boolean result = meetingLogDAO.updateMeetingLog(dto);

        if (result) {
            response.put("status", 0);
            response.put("message", "회의록이 수정되었습니다.");
        } else {
            response.put("status", 1);
            response.put("message", "회의록 수정에 실패했습니다.");
        }
        return response;
    }

    // 회의록 삭제
    public Map<String, Object> deleteMeetingLog(MeetingLogDTO dto) {
        Map<String, Object> response = new HashMap<>();
        boolean result = meetingLogDAO.deleteMeetingLog(dto);

        if (result) {
            response.put("status", 0);
            response.put("message", "회의록이 삭제되었습니다.");
        } else {
            response.put("status", 1);
            response.put("message", "회의록 삭제에 실패했습니다.");
        }
        return response;
    }
}

