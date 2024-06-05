package com.example.project.CatchMonster.game;

import android.graphics.Rect;
import android.graphics.RectF;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.objects.SheetSprite;
import com.example.project.framework.scene.Scene;

public class BossFireSkill extends SheetSprite implements IBoxCollidable {

    private boolean noDamage;

    private int damageCount = 0;
    private boolean damageStatus = false;
    private float damageStatusSeconds = 0.f;
    private float seconds = 0.f;
    private RectF collisionRect = new RectF();
    protected Rect[][] srcRectsArray = {
            makeRects(0), //가만히
            makeRects(0,1,2,3,4,5,6,7,8,9), //점점 커짐
            makeRects(6,7,8,9), //불꽃 유지
            makeRects(6,7,8,9,10,11,12,13,14,15), //불꽃 사그라짐
            makeRects(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15), // State.running
    };

    private void fixCollisionRect() {
        collisionRect.set(
                dstRect.left + 0.6f,
                dstRect.top,
                dstRect.right - 0.6f,
                dstRect.bottom);
    }
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = idx * 100;
            int t = 0;

            rects[i] = new Rect(l, t, l + 100, t + 150);
        }
        return rects;
    }
    public BossFireSkill(float x, float y) {
        super(R.mipmap.catchmonster_bossfireskill, 5);
        setPosition(x, 6.5f, 2.f, 2.f);
        fixCollisionRect();

        damageCount = 0;
        damageStatus = false;
        damageStatusSeconds = 0.f;
        seconds = 0.f;
        noDamage = true;

        srcRects = makeRects(0);
    }



    private int skillPage = 0;
    @Override
    public void update(float elapsedSeconds) {
        seconds = seconds + elapsedSeconds;

        if(damageStatus){
            damageStatusSeconds += elapsedSeconds;
            srcRects = makeRects(6,0,7,0,8,0,9,0);
            if(damageStatusSeconds > 2.f){
                damageStatusSeconds = 0.f;
                damageStatus = false;
            }
        }
        //3초 지난뒤에 데미지 주기
        if(skillPage == 0 && seconds >=3.0f){
            if(!damageStatus)
                srcRects = makeRects(6,7,8,9);
            skillPage = 3;
            noDamage = false;
        }
        if(skillPage == 3 && seconds >= 9.2f){
            if(!damageStatus)
                srcRects = makeRects(15,16,15,16,15);
            skillPage = 4;
        }
        if(skillPage == 4 && seconds >= 12.f){
            noDamage = true;
            Scene.top().remove(MainScene.Layer.bossFire, this);
        }
    }

    public void ReceiveDamage(){
        damageCount+=1;
        damageStatus = true;
        damageStatusSeconds = 0.f;
        if(damageCount >= 3){
            damageStatus = false;
            noDamage = true;
            Scene.top().remove(MainScene.Layer.bossFire, this);
        }
    }

    @Override
    public RectF getCollisionRect() {
        if(noDamage)
            return new RectF(0,0,0,0);
        else
            return collisionRect;
    }


}
