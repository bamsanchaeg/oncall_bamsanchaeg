package oncall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalendarUtils {

    //키가 중복되는 경우, 하나의 키에 여러 값을 저장해야 하므로 Set을 사용해서 해당 월의 공휴일들을 관리한다.
    //Map.of를 사용할시 동일한 키가 중복되면 IllegalArgumentException이 발생한다. Map에서 키는 고유해야 한다는 기본 규칙 때문이다.
    private static final Map<Integer, Set<Integer>> HOLIDAYS = Map.ofEntries(
            Map.entry(1, Set.of(1)),       // 1월 1일
            Map.entry(3, Set.of(1)),       // 3월 1일
            Map.entry(5, Set.of(5)),       // 5월 5일
            Map.entry(6, Set.of(6)),       // 6월 6일
            Map.entry(8, Set.of(15)),      // 8월 15일
            Map.entry(10, Set.of(3, 9)),   // 10월 3일, 9일
            Map.entry(12, Set.of(25))      // 12월 25일
    );

    private static final List<String> WEEK_DAYS = Arrays.asList("일", "월", "화", "수", "목", "금", "토");

    public static int getDaysInMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("월은 1에서 12 사이여야 합니다.");
        }

        // Calendar 인스턴스 생성 (현재 연도 기반)
        Calendar calendar = Calendar.getInstance();

        // 월 설정 (MONTH는 0부터 시작)
        calendar.set(Calendar.MONTH, month - 1);

        // 2월을 28일로 강제 설정
        if (month == 2) {
            return 28;
        }

        // 해당 월의 마지막 날짜 반환
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static List<String> generateDaySequence(String startDay, int daysInMonth) {
        List<String> sequence = new ArrayList<>();
        int startIndex = WEEK_DAYS.indexOf(startDay);

        //요일이 일주일 단위로 순환하기 때문에 7로 나누었다. 월의 날짜마다 요일을 가져간다.
        //인덱스가 7 이상이 되면 다시 0으로 돌아가니 7로 나눈 나머지를 이용해 순환한다.
        for (int i = 0; i < daysInMonth; i++) {
            sequence.add(WEEK_DAYS.get((startIndex + i) % 7));
        }

        return sequence;
    }

    public static Set<Integer> getHolidays(int month) {
        return HOLIDAYS.getOrDefault(month, Set.of());
    }


}
