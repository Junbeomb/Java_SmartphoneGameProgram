package com.example.project.CatchMonster.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.example.project.CatchMonster.R;
import com.example.project.framework.activity.GameActivity;
import com.example.project.framework.objects.Button;
import com.example.project.framework.objects.Score;
import com.example.project.framework.objects.VertScrollBackground;
import com.example.project.framework.scene.Scene;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    Score score; // package private

    public enum Layer {
        bg, enemy, bullet, player, ui,touch, controller, COUNT
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


        //Log.d(TAG, "Button: Slide " + action);

        add(Layer.touch, new Button(R.mipmap.arrow_left, 1.5f, 8.3f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                Log.d(TAG, "left");
                player.leftMove(action == Button.Action.pressed);
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.arrow_right, 3.5f, 8.3f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                Log.d(TAG, "Button: Slide " + action);
                player.rightMove(action == Button.Action.pressed);
                return true;
            }
        }));


        //add(Layer.enemy, new Enemy(R.mipmap.catchmonster_herosprite,2.5f,8.f));



//        this.fighter = new Fighter();
//        add(Layer.player, fighter);


        this.score = new Score(R.mipmap.number_24x32, 8.5f, 0.5f, 0.6f);
        score.setScore(0);
        add(Layer.ui, score);
    }

    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    public void addScore(int amount) {
        score.add(amount);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }


}
