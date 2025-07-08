package codein.back.biz.calendar;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class CalendarDTO {

    //team별 달력 보여줘야해서 teamId도 DB에 column 추가 예정 (임시적으로 추가)
    private String teamId;
    private Long eventId;
    private String eventTitle;
    private String eventContent;
    private int eventCategory;
    private int eventStatus;
    private Date eventStartDate;
    private Date eventEndDate;
    private String eventWriter;
    private LocalDateTime eventCreatedDate;
}
