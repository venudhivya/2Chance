package com.secondchance.ui.sociallogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.secondchance.R;
import com.squareup.picasso.Picasso;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class GooglePlusActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = GooglePlusActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressBar mProgressDialog;

    private SignInButton btn_SignIN;
    private Button btn_SignOut;
    private LinearLayout ll_profileLayout;
    private ImageView iv_profilePic;
    private TextView tv_Name, tv_Email;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googleplus_layout);

        btn_SignIN = (SignInButton) findViewById(R.id.btn_sign_in);
        btn_SignOut = (Button) findViewById(R.id.btn_sign_out);
        ll_profileLayout = (LinearLayout) findViewById(R.id.ll_Profile);
        iv_profilePic = (ImageView) findViewById(R.id.iv_ProfilePic);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Email = (TextView) findViewById(R.id.tv_Email);

        btn_SignIN.setOnClickListener(this);
        btn_SignOut.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account,true);

        // Customizing G+ button
        btn_SignIN.setSize(SignInButton.SIZE_STANDARD);
        btn_SignIN.setScopes(gso.getScopeArray());
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(acct,true);



            Log.e(TAG, "display name: " + acct.getDisplayName());

            String user_Name = acct.getDisplayName();
            String user_profilePic = acct.getPhotoUrl().toString();
            String user_Email = acct.getEmail();

            Log.e(TAG, "Name: " + user_Name + ", user_Email: " + user_Email
                    + ", Image: " + user_profilePic);

            tv_Name.setText(user_Name);
            tv_Email.setText(user_Email);

            Picasso.with(getApplicationContext()).load(user_profilePic).into(iv_profilePic);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(false);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);


        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String user_Name = acct.getDisplayName();
            String user_profilePic = acct.getPhotoUrl().toString();
            String user_Email = acct.getEmail();



            tv_Name.setText(user_Name);
            tv_Email.setText(user_Email);

            Picasso.with(getApplicationContext()).load(user_profilePic).into(iv_profilePic);
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                break;

            case R.id.btn_sign_out:
                signOut();
                break;


        }
    }


    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressBar(this);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.setVisibility(View.VISIBLE);
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.setVisibility(View.GONE);
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btn_SignIN.setVisibility(View.GONE);
            btn_SignOut.setVisibility(View.VISIBLE);
            ll_profileLayout.setVisibility(View.VISIBLE);
        } else {
            btn_SignIN.setVisibility(View.VISIBLE);
            btn_SignOut.setVisibility(View.GONE);
            ll_profileLayout.setVisibility(View.GONE);
        }
    }

    private void updateUI(GoogleSignInAccount mgoo,boolean isSignedIn) {
        if (isSignedIn) {
            btn_SignIN.setVisibility(View.GONE);
            btn_SignOut.setVisibility(View.VISIBLE);
            ll_profileLayout.setVisibility(View.VISIBLE);


        } else {
            btn_SignIN.setVisibility(View.VISIBLE);
            btn_SignOut.setVisibility(View.GONE);
            ll_profileLayout.setVisibility(View.GONE);
        }
    }
}
