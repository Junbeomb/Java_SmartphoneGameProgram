package com.example.project.CatchMonster.game;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IGameObject;
import com.example.project.framework.scene.Scene;
import com.example.project.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final MainScene scene;
    private final Player player;
    private Context context;

    private Sound soundPlayer;

    public CollisionChecker(Scene scene, Player player, Context context) {
        this.scene = (MainScene)scene;
        this.player = player;
        this.context = context;
    }

    @Override
    public void update(float elapsedSeconds) {

        ArrayList<IGameObject> swordboxs = scene.objectsAt(MainScene.Layer.collisionBox);

        switch(this.scene.currentStage){
            case 1:
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
                            enemy.receiveDamage(20.f,enemy.dx - tempSb.currentX);
                            tempSb.removeCollision(R.mipmap.catchmonster_herohiteffect,scene,enemy.dx);
                            break;
                        }
                    }
                }
                ArrayList<IGameObject> traps = scene.objectsAt(MainScene.Layer.trap1);
                for(int i=traps.size()-1;i>=0;i--){
                    Trap1 t = (Trap1)traps.get(i);
                    if (CollisionHelper.collides(player, t)) {
                        t.pangFunc(player.dy-0.8f);
                        player.hurt(scene); //플레이어 데미지
                        break;
                    }
                }
                break;
            case 2:
                ArrayList<IGameObject> bullets = scene.objectsAt(MainScene.Layer.bullet);
                for(int b = 0; b<=bullets.size()-1;b++){
                    Enemy2Bullet bullet = (Enemy2Bullet)bullets.get(b);

                    //bullet과 플레이어가 부딪히면
                    if(CollisionHelper.collides(player,bullet)){
                        bullet.bombBullet(player.dx,scene);
                        player.hurt(scene);
                    }

                    //플레이어의 칼과 bullet 부딪히면
                    for(int sb = swordboxs.size()-1;sb>=0;sb--){
                        SwordBox tempSb = (SwordBox)swordboxs.get(sb);
                        if(CollisionHelper.collides(bullet,tempSb)){
                            bullet.bounceBullet();
                            tempSb.removeCollision(R.mipmap.catchmonster_herohiteffect,scene,tempSb.currentX);

                            soundPlayer = new Sound();
                            soundPlayer.playSound(context, R.raw.swordhitbullet,1);

                            //scene.remove(MainScene.Layer.bullet, bullet);
                            break;
                        }
                    }
                }
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
                            enemy2.receiveDamage(20.f);
                            tempSb.removeCollision(R.mipmap.catchmonster_herohiteffect,scene,enemy2.dx);
                            //scene.remove(MainScene.Layer.enemy, enemy);
                            break;
                        }
                    }
                }
                ArrayList<IGameObject> traps2 = scene.objectsAt(MainScene.Layer.trap1);
                for(int i=traps2.size()-1;i>=0;i--){
                    Trap1 t2 = (Trap1)traps2.get(i);
                    if (CollisionHelper.collides(player, t2)) {
                        t2.pangFunc(player.dy-0.8f);
                        player.hurt(scene); //플레이어 데미지
                        break;
                    }
                }

                break;
            case 3:
                ArrayList<IGameObject> bossFires = scene.objectsAt(MainScene.Layer.bossFire);
                for(int f =bossFires.size()-1;f>=0;f--){
                    BossFireSkill bossFire = (BossFireSkill)bossFires.get(f);
                    if(bossFire.noDamage == false){
                        //bossFire 와 플레이어가 부딪히면
                        if(CollisionHelper.collides(bossFire,player)){
                            player.hurt(scene);
                        }
                        //bossFire 와 플레이어 공격이 부딪힘
                        for(int sb = swordboxs.size()-1;sb>=0;sb--){
                            SwordBox tempSb = (SwordBox)swordboxs.get(sb);
                            if(CollisionHelper.collides(bossFire,tempSb)){
                                tempSb.removeCollision(R.mipmap.catchmonster_herohiteffect,scene,tempSb.currentX);
                                bossFire.ReceiveDamage();
                                break;
                            }
                        }
                    }
                }

                ArrayList<IGameObject> enemies3 = scene.objectsAt(MainScene.Layer.enemy3);
                for(int b = 0; b<=enemies3.size()-1;b++){
                    Enemy3 enemy3 = (Enemy3)enemies3.get(b);

                    //플레이어의 칼과 몬스터가 부딪히면
                    for(int sb = swordboxs.size()-1;sb>=0;sb--){
                        SwordBox tempSb = (SwordBox)swordboxs.get(sb);
                        if(CollisionHelper.collides(enemy3,tempSb)){
                            enemy3.receiveDamage(20.f,tempSb);
                            tempSb.removeCollision(R.mipmap.catchmonster_herohiteffect,scene,tempSb.currentX);
                            //scene.remove(MainScene.Layer.enemy, enemy);
                            break;
                        }
                    }
                }
                ArrayList<IGameObject> bossLights = scene.objectsAt(MainScene.Layer.bossLighting);
                for(int l =0;l<=bossLights.size()-1;l++){
                    BossLigtningSkill bosslight = (BossLigtningSkill)bossLights.get(l);
                    //bosslight 와 플레이어가 부딪히면
                    if(CollisionHelper.collides(bosslight,player)){
                        player.hurt(scene);
                    }
                }

                ArrayList<IGameObject> bossBombs = scene.objectsAt(MainScene.Layer.bossBomb);
                for(int l =0;l<=bossBombs.size()-1;l++){
                    BossBombSkill bossBomb = (BossBombSkill)bossBombs.get(l);
                    //bossBomb 와 플레이어가 부딪히면
                    if(CollisionHelper.collides(bossBomb,player)){
                        player.hurt(scene);
                    }
                }

                break;
        }

    }

    @Override
    public void draw(Canvas canvas) {

    }
}
