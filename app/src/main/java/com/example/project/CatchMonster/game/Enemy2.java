package com.example.project.CatchMonster.game;

import android.graphics.Rect;
import android.graphics.RectF;

import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.objects.SheetSprite;

public class Enemy2 extends SheetSprite implements IBoxCollidable{
    protected float dx = 1.f;

    public enum State {
        idle,walk,attack,hurt,die
    }

    protected Enemy2.State state = Enemy2.State.idle;

    protected double speed;

    protected MainScene scene;
    protected float maxHp = 100.f;
    protected float currentHp;

    protected float spawnWaitingTime;
    protected float spawnCurrentTime;

    public Player player;
    protected Rect[][] srcRectsArray = {
            makeRects(200, 201, 202, 203,204,205), // State.running
            makeRects(400, 401, 402, 403, 404), // State.attack
            makeRects(600, 601, 602, 603, 604) // State.die

    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 200) * 200;
            int t = ((idx/200)-1) * 200;

            if(speed < 0.f)
                rects[i] = new Rect(l , t, l+200, t + 200);
            else
                rects[i] = new Rect(l+200, t, l, t + 200);
        }
        return rects;
    }
    public Enemy2(int imageId, MainScene scene, Player player) {
        super(imageId, 8);
        this.scene = scene;
        this.player = player;
        currentHp = maxHp;
        setState(State.idle);

        setPosition(dx, 6.5f, 2.0f, 2.0f);
        srcRects = srcRectsArray[0];
    }

    private void setState(Enemy2.State state) {
        this.state = state;
    }

    @Override
    public void update(float elapsedSeconds) {

        switch(state){
            case idle:
                spawnCurrentTime = spawnCurrentTime + elapsedSeconds;
                if(spawnCurrentTime > spawnWaitingTime){
                    setState(Enemy2.State.walk);

                    speed = Math.random();
                    if(speed > 0.5f) speed = -0.03f;
                    else speed = 0.03f;

                    srcRects = srcRectsArray[0];
                }
                break;
            case walk:
                if(Math.abs(dx - player.dx) <= 2.f){
                    setState(State.attack);
                }
                else{
                    if(speed > 0.f){
                        dx = dx + (float) speed;
                        setPosition(dx, 6.5f, 2.0f, 2.0f);

                        if(dx >= 16.f){
                            srcRects = srcRectsArray[0];
                            speed = -0.02f;
                        }
                    }
                    else{
                        dx = dx + (float) speed;
                        setPosition(dx,6.5f,2.0f,2.0f);

                        if(dx <= 0.f){
                            srcRects = srcRectsArray[0];
                            speed = 0.02f;
                        }
                    }

                }
                break;
            case attack:
                srcRects = srcRectsArray[1];
                break;
            case die:
                break;
        }
        if(state != State.die){




        }
    }

    public void receiveDamage(float damageAmount){
        currentHp = currentHp - damageAmount;
        if(currentHp <= 0){
            setState(State.die);
            srcRects = srcRectsArray[2];
            this.scene.remainMonster =this.scene.remainMonster -1;
        }
    }

    @Override
    public RectF getCollisionRect() {
        if(state == State.die) return new RectF(0,0,0,0);

        return dstRect;
    }

}
