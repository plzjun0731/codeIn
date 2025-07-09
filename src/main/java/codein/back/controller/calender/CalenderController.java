//package codein.back.controller.calender;
//
//import codein.back.biz.calendar.CalendarDTO;
//import codein.back.biz.calendar.CalendarService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//public class CalenderController {
//
//    private final CalendarService calendarService;
//
//    @GetMapping("/main/team/{teamId}/calendar?year={year)&month={month}&date={date}")
//    public ResponseEntity<CalendarDTO> selectOneCalendar(@PathVariable String teamId, @RequestParam int year, @RequestParam int month, @RequestParam int date) {
//        CalendarDTO calendar = calendarService.selectOne(teamId, year, month, date);
//        return ResponseEntity.ok(calendar);
//    }
//
//    @GetMapping("/main/team/{teamId}/calendar?year={year}&month={month}")
//    public ResponseEntity<List<CalendarDTO>> selectAllCalendar(@PathVariable String teamId, @RequestParam int year, @RequestParam int month) {
//        List<CalendarDTO> calendar = calendarService.selectAll(teamId, year, month);
//        //이렇게 List로 넣으면 json이 나열된 형태로 직렬화돼서 return 될 수 있나?
//        return ResponseEntity.ok(calendar);
//    }
//
//    @PostMapping("/main/team/{teamId}/calendar") //create 메서드
//    public boolean insertCalendar(@PathVariable String teamId, @RequestBody CalendarDTO dto) {
//        dto.setTeamId(teamId);
//        boolean tf = calendarService.insert(dto);
//        return tf;
//    }
//
//    @PutMapping("/main/team/{teamId}/calendar") //update 메서드
//    public boolean updateCalendar(@PathVariable String teamId, @RequestBody CalendarDTO dto) {
//        dto.setTeamId(teamId);
//        boolean tf = calendarService.update(dto);
//        return tf;
//    }
//
//    @DeleteMapping("/main/team/{teamId}/calendar") //delete 메서드
//    public boolean deleteCalendar(@PathVariable String teamId, @RequestBody CalendarDTO dto) {
//        dto.setTeamId(teamId);
//        boolean tf = calendarService.delete(dto);
//        return tf;
//    }
//    //tf가 true면 GetMapping으로 selectAll을 해서 캘린더 불러오기 (이건 프론트가 하는걸로 하는건가 마는건가)
//    //tf가 false면?
//}
