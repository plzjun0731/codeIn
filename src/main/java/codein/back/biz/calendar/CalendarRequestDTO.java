package codein.back.biz.calendar;

import lombok.Data;
import java.sql.Date;

@Data
public class CalendarRequestDTO {
    private String teamId;
    private Date SearchDate;
}
