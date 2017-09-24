package sg.edu.nus.iss.phoenix.user.android.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;


/**
 * Created by siddharth on 9/23/2017.
 */

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(@NonNull Context context, ArrayList<User> userList) {
        super(context,0, userList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_user, parent, false);
        }
        //    Word currentWord = getItem(position);
        User currentUser = getItem(position);
        EditText username = (EditText)listItemView.findViewById(R.id.maintain_user_name_text_view);
        username.setText(currentUser.getName(), TextView.BufferType.NORMAL);
        username.setKeyListener(null);
        TextView idTextView = (TextView) listItemView.findViewById(R.id.id_text_View);
        TextView pwdTextView =(TextView) listItemView.findViewById(R.id.password_text_view);
        TextView roleTextView =(TextView) listItemView.findViewById(R.id.role_text_view);
        EditText id = (EditText) listItemView.findViewById(R.id.maintain_user_id_text_view);
        EditText password = (EditText) listItemView.findViewById(R.id.maintain_user_password_text_view);

        CheckBox adminCheckBox = (CheckBox) listItemView.findViewById(R.id.admin_checkbox);
        CheckBox managerCheckBox = (CheckBox) listItemView.findViewById(R.id.manager_checkBox);
        CheckBox presenterCheckBox = (CheckBox) listItemView.findViewById(R.id.presenter_checkBox);
        CheckBox producerCheckBox = (CheckBox) listItemView.findViewById(R.id.producer_checkBox);
        idTextView.setVisibility(ViewGroup.GONE);
        pwdTextView.setVisibility(ViewGroup.GONE);
        roleTextView.setVisibility(ViewGroup.GONE);
        id.setVisibility(ViewGroup.GONE);
        password.setVisibility(ViewGroup.GONE);
        List<Role> roleList = currentUser.getRoles();
        if(roleList!=null) {
            for (Role role : roleList) {
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
                        return null;

                }
            }
        }
        adminCheckBox.setEnabled(false);
        managerCheckBox.setEnabled(false);
        presenterCheckBox.setEnabled(false);
        producerCheckBox.setEnabled(false);
        return listItemView;
    }
}
