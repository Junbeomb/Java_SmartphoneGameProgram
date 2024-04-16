package com.example.project.framework.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.framework.view.GameView;
import com.example.project.game.MainScene;

public class GameActivity extends AppCompatActivity {

    public static GameActivity activity;
    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = this;
        gameView = new GameView(this);
        setContentView(gameView);
        new MainScene().push();
    }

}
