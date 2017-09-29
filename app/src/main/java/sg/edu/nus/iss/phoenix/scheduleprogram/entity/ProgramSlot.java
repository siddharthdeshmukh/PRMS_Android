package sg.edu.nus.iss.phoenix.scheduleprogram.entity;

/**
 * Created by thushara on 9/26/2017.
 */

public class ProgramSlot {
    private String radioProgramName;
    private String scheduleDate;
    private String scheduleDuration;
    private String scheduleStartTime;
    private String presenter;
    private String producer;


    public ProgramSlot(String radioProgramName, String scheduleDate, String scheduleDuration, String scheduleStartTime, String presenter, String producer) {
        this.radioProgramName = radioProgramName;
       this.scheduleDate=scheduleDate;
        this.scheduleDuration=scheduleDuration;
        this.scheduleStartTime=scheduleStartTime;
        this.presenter = presenter;
        this.producer = producer;
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

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
}
