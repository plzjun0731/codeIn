package codein.back.biz.dao.meetingLog;

import codein.back.biz.domain.meetingLog.MeetingLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("meetingLogDAO")
public class MeetingLogDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //회의록 목록
    private static final String LIST_MEETINGLOG=
            "SELECT MEETINGLOG_TITLE, MEETINGLOG_WRITER, MEETINGLOG_ID"+
                    "FROM BOARD_MEETINGLOG"+
                    "ORDER BY MEETINGLOG_DATE";

    // 회의록 등록
    private static final String INSERT_MEETINGLOG =
            "INSERT INTO BOARD_MEETINGLOG(MEETINGLOG_TITLE, MEETINGLOG_DATE, MEETINGLOG_WRITER, MEETINGLOG_SUBJECT, " +
                    "MEETINGLOG_PLACE, MEETINGLOG_ABSENTEE, MEETINGLOG_JOINER, MEETINGLOG_CONTENT, " +
                    "MEETINGLOG_RESULT, MEETINGLOG_NEXT, MEETINGLOG_WorkDate) " +
                    "VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // 팔로우업 등록
    private static final String INSERT_FOLLOWUP =
            "INSERT INTO BOARD_FOLLOWUP(FOLLOWUP_WORK, FOLLOWUP_PERFORMER, FOLLOWUP_DEADLINE, MEETINGLOG_ID) " +
                    "VALUES (?, ?, ?, ?)";

    // 회의록 단건 조회
    private static final String SELECT_MEETINGLOG =
            "SELECT * FROM BOARD_MEETINGLOG WHERE MEETINGLOG_ID = ?";

    // 팔로우업 목록 조회
    private static final String SELECT_FOLLOWUPS =
            "SELECT * FROM BOARD_FOLLOWUP WHERE MEETINGLOG_ID = ? ORDER BY FOLLOWUP_ID ASC";


    // 회의록 수정
    private static final String UPDATE_MEETINGLOG =
            "UPDATE BOARD_MEETINGLOG SET " +
                    "MEETINGLOG_TITLE = ?, MEETINGLOG_SUBJECT = ?, MEETINGLOG_PLACE = ?, " +
                    "MEETINGLOG_ABSENTEE = ?, MEETINGLOG_JOINER = ?, MEETINGLOG_CONTENT = ?, " +
                    "MEETINGLOG_RESULT = ?, MEETINGLOG_NEXT = ?, MEETINGLOG_WORKDATE = ?, MEETINGLOG_DATE = NOW() " +
                    "WHERE MEETINGLOG_ID = ?";

    // 기존 팔로우업 전체 삭제
    private static final String DELETE_FOLLOWUP_BY_MEETINGLOG_ID =
            "DELETE FROM BOARD_FOLLOWUP WHERE MEETINGLOG_ID = ?";

    // 회의록 삭제
    private static final String DELETE_MEETINGLOG =
            "DELETE FROM BOARD_MEETINGLOG WHERE MEETINGLOG_ID = ?";

