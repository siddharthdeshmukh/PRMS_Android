package sg.edu.nus.iss.phoenix.user.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by siddharth on 9/21/2017.
 */

public class MaintainUserScreen extends AppCompatActivity {
    private static final String TAG = MaintainUserScreen.class.getName();
    private EditText nameEditText;
    private EditText idEditText;
    private EditText pwdEditText;
    private User user;
    private CheckBox adminCheckBox;
    private CheckBox managerCheckBox;
    private CheckBox presenterCheckBox;
    private CheckBox producerCheckBox;
    private ArrayList<Role> roleList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        nameEditText = (EditText) findViewById(R.id.maintain_user_name_text_view);
        idEditText = (EditText) findViewById(R.id.maintain_user_id_text_view);
        pwdEditText = (EditText) findViewById(R.id.maintain_user_password_text_view);
        adminCheckBox = (CheckBox)findViewById(R.id.admin_checkbox);
        managerCheckBox = (CheckBox)findViewById(R.id.manager_checkBox);
        presenterCheckBox = (CheckBox)findViewById(R.id.presenter_checkBox);
        producerCheckBox = (CheckBox)findViewById(R.id.producer_checkBox);
        /*adminCheckBox.setOnClickListener(OnCheckBoxClick(adminCheckBox));
        managerCheckBox.setOnClickListener(OnCheckBoxClick(managerCheckBox));
        presenterCheckBox.setOnClickListener(OnCheckBoxClick(presenterCheckBox));
        producerCheckBox.setOnClickListener(OnCheckBoxClick(producerCheckBox));*/

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getUserController().onDisplayUser(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (user == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                if (user == null) { // Newly created.
                    if(adminCheckBox.isChecked()){
                        roleList.add(new Role(Role.ADMIN));
                    }
                    if(managerCheckBox.isChecked()){
                        roleList.add(new Role(Role.MANAGER));
                    }
                    if(presenterCheckBox.isChecked()){
                        roleList.add(new Role(Role.PRESENTER));
                    }
                    if(producerCheckBox.isChecked()){
                        roleList.add(new Role(Role.PRODUCER));
                    }
                    User newUser = new User(idEditText.getText().toString(),nameEditText.getText().toString(),
                            pwdEditText.getText().toString(),roleList);

                    ControlFactory.getUserController().selectCreateUser(newUser);
                }
                else { // Edited.
                    user.setId(idEditText.getText().toString());
                    user.setName(nameEditText.getText().toString());
                    user.setPassword(pwdEditText.getText().toString());
                    if(adminCheckBox.isChecked()){
                        roleList.add(new Role(Role.ADMIN));
                    }
                    if(managerCheckBox.isChecked()){
                        roleList.add(new Role(Role.MANAGER));
                    }
                    if(presenterCheckBox.isChecked()){
                        roleList.add(new Role(Role.PRESENTER));
                    }
                    if(producerCheckBox.isChecked()){
                        roleList.add(new Role(Role.PRODUCER));
                    }
                    user.setRoles(roleList);
                    ControlFactory.getUserController().selectUpdateUser(user);
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                ControlFactory.getUserController().selectDeleteUser(user);
                return true;
            // Respond to a click on the "Cancel" menu option
            case R.id.action_cancel:
                ControlFactory.getUserController().selectCancelCreateEditUser();
                return true;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        ControlFactory.getUserController().selectCancelCreateEditUser();
    }

    public void createUser() {
        this.user = null;

    }

    public void editUser(User editUser) {
        this.user = editUser;
        if (editUser != null) {
            nameEditText.setText(editUser.getName());
            idEditText.setText(editUser.getId());
            pwdEditText.setText(editUser.getPassword());
            if(!editUser.getRoles().isEmpty()){
                List<Role> roleList = editUser.getRoles();
                for(Role role:roleList){
                    switch (role.getRole()) {
                        case Role.ADMIN:
                            adminCheckBox.setChecked(true);
                            break;
                        case Role.MANAGER:
                            managerCheckBox.setChecked(true);
                            break;
                        case Role.PRESENTER:
                            presenterCheckBox.setChecked(true);
                            break;
                        case Role.PRODUCER:
                            producerCheckBox.setChecked(true);
                            break;
                        default:
                            return;

                    }

                }
            }
        }
    }

    /*View.OnClickListener OnCheckBoxClick(final View view){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "View id is "+ view.getId());
                switch(view.getId()) {
                    case R.id.admin_checkbox:
                        Log.v(TAG, "View  is called");
                        roleList.add(new Role(Role.ADMIN));
                        break;
                    case R.id.manager_checkBox:
                        roleList.add(new Role(Role.MANAGER));
                        break;
                    case R.id.presenter_checkBox:
                        roleList.add(new Role(Role.PRESENTER));
                        break;
                    case R.id.producer_checkBox:
                        roleList.add(new Role(Role.PRODUCER));
                        break;
                }
            }
        };
    }*/
}
