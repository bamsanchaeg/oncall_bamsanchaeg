package oncall.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Set;
import java.util.regex.Pattern;
import oncall.view.input.ConsoleReader;
import oncall.view.input.Reader;
import oncall.view.output.ConsoleWriter;
import oncall.view.output.Writer;

public class InputView {

    private static final Reader DEFAULT_READER = new ConsoleReader();
    private static final Writer DEFAULT_WRITER = new ConsoleWriter();

    private final Reader reader;
    private final Writer writer;

    public InputView(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public InputView() {
        this(DEFAULT_READER, DEFAULT_WRITER);
    }


    // 월과 시작 요일 입력
    public String[] getMonthAndStartDay() {
        while (true) {
            try {
                System.out.print("비상 근무를 배정할 월과 시작 요일을 입력하세요> ");
                String input = Console.readLine();
                String[] monthAndDaySplit = input.split(",");

                // 유효성 검사 호출
                validationDate(monthAndDaySplit);

                // 입력값이 유효하다면 반환
                return monthAndDaySplit;

            } catch (IllegalArgumentException e) {
                // 예외 메시지를 출력하고 다시 입력 요청
                System.out.println("[ERROR] " + e.getMessage() + " 다시 입력해주세요.");
            }
        }
    }


    public String getWeekDayOrder() {
        while (true) {
            System.out.print("평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
            String input = Console.readLine();
            return input;
        }
    }

    public String getHolidayOrder() {
        while (true) {
            System.out.print("휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
            String input = Console.readLine();
            return input;
        }
    }

    private void validationDate(String[] monthAndDaySplit) {

        // 입력값 배열이 두 개의 요소(월, 요일)로 나뉘어야 함
        if (monthAndDaySplit.length != 2) {
            throw new IllegalArgumentException("입력 형식이 잘못되었습니다. 예: '5,월'");
        }

        try {
            // 월 검증
            int month = Integer.parseInt(monthAndDaySplit[0]);
            if (month < 1 || month > 12) {
                throw new IllegalArgumentException("월은 1에서 12 사이의 값을 입력해야 합니다.");
            }

            // 요일 검증
            String startDay = monthAndDaySplit[1];
            Set<String> validDays = Set.of("일", "월", "화", "수", "목", "금", "토");
            if (!validDays.contains(startDay)) {
                throw new IllegalArgumentException("유효한 요일(일, 월, 화, 수, 목, 금, 토)을 입력해야 합니다.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("월은 숫자로 입력해야 합니다.");
        }

    }

}
