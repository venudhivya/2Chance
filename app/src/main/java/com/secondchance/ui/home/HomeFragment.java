package com.secondchance.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.secondchance.R;
import com.secondchance.utils.StorageUtil;

public class HomeFragment extends Fragment {
    LinearLayout linear_login_layout;
    LinearLayout registration_layout;
    StorageUtil mStore;

    TextView username_text;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mStore = StorageUtil.getInstance(getActivity());

        registration_layout = root.findViewById(R.id.registration_layout);
        linear_login_layout = root.findViewById(R.id.linear_login_layout);
        username_text = root.findViewById(R.id.username_text);
        if (mStore.getString("USERNAME") != null) {
            username_text.setText("Welcome " + mStore.getString("USERNAME"));
        }
        if (mStore.getString("USERACTIVITY") != null && mStore.getString("USERACTIVITY").equalsIgnoreCase("REGISTER")) {
            linear_login_layout.setVisibility(View.GONE);
            registration_layout.setVisibility(View.VISIBLE);
        } else if (mStore.getString("USERACTIVITY") != null && mStore.getString("USERACTIVITY").equalsIgnoreCase("LOGIN")) {
            linear_login_layout.setVisibility(View.VISIBLE);
            registration_layout.setVisibility(View.GONE);
        } else {
            linear_login_layout.setVisibility(View.VISIBLE);
            registration_layout.setVisibility(View.GONE);
        }

        return root;
    }
}
