package sg.edu.nus.iss.phoenix.core.android.controller;

import android.app.Application;
import android.content.Intent;

import sg.edu.nus.iss.phoenix.core.android.ui.MainScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.user.entity.User;

public class MainController {
    private static Application app = null;
    private User user;
    private MainScreen mainScreen;

    public static Application getApp() {
        return app;
    }

    public static void setApp(Application app) {
        MainController.app = app;
    }

    public static void displayScreen(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        app.startActivity(intent);
    }

    public void startUseCase(User user) {
        this.user = user;

        Intent intent = new Intent(MainController.getApp(), MainScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplay(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        mainScreen.showUsername(user.getId());
        mainScreen.enableDisableButtons(user.getRoles());
    }

    public void selectMaintainProgram() {
        ControlFactory.getProgramController().startUseCase();
    }

    public void maintainedProgram() {
        startUseCase(user);
    }

    public void selectLogout() {
        user.setId("<not logged in>");
        ControlFactory.getLoginController().logout();
    }

    public void selectMaintainSchedule() {
        ControlFactory.getScheduleController().startUseCase();
    }

    // This is a dummy operation to test the invocation of Review Select Radio Program use case.
    public void selectedProgram(RadioProgram rpSelected) {
        startUseCase(user);
    }

    public void selectedProgram(ProgramSlot rpSelected) {
        startUseCase(user);
    }
    public void selectMaintainUser(){
        ControlFactory.getUserController().startUseCase();
    }
    public void maintainedUser() {
        startUseCase(user);
    }

    public void maintainSchedule() {
        startUseCase(user);
    }
}
