package com.example.project.CatchMonster.app;

import android.os.Bundle;

import com.example.project.CatchMonster.game.MainScene;
import com.example.project.framework.activity.GameActivity;

public class CatchMonsterActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MainScene().push();
    }
}