package sg.edu.nus.iss.phoenix.scheduleprogram.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.user.entity.Producer;

/**
 * Created by Gautam on Shubhanshu Gautam (e0146956) on 9/28/2017.
 */

public class ProducerArrayAdapter extends ArrayAdapter<Producer> {

    public ProducerArrayAdapter(@NonNull Context context, ArrayList<Producer> producers) {
        super(context, 0, producers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.row_presenter_producer, parent, false);
        }
        Producer currentProducer= getItem(position);

        TextView producerID = (TextView)listItemView.findViewById(R.id.pres_prod_id);
        producerID.setText(currentProducer.getId(), TextView.BufferType.NORMAL);

        TextView producerName = (TextView)listItemView.findViewById(R.id.pres_prod_name);
        producerName.setText(currentProducer.getName(), TextView.BufferType.NORMAL);

        return listItemView;
    }
}
