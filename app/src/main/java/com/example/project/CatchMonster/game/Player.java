package com.example.project.CatchMonster.game;

import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.project.CatchMonster.R;
import com.example.project.framework.objects.SheetSprite;

public class Player extends SheetSprite {
    public enum State {
        running, jump, doubleJump, falling, COUNT
    }
    protected State state = State.running;

    public float heroSpeed = 0.1f;
    protected float dx = 5.f;

    protected boolean goLeft = false;
    protected boolean goRight = false;
    protected boolean goAttack = false;
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
    @Override
    public void update(float elapsedSeconds) {

        if(goLeft){
            srcRects = makeRects(100, 101, 102);
            heroSpeed = -0.1f;
            dx = dx + heroSpeed;
            setPosition(dx, 6.5f, 2.0f, 2.0f);
        }
        if(goRight){
            srcRects = makeRects(100, 101, 102);
            heroSpeed = 0.1f;
            dx = dx + heroSpeed;
            setPosition(dx, 6.5f, 2.0f, 2.0f);
        }
        if(goAttack){
            srcRects = makeRects(200, 201, 202, 203, 204);
            goAttack = false;
        }

    }

    public void leftMove(boolean StartLeft){
        if(StartLeft){
            goLeft = true;
        }
        else{
            goLeft = false;
        }
    }
    public void rightMove(boolean StartLeft){
        if(StartLeft){
            goRight = true;
        }
        else{
            goRight = false;
        }
    }

    public void attack(boolean startAttack){
        if(startAttack){
            goAttack = true;
        }
        else{
            goAttack = false;
            srcRects = makeRects(300);
        }
    }
    public void jump() {
        int ord = state.ordinal() + 1;
        if (ord == State.COUNT.ordinal()) {
            ord = 0;
        }
        state = State.values()[ord]; // int 로부터 enum 만들기
        srcRects = srcRectsArray[ord];
    }
    public boolean onTouch(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            jump();
//        }
        //heroSpeed = -1 * heroSpeed;
        return false;
    }
}
