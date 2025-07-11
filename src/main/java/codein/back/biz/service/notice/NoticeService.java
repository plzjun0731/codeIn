package codein.back.biz.service.notice;

import codein.back.biz.dao.notice.NoticeDAO;
import codein.back.biz.domain.notice.NoticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("noticeService")
public class NoticeService {

    @Autowired
    private NoticeDAO noticeDAO;

    //공지 사항 목록조회
    public List<NoticeDTO> selectAll(NoticeDTO noticeDTO) {
        return noticeDAO.selectAll(noticeDTO);
    }

    //공지 사항 제목 검색
    public List<NoticeDTO> searchNotice(String keyword){
        return noticeDAO.searchNotice(keyword);
    }

    //단건 조회
    @Transactional
    public NoticeDTO selectOne(NoticeDTO noticeDTO) {
        return noticeDAO.selectOne(noticeDTO);
    }

    //공지 등록
    @Transactional
    public boolean insert(NoticeDTO noticeDTO) {
        return noticeDAO.insert(noticeDTO);
    }

    //공지 수정
    @Transactional
    public boolean update(NoticeDTO noticeDTO) {
        return noticeDAO.update(noticeDTO);
    }

    //공지 삭제
    @Transactional
    public boolean delete(NoticeDTO noticeDTO) {
        return noticeDAO.delete(noticeDTO);
    }

}
