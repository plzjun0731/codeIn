package codein.back.biz.service.minutes;

import codein.back.biz.dao.minutes.MinutesDAO;
import codein.back.biz.domain.minutes.MinutesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Service("minutesService")
public class MinutesService {

    @Autowired
    private MinutesDAO minutesDAO;

    // 회의록 전체 목록 조회
    public Map<String, Object> selectAllMinutes() {
        Map<String, Object> response = new HashMap<>();

        List<MinutesDTO> list = minutesDAO.selectAllMinutes();

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
    public Map<String, Object> insertMinutes(MinutesDTO dto) {
        Map<String, Object> response = new HashMap<>();
        boolean result = minutesDAO.insert(dto);

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
    public Map<String, Object> selectMinutes(MinutesDTO dto) {
        Map<String, Object> response = new HashMap<>();
        MinutesDTO result = minutesDAO.selectMinutes(dto);

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
    public Map<String, Object> updateMinutes(MinutesDTO dto) {
        Map<String, Object> response = new HashMap<>();
        boolean result = minutesDAO.updateMinutes(dto);

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
    public Map<String, Object> deleteMinutes(MinutesDTO dto) {
        Map<String, Object> response = new HashMap<>();
        boolean result = minutesDAO.deleteMinutes(dto);

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

