package com.uae_barq.uaebarqtasks.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.uae_barq.uaebarqtasks.R;
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
