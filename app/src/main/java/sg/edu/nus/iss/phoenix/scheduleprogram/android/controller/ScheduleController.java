package sg.edu.nus.iss.phoenix.scheduleprogram.android.controller;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.CreateScheduleDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.DeleteScheduleDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.ModifyScheduleDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.SchdeuleListScreen;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.ScheduleProgramScreen;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;
import sg.edu.nus.iss.phoenix.user.entity.Producer;

/**
 * Created by thushara on 9/26/2017.
 */

public class ScheduleController {
    private static final String TAG = ScheduleController.class.getName();
    private SchdeuleListScreen scheduleListScreen;
    private ScheduleProgramScreen scheduleProgramScreen;
    private ProgramSlot sp2edit = null;
    private RadioProgram radioProgramSelected;
    private Presenter selectedPresenter;
    private Producer selectedProducer;
    private String reviewSelect;
    public void startUseCase() {
        sp2edit = null;
        radioProgramSelected = null;
        reviewSelect = "";
        selectedPresenter = null;
        selectedProducer = null;
        Intent intent = new Intent(MainController.getApp(), SchdeuleListScreen.class);
        intent.putExtra("copy",false);
        MainController.displayScreen(intent);
    }
    public void onDisplayProgramList(SchdeuleListScreen programListScreen) {
        this.scheduleListScreen = programListScreen;
        new RetrieveScheduleDelegate(this).execute("all");
    }
    public void scheduleRetrieved(List<ProgramSlot> programSlotList) {
        scheduleListScreen.showSchedule(programSlotList);
    }
    public void selectCreateSchedule() {
        sp2edit = null;
        Intent intent = new Intent(MainController.getApp(), ScheduleProgramScreen.class);
        MainController.displayScreen(intent);
    }
    public void selectEditSchedule(ProgramSlot programSlot) {
        sp2edit = programSlot;
        Log.v(TAG, "Editing program Slot : " + programSlot.getRadioProgram() + "...");

        Intent intent = new Intent(MainController.getApp(), ScheduleProgramScreen.class);
/*        Bundle b = new Bundle();
        b.putString("Name", radioProgram.getRadioProgram());
        b.putString("Description", radioProgram.getRadioProgramDescription());
        b.putString("Duration", radioProgram.getRadioProgramDuration());
        intent.putExtras(b);
*/
        MainController.displayScreen(intent);
    }

    public void scheduleCopy() {
        Intent intent = new Intent(MainController.getApp(), SchdeuleListScreen.class);
        intent.putExtra("copy",true);
        MainController.displayScreen(intent);
        // new ModifyScheduleDelegate(this).execute(sp2edit);
    }

    public void reviewSelectProgramSelected(ProgramSlot rpSelected) {
        sp2edit = rpSelected;
        reviewSelect = "true";
    }

    public void reviewSelectRadioProgramSelected(RadioProgram radioProgram) {
        radioProgramSelected = radioProgram;
        reviewSelect = "true";
    }

    // -----
    public void reviewSelectPresenterSelected(Presenter presenterSelected) {
        selectedPresenter = presenterSelected;
    }

    public void reviewSelectProducerSelected(Producer producerSelected) {
        selectedProducer= producerSelected;
    }
    // -----

    public void onDisplayScheduleProgram(ScheduleProgramScreen scheduleProgramScreen) {
        this.scheduleProgramScreen = scheduleProgramScreen;
        if (sp2edit == null){
            scheduleProgramScreen.createSchedule();
            if(radioProgramSelected != null)
                scheduleProgramScreen.selectedRadioProgram(radioProgramSelected);
            if(selectedPresenter != null)
                scheduleProgramScreen.selectedPresenter(selectedPresenter);
            if(selectedProducer != null)
                scheduleProgramScreen.selectedProducer(selectedProducer);
        /*else if(reviewSelect.equalsIgnoreCase("true")) {
            scheduleProgramScreen.selectSchedule(sp2edit);
        }*/
        }
        else {
            scheduleProgramScreen.editSchedule(sp2edit, reviewSelect);
            if (selectedPresenter != null)
                scheduleProgramScreen.selectedPresenter(selectedPresenter);
            if (selectedProducer != null)
                scheduleProgramScreen.selectedProducer(selectedProducer);
        }
    }


    public void selectUpdateScheduleProgram(ProgramSlot programSlot) {
        new ModifyScheduleDelegate(this).execute(programSlot);
    }





    public void scheduleUpdated(boolean success) {
        if(!success){
            scheduleProgramScreen.showErrorALert();
        }else {
            startUseCase();
        }
    }

    public void scheduleCreated(boolean success) {
        // Go back to List screen with refreshed programs.
        if(!success){
            scheduleProgramScreen.showErrorALert();
        }else {
            startUseCase();
        }

    }

    public void selectCancelCreateEditSchedule() {
        // Go back to List screen with refreshed programs.
        startUseCase();
    }

//    public void ScheduleProgram() {
//        ControlFactory.getMainController().maintainedProgram();
//    }

    public void selectCreateSchedule(ProgramSlot programSlot) {
        new CreateScheduleDelegate(this).execute(programSlot);
    }

    public void scheduleUpdated(ProgramSlot sp2edit) {
        new ModifyScheduleDelegate(this).execute(sp2edit);
    }

   /* public void onDisplayProgram(ScheduleProgramScreen scheduleProgramScreen) {
        this.scheduleProgramScreen = scheduleProgramScreen;
        if (sp2edit == null) {
            scheduleProgramScreen.createSchedule();
        }
        else{scheduleProgramScreen.editSchedule(sp2edit,reviewSelect);}

    }*/
    public void maintainedSchedule() {
        ControlFactory.getMainController().maintainSchedule();
    }

    public void selectDeleteSchedule(ProgramSlot programSlot) {
        new DeleteScheduleDelegate(this).execute(programSlot);
    }
    public void scheduleDeleted(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }
}
