package com.example.project.game;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import com.example.project.R;
import com.example.project.framework.objects.Sprite;
import com.example.project.framework.res.BitmapPool;
import com.example.project.framework.interfaces.IGameObject;
import com.example.project.framework.view.Metrics;

public class Ball extends Sprite {

    private static final float BALL_RADIUS = 1.0f;
    private static final float SPEED = 7.0f;

    private static final Random random = new Random();
    public static Ball random() {
        return new Ball(random.nextFloat() * Metrics.width,
                random.nextFloat() * Metrics.height,
                random.nextFloat() * 360);
    }
    public Ball(float centerX, float centerY, float angle_degree) {
        super(R.mipmap.soccer_ball_240);
        setPosition(centerX, centerY, BALL_RADIUS);
        double radian = Math.toRadians(angle_degree);
        this.dx = SPEED * (float) Math.cos(radian);
        this.dy = SPEED * (float) Math.sin(radian);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dx > 0) {
            if (dstRect.right > Metrics.width) {
                dx = -dx;
            }
        } else {
            if (dstRect.left < 0) {
                dx = -dx;
            }
        }
        if (dy > 0) {
            if (dstRect.bottom > Metrics.height) {
                dy = -dy;
            }
        } else {
            if (dstRect.top < 0) {
                dy = -dy;
            }
        }
    }
}
