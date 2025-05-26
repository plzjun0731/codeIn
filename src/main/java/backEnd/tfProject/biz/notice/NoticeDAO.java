package backEnd.tfProject.biz.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("noticeDAO")
public class NoticeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
}
