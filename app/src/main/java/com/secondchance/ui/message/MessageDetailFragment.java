package com.secondchance.ui.message;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.utils.StorageUtil;

import java.util.Calendar;

public class MessageDetailFragment extends Fragment implements View.OnClickListener {
    SecondChanceApplication secondChanceApplication;
    StorageUtil mStore;
    TextInputLayout deliverdate_textinputlayout;
    TextInputEditText choose_deliver_date;
    SwitchMaterial text_switch;
    SwitchMaterial voice_switch;
    Button save_btn;
    DatePickerDialog picker;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.msg_detail_layout, container, false);

        secondChanceApplication = (SecondChanceApplication) getActivity().getApplication();
        mStore = StorageUtil.getInstance(getActivity());
        choose_deliver_date = root.findViewById(R.id.choose_deliver_date);
        deliverdate_textinputlayout = root.findViewById(R.id.deliverdate_textinputlayout);
        text_switch = root.findViewById(R.id.text_switch);
        voice_switch = root.findViewById(R.id.voice_switch);
        save_btn = root.findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        choose_deliver_date.setInputType(InputType.TYPE_NULL);
        choose_deliver_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                choose_deliver_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.save_btn) {
            Validation();
        }

    }

    private void Validation() {
        boolean isDOBValid = false;

        if (choose_deliver_date.getText().toString().isEmpty()) {
            deliverdate_textinputlayout.setError(getResources().getString(R.string.dob_error));
            isDOBValid = false;
        } else {
            isDOBValid = true;
        }

        if (isDOBValid) {

        }
    }

}
