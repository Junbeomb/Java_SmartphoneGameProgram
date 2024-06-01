package com.example.project.CatchMonster.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import com.example.project.framework.interfaces.IGameObject;
import com.example.project.framework.objects.Sprite;
import com.example.project.framework.objects.UI;
import com.example.project.framework.scene.Scene;
import com.example.project.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final MainScene scene;
    private final Player player;


    public CollisionChecker(Scene scene, Player player) {
        this.scene = (MainScene)scene;
        this.player = player;
    }

    @Override
    public void update(float elapsedSeconds) {


        ArrayList<IGameObject> swordboxs = scene.objectsAt(MainScene.Layer.collisionBox);

        if(this.scene.currentStage == 1){
            ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
            for (int e = enemies.size() - 1; e >= 0; e--) {
                Enemy enemy = (Enemy)enemies.get(e);

                //플레이어와 몬스터가 부딪히면
                if (CollisionHelper.collides(player, enemy)) {
                    player.hurt(scene); //플레이어 데미지
                    break;
                }

                //플레이어의 칼과 몬스터가 부딪히면
                for(int sb = swordboxs.size()-1;sb>=0;sb--){
                    SwordBox tempSb = (SwordBox)swordboxs.get(sb);
                    if(CollisionHelper.collides(enemy,tempSb)){
                        enemy.receiveDamage(50.f);
                        //scene.remove(MainScene.Layer.enemy, enemy);
                        break;
                    }
                }
            }
        }
        else if(this.scene.currentStage == 2){
            ArrayList<IGameObject> enemies2 = scene.objectsAt(MainScene.Layer.enemy2);
            for (int e = enemies2.size() - 1; e >= 0; e--) {
                Enemy2 enemy2 = (Enemy2)enemies2.get(e);

                //플레이어와 몬스터가 부딪히면
                if (CollisionHelper.collides(player, enemy2)) {
                    player.hurt(scene); //플레이어 데미지
                    break;
                }

                //플레이어의 칼과 몬스터가 부딪히면
                for(int sb = swordboxs.size()-1;sb>=0;sb--){
                    SwordBox tempSb = (SwordBox)swordboxs.get(sb);
                    if(CollisionHelper.collides(enemy2,tempSb)){
                        enemy2.receiveDamage(50.f);
                        //scene.remove(MainScene.Layer.enemy, enemy);
                        break;
                    }
                }
            }
            ArrayList<IGameObject> bullets = scene.objectsAt(MainScene.Layer.bullet);
            for(int b = 0; b<=bullets.size()-1;b++){
                Bullet bullet = (Bullet)bullets.get(b);
                if(CollisionHelper.collides(player,bullet)){
                    player.hurt(scene);
                    scene.remove(MainScene.Layer.bullet, bullet);
                }
            }
        }



    }

    @Override
    public void draw(Canvas canvas) {

    }
}
