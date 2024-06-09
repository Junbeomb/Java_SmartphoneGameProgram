package com.example.project.CatchMonster.game;

import android.graphics.Rect;
import android.graphics.RectF;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.objects.SheetSprite;
import com.example.project.framework.scene.Scene;

public class BossBombSkill extends SheetSprite implements IBoxCollidable {

    private boolean noDamage;
    private RectF collisionRect = new RectF();
    protected Rect[][] srcRectsArray = {
            makeRects(0, 1, 2, 3, 4, 5, 6, 7), // State.running
    };

    private void fixCollisionRect() {
        collisionRect.set(
                dstRect.left,
                dstRect.top,
                dstRect.right,
                dstRect.bottom);
    }
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = idx * 200;
            int t = 0;
            rects[i] = new Rect(l, t, l + 200, t + 200);
        }
        return rects;
    }
    public BossBombSkill(float x, float y) {
        super(R.mipmap.catchnomster_bossbombskill, 4);
        setPosition(x, y, 10.f, 10.f);
        fixCollisionRect();
        noDamage = true;
        srcRects = makeRects(0, 1, 2, 3, 4, 5, 6, 7);
    }

    private float seconds = 0.f;
    @Override
    public void update(float elapsedSeconds) {
        seconds = seconds + elapsedSeconds;

        //1초 지난뒤에 데미지 주기
        if(noDamage && seconds >=1.0f){
            noDamage = false;
        }

        if(seconds >= 2.0f){
            Scene.top().remove(MainScene.Layer.bossBomb, this);
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
