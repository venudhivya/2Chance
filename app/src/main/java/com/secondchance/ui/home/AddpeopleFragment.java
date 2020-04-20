package com.secondchance.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.secondchance.R;
import com.secondchance.ui.HomeActivity;
import com.secondchance.ui.login.WelcomeActivity;
import com.secondchance.utils.StorageUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class AddpeopleFragment extends Fragment implements View.OnClickListener {
    TextInputLayout name_textinputlayout;
    TextInputEditText name;
    TextInputLayout parent_textinputlayout;
    TextInputEditText parent;
    TextInputLayout email_textinputlayout;
    TextInputEditText email;
    TextInputLayout phone_textinputlayout;
    TextInputEditText phone;
    TextInputLayout watsapp_textinputlayout;
    TextInputEditText watsapp_no;
    TextInputLayout facebook_textinputlayout;
    TextInputEditText facebook;
    TextInputLayout instagram_textinputlayout;
    TextInputEditText instagram;
    TextInputLayout dob_textinputlayout;
    StorageUtil mStore;
    TextInputEditText dob;
    Button save_btn;
    ImageView profile_pic;
    DatePickerDialog picker;
    private static final int SELECT_PICTURE = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addpeople_layout, container, false);
        mStore = StorageUtil.getInstance(getActivity());
        name_textinputlayout = root.findViewById(R.id.name_textinputlayout);
        name = root.findViewById(R.id.name);
        parent = root.findViewById(R.id.parent);
        parent_textinputlayout = root.findViewById(R.id.parent_textinputlayout);
        email_textinputlayout = root.findViewById(R.id.email_textinputlayout);
        phone_textinputlayout = root.findViewById(R.id.phone_textinputlayout);
        facebook_textinputlayout = root.findViewById(R.id.facebook_textinputlayout);
        watsapp_textinputlayout = root.findViewById(R.id.watsapp_textinputlayout);
        instagram_textinputlayout = root.findViewById(R.id.instagram_textinputlayout);
        dob_textinputlayout = root.findViewById(R.id.dob_textinputlayout);
        instagram = root.findViewById(R.id.instagram);
        facebook = root.findViewById(R.id.facebook);
        watsapp_no = root.findViewById(R.id.watsapp_no);
        phone = root.findViewById(R.id.phone);
        email = root.findViewById(R.id.email);
        dob = root.findViewById(R.id.dob);
        dob.setOnClickListener(this);
        save_btn = root.findViewById(R.id.save_btn);
        profile_pic = root.findViewById(R.id.profile_pic);
        save_btn.setOnClickListener(this);
        profile_pic.setOnClickListener(this);

        dob.setInputType(InputType.TYPE_NULL);
        dob.setOnClickListener(new View.OnClickListener() {
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
                                dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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
            SetValidation();
        } else if (id == R.id.dob) {
            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            builder.setTitleText(R.string.dob);
            MaterialDatePicker<Long> picker = builder.build();
            picker.show(getActivity().getSupportFragmentManager(), picker.toString());

        } else if (id == R.id.profile_pic) {
            openImageChooser();
        }
    }

    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    // Set the image in ImageView
                    profile_pic.setImageURI(selectedImageUri);
                    try {
                        URL pm = new URL(selectedImageUri.toString());
                        Log.i("PATH", "Image Path : " + pm);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    private void SetValidation() {

        // Check for a valid email address.
        boolean isEmailValid = false;
        boolean isPhoneValid = false;
        boolean isNameValid = false;
        boolean isParentNameValid = false;
        boolean iswatsappValid = false;
        boolean isFacebookValid = false;
        boolean isInstaValid = false;
        boolean isDOBValid = false;

        if (email.getText().toString().isEmpty()) {
            email_textinputlayout.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email_textinputlayout.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else {
            isEmailValid = true;
        }


        // Check for a valid phone number.
        if (phone.getText().toString().isEmpty()) {
            phone_textinputlayout.setError(getResources().getString(R.string.phone_error));
            isPhoneValid = false;
        } else if (phone.getText().length() < 10 || phone.getText().length() > 10) {
            phone_textinputlayout.setError(getResources().getString(R.string.error_invalid_phone));
        } else {
            isPhoneValid = true;
        }


        // Check for a valid watsapp number.
        if (watsapp_no.getText().toString().isEmpty()) {
            watsapp_textinputlayout.setError(getResources().getString(R.string.watsapp_error));
            iswatsappValid = false;
        } else if (watsapp_no.getText().length() < 10 || watsapp_no.getText().length() > 10) {
            watsapp_textinputlayout.setError(getResources().getString(R.string.error_invalid_phone));
        } else {
            iswatsappValid = true;
        }


        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            name_textinputlayout.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else {
            isNameValid = true;
        }

        // Check for a valid parent name.
        if (parent.getText().toString().isEmpty()) {
            parent_textinputlayout.setError(getResources().getString(R.string.parentsname_error));
            isParentNameValid = false;
        } else {
            isParentNameValid = true;
        }
        // Check for a valid parent name.
        if (facebook.getText().toString().isEmpty()) {
            facebook_textinputlayout.setError(getResources().getString(R.string.facebook_error));
            isFacebookValid = false;
        } else {
            isFacebookValid = true;
        }

        // Check for a valid parent name.
        if (instagram.getText().toString().isEmpty()) {
            instagram_textinputlayout.setError(getResources().getString(R.string.insta_error));
            isInstaValid = false;
        } else {
            isInstaValid = true;
        }

        if (dob.getText().toString().isEmpty()) {
            dob_textinputlayout.setError(getResources().getString(R.string.dob_error));
            isDOBValid = false;
        } else {
            isDOBValid = true;
        }


        if (isNameValid && isEmailValid && isPhoneValid && isParentNameValid && iswatsappValid && isFacebookValid && isInstaValid && isDOBValid) {
//            callRegisterApi();
            alertMessage();
        } else {

        }


    }


    public void alertMessage() {

        SpannableString message;

        SpannableString ss = new SpannableString("Click Here");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View textView) {
                Navigation.findNavController(textView).navigate(R.id.nav_pendingnominate);

            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan, 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new UnderlineSpan(), 0, 9, 0);
        ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        message = ss;

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle(R.string.people_success_msg);
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Showing Alert Message
        alertDialog.show();

    }

}
