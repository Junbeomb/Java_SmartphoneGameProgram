package com.example.project.game;
import android.view.MotionEvent;

import com.example.project.R;
import com.example.project.framework.objects.JoyStick;
import com.example.project.framework.objects.MoveButton;
import com.example.project.framework.view.Metrics;
import com.example.project.framework.scene.Scene;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Fighter player;
    private final JoyStick joyStick;
    private final MoveButton moveButton;

    public MainScene() {
        Metrics.setGameSize(16.0f, 9.0f);

        for (int i = 0; i < 10; i++) {
            add(Ball.random());
        }
        this.moveButton = new MoveButton(R.mipmap.hero_face);
        moveButton.setRects(4.5f, 4.5f, 3.0f);


        this.joyStick = new JoyStick(R.mipmap.joystick_bg, R.mipmap.joystick_thumb);
        joyStick.setRects(4.5f, 4.5f, 3.0f, 1.0f, 2.5f);
        //joyStick.thumbRect.set(....);
        this.player = new Fighter(joyStick);
        add(player);
        add(joyStick);
        add(moveButton);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return joyStick.onTouch(event);
    }
}
