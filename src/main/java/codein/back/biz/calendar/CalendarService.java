//package codein.back.biz.calendar;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.sql.Date;
//import java.util.*;
//import java.time.LocalDate;
//
//@Service("calendarService")
//public class CalendarService {
//
//    @Autowired
//    private CalendarDAO calendarDAO;
//
//    public CalendarDTO selectOne(String teamId, int year, int month, int date){
//        CalendarRequestDTO dto = new CalendarRequestDTO();
//        dto.setTeamId(teamId);
//        // java.time → java.sql.Date 변환
//        LocalDate firstOfMonth = LocalDate.of(year, month, date);
//        dto.setSearchDate(Date.valueOf(firstOfMonth));
//        return calendarDAO.selectOne(dto);
//    } //일별 조회
//    //select할 때 팀명이랑 연월 세 개 @PathVariable로 받아서 해야할 것 같아요
//
//    public List<CalendarDTO> selectAll(String teamId, int year, int month){
//        CalendarRequestDTO dto = new CalendarRequestDTO();
//        dto.setTeamId(teamId);
//        // java.time → java.sql.Date 변환
//        LocalDate firstOfMonth = LocalDate.of(year, month, 1);
//        dto.setSearchDate(Date.valueOf(firstOfMonth));
//
//        return calendarDAO.selectAll(dto);
//    }//월별 조회
//
//    public boolean insert(CalendarDTO dto){
//        return calendarDAO.insert(dto);
//    }//해당하는 년,월,일의 이벤트 추가, 추가가 완료된 calendar return
//
//    public boolean update(CalendarDTO dto){
//        return calendarDAO.update(dto);
//    }//해당하는 년,월,일의 이벤트 변경, 변경이 완료된 calendar return
//
//    public boolean delete(CalendarDTO dto){
//        return calendarDAO.delete(dto);
//    }//해당하는 년,월,일의 이벤트 삭제, 삭제가 완료된 calendar return
//}
//
