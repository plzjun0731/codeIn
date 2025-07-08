package codein.back.biz.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("calendarDAO")
public class CalendarDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_CALENDAR =
            "SELECT * FROM CALENDAR_EVENT WHERE EVENT_WRITER = ? AND YEAR(EVENT_START_DATE) = ? AND MONTH(EVENT_START_DATE) = ?";

    private static final String SELECT_ALL_CALENDAR = "SELECT * FROM CALENDAR_EVENT" +
            " WHERE EVENT_WRITER = ?" +
            "   AND (" +
            "        (YEAR(EVENT_START_DATE) = ? AND MONTH(EVENT_START_DATE) = ?)" +
            "     OR (YEAR(EVENT_END_DATE)   = ? AND MONTH(EVENT_END_DATE)   = ?)" +
            "   )";

    private static final String INSERT_CALENDAR =
            "INSERT INTO CALENDAR_EVENT (EVENT_TITLE, EVENT_CONTENT, EVENT_CATEGORY, EVENT_STATUS, EVENT_START_DATE, EVENT_END_DATE, EVENT_WRITER) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_CALENDAR =
            "UPDATE CALENDAR_EVENT SET EVENT_TITLE=?, EVENT_CONTENT=?, EVENT_CATEGORY=?, EVENT_STATUS=?, EVENT_START_DATE=?, EVENT_END_DATE=? WHERE EVENT_ID=?";

    private static final String DELETE_CALENDAR =
            "DELETE FROM CALENDAR_EVENT WHERE EVENT_ID=?";

    private static final String SELECT_CALENDAR_ONE_EXACT =
            "SELECT * FROM CALENDAR_EVENT " +
                    " WHERE EVENT_WRITER    = ? " +
                    "   AND EVENT_START_DATE = ?";

    @Transactional
    public List<CalendarDTO> selectAll(CalendarRequestDTO req) {
        try {
            Object[] args = {
                    req.getTeamId(),
                    req.getSearchDate().toLocalDate().getYear(),
                    req.getSearchDate().toLocalDate().getMonthValue(),
                    req.getSearchDate().toLocalDate().getYear(),
                    req.getSearchDate().toLocalDate().getMonthValue()
            };
            return jdbcTemplate.query(SELECT_ALL_CALENDAR, args, new CalendarRowMapper());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public CalendarDTO selectOne(CalendarRequestDTO req) {
        try {
            Object[] args = {
                    req.getTeamId(),
                    req.getSearchDate()   // java.sql.Date, year/month/date 모두 포함
            };
            return jdbcTemplate.queryForObject(
                    SELECT_CALENDAR_ONE_EXACT,
                    args,
                    new CalendarRowMapper()
            );
        } catch (Exception e) {
            // 조회된 결과가 없으면 null 리턴
            return null;
        }
    }


    @Transactional
    public boolean insert(CalendarDTO dto) {
        try {
            Object[] args = {
                dto.getEventTitle(),
                dto.getEventContent(),
                dto.getEventCategory(),
                dto.getEventStatus(),
                dto.getEventStartDate(),
                dto.getEventEndDate(),
                dto.getEventWriter()
            };
            int rows = jdbcTemplate.update(INSERT_CALENDAR, args);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean update(CalendarDTO dto) {
        try {
            Object[] args = {
                    dto.getEventTitle(),
                    dto.getEventContent(),
                    dto.getEventCategory(),
                    dto.getEventStatus(),
                    dto.getEventStartDate(),
                    dto.getEventEndDate(),
                    dto.getEventId()
            };
            int rows = jdbcTemplate.update(UPDATE_CALENDAR, args);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean delete(CalendarDTO dto) {
        try {
            int rows = jdbcTemplate.update(DELETE_CALENDAR, dto.getEventId());
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

class CalendarRowMapper implements RowMapper<CalendarDTO> {
    @Override
    public CalendarDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CalendarDTO dto = new CalendarDTO();
        dto.setEventId(rs.getLong("EVENT_ID"));
        dto.setEventTitle(rs.getString("EVENT_TITLE"));
        dto.setEventContent(rs.getString("EVENT_CONTENT"));
        dto.setEventCategory(rs.getInt("EVENT_CATEGORY"));
        dto.setEventStatus(rs.getInt("EVENT_STATUS"));
        dto.setEventStartDate(rs.getDate("EVENT_START_DATE"));
        dto.setEventEndDate(rs.getDate("EVENT_END_DATE"));
        dto.setEventWriter(rs.getString("EVENT_WRITER"));
        dto.setEventCreatedDate(rs.getTimestamp("EVENT_CREATED_DATE").toLocalDateTime());
        return dto;
    }
} //selectOne 제외 모든 메서드들