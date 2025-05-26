package backEnd.tfProject.biz.calendar;

import lombok.Data;
import java.sql.Date;

@Data
public class CalendarRequestDTO {
    private String teamId;
    private Date SearchDate;
}
