package com.example.project.CatchMonster.game;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.project.CatchMonster.R;
import com.example.project.CatchMonster.app.CatchMonsterActivity;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.interfaces.IGameObject;
import com.example.project.framework.objects.SheetSprite;
import com.example.project.framework.objects.UI;

import java.util.ArrayList;

public class Player extends SheetSprite implements IBoxCollidable {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    public enum State {
        idle,goLeft,goRight,attack,hurt,invincibility,die
    }

    public int heart = 3;

    private final RectF collisionRect = new RectF();
    protected State state = State.idle;


    public float heroSpeed = 0.1f;
    protected float dx = 5.f;

    protected float attackTime = 0.f;
    protected float hurtTime = 0.f;
    protected boolean invincibility = false;
    protected float invincibilityTime = 0.f;
    protected Rect[][] srcRectsArray = {
            makeRects(101, 103, 105), // State.running
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

            if(heroSpeed > 0.f){
                rects[i] = new Rect(l, t, l + 100, t + 90);
            }
            else{
                rects[i] = new Rect(l+100 , t, l, t + 90);
            }
        }
        return rects;
    }
    public Player() {
        super(R.mipmap.catchmonster_herosprite, 8);
        setPosition(dx, 6.5f, 2.0f, 2.0f);
        srcRects = srcRectsArray[state.ordinal()];
    }

    private void setState(State state) {
        this.state = state;
    }

    @Override
    public void update(float elapsedSeconds) {


        switch(state){
            case idle:
                srcRects = makeRects(100);
                break;
            case goLeft:
                srcRects = makeRects(101, 103, 105);
                heroSpeed = -0.1f;
                dx = dx + heroSpeed;
                setPosition(dx, 6.5f, 2.0f, 2.0f);
                break;
            case goRight:
                srcRects = makeRects(101, 103, 105);
                heroSpeed = 0.1f;
                dx = dx + heroSpeed;
                setPosition(dx, 6.5f, 2.0f, 2.0f);
                break;
            case attack:
                attackTime = attackTime + elapsedSeconds;
                srcRects = makeRects(200, 201, 202, 203, 204);
                if(attackTime > 0.4f){
                    attackTime = 0.f;
                    setState(State.idle);
                }
                break;
            case hurt:
                hurtTime = hurtTime + elapsedSeconds;
                Log.d(TAG, "Collision !!");
                srcRects = makeRects(204,205);
                if(hurtTime > 0.5f){
                    invincibility = true;//무적 상태 변환
                    hurtTime = 0.f;
                    setState(State.idle);
                }
                break;
            case die:
                srcRects = makeRects(400, 401, 402);
                break;
        }

        if(invincibility){ //1초간 무적
            invincibilityTime = invincibilityTime + elapsedSeconds;
            if(invincibilityTime > 1.0f){
                invincibilityTime = 0.f;
                invincibility = false;
            }
        }

    }

    public void leftMove(boolean StartLeft){
        if(state == State.hurt || state == State.die) return;

        if(StartLeft){
            setState(State.goLeft);
        }
        else{
            setState(State.idle);
        }
    }
    public void rightMove(boolean StartLeft){
        if(state == State.hurt|| state == State.die) return;

        if(StartLeft){
            setState(State.goRight);
        }
        else{
            setState(State.idle);
        }
    }

    public void attack(boolean startAttack){
        if(state == State.hurt || state == State.attack|| state == State.die ) return;

        if(startAttack){
            //scene.add(MainScene.Layer.swordbox, new SwordBox(dx+0.1f,6.5f));


            setState(State.attack);
        }

    }

    public void hurt(MainScene scene){
        if(state == State.hurt || invincibility || state == State.die) return;

        ArrayList<IGameObject> heartUi = scene.objectsAt(MainScene.Layer.ui);

        for (int u = heartUi.size() - 1; u >= 0; u--){
            if(u == this.heart-1){
                UI uih = (UI)heartUi.get(u);
                scene.remove(MainScene.Layer.ui,uih);
                break;
            }
        }

        this.heart = this.heart -1;

        if(this.heart <= 0){
            setState(State.die);
            return;
        }


        setState(State.hurt);
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }
}
