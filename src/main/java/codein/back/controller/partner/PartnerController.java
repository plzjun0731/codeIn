package codein.back.controller.partner;

import codein.back.biz.domain.partner.PartnerDTO;
import codein.back.biz.service.partner.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @GetMapping("/partnerList")
    public ResponseEntity<Map<String, Object>> partnerList(PartnerDTO partnerDTO) {
        Map<String, Object> response = partnerService.partnerList(partnerDTO);
        System.out.println("PartnerController 응답(partnerList)");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/insertPartner")
    public ResponseEntity<Map<String, Object>> insertPartner(@RequestBody PartnerDTO partnerDTO) {
        Map<String, Object> response = partnerService.insertPartner(partnerDTO);

        System.out.println("PartnerController 응답(insertPartner)");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/updatePartner")
    public ResponseEntity<Map<String, Object>> updatePartner(
            @RequestPart("partnerDTO") PartnerDTO partnerDTO,
            @RequestPart(value = "noticeImg1", required = false) MultipartFile noticeImg1,
            @RequestPart(value = "noticeImg2", required = false) MultipartFile noticeImg2,
            @RequestPart(value = "noticeImg3", required = false) MultipartFile noticeImg3
    )  {
        Map<String, Object> response = partnerService.updatePartner(partnerDTO, noticeImg1, noticeImg2, noticeImg3);
        System.out.println("PartnerController 응답(updatePartner)");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/deletePartner")
    public ResponseEntity<Map<String, Object>> deletePartner(@RequestBody PartnerDTO partnerDTO) {
        Map<String, Object> response = partnerService.deletePartner(partnerDTO);

        System.out.println("PartnerController 응답(deletePartner)");

        return ResponseEntity.ok(response);
    }

}
