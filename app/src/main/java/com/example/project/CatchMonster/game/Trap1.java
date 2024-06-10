package com.example.project.CatchMonster.game;

import android.graphics.Rect;
import android.graphics.RectF;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.objects.SheetSprite;
import com.example.project.framework.scene.Scene;

public class Trap1 extends SheetSprite implements IBoxCollidable {

    public enum State {
        idle, ready,drop,pang, earlypang
    }
    protected Trap1.State state = Trap1.State.idle;
    private void setState(Trap1.State state) {
        this.state = state;
    }
    public float seconds;
    protected Rect[][] srcRectsArray = {
            makeRects(0,1,2,3,4,5,6,7,8,9,10,11),
            makeRects(0,1,2,3,4,5,6,7,8)
    };

    private RectF collisionRect = new RectF();
    private void fixCollisionRect() {
        collisionRect.set(
                dstRect.left + 0.2f,
                dstRect.top,
                dstRect.right - 0.2f,
                dstRect.bottom);
    }

    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = idx * 50;
            int t = 0;

            rects[i] = new Rect(l, t, l + 50, t + 50);
        }
        return rects;
    }

    protected Rect[] makeRects2() {
        Rect[] rects = new Rect[1];
        rects[0] = new Rect(0, 50, 50, 100);
        return rects;
    }

    protected Rect[] makeRects3(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = idx * 100;
            int t = 100;

            rects[i] = new Rect(l, t, l + 100, t + 50);
        }
        return rects;
    }

    private float startTime;
    public Trap1(float x, float y) {
        super(R.mipmap.catchmonster_trap1, 5);
        setPosition(x, y, 1.f, 1.f);
        this.fps =5;
        startTime = (float)Math.random();
        startTime *=10;

        setState(State.idle);

        seconds = 0.f;
        srcRects = makeRects(0);
    }

    private float yy;
    @Override
    public void update(float elapsedSeconds) {

        seconds += elapsedSeconds;
        fixCollisionRect();

        switch(state){
            case idle:
                if(seconds>=startTime){
                    setState(State.ready);
                    srcRects = makeRects(0,1,2,3,4, 5,6,7,8,9, 10,11);
                    seconds = 0.f;
                }
                break;
            case ready:
                if(seconds>=1.1f){
                    setState(State.drop);
                }
                break;
            case drop:
                yy = y+ seconds*elapsedSeconds;
                srcRects = makeRects2();
                setPosition(x, yy, 1.f, 1.f);

                if(y+seconds > 11.f){
                    setState(State.pang);
                    seconds = 0.f;
                }
                break;
            case pang:
                if(seconds > 1.5f){
                    Scene.top().remove(MainScene.Layer.trap1, this);
                }
                setPosition(x, 7.f, 1.f, 1.f);
                srcRects = makeRects3(0,1,2,3,4, 5,6,7,8);
                break;
            case earlypang:
                if(seconds > 1.5f){
                    Scene.top().remove(MainScene.Layer.trap1, this);
                }
                break;
        }
    }

    public void pangFunc(float currentY){
        setState(State.earlypang);
        seconds = 0.f;
        setPosition(x, currentY, 1.f, 1.f);
        srcRects = makeRects3(0,1,2,3,4, 5,6,7,8);
    }
    @Override
    public RectF getCollisionRect() {
        if(state==State.earlypang || state == State.pang)return new RectF(0,0,0,0);

        return collisionRect;
    }

}
