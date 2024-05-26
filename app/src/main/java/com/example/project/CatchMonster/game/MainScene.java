package com.example.project.CatchMonster.game;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IGameObject;
import com.example.project.framework.objects.Button;
import com.example.project.framework.objects.UI;
import com.example.project.framework.objects.VertScrollBackground;
import com.example.project.framework.scene.Scene;

import java.util.ArrayList;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;
    private int currentStage;
    private boolean nextStageToggle;

    private float nextStageDelayCurrent = 0.f;
    private float nextStageDelay = 3.f;

    public int remainMonster;

    public enum Layer {
        bg, enemy, bullet, player, ui,touch, controller, COUNT, SWORDBOX
    }
    public MainScene() {

        currentStage = 1;
        nextStageToggle = true;


        initLayers(Layer.COUNT);

        //add(Layer.controller, new EnemyGenerator());

        player = new Player();
        add(Layer.player, player);
        add(Layer.controller, new CollisionChecker(this, player));

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
                add(Layer.bullet, new SwordBox(player.dx + (player.heroSpeed*10.0f),6.5f));
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

        if(nextStageToggle){
            nextStageToggle = false;
            switch(currentStage){
                case 1:
                    StageOneStart();
                    break;
                case 2:
                    StageTwoStart();
                    break;
            }
        }

        //다음 스테이지로
        if(remainMonster <= 0){
            nextStageDelayCurrent += elapsedSeconds;
            if(nextStageDelayCurrent > nextStageDelay){
                nextStageDelayCurrent = 0.f;
                nextStageToggle = true;
                currentStage = currentStage +1;
            }
        }
    }
    public void StageOneStart(){

        add(Layer.bg, new VertScrollBackground(R.mipmap.catchmonster_stage2bg, 0.2f));
        add(Layer.enemy, new Enemy(this));

        ArrayList<IGameObject> enemies = this.objectsAt(MainScene.Layer.enemy);
        remainMonster = enemies.size();
    }

    public void StageTwoStart(){
        add(Layer.bg, new VertScrollBackground(R.mipmap.catchmonster_stage3bg, 0.2f));
        add(Layer.enemy, new Enemy(this));

        ArrayList<IGameObject> enemies = this.objectsAt(MainScene.Layer.enemy);
        remainMonster = enemies.size();
    }


}
