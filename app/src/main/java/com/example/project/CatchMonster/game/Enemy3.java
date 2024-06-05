package com.example.project.CatchMonster.game;

import android.graphics.Rect;
import android.graphics.RectF;

import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.objects.SheetSprite;

public class Enemy3 extends SheetSprite implements IBoxCollidable{
    protected float dx = 5.f;

    protected MainScene scene;

    private RectF collisionRect = new RectF();

    public enum State {
        idle, walk, attack1, attack2, hurt, die
    }
    protected Enemy3.State state = Enemy3.State.idle;
    protected float maxHp = 100.f;
    protected float currentHp;
    private float hurtCooltime = 1.2f;
    private float hurtCooltimeCurrent =0.f;
    private float skillCooltime = 5.f;
    private float skillCoolCurrent = 0.f;
    protected Rect[][] srcRectsArray = {
            makeRects(300, 301, 302, 303, 304), // State.running
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];

            if(idx/100 == 6){
                int l = (idx % 300) * 400;
                int t = ((idx/300)-1) * 300;
                rects[i] = new Rect(l , t, l+400, t + 300);
            }
            else{
                int l = (idx % 300) * 300;
                int t = ((idx/300)-1) * 300;
                rects[i] = new Rect(l , t, l+300, t + 300);
            }


        }
        return rects;
    }
    public Enemy3(int imageId, MainScene scene) {
        super(imageId, 5);
        this.scene = scene;
        currentHp = maxHp;
        setState(State.idle);
        fixCollisionRect();

        setPosition(8.0f, 5.0f, 6.0f, 6.0f);
        srcRects = srcRectsArray[0];
    }

    private void fixCollisionRect() {
        collisionRect.set(
                dstRect.left + 1.5f,
                dstRect.top,
                dstRect.right - 1.5f,
                dstRect.bottom);
    }

    private void setState(Enemy3.State state) {
        this.state = state;
    }
    @Override
    public void update(float elapsedSeconds) {

        skillCoolCurrent = skillCoolCurrent + elapsedSeconds;
        if(skillCoolCurrent > skillCooltime){
            skillCoolCurrent = 0.f;

            float tempRandom = (float)Math.random();
            if(tempRandom > 0.5){
                setState(State.attack2);
            }
            else{
                setState(State.attack2);
            }
        }
        switch(state){
            case idle:
                srcRects = makeRects(300, 301, 302, 303, 304);
                break;
            case attack1:
                for(int i=0;i<5;i++){
                    float randomX = (float)Math.random() * 16;
                    BossLigtningSkill lSkill = new BossLigtningSkill(randomX, y);
                    scene.add(MainScene.Layer.bossLighting, lSkill);
                }
                setState(State.idle);
                break;
            case attack2:
                for(int i=0;i<3;i++){
                    float randomX = (float)Math.random() * 16;
                    BossFireSkill fSkill = new BossFireSkill(randomX, y);
                    scene.add(MainScene.Layer.bossFire, fSkill);
                }
                setState(State.idle);
                break;
            case hurt:
                hurtCooltimeCurrent = hurtCooltimeCurrent + elapsedSeconds;
                if(hurtCooltimeCurrent > hurtCooltime){
                    hurtCooltimeCurrent = 0.f;
                    setState(State.idle);
                }
                break;
            case die:
                break;
        }
        fixCollisionRect();
    }

    public void receiveDamage(float damageAmount,SwordBox tempSb){
        if(state == Enemy3.State.hurt|| state == Enemy3.State.die) return;
        setState(State.hurt);

        currentHp = currentHp - damageAmount;

        if(currentHp <= 0){
            scene.remove(MainScene.Layer.enemy3, this);
            this.scene.remainMonster =this.scene.remainMonster -1;
            setState(State.die);
        }
    }

    @Override
    public RectF getCollisionRect() {
        if(state == Enemy3.State.die) return new RectF(0,0,0,0);

        return collisionRect;
    }

}
