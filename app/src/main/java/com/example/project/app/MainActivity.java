package com.example.project.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.framework.activity.GameActivity;
import com.example.project.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnStartGame(View view) {
        //Intent : 안드로이드에서 활동을 시작하거나 다른 컴포넌트와 상호작용할 때 사용한다.
        startActivity(new Intent(this, GameActivity.class));
    }
}