package sg.edu.nus.iss.phoenix.scheduleprogram.entity;

import java.util.Date;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 * Created by thushara on 9/26/2017.
 */

public class ProgramSlot {
    private RadioProgram radioProgram;
    private Date scheduleDate;
    private Integer scheduleDuration;
    private Date scheduleStartTime;
    private String presenter;
    private String producer;


    public ProgramSlot(RadioProgram radioProgram, Date scheduleDate, Integer scheduleDuration, Date scheduleStartTime, String presenter, String producer) {
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

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Integer getScheduleDuration() {
        return scheduleDuration;
    }

    public Date getScheduleStartTime() {
        return scheduleStartTime;
    }

    public void setScheduleStartTime(Date scheduleStartTime) {
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
