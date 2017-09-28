package sg.edu.nus.iss.phoenix.scheduleprogram.entity;

/**
 * Created by thushara on 9/26/2017.
 */

public class ProgramSlot {
    private String radioProgramName;
    private String scheduleDate;
    private String scheduleDuration;
    private String scheduleStartTime;

    public ProgramSlot(String radioProgramName, String scheduleDate, String scheduleDuration, String scheduleStartTime) {
        this.radioProgramName = radioProgramName;
       this.scheduleDate=scheduleDate;
        this.scheduleDuration=scheduleDuration;
        this.scheduleStartTime=scheduleStartTime;
    }

    public String getRadioProgramName() {
        return radioProgramName;
    }

    public void setRadioProgramName(String radioProgramName) {
        this.radioProgramName = radioProgramName;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleDuration() {
        return scheduleDuration;
    }

    public String getScheduleStartTime() {
        return scheduleStartTime;
    }

    public void setScheduleStartTime(String scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public void setScheduleDuration(String scheduleDuration) {
        this.scheduleDuration = scheduleDuration;
    }
}
