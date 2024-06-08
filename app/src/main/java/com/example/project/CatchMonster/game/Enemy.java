package com.example.project.CatchMonster.game;

import android.graphics.Rect;
import android.graphics.RectF;

import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.objects.SheetSprite;

public class Enemy extends SheetSprite implements IBoxCollidable{

    public enum State {
        idle, walk, die
    }
    protected Enemy.State state = Enemy.State.idle;
    protected float dx = 5.f;

    protected MainScene scene;
    protected int maxHp = 3;
    protected int currentHp;

    protected float spawnWaitingTime;
    protected float spawnCurrentTime;
    protected static float speed;

    private float knockbackDirection;
    protected Rect[][] srcRectsArray = {
            makeRects(100), // State.idle
            makeRects(100, 101, 102, 103), // State.walk
            makeRects(200, 201, 202, 203, 204, 205, 206, 207),// State.die
            makeRects(300, 301, 302, 303),// State.die
            makeRects(400, 401, 402, 403)// State.die
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 100) * 100;
            int t = ((idx/100)-1) * 100;

            if(speed <= 0.f)
                rects[i] = new Rect(l , t, l+100, t + 100);
            else
                rects[i] = new Rect(l+100, t, l, t + 100);
        }
        return rects;
    }
    public Enemy(int imageId,MainScene scene) {
        super(imageId, 8);
        this.scene = scene;
        currentHp = maxHp;
        spawnCurrentTime=0.f;
        spawnWaitingTime = 2.f;
        setState(State.idle);

        speed = (float)Math.random();
        if(speed > 0.5f) speed = -0.03f;
        else speed = 0.03f;

        setPosition(dx, 6.5f, 2.0f, 2.0f);
        srcRects = makeRects(100);
    }
    private void setState(Enemy.State state) {
        this.state = state;
    }

    @Override
    public void update(float elapsedSeconds) {

        switch(state){
            case idle:
                spawnCurrentTime = spawnCurrentTime + elapsedSeconds;
                if(spawnCurrentTime > spawnWaitingTime){
                    setState(State.walk);
                }
                break;
            case walk:
                if(currentHp == 3)
                    srcRects = makeRects(100, 101, 102, 103);
                else if(currentHp == 2)
                    srcRects = makeRects(300, 301, 302, 303);
                else if(currentHp == 1)
                    srcRects = makeRects(400, 401, 402, 403);

                if(speed > 0.f){
                    dx = dx + (float)speed * elapsedSeconds * 60;
                    setPosition(dx, 6.5f, 2.0f, 2.0f);
                    if(dx >= 20.f){
                        speed *= -1;
                    }
                }
                else{
                    dx = dx + (float) speed * elapsedSeconds * 60;
                    setPosition(dx,6.5f,2.0f,2.0f);
                    if(dx <= 0.f){
                        speed *= -1;
                    }
                }
                break;
            case die:
                break;
        }
    }

    public void receiveDamage(float damageAmount,float attackDirection){
        currentHp -= 1;
        speed = speed * 2;
        if(attackDirection>=0) { //몬스터가 오른쪽
            if (speed < 0) speed *= -1;
        }
        else{
            if (speed > 0) speed *= -1;
        }

        if(currentHp <= 0){
            setState(State.die);
            srcRects = makeRects(200, 201, 202, 203, 204, 205, 206, 207);
            this.scene.remainMonster =this.scene.remainMonster -1;
        }
    }

    @Override
    public RectF getCollisionRect() {
        if(state == Enemy.State.die) return new RectF(0,0,0,0);

        return dstRect;
    }

}
