package sg.edu.nus.iss.phoenix.scheduleprogram.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


import sg.edu.nus.iss.phoenix.R;

import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;

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

        EditText SlotName = (EditText)listItemView.findViewById(R.id.maintain_schedule_name_text_view);
        SlotName.setText(currentPS.getRadioProgramName(), TextView.BufferType.NORMAL);
        SlotName.setKeyListener(null); // This disables editing.

        EditText SlotDate = (EditText)listItemView.findViewById(R.id.maintain_schedule_date_text_view);
        SlotDate.setText(currentPS.getScheduleDate(), TextView.BufferType.NORMAL);
        SlotDate.setKeyListener(null);

        EditText SlotTime = (EditText)listItemView.findViewById(R.id.maintain_schedule_starttime_text_view);
        SlotTime.setText(currentPS.getScheduleStartTime().substring(11,19), TextView.BufferType.NORMAL);
        SlotTime.setKeyListener(null);

        EditText SlotDuration = (EditText)listItemView.findViewById(R.id.maintain_schedule_duration_text_view);
        SlotDuration.setText(currentPS.getScheduleDuration(), TextView.BufferType.NORMAL);
        SlotDuration.setKeyListener(null);

        EditText presenter = (EditText)listItemView.findViewById(R.id.maintain_schedule_Presenter_text_view);
        presenter.setText(currentPS.getPresenter(), TextView.BufferType.NORMAL);
        presenter.setKeyListener(null);

        EditText producer = (EditText)listItemView.findViewById(R.id.maintain_schedule_Producer_text_view);
        producer.setText(currentPS.getProducer(), TextView.BufferType.NORMAL);
        producer.setKeyListener(null);

        return listItemView;
    }

}
