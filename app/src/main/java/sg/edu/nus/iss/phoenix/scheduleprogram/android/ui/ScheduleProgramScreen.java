package sg.edu.nus.iss.phoenix.scheduleprogram.android.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;

/**
 * Created by thushara on 9/26/2017.
 */

public class ScheduleProgramScreen extends AppCompatActivity {
    private static final String TAG = ScheduleProgramScreen.class.getName();

    private EditText scheduleRPNameEditText;
    //private EditText scheduleDurationEditText;
    private EditText scheduleTimeEditText;
    private EditText scheduleDateEditText;
    private EditText schedulePresenter;
    private EditText scheduleProducer;
    private Spinner scheduleDurationEditText;
    private ProgramSlot sp2edit = null;
    KeyListener sRPNameEditTextKeyListener = null;
    Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Find all relevant views that we will need to read user input from
        scheduleRPNameEditText = (EditText) findViewById(R.id.maintain_schedule_name_text_view);
        //scheduleDurationEditText = (EditText) findViewById(R.id.maintain_schedule_duration_text_view);
        scheduleTimeEditText = (EditText) findViewById(R.id.maintain_schedule_starttime_text_view);
        scheduleDateEditText = (EditText) findViewById(R.id.maintain_schedule_date_text_view);
        schedulePresenter = (EditText) findViewById(R.id.maintain_schedule_Presenter_text_view);
        scheduleProducer = (EditText) findViewById(R.id.maintain_schedule_Producer_text_view);
        // Keep the KeyListener for name EditText so as to enable editing after disabling it.
        sRPNameEditTextKeyListener = scheduleRPNameEditText.getKeyListener();
        scheduleRPNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlFactory.getReviewSelectScheduleController().startUseCase();
            }

        });

        scheduleDurationEditText=(Spinner) findViewById(R.id.maintain_schedule_duration_text_view);
        setSpinner();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        final TimePickerDialog.OnTimeSetListener timePicker = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute) {
                myCalendar.set(Calendar.HOUR, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                scheduleTimeEditText.setText(hourOfDay + ":" + minute);
            }
        };

        scheduleDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ScheduleProgramScreen.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        scheduleTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ScheduleProgramScreen.this, timePicker,
                        myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),false);
                timePickerDialog.show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM-dd-yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        scheduleDateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void setSpinner() {
        String[]value=new String[]{"30","60","90","120"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, value);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scheduleDurationEditText.setAdapter(dataAdapter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getScheduleController().onDisplayScheduleProgram(this);
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
        if (sp2edit == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }else {
            MenuItem menuItem = menu.findItem(R.id.action_copy);
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
                Log.v(TAG, "Saving schedule program for create" + scheduleRPNameEditText.toString() + "...");
                RadioProgram rp = new RadioProgram();
                rp.setRadioProgramName(scheduleRPNameEditText.getText().toString());
                ProgramSlot programSlot = new ProgramSlot(rp,
                        scheduleDateEditText.getText().toString(),
                        Integer.parseInt(scheduleDurationEditText.getSelectedItem().toString()),
                        scheduleTimeEditText.getText().toString(),
                        schedulePresenter.getText().toString(),
                        scheduleProducer.getText().toString());
                ControlFactory.getScheduleController().selectCreateSchedule(programSlot);
            }
            else { // Edited.
                Log.v(TAG, "Saving schedule program update" +sp2edit.getRadioProgram()+ "...");
                RadioProgram rp = new RadioProgram();
                rp.setRadioProgramName(scheduleRPNameEditText.getText().toString());
                sp2edit.setRadioProgram(rp);
                sp2edit.setPresenter(schedulePresenter.getText().toString());
                sp2edit.setProducer(scheduleProducer.getText().toString());
                sp2edit.setScheduleDate(scheduleDateEditText.getText().toString());
                sp2edit.setScheduleDuration(Integer.parseInt(scheduleDurationEditText.getSelectedItem().toString()));
                sp2edit.setScheduleStartTime(scheduleTimeEditText.getText().toString());
                ControlFactory.getScheduleController().scheduleUpdated(sp2edit);
            }
            return true;
        // Respond to a click on the "Delete" menu option
      /*  case R.id.action_delete:
            Log.v(TAG, "Deleting radio program " + rp2edit.getRadioProgram() + "...");
            ControlFactory.getProgramController().selectDeleteProgram(rp2edit);
            return true;*/
        // Respond to a click on the "Cancel" menu option
        case R.id.action_cancel:
            Log.v(TAG, "Canceling creating/editing schedule program...");
            ControlFactory.getScheduleController().selectCancelCreateEditSchedule();
            return true;
        case R.id.action_copy:
            Log.v(TAG, "Copy schedule program...");
            ControlFactory.getScheduleController().scheduleCopy();
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
        setSpinner();
        scheduleDateEditText.setText("",TextView.BufferType.EDITABLE);
        schedulePresenter.setText("",TextView.BufferType.EDITABLE);
        scheduleProducer.setText("",TextView.BufferType.EDITABLE);
        scheduleRPNameEditText.setKeyListener(sRPNameEditTextKeyListener);
    }

    public void editSchedule( ProgramSlot sp2edit, String reviewSelect ) {
        this.sp2edit = sp2edit;
        if (sp2edit != null && !reviewSelect.equalsIgnoreCase("true")) {
            scheduleRPNameEditText.setText(sp2edit.getRadioProgram().getRadioProgramName(), TextView.BufferType.NORMAL);
            scheduleDateEditText.setText(sp2edit.getScheduleDate(),TextView.BufferType.NORMAL);
            List<String> dbValue = new ArrayList<String>();
            dbValue.add(String.valueOf(sp2edit.getScheduleDuration()));
            dbValue.add("30");
            dbValue.add("60");
            dbValue.add("90");
            dbValue.add("120");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, dbValue);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            scheduleDurationEditText.setAdapter(dataAdapter);
            // setSpinner();
            scheduleTimeEditText.setText(sp2edit.getScheduleStartTime(), TextView.BufferType.EDITABLE);
            schedulePresenter.setText(sp2edit.getPresenter(), TextView.BufferType.EDITABLE);
            scheduleProducer.setText(sp2edit.getProducer(), TextView.BufferType.EDITABLE);
            scheduleRPNameEditText.setKeyListener(null);
        }
        else if(sp2edit != null && reviewSelect.equalsIgnoreCase("true")){
            scheduleRPNameEditText.setText(sp2edit.getRadioProgram().getRadioProgramName(), TextView.BufferType.NORMAL);
            scheduleRPNameEditText.setKeyListener(null);
            this.sp2edit = null;
        }
    }

}

