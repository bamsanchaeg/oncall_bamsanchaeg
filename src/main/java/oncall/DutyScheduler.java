package oncall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

//구현보다는 행위와 책임에 집중하는것이 클래스 설계에 더 도움이 되는 것 같다.
//하나 만드는게 복잡해지면 메서드로 빼는것이 맞다.
public class DutyScheduler {

    public List<String> generateDutySchedule(DutySchedulerRequest request) {
        // 입력값 처리
        int month = request.getMonth();
        String startDay = request.getStartDay();
        List<String> weekdays = Arrays.asList(request.getWeekDateOrder().split(","));
        List<String> holidays = Arrays.asList(request.getHolidayOrder().split(","));

        // 달력 데이터 생성
        int daysInMonth = CalendarUtils.getDaysInMonth(month);
        List<String> daySequence = CalendarUtils.generateDaySequence(startDay, daysInMonth);
        Set<Integer> holidayDates = CalendarUtils.getHolidays(month);

        // 스케줄 리스트 초기화
        List<String> schedule = new ArrayList<>();
        int weekdayIndex = 0;
        int holidayIndex = 0;
        String lastAssigned = null;

        // 스케줄 생성, 스트링 객체에서 == 는 '메모리 주소값을 비교하는 것이고, 값은 equals를 사용한다.
        // 자바의 원시타입 참고
        for (int i = 1; i <= daysInMonth; i++) {
            String day = daySequence.get(i - 1); // 해당 날짜의 요일
            boolean isHoliday = day.equals("토") || day.equals("일") || holidayDates.contains(i);
            String assigned;
            String holidayMark;

            if (isHoliday) {
                // 휴일 근무자 배정
                // holidays와 weekdays 리스트의 크기를 초과하지 않도록 % 리스트 크기를 사용
                assigned = holidays.get(holidayIndex % holidays.size());
                holidayIndex++;
            } else {
                // 평일 근무자 배정
                assigned = weekdays.get(weekdayIndex % weekdays.size());
                weekdayIndex++;
            }

            // 연속 근무 방지 로직
            // 직전 날짜(lastAssigned)와 현재 배정된 근무자(assigned)가 동일할 경우, 다음 순번의 근무자로 변경...
            if (assigned.equals(lastAssigned)) {
                if (isHoliday) {
                    //현재 근무자를 리스트 마지막에서 제거
                    String temp = holidays.remove(holidayIndex % holidays.size());
                    holidays.add(temp); //순서를 뒤로 이동(제거된 근무자를 리스트의 끝으로 추가)
                    assigned = holidays.get(holidayIndex % holidays.size());
                    holidayIndex++; // 다음 순번의 근무자 준비
                } else {
                    String temp = weekdays.remove(weekdayIndex % weekdays.size());
                    weekdays.add(temp); // 순서를 뒤로 이동
                    assigned = weekdays.get(weekdayIndex % weekdays.size());
                    weekdayIndex++;
                }
            }

            lastAssigned = assigned;

            // 휴일 여부 표시
            if (holidayDates.contains(i)) {
                holidayMark = " (휴일)";
            } else {
                holidayMark = "";
            }
            //d는 정수(demical), s는 문자열(String)이다.
            schedule.add(String.format("%d월 %d일 %s %s%s", month, i, day, assigned, holidayMark));
        }

        return schedule;
    }

}
