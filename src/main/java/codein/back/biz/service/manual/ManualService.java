package codein.back.biz.service.manual;

import codein.back.biz.dao.manual.ManualDAO;
import codein.back.biz.domain.manual.ManualDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("manualService")
public class ManualService {

    @Autowired
    private ManualDAO manualDAO;

    public Map<String, Object> showManual(ManualDTO manualDTO){
        Map<String, Object> response = new java.util.HashMap<>();

        System.out.println("ManualService In 로그 (showManual) = [" + manualDTO + "]");

        ManualDTO result = manualDAO.selectOne(manualDTO);

        if (result == null) {
            response.put("status", 1);
            return response;
        }

        response.put("status", 0);
        response.put("manual", result);  // Map 형태로 변환된 데이터 전달

        System.out.println("ManualService Out 로그 (showManual)");

        return response;
    }

    public Map<String, Object> writeManual(ManualDTO manualDTO){
        Map<String, Object> response = new java.util.HashMap<>();

        System.out.println("ManualService In로그 (writeManual) = ["+manualDTO+"]");

        boolean result = manualDAO.update(manualDTO);

        if (!result) { // 응답 실패
            response.put("status",0);
            return response;
        }

        response.put("status",1); // 응답 성공
        return response;
    }

}
