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

public class Enemy extends SheetSprite {
    public float monsterSpeed = 0.05f;
    protected float dx = 5.f;

    protected Rect[][] srcRectsArray = {
            makeRects(100, 101, 102), // State.running
            makeRects(7, 8),               // State.jump
            makeRects(1, 2, 3, 4),         // State.doubleJump
            makeRects(0),                  // State.falling
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 100) * 110 + 0;
            int t = 22;

            rects[i] = new Rect(l+105 , t, l, t + 130);
        }
        return rects;
    }
    public Enemy() {
        super(R.mipmap.catchmonster_monster1, 8);
        setPosition(6.5f, 6.5f, 2.0f, 2.0f);
        srcRects = srcRectsArray[0];
    }
    @Override
    public void update(float elapsedSeconds) {

    }
}
