package oncall;

import java.util.List;
import oncall.view.InputView;
import oncall.view.OutputView;

// 다섯시간 안에 어느정도 퀄리티를 만드는 것을 객관적으로 아는게 가장 중요하다.
// 리드미를 정말 꼼꼼하게 읽어야 한다.(How to solve 문서와 같이), 앞단에 시간을 많이 투자해야한다.
// 가장 중요한걸 구현하기

public class Application {

    public static void main(String[] args) {

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        DutyScheduler dutyScheduler = new DutyScheduler();
        String[] monthAndDaySplit = inputView.getMonthAndStartDay();
        String weekDayOrder = inputView.getWeekDayOrder();
        String holidayOrder = inputView.getHolidayOrder();

        //입력값 처리 및 스케줄 생성
        int month = Integer.parseInt(monthAndDaySplit[0]);
        String startDay = monthAndDaySplit[1];
        DutySchedulerRequest request = new DutySchedulerRequest(month, startDay, weekDayOrder, holidayOrder);

        //스케줄 생성
        List<String> schedule = dutyScheduler.generateDutySchedule(request);

        // 스케줄 출력
        outputView.printResults(schedule);

    }

}
