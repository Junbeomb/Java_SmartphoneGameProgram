package com.example.project.CatchMonster.game;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.interfaces.IRecyclable;
import com.example.project.framework.objects.AnimSprite;
import com.example.project.framework.objects.Button;
import com.example.project.framework.objects.SheetSprite;
import com.example.project.framework.scene.RecycleBin;
import com.example.project.framework.scene.Scene;
import com.example.project.framework.view.Metrics;

public class Enemy extends SheetSprite implements IBoxCollidable{
    public float monsterSpeed = 0.05f;
    protected float dx = 5.f;

    protected MainScene scene;
    protected float maxHp = 100.f;
    protected float currentHp = 100.f;
    protected boolean deathToggle = false;
    protected double direction = 0.1f;
    protected float enemySpeed = 0.1f;
    protected Rect[][] srcRectsArray = {
            makeRects(100, 101, 102, 103), // State.running
            makeRects(7, 8),               // State.jump
            makeRects(1, 2, 3, 4),         // State.doubleJump
            makeRects(0),                  // State.falling
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 100) * 100;
            int t = ((idx/100)-1) * 100;

            if(direction > 0.f)
                rects[i] = new Rect(l , t, l+100, t + 100);
            else
                rects[i] = new Rect(l+100, t, l, t + 100);
        }
        return rects;
    }
    public Enemy(MainScene scene) {
        super(R.mipmap.catchmonster_monster1, 8);
        this.scene = scene;
        currentHp = maxHp;
        deathToggle = false;

        direction = Math.random();
        if(direction > 0.5f) direction = -0.1f;
        else direction = 0.1f;

        setPosition(6.5f, 6.5f, 2.0f, 2.0f);
        srcRects = srcRectsArray[0];
    }
    @Override
    public void update(float elapsedSeconds) {

        if(!deathToggle){
            if(direction > 0.f){
                dx = dx + (float)direction;
                setPosition(dx, 6.5f, 2.0f, 2.0f);

                if(dx >= 16.f){
                    srcRects = makeRects(100, 101, 102, 103);
                    direction = -0.1f;
                }
            }
            else{
                dx = dx + (float)direction;
                setPosition(dx,6.5f,2.0f,2.0f);

                if(dx <= 0.f){
                    srcRects = makeRects(100, 101, 102, 103);
                    direction = 0.1f;
                }
            }
        }
    }

    public void receiveDamage(float damageAmount){
        currentHp = currentHp - damageAmount;
        if(currentHp <= 0){
            srcRects = makeRects(200, 201, 202, 203, 204, 205, 206, 207);

            this.scene.remainMonster =this.scene.remainMonster -1;
            deathToggle = true;
        }
    }

    @Override
    public RectF getCollisionRect() {
        if(deathToggle) return new RectF(0,0,0,0);

        return dstRect;
    }

}
