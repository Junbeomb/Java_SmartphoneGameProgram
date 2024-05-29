package com.example.project.CatchMonster.game;

import android.graphics.Rect;
import android.graphics.RectF;

import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.objects.SheetSprite;

public class Enemy3 extends SheetSprite implements IBoxCollidable{
    protected float dx = 5.f;

    protected MainScene scene;
    protected float maxHp = 100.f;
    protected float currentHp;
    protected boolean deathToggle;
    protected double direction;
    protected Rect[][] srcRectsArray = {
            makeRects(300, 301, 302, 303, 304), // State.running
            makeRects(7, 8),               // State.jump
            makeRects(1, 2, 3, 4),         // State.doubleJump
            makeRects(0),                  // State.falling
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
        super(imageId, 8);
        this.scene = scene;
        currentHp = maxHp;
        deathToggle = false;

        setPosition(8.0f, 5.0f, 6.0f, 6.0f);
        srcRects = srcRectsArray[0];
    }
    @Override
    public void update(float elapsedSeconds) {

        if(!deathToggle){
            srcRects = makeRects(300, 301, 302, 303, 304);
        }
    }

    public void receiveDamage(float damageAmount){
        currentHp = currentHp - damageAmount;
        if(currentHp <= 0){
            srcRects = makeRects(600, 601, 602, 603, 604);

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
