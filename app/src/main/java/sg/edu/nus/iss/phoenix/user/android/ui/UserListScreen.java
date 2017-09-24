package sg.edu.nus.iss.phoenix.user.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by siddharth on 9/21/2017.
 */

public class UserListScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = UserListScreen.class.getName();

    private ListView userListView;
    private UserAdapter mUserAdapter;
    private User selectedUser = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ArrayList<User> userList = new ArrayList<User>();
        mUserAdapter = new UserAdapter(this, userList);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlFactory.getUserController().selectCreateUser();
            }
        });

        userListView = (ListView) findViewById(R.id.user_list);
        userListView.setAdapter(mUserAdapter);

        // Setup the item selection listener
        userListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                User user = (User) adapterView.getItemAtPosition(position);
                selectedUser = user;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        userListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        userListView.setSelection(0);

        ControlFactory.getUserController().onDisplayUserList(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_view:
                if (selectedUser == null) {

                    Toast.makeText(this, "Select a user first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no selected User.");
                }
                else {
                    Log.v(TAG, "Viewing User: " + selectedUser.getName() + "...");
                   ControlFactory.getUserController().selectEditUser(selectedUser);
                }
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        ControlFactory.getUserController().maintainedUser();
    }
    public void showUser(List<User> users) {
        mUserAdapter.clear();
        for (int i = 0; i < users.size(); i++) {
            mUserAdapter.add(users.get(i));
        }
    }
}
