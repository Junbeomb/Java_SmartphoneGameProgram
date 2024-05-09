package com.example.project.CatchMonster.game;

import android.view.MotionEvent;

import com.example.project.CatchMonster.R;
import com.example.project.framework.objects.LeftButton;
import com.example.project.framework.objects.RightButton;
import com.example.project.framework.objects.Score;
import com.example.project.framework.objects.VertScrollBackground;
import com.example.project.framework.scene.Scene;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    Score score; // package private

    public enum Layer {
        bg, enemy, bullet, player, ui, controller, COUNT
    }
    public MainScene() {
        initLayers(Layer.COUNT);

        //add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker(this));

        add(Layer.bg, new VertScrollBackground(R.mipmap.catchmonster_stage2bg, 0.2f));
        //add(Layer.bg, new VertScrollBackground(R.mipmap.catchmonster_floor, 0.2f));

        //add(Layer.ui, new LeftButton(R.mipmap.arrow_left,1.f,8.f));
        //add(Layer.ui, new RightButton(R.mipmap.arrow_right,2.5f,8.f));


        //add(Layer.enemy, new Enemy(R.mipmap.catchmonster_herosprite,2.5f,8.f));
        player = new Player();
        add(Layer.player, player);


//        this.fighter = new Fighter();
//        add(Layer.player, fighter);


        this.score = new Score(R.mipmap.number_24x32, 8.5f, 0.5f, 0.6f);
        score.setScore(0);
        add(Layer.ui, score);
    }

    public void addScore(int amount) {
        score.add(amount);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return player.onTouch(event);
    }
}