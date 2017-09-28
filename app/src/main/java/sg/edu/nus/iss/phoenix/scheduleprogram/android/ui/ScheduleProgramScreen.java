package sg.edu.nus.iss.phoenix.scheduleprogram.android.ui;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;
/**
 * Created by thushara on 9/26/2017.
 */

public class ScheduleProgramScreen extends AppCompatActivity {
    private static final String TAG = ScheduleProgramScreen.class.getName();

    private EditText scheduleRPNameEditText;
    private EditText scheduleDurationEditText;
    private EditText scheduleTimeEditText;
    private EditText scheduleDateEditText;
    private EditText schedulePresenter;
    private EditText scheduleProducer;
    private ProgramSlot sp2edit = null;
    KeyListener sRPNameEditTextKeyListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Find all relevant views that we will need to read user input from
        scheduleRPNameEditText = (EditText) findViewById(R.id.maintain_schedule_name_text_view);
        scheduleDurationEditText = (EditText) findViewById(R.id.maintain_schedule_duration_text_view);
        scheduleTimeEditText = (EditText) findViewById(R.id.maintain_schedule_starttime_text_view);
        scheduleDateEditText = (EditText) findViewById(R.id.maintain_schedule_date_text_view);
        schedulePresenter = (EditText) findViewById(R.id.maintain_schedule_Presenter_text_view);
        scheduleProducer = (EditText) findViewById(R.id.maintain_schedule_Producer_text_view);
        // Keep the KeyListener for name EditText so as to enable editing after disabling it.
        sRPNameEditTextKeyListener = scheduleRPNameEditText.getKeyListener();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getScheduleController().onDisplayProgram(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new radioprogram, hide the "Delete" menu item.
        if (sp2edit == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }
//for save
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // User clicked on a menu option in the app bar overflow menu
    switch (item.getItemId()) {
        // Respond to a click on the "Save" menu option
        case R.id.action_save:
            // Save schedule.
            if (sp2edit == null) { // Newly created.
                Log.v(TAG, "Saving schedule program " + scheduleRPNameEditText.toString() + "...");
                ProgramSlot programSlot = new ProgramSlot(scheduleRPNameEditText.getText().toString(),
                        scheduleDateEditText.getText().toString(), scheduleDurationEditText.getText().toString(),
                        scheduleTimeEditText.getText().toString(), schedulePresenter.getText().toString(),
                        scheduleProducer.getText().toString());
                ControlFactory.getScheduleController().selectCreateSchedule(programSlot);
            }
            else { // Edited.
                Log.v(TAG, "Saving schedule program " +sp2edit.getRadioProgramName()+ "...");
                sp2edit.setScheduleStartTime(scheduleTimeEditText
                        .getText().toString());

                ControlFactory.getScheduleController().scheduleUpdated(sp2edit);
            }
            return true;
        // Respond to a click on the "Delete" menu option
      /*  case R.id.action_delete:
            Log.v(TAG, "Deleting radio program " + rp2edit.getRadioProgramName() + "...");
            ControlFactory.getProgramController().selectDeleteProgram(rp2edit);
            return true;*/
        // Respond to a click on the "Cancel" menu option
        case R.id.action_cancel:
            Log.v(TAG, "Canceling creating/editing schedule program...");
            ControlFactory.getScheduleController().selectCancelCreateEditSchedule();
            return true;
    }

    return true;
}
    @Override
    public void onBackPressed() {
        Log.v(TAG, "Canceling creating/editing schedule program...");
        ControlFactory.getScheduleController().selectCancelCreateEditSchedule();
    }

    public void createSchedule() {
        this.sp2edit = null;
        scheduleRPNameEditText.setText("", TextView.BufferType.EDITABLE);
        scheduleTimeEditText.setText("", TextView.BufferType.EDITABLE);
        scheduleDurationEditText.setText("", TextView.BufferType.EDITABLE);
        scheduleDateEditText.setText("",TextView.BufferType.EDITABLE);
        schedulePresenter.setText("",TextView.BufferType.EDITABLE);
        scheduleProducer.setText("",TextView.BufferType.EDITABLE);
        scheduleRPNameEditText.setKeyListener(sRPNameEditTextKeyListener);
    }

    public void editSchedule( ProgramSlot sp2edit ) {
        this.sp2edit = sp2edit;
        if (sp2edit != null) {
            scheduleRPNameEditText.setText(sp2edit.getRadioProgramName(), TextView.BufferType.NORMAL);
            scheduleDateEditText.setText(sp2edit.getScheduleDate(),TextView.BufferType.NORMAL);
            scheduleDurationEditText.setText(sp2edit.getScheduleDuration(), TextView.BufferType.EDITABLE);
            scheduleTimeEditText.setText(sp2edit.getScheduleStartTime(), TextView.BufferType.EDITABLE);
            schedulePresenter.setText(sp2edit.getPresenter(), TextView.BufferType.EDITABLE);
            scheduleProducer.setText(sp2edit.getProducer(), TextView.BufferType.EDITABLE);
            scheduleRPNameEditText.setKeyListener(null);
        }
    }
}

