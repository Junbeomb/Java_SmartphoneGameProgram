package com.example.project.game;

import android.view.MotionEvent;

import java.util.Random;

import com.example.project.framework.scene.Scene;

public class SubScene extends Scene {
    private static final String TAG = SubScene.class.getSimpleName();
    private float time;
    private final Random random = new Random();

    public SubScene() {
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Scene.pop();
            return true;
        }
        return false;
    }
}
