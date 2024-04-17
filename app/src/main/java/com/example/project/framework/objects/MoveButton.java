package com.example.project.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.project.framework.interfaces.IGameObject;
import com.example.project.framework.res.BitmapPool;

public class MoveButton implements IGameObject {
    private static final String TAG = MoveButton.class.getSimpleName();


    private final Bitmap bgBitmap;
    private float centerX = 2.0f;
    private float centerY = 14.0f;
    private float bgRadius = 2.0f;

    private boolean visible = true;

    private final RectF bgRect = new RectF();

    public MoveButton(int bgBmpId){
        bgBitmap = BitmapPool.get(bgBmpId);

        setRects(centerX,centerY,bgRadius);
    }

    public void setRects(float cx, float cy,float bgRadius){
        this.centerX = cx;
        this.centerY = cy;
        this.bgRadius = bgRadius;
        bgRect.set(centerX - this.bgRadius, centerY - this.bgRadius, centerX + this.bgRadius, centerY + this.bgRadius);
    }

    @Override
    public void update(float elapsedSeconds) {

    }

    @Override
    public void draw(Canvas canvas) {
        if (!visible) return;
        canvas.drawBitmap(bgBitmap, null, bgRect, null);
    }
}