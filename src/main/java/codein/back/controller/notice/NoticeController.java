package codein.back.controller.notice;


import codein.back.biz.notice.NoticeDTO;
import codein.back.biz.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    //공지 목록
    @GetMapping("/list")
    private ResponseEntity<List<NoticeDTO>> getNotices() {
        List<NoticeDTO> list= noticeService.selectAll(new NoticeDTO());
        return ResponseEntity.ok(list);
    }

    //제목 검색
    @GetMapping("/search")
    public ResponseEntity<List<NoticeDTO>> searchNotices(@RequestParam String keyword) {
        List<NoticeDTO> list = noticeService.searchNotice(keyword);
        return ResponseEntity.ok(list);
    }

    //단건 조회
    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable Integer noticeId) {
        NoticeDTO dto=new NoticeDTO();
        dto.setNoticeId(noticeId);
        NoticeDTO result=noticeService.selectOne(dto);
        if(result==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    //공지 등록
    @PostMapping("/insert")
    public ResponseEntity<String> insertNotice(@RequestBody NoticeDTO dto) {
        boolean result=noticeService.insert(dto);
        return ResponseEntity.ok(result ?"공지 등록 성공":"공지 등록 실패");
    }
    
    //공지 수정
    @PutMapping("/update")
    public ResponseEntity<String> updateNotice(@RequestBody NoticeDTO dto) {
        boolean result=noticeService.update(dto);
        return ResponseEntity.ok(result ?"공지 수정 성공":"공지 수정 실패");
    }

    //공지 삭제
    @DeleteMapping("/delete/{noticeId}")
    public ResponseEntity<String> deleteNotice(@RequestBody int noticeId){
        NoticeDTO dto=new NoticeDTO();
        dto.setNoticeId(noticeId);
        boolean result=noticeService.delete(dto);
        return ResponseEntity.ok(result ?"공지 삭제 성공":"공지 삭제 실패");
    }

}
