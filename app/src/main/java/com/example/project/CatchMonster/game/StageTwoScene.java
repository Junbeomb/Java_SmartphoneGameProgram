package com.example.project.CatchMonster.game;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IGameObject;
import com.example.project.framework.objects.Button;
import com.example.project.framework.objects.UI;
import com.example.project.framework.objects.VertScrollBackground;
import com.example.project.framework.scene.Scene;

import java.util.ArrayList;

public class StageTwoScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    //private final Player player;

    public enum Layer {
        bg, enemy, bullet, player, ui,touch, controller, COUNT
    }
    public StageTwoScene() {

        //initLayers(StageTwoScene.Layer.COUNT);

        add(StageTwoScene.Layer.bg, new VertScrollBackground(R.mipmap.catchmonster_stage3bg, 0.2f));


//        player = new Player();
//        add(StageTwoScene.Layer.player, player);
//
//        add(StageTwoScene.Layer.controller, new CollisionChecker(this, player));
//
//        add(StageTwoScene.Layer.enemy, new Enemy());
//
//
//
//        for(int i=0;i<player.heart;i = i+1){
//            add(StageTwoScene.Layer.ui, new UI(R.mipmap.hero_face,1.5f * i,1.5f,1.8f,1.8f));
//        }
//
//        add(StageTwoScene.Layer.touch, new Button(R.mipmap.arrow_left, 1.5f, 8.3f, 2.0f, 0.75f, new Button.Callback() {
//            @Override
//            public boolean onTouch(Button.Action action) {
//                //Log.d(TAG, "left");
//                player.leftMove(action == Button.Action.pressed);
//                return true;
//            }
//        }));
//        add(StageTwoScene.Layer.touch, new Button(R.mipmap.arrow_right, 3.5f, 8.3f, 2.0f, 0.75f, new Button.Callback() {
//            @Override
//            public boolean onTouch(Button.Action action) {
//                //Log.d(TAG, "Button: Slide " + action);
//                player.rightMove(action == Button.Action.pressed);
//                return true;
//            }
//        }));
//        add(StageTwoScene.Layer.touch, new Button(R.mipmap.attack_button, 10.5f, 8.3f, 2.0f, 2.0f, new Button.Callback() {
//            @Override
//            public boolean onTouch(Button.Action action) {
//                //Log.d(TAG, "Button: Slide " + action);
//                player.attack(action == Button.Action.pressed);
//                add(StageTwoScene.Layer.bullet, new SwordBox(player.dx,6.5f));
//                return true;
//            }
//        }));
    }

    protected int getTouchLayerIndex() {
        return StageTwoScene.Layer.touch.ordinal();
    }


    @Override
    public void update(float elapsedSeconds) {

        super.update(elapsedSeconds);
    }
}
