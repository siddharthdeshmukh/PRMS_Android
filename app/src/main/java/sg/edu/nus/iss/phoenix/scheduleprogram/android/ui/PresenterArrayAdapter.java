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
import sg.edu.nus.iss.phoenix.user.entity.Presenter;

/**
 * Created by Shubhanshu Gautam (e0146956) on 9/28/2017.
 */

public class PresenterArrayAdapter extends ArrayAdapter<Presenter> {

    public PresenterArrayAdapter(@NonNull Context context, ArrayList<Presenter> presenters) {
        super(context, 0, presenters);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.row_presenter_producer, parent, false);
        }
        Presenter currentPresenter = getItem(position);

        TextView presenterID = (TextView)listItemView.findViewById(R.id.pres_prod_id);
        presenterID.setText(currentPresenter.getId(), TextView.BufferType.NORMAL);

        TextView presenterName = (TextView)listItemView.findViewById(R.id.pres_prod_name);
        presenterName.setText(currentPresenter.getName(), TextView.BufferType.NORMAL);

        return listItemView;
    }
}
