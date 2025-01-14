package oncall;

//전체적인 년,일,스케줄 아이디를 받아오기
public class DutySchedulerRequest {

    private final int month;
    private final String startDay;
    private final String weekDateOrder;
    private final String holidayOrder;

    public DutySchedulerRequest(int month, String startDay, String weekDateOrder, String holidayOrder){
        this.month = month;
        this.startDay = startDay;
        this.weekDateOrder = weekDateOrder;
        this.holidayOrder = holidayOrder;
    }

    public int getMonth() { return month; }
    public String getStartDay() { return startDay; }
    public String getWeekDateOrder() { return weekDateOrder; }
    public String getHolidayOrder() { return holidayOrder; }

}
