package backEnd.tfProject.controller.manual;

import backEnd.tfProject.biz.manual.ManualDTO;
import backEnd.tfProject.biz.manual.ManualService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tf")
public class WriteManualController {

  @Autowired
  private ManualService manualService;

  @PostMapping("/manual")
  public String writeManual(ManualDTO manualDTO, HttpSession session){
    return null;
  }

}
