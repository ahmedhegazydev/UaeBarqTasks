package com.uae_barq.uaebarqtasks.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.uae_barq.uaebarqtasks.R;
import com.uae_barq.uaebarqtasks.constants.BarqConstants;
import com.uae_barq.uaebarqtasks.task_dynamic.TaskDynamicActivity;
import com.uae_barq.uaebarqtasks.task_speed.TaskSpeedActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        testingFirebaseRealtimeDatabase();
        checkForDynamicLinks();

    }

    private void checkForDynamicLinks() {

        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent())
                .addOnSuccessListener(new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Log.e(TAG, "onSuccess: we have a dynamic link");
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            Log.e(TAG, "onSuccess: " + deepLink.toString());
                            String whichTaskToOpen = deepLink.getQueryParameter(BarqConstants.WHICH_TASK_TO_OPEN);
                            int whichTaskToOpenValue = Integer.parseInt(whichTaskToOpen);
                            Log.e(TAG, "onSuccess: " + whichTaskToOpenValue);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: Oops we can't retrieve dynamic link data");
                    }
                });

    }

    private void testingFirebaseRealtimeDatabase() {
        FirebaseDatabase.getInstance().getReference()
                .child(BarqConstants.STATUS)
                .setValue(true)
//                .push()
//                .setValue(new HashMap<String, Object>().put(BarqConstants.STATUS, true))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, R.string.connected, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @OnClick({R.id.btnStartTaskDynamic, R.id.btnStartTaskSpeed})
    public void onBtnTaskClicked(View v) {
        switch (v.getId()) {
            case R.id.btnStartTaskDynamic:
                Log.e(TAG, "onBtnTaskClicked: " + ((Button) v).getText().toString());
                openTaskDynamic();
                break;
            case R.id.btnStartTaskSpeed:
                Log.e(TAG, "onBtnTaskClicked: " + ((Button) v).getText().toString());
                openTaskSpeed();
                break;
            default:
                break;
        }
    }

    private void openTaskSpeed() {
        openActivity(TaskSpeedActivity.class);
    }

    private void openActivity(Class<?> classToOpen) {
        finish();
        Intent intent = new Intent(this, classToOpen);
        startActivity(intent);
    }

    private void openTaskDynamic() {
        openActivity(TaskDynamicActivity.class);
    }

}
