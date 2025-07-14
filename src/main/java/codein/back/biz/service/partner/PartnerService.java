package codein.back.biz.service.partner;

import codein.back.biz.dao.partner.PartnerDAO;
import codein.back.biz.domain.partner.PartnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("partnerService")
public class PartnerService {

    @Autowired
    private PartnerDAO partnerDAO;

    public Map<String, Object> partnerList(PartnerDTO partnerDTO){
        Map<String, Object> response = new java.util.HashMap<>();

        System.out.println("PartnerService In로그(partnerList) = [" + partnerDTO + "]");

        List<PartnerDTO> result = partnerDAO.selectAll(partnerDTO);

        if (result == null) {
            response.put("status", 1);
            return response;
        }

        response.put("status", 0);
        response.put("partnerList", result);

        System.out.println("partnerService Out 로그 (partnerList)");

        return response;
    }

    public Map<String, Object> insertPartner(PartnerDTO partnerDTO){
        Map<String, Object> response = new java.util.HashMap<>();

        System.out.println("PartnerService In로그 (insertPartner) = [" + partnerDTO + "]");

        boolean result = partnerDAO.insert(partnerDTO);

        if(!result) {
            response.put("status", 0);
            return response;
        }

        response.put("status", 1);
        return response;
    }

    public Map<String, Object> updatePartner(PartnerDTO partnerDTO,
                                             MultipartFile noticeImg1,
                                             MultipartFile noticeImg2,
                                             MultipartFile noticeImg3
    ){
        Map<String, Object> response = new java.util.HashMap<>();

        System.out.println("PartnerService In로그 (updatePartner) = [" + partnerDTO + "]");

        try {
            if (noticeImg1 != null && !noticeImg1.isEmpty()) {
                String filePath1 = saveFile(noticeImg1); // 파일 저장
                partnerDTO.setNoticeImg1(filePath1);     // 경로를 DTO에 set
            }
            if (noticeImg2 != null && !noticeImg2.isEmpty()) {
                String filePath2 = saveFile(noticeImg2);
                partnerDTO.setNoticeImg2(filePath2);
            }
            if (noticeImg3 != null && !noticeImg3.isEmpty()) {
                String filePath3 = saveFile(noticeImg3);
                partnerDTO.setNoticeImg3(filePath3);
            }
        } catch (Exception e) {
            response.put("status", 0);
            response.put("message", "파일 저장 중 오류 발생");
            return response;
        }

        boolean result = partnerDAO.update(partnerDTO);
        System.out.println("PartnerService Out로그 (updatePartner) = [" + result + "]");

        if(!result) {
            response.put("status", 0);
            return response;
        }

        response.put("status", 1);
        return response;
    }

    public Map<String, Object> deletePartner(PartnerDTO partnerDTO){
        Map<String, Object> response = new java.util.HashMap<>();

        System.out.println("PartnerService In로그 (deletePartner) = [" + partnerDTO + "]");

        boolean result = partnerDAO.delete(partnerDTO);

        if(!result) {
            response.put("status", 0);
            return response;
        }

        response.put("status", 1);
        return response;
    }

    private String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "C:/java/tf-back/src/main/resources/static/uploads"; // 실제 서버에 맞게 경로 지정
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir, fileName);
        file.transferTo(dest);
        return "/uploads/" + fileName; // 서버 static 매핑 경로
    }

}
