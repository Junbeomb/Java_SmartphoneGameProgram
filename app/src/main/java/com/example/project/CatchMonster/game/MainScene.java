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
    public int currentStage;
    private boolean nextStageToggle;

    private float nextStageDelayCurrent = 0.f;
    private float nextStageDelay = 3.f;

    public int remainMonster;

    public enum Layer {
        bg, enemy,enemy2,enemy3,trap1, bullet,bossLighting,bossFire,bossBomb, collisionBox, player, ui,touch,effect, controller,COUNT
    }
    public MainScene() {

        initLayers(Layer.COUNT);

        currentStage = 1;
        nextStageToggle = true;


        //add(Layer.controller, new EnemyGenerator())

        player = new Player(this);
        add(Layer.player, player);
        add(Layer.controller, new CollisionChecker(this, player));

        for(int i=0;i<player.heart;i = i+1){
            add(Layer.ui, new UI(R.mipmap.hero_face,1.5f * i + 0.8f,1.5f,1.3f,1.3f));
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
        add(Layer.touch, new Button(R.mipmap.attack_button, 16.0f, 8.3f, 1.2f, 1.2f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                //Log.d(TAG, "Button: Slide " + action);
                player.attack(action == Button.Action.pressed);
                //add(Layer.collisionBox, new SwordBox(player.dx + (player.heroSpeed*10.0f),6.5f, player));
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.catchmonster_cheatingbutton, 16.0f , 1.5f, 0.8f, 0.8f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                StageOneStart();
                currentStage = 1;
                player.HPMax();
                //player에 회복하는 기능 추가하기
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.catchmonster_cheatingbutton, 17.5f , 1.5f, 0.8f, 0.8f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                StageTwoStart();
                currentStage = 2;
                player.HPMax();
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.catchmonster_cheatingbutton, 19.f , 1.5f, 0.8f, 0.8f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                StageThreeStart();
                currentStage = 3;
                player.HPMax();
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
                case 3:
                    StageThreeStart();
                    break;
                case 4:
                    StageFourStart();
                    break;
            }
        }

        //다음 스테이지로
        if(remainMonster <= 0){
            nextStageDelayCurrent += elapsedSeconds;
            if(nextStageDelayCurrent > nextStageDelay){
                nextStageDelayCurrent = 0.f;
                nextStageToggle = true;
                currentStage += 1;
            }
        }
    }

    public void removeAll(){
        //이전의 objects 모두 제거
        ArrayList<IGameObject> enemies = this.objectsAt(MainScene.Layer.enemy);
        ArrayList<IGameObject> enemies2 = this.objectsAt(MainScene.Layer.enemy2);
        ArrayList<IGameObject> enemies3 = this.objectsAt(MainScene.Layer.enemy3);
        ArrayList<IGameObject> traps = this.objectsAt(MainScene.Layer.trap1);

        for(int i=enemies.size()-1;i>=0;i--){
            Enemy enemy = (Enemy)enemies.get(i);
            remove(MainScene.Layer.enemy, enemy);
        }
        for(int i=enemies2.size()-1;i>=0;i--){
            Enemy2 enemy2 = (Enemy2)enemies2.get(i);
            remove(MainScene.Layer.enemy2, enemy2);
        }
        for(int i=enemies3.size()-1;i>=0;i--){
            Enemy3 enemy3 = (Enemy3)enemies3.get(i);
            remove(MainScene.Layer.enemy3, enemy3);
        }
        for(int i=traps.size()-1;i>=0;i--){
            Trap1 t = (Trap1)traps.get(i);
            remove(MainScene.Layer.trap1, t);
        }
    }
    public void StageOneStart(){

        removeAll();

        add(Layer.bg, new VertScrollBackground(R.mipmap.catchmonster_stage2bg, 0.2f));

        add(Layer.trap1, new Trap1(3.5f,0.5f));
        add(Layer.trap1, new Trap1(5.5f,0.5f));

        int monsterCount = 1;
        for(int i=0;i<monsterCount;i++){
            add(Layer.enemy, new Enemy(R.mipmap.catchmonster_monster1,this));
        }
        remainMonster = monsterCount;
    }


    public void StageTwoStart(){

        removeAll();

        add(Layer.bg, new VertScrollBackground(R.mipmap.catchmonster_stage3bg, 0.2f));

        add(Layer.trap1, new Trap1(2.5f,0.5f));
        add(Layer.trap1, new Trap1(7.5f,0.5f));
        add(Layer.trap1, new Trap1(3.5f,0.5f));
        add(Layer.trap1, new Trap1(15.5f,0.5f));

        int monsterCount = 1;
        for(int i=0;i<monsterCount;i++){
            add(Layer.enemy2, new Enemy2(R.mipmap.catchmonster_monster2,this,player));
        }
        remainMonster = monsterCount;
    }

    public void StageThreeStart(){

        removeAll();

        add(Layer.bg, new VertScrollBackground(R.mipmap.catchmonster_bossbg, 0.2f));

        int monsterCount = 1;
        add(Layer.enemy3, new Enemy3(R.mipmap.catchmonster_boss,this));
        remainMonster = monsterCount;
    }

    public void StageFourStart(){

        removeAll();
        ArrayList<IGameObject> character = this.objectsAt(MainScene.Layer.player);
        for(int i=character.size()-1;i>=0;i--){
            Player t = (Player)character.get(i);
            remove(MainScene.Layer.player, t);
        }
        add(Layer.bg, new VertScrollBackground(R.mipmap.catchmonster_finish, 0.2f));

    }

}
