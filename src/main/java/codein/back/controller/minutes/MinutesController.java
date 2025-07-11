package codein.back.controller.minutes;
import codein.back.biz.domain.minutes.MinutesDTO;
import codein.back.biz.service.minutes.MinutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/minutes")
public class MinutesController {

    @Autowired
    private MinutesService minutesService;

    public Map<String, Object> listMinutes() {
        System.out.println("목록 출력");
        return minutesService.selectAllMinutes();
    }

    // 회의록 등록
    @PostMapping("/insert")
    public Map<String, Object> insertMinutes(@RequestBody MinutesDTO dto) {
        return minutesService.insertMinutes(dto);
    }

    // 회의록 단건 조회
    @PostMapping("/select")
    public Map<String, Object> selectMinutes(@RequestBody MinutesDTO dto) {
        return minutesService.selectMinutes(dto);
    }

    // 회의록 수정
    @PostMapping("/update")
    public Map<String, Object> updateMinutes(@RequestBody MinutesDTO dto) {
        return minutesService.updateMinutes(dto);
    }

    // 회의록 삭제
    @PostMapping("/delete")
    public Map<String, Object> deleteMinutes(@RequestBody MinutesDTO dto) {
        return minutesService.deleteMinutes(dto);
    }
}

