package sg.edu.nus.iss.phoenix.scheduleprogram.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.scheduleprogram.util.Util;

/**
 * Created by thushara on 9/26/2017.
 */

public class ScheduleAdapter extends ArrayAdapter<ProgramSlot> {
    public ScheduleAdapter(@NonNull Context context, ArrayList<ProgramSlot> slotsPrograms) {
        super(context, 0, slotsPrograms);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_schedule, parent, false);
        }
        //    Word currentWord = getItem(position);
        ProgramSlot currentPS = getItem(position);

        EditText slotName = (EditText)listItemView.findViewById(R.id.maintain_schedule_name_text_view);
        slotName.setText(currentPS.getRadioProgram().getRadioProgramName(), TextView.BufferType.NORMAL);
        slotName.setKeyListener(null); // This disables editing.

        EditText slotDate = (EditText)listItemView.findViewById(R.id.maintain_schedule_date_text_view);
        slotDate.setText(Util.convertProgramDateToString(currentPS.getScheduleDate()), TextView.BufferType.NORMAL);
        slotDate.setKeyListener(null);

        EditText slotTime = (EditText)listItemView.findViewById(R.id.maintain_schedule_starttime_text_view);
        slotTime.setText(Util.convertDateToString(currentPS.getScheduleStartTime()), TextView.BufferType.NORMAL);
        slotTime.setKeyListener(null);

        Spinner slotDuration = (Spinner) listItemView.findViewById(R.id.maintain_schedule_duration_text_view);
        List<String> dbValue = new ArrayList<String>();
        Log.v("currentDur",String.valueOf(currentPS.getScheduleDuration()));
        dbValue.add(String.valueOf(currentPS.getScheduleDuration()));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, dbValue);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slotDuration.setAdapter(dataAdapter);

        EditText presenter = (EditText)listItemView.findViewById(R.id.maintain_schedule_Presenter_text_view);
        presenter.setText(currentPS.getPresenter(), TextView.BufferType.NORMAL);
        presenter.setKeyListener(null);

        EditText producer = (EditText)listItemView.findViewById(R.id.maintain_schedule_Producer_text_view);
        producer.setText(currentPS.getProducer(), TextView.BufferType.NORMAL);
        producer.setKeyListener(null);

        return listItemView;
    }
}
