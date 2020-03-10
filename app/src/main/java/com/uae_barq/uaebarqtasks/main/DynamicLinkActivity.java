package com.uae_barq.uaebarqtasks.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.uae_barq.uaebarqtasks.R;
import com.uae_barq.uaebarqtasks.constants.BarqConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DynamicLinkActivity extends AppCompatActivity {

    private static final String TAG = "DynamicLinkActivity";

    @BindView(R.id.tvFirstName)
    TextView tvFirstName;
    @BindView(R.id.tvLastName)
    TextView tvLastName;
    @BindView(R.id.tvAge)
    TextView tvAge;
    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    private Uri appLinkData = null;


    //called firstly
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_link);
        ButterKnife.bind(this);
    }


    /**
     * called after onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //Only for app links
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        appLinkData = appLinkIntent.getData();
        if (appLinkData != null) {
            showProgressBar();
        }

        //for firebase dynamic links
        checkForDynamicLinks();

    }

    private void showProgressBar() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching user data...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        //showing the progress dialog for 2 sec
        new Handler().postDelayed(() -> {
            //dismissing the dialog
            if (progressDialog != null) {
                progressDialog.dismiss();

                //then fetching the user data from parameters
                fetchUserProfileData(appLinkData);
            }

        }, BarqConstants.MILLI);

    }


    /**
     * Fetching the user profile data from the intent through the QueryParameters.
     *
     * @param appLinkData
     */
    private void fetchUserProfileData(Uri appLinkData) {
        String firstName = appLinkData.getQueryParameter(BarqConstants.USER_FIRST_NAME);
        String lastName = appLinkData.getQueryParameter(BarqConstants.USER_LAST_NAME);
        String phoneNumber = appLinkData.getQueryParameter(BarqConstants.USER_PHONE_NUMBER);
        String country = appLinkData.getQueryParameter(BarqConstants.USER_COUNTRY);
        String age = appLinkData.getQueryParameter(BarqConstants.USER_AGE);
//        String photo = appLinkData.getQueryParameter("photo");

        Log.e(TAG, "onCreate: firstName   " + firstName);
        Log.e(TAG, "onCreate: lastName    " + lastName);
        Log.e(TAG, "onCreate: phoneNumber " + phoneNumber);
        Log.e(TAG, "onCreate: country     " + country);
        Log.e(TAG, "onCreate: age         " + age);
//        Log.e(TAG, "onCreate: " + photo);

        if (!notNull(firstName)) {
            tvFirstName.setText(firstName);
        }
        if (!notNull(lastName)) {
            tvLastName.setText(lastName);
        }
        if (!notNull(phoneNumber)) {
            tvPhoneNumber.setText(phoneNumber);
        }
        if (!notNull(country)) {
            tvCountry.setText(country);
        }
        if (!notNull(age)) {
            tvAge.setText(age);
        }


    }

    /**
     * Checking if the passed text not equals null
     * @param firstName
     * @return
     */
    private boolean notNull(String firstName) {
        return TextUtils.equals(firstName, null);
    }

    private void checkForDynamicLinks() {

        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Log.e(TAG, "onSuccess: ");
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            Log.e(TAG, "onSuccess: " + deepLink.toString());
                            fetchUserProfileData(deepLink);
                        }

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "getDynamicLink:onFailure", e);
                    }
                });

    }
}
