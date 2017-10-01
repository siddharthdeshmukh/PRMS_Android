package sg.edu.nus.iss.phoenix.scheduleprogram.entity;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 * Created by thushara on 9/26/2017.
 */

public class ProgramSlot {
    private RadioProgram radioProgram;
    private String scheduleDate;
    private Integer scheduleDuration;
    private String scheduleStartTime;
    private String presenter;
    private String producer;


    public ProgramSlot(RadioProgram radioProgram, String scheduleDate, Integer scheduleDuration, String scheduleStartTime, String presenter, String producer) {
        this.radioProgram = radioProgram;
       this.scheduleDate=scheduleDate;
        this.scheduleDuration=scheduleDuration;
        this.scheduleStartTime=scheduleStartTime;
        this.presenter = presenter;
        this.producer = producer;
    }

    public RadioProgram getRadioProgram() {
        return radioProgram;
    }

    public void setRadioProgram(RadioProgram radioProgram) {
        this.radioProgram = radioProgram;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Integer getScheduleDuration() {
        return scheduleDuration;
    }

    public String getScheduleStartTime() {
        return scheduleStartTime;
    }

    public void setScheduleStartTime(String scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public void setScheduleDuration(Integer scheduleDuration) {
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
