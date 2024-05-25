package com.example.project.CatchMonster.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.example.project.CatchMonster.game.StageTwoScene;
import com.example.project.CatchMonster.R;
import com.example.project.framework.activity.GameActivity;
import com.example.project.framework.interfaces.IGameObject;
import com.example.project.framework.objects.Button;
import com.example.project.framework.objects.Score;
import com.example.project.framework.objects.UI;
import com.example.project.framework.objects.VertScrollBackground;
import com.example.project.framework.scene.Scene;

import java.util.ArrayList;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    public enum Layer {
        bg, enemy, bullet, player, ui,touch, controller, COUNT, SWORDBOX
    }
    public MainScene() {

        initLayers(Layer.COUNT);

        //add(Layer.controller, new EnemyGenerator());

        add(Layer.bg, new VertScrollBackground(R.mipmap.catchmonster_stage2bg, 0.2f));
        //add(Layer.bg, new VertScrollBackground(R.mipmap.catchmonster_floor, 0.2f));

        //add(Layer.ui, new LeftButton(R.mipmap.arrow_left,1.f,8.f));
        //add(Layer.ui, new RightButton(R.mipmap.arrow_right,2.5f,8.f));

        player = new Player();
        add(Layer.player, player);

        add(Layer.controller, new CollisionChecker(this, player));

        add(Layer.enemy, new Enemy());



        for(int i=0;i<player.heart;i = i+1){
            add(Layer.ui, new UI(R.mipmap.hero_face,1.5f * i,1.5f,1.8f,1.8f));
        }

        add(Layer.touch, new Button(R.mipmap.arrow_left, 1.5f, 8.3f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                //Log.d(TAG, "left");
                player.leftMove(action == Button.Action.pressed);
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.arrow_right, 3.5f, 8.3f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                //Log.d(TAG, "Button: Slide " + action);
                player.rightMove(action == Button.Action.pressed);
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.attack_button, 10.5f, 8.3f, 2.0f, 2.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                //Log.d(TAG, "Button: Slide " + action);
                player.attack(action == Button.Action.pressed);
                add(Layer.bullet, new SwordBox(player.dx,6.5f));
                return true;
            }
        }));
    }

    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }


    @Override
    public void update(float elapsedSeconds) {

        super.update(elapsedSeconds);


    }


}
