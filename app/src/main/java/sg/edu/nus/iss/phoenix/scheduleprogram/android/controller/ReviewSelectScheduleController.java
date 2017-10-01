package sg.edu.nus.iss.phoenix.scheduleprogram.android.controller;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.ReviewSelectScheduleScreen;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.ScheduleProgramScreen;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;

/**
 * Created by Ritesh on 10/1/2017.
 */

public class ReviewSelectScheduleController {
    // Tag for logging.
    private static final String TAG = ReviewSelectScheduleController.class.getName();

    private ReviewSelectScheduleScreen reviewSelectScheduleScreen;
    private ProgramSlot rpSelected = null;

    public void startUseCase() {
        rpSelected = null;
        Intent intent = new Intent(MainController.getApp(), ReviewSelectScheduleScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplay(ReviewSelectScheduleScreen reviewSelectScheduleScreen) {
        this.reviewSelectScheduleScreen = reviewSelectScheduleScreen;
        new RetrieveScheduleDelegate(this).execute("all");
    }

    public void programsRetrieved(List<ProgramSlot> programSlot) {
        reviewSelectScheduleScreen.showPrograms(programSlot);
    }

    public void selectProgram(ProgramSlot programSlot) {
        rpSelected = programSlot;
        Log.v(TAG, "Selected schedule program: " + programSlot.getRadioProgram() + ".");
        ControlFactory.getScheduleController().reviewSelectProgramSelected(rpSelected);
        Intent intent = new Intent(MainController.getApp(), ScheduleProgramScreen.class);
        MainController.displayScreen(intent);
    }

    public void selectCancel() {
        rpSelected = null;
        Log.v(TAG, "Cancelled the seleciton of radio program.");
        // To call the base use case controller without selection;
        // At present, call the MainController instead.
        ControlFactory.getMainController().selectedProgram(rpSelected);
    }
}
