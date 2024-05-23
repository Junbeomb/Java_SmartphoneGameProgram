package com.example.project.framework.objects;

import android.view.MotionEvent;

import com.example.project.framework.view.Metrics;

public class UI extends Sprite{
    private boolean processedDown;
    public UI(int bitmapResId, float cx, float cy, float width, float height) {
        super(bitmapResId, cx, cy, width, height);
    }
}
