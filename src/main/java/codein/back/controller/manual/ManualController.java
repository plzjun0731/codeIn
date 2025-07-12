package codein.back.controller.manual;

import codein.back.biz.domain.manual.ManualDTO;
import codein.back.biz.service.common.SignUpService;
import codein.back.biz.service.manual.ManualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ManualController {

    @Autowired
    private ManualService manualService;

    @GetMapping("/showManual")
    public ResponseEntity<Map<String, Object>> showManual(ManualDTO manualDTO){
        Map<String, Object> response = manualService.showManual(manualDTO);

        System.out.println("ManualController 응답(showManual)");

        return ResponseEntity.ok(response);
    }

    @PostMapping("writeManual")
    public ResponseEntity<Map<String, Object>> writeManual(@RequestBody ManualDTO manualDTO){
        Map<String, Object> response = manualService.writeManual(manualDTO);

        System.out.println("ManualController 응답(writeManual)");

        return ResponseEntity.ok(response);
    }

}
