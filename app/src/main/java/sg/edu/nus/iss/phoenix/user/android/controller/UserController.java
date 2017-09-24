package sg.edu.nus.iss.phoenix.user.android.controller;


import android.content.Intent;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.user.android.delegate.CreateUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.DeleteUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.RetrieveUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.UpdateUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.ui.UserListScreen;
import sg.edu.nus.iss.phoenix.user.android.ui.MaintainUserScreen;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by siddharth on 9/21/2017.
 */

public class UserController {

    private static final String TAG = UserController.class.getName();

    private UserListScreen userListScreen;
    private MaintainUserScreen userScreen;
    private User editedUser;

    public void startUseCase() {
        editedUser = null;
        Intent intent = new Intent(MainController.getApp(), UserListScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayUserList(UserListScreen userListScreen) {
        this.userListScreen = userListScreen;
        new RetrieveUserDelegate(this).execute("all");
    }

    public void userRetrieved(List<User> userList){
         userListScreen.showUser(userList);
    }

    public void selectEditUser(User user){
        editedUser= user;
        Intent intent = new Intent(MainController.getApp(), MaintainUserScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayUser(MaintainUserScreen maintainUserScreen) {
        this.userScreen = maintainUserScreen;
        if (editedUser == null)
            maintainUserScreen.createUser();
        else
            maintainUserScreen.editUser(editedUser);
    }

    public void selectCreateUser() {
        editedUser = null;
        Intent intent = new Intent(MainController.getApp(), MaintainUserScreen.class);
        MainController.displayScreen(intent);
    }

    public void userCreated(boolean success) {
        startUseCase();
    }
    public void selectCreateUser(User user){
        new CreateUserDelegate(this).execute(user);
    }

    public void selectUpdateUser(User user){
        new UpdateUserDelegate(this).execute(user);
    }
    public void userUpdated(boolean success){
        startUseCase();
    }
    public void selectCancelCreateEditUser() {
        startUseCase();
    }
    public void selectDeleteUser(User user) {
        new DeleteUserDelegate(this).execute(user.getId());
    }
    public void userDeleted(boolean success) {
        startUseCase();
    }
    public void maintainedUser() {
        ControlFactory.getMainController().maintainedUser();
    }
}
