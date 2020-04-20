package com.secondchance.ui.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.utils.StorageUtil;

public class NewMessageFragment extends Fragment implements View.OnClickListener {

    LinearLayout birthday_linear;
    LinearLayout newyear_linear;
    LinearLayout christmas_linear;
    SecondChanceApplication secondChanceApplication;
    StorageUtil mStore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.newmsg_layout, container, false);

        secondChanceApplication = (SecondChanceApplication) getActivity().getApplication();
        mStore = StorageUtil.getInstance(getActivity());

        birthday_linear = root.findViewById(R.id.birthday_linear);
        newyear_linear = root.findViewById(R.id.newyear_linear);
        christmas_linear = root.findViewById(R.id.christmas_linear);
        newyear_linear.setOnClickListener(this);
        birthday_linear.setOnClickListener(this);
        christmas_linear.setOnClickListener(this);

        return root;
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.newyear_linear)
        {

        }else if (id == R.id.birthday_linear) {

        } else if (id == R.id.christmas_linear) {

        }

    }
}
