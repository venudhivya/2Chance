package com.secondchance.ui.myaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.secondchance.R;
import com.secondchance.ui.forgotpassword.ForgotPwdReenterActivity;
import com.secondchance.ui.home.HomeFragment;

import java.util.Objects;

public class MyAccountFragment extends Fragment implements View.OnClickListener{

    private TextView changepassword_txt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_myaccount, container, false);

        changepassword_txt = root.findViewById(R.id.changepassword_txt);
        changepassword_txt.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.changepassword_txt)
        {
            Navigation.findNavController(v).navigate(R.id.nav_changepassword);


        }
    }
}