// 회의록 목록 조회
    @Transactional
    public List<MeetingLogDTO> selectAllMeetingLog() {
        try {
            List<MeetingLogDTO> list= jdbcTemplate.query(LIST_MEETINGLOG, new MeetingLogRowMapper());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Transactional
    public boolean insert(MeetingLogDTO dto) {
        try{

            //회의록 저장
            jdbcTemplate.update(INSERT_MEETINGLOG,
                    dto.getMeetingLogTitle(),
                    dto.getMeetingLogSubject(),
                    dto.getMeetingLogPlace(),
                    dto.getMeetingLogAbsentee(),
                    dto.getMeetingLogJoiner(),
                    dto.getMeetingLogContent(),
                    dto.getMeetingLogResult(),
                    dto.getMeetingLogNext(),
                    dto.getMeetingLogWorkDate());

            Integer newMeetingLogId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);


            // 팔로우업 저장
            if (dto.getFollowUpWork() != null) {
                for (int i = 0; i < dto.getFollowUpWork().size(); i++) {
                    jdbcTemplate.update(INSERT_FOLLOWUP,
                            dto.getFollowUpWork().get(i),
                            dto.getFollowUpPerformer().get(i),
                            dto.getFollowUpDeadLine().get(i),
                            newMeetingLogId);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //회의록 단건 조회
    @Transactional
    public MeetingLogDTO selectMeetingLog(MeetingLogDTO dto) {
        try {
            MeetingLogDTO result = jdbcTemplate.queryForObject(SELECT_MEETINGLOG, new MeetingLogRowMapper(), dto.getMeetingLogId());
            List<FollowUpRow> followUps = jdbcTemplate.query(SELECT_FOLLOWUPS, new FollowUpRowMapper(), dto.getMeetingLogId());

            ArrayList<String> works = new ArrayList<>();
            ArrayList<String> performers = new ArrayList<>();
            ArrayList<String> deadlines = new ArrayList<>();

            for (FollowUpRow row : followUps) {
                works.add(row.work);
                performers.add(row.performer);
                deadlines.add(row.deadline);
            }

            result.setFollowUpWork(works);
            result.setFollowUpPerformer(performers);
            result.setFollowUpDeadLine(deadlines);
            return dto;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //회의록 수정
    @Transactional
    public boolean updateMeetingLog(MeetingLogDTO dto) {
        try {
            int updated = jdbcTemplate.update(UPDATE_MEETINGLOG,
                    dto.getMeetingLogTitle(),
                    dto.getMeetingLogSubject(),
                    dto.getMeetingLogPlace(),
                    dto.getMeetingLogAbsentee(),
                    dto.getMeetingLogJoiner(),
                    dto.getMeetingLogContent(),
                    dto.getMeetingLogResult(),
                    dto.getMeetingLogNext(),
                    dto.getMeetingLogWorkDate());

            // 기존 팔로우업 삭제
            jdbcTemplate.update(DELETE_FOLLOWUP_BY_MEETINGLOG_ID, dto.getMeetingLogId());

            // 새 팔로우업 삽입
            if (dto.getFollowUpWork() != null) {
                for (int i = 0; i < dto.getFollowUpWork().size(); i++) {
                    jdbcTemplate.update(INSERT_FOLLOWUP,
                            dto.getFollowUpWork().get(i),
                            dto.getFollowUpPerformer().get(i),
                            dto.getFollowUpDeadLine().get(i),
                            dto.getMeetingLogId());
                }
            }

            return updated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //회의록 삭제
    @Transactional
    public boolean deleteMeetingLog(MeetingLogDTO dto) {
        try {
            int id = dto.getMeetingLogId();

            // 팔로우업 먼저 삭제
            jdbcTemplate.update(DELETE_FOLLOWUP_BY_MEETINGLOG_ID, id);

            // 회의록 삭제
            int deleted = jdbcTemplate.update(DELETE_MEETINGLOG, id);

            return deleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}


class MeetingLogRowMapper implements RowMapper<MeetingLogDTO> {
    @Override
    public MeetingLogDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MeetingLogDTO dto = new MeetingLogDTO();
        dto.setMeetingLogId(rs.getInt("MEETINGLOG_ID"));
        dto.setMeetingLogTitle(rs.getString("MEETINGLOG_TITLE"));
        dto.setMeetingLogDate(rs.getString("MEETINGLOG_DATE"));
        dto.setMeetingLogWriter(rs.getString("MEETINGLOG_WRITER"));
        dto.setMeetingLogSubject(rs.getString("MEETINGLOG_SUBJECT"));
        dto.setMeetingLogPlace(rs.getString("MEETINGLOG_PLACE"));
        dto.setMeetingLogAbsentee(rs.getString("MEETINGLOG_ABSENTEE"));
        dto.setMeetingLogJoiner(rs.getString("MEETINGLOG_JOINER"));
        dto.setMeetingLogContent(rs.getString("MEETINGLOG_CONTENT"));
        dto.setMeetingLogResult(rs.getString("MEETINGLOG_RESULT"));
        dto.setMeetingLogNext(rs.getString("MEETINGLOG_NEXT"));
        dto.setMeetingLogWorkDate(rs.getString("MEETINGLOG_WORKDATE"));

        return dto;
    }
}

class FollowUpRowMapper implements RowMapper<FollowUpRow> {
    @Override
    public FollowUpRow mapRow(ResultSet rs, int rowNum) throws SQLException {
        FollowUpRow row = new FollowUpRow();
        row.work = rs.getString("FOLLOWUP_WORK");
        row.performer = rs.getString("FOLLOWUP_PERFORMER");
        row.deadline = rs.getString("FOLLOWUP_DEADLINE");
        return row;
    }
}

class FollowUpRow {
    String work;
    String performer;
    String deadline;
}

