package codein.back.controller.manual;

import codein.back.biz.manual.ManualDTO;
import codein.back.biz.manual.ManualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ManualController {

    @Autowired
    private ManualService manualService;

    @GetMapping("/manual")
    public ResponseEntity<Map<String, Object>> manual(ManualDTO manualDTO, Model model) {

        Map<String, Object> response = new HashMap<>();

        ManualDTO result = manualService.selectOne(manualDTO);

        response.put("manual",result);
        return ResponseEntity.ok(response);

    }

}
