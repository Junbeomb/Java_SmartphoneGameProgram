package com.example.project.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.project.framework.interfaces.IGameObject;
import com.example.project.framework.res.BitmapPool;


public class InteractButton implements IGameObject {
    public Bitmap bgBitmap;
    private float centerX;
    private float centerY;
    public float bgRadius = 1.f;

    private boolean visible = true;

    private final RectF bgRect = new RectF();

    public InteractButton(int bgBmpId, float X, float Y){
        this.bgBitmap = BitmapPool.get(bgBmpId);
        setRects(X,Y,bgRadius);
    }

    public void setRects(float cx, float cy,float bgRadius){
        this.centerX = cx;
        this.centerY = cy;
        this.bgRadius = bgRadius;
        bgRect.set(this.centerX - this.bgRadius, this.centerY - this.bgRadius, this.centerX + this.bgRadius, this.centerY + this.bgRadius);
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