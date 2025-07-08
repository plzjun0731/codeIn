package codein.back.controller.manual;

import codein.back.biz.manual.ManualDTO;
import codein.back.biz.manual.ManualService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WriteManualController {

  @Autowired
  private ManualService manualService;

  @PostMapping("/manual")
  public ResponseEntity<Map<String, Object>> writeManual(ManualDTO manualDTO){

    Map<String, Object> response = new HashMap<>();

    if (!manualService.insert(manualDTO)) {
      response.put("status",0);
      return ResponseEntity.ok(response);
    }

    response.put("status",1);
    return ResponseEntity.ok(response);

  }

}
