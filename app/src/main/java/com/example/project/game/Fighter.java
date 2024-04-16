package com.example.project.game;

import android.graphics.Canvas;

import com.example.project.R;
import com.example.project.framework.objects.JoyStick;
import com.example.project.framework.objects.Sprite;
import com.example.project.framework.scene.Scene;
import com.example.project.framework.view.Metrics;

public class Fighter extends Sprite {
    private static final float RADIUS = 1.25f;
    private static final float SPEED = 8.0f;
    private static final String TAG = Fighter.class.getSimpleName();
    private static final float BULLET_INTERVAL = 1.0f / 3.0f;
    private final JoyStick joyStick;
    private float angle;
    private float bulletCoolTime;

    public Fighter(JoyStick joyStick) {
        super(R.mipmap.plane_240);
        x = Metrics.width / 2;
        y = 2 * Metrics.height / 3;
        angle = -90;
        setPosition(x, y, RADIUS);
        //dstRect.set(x-RADIUS, y, x+RADIUS, y+2*RADIUS);

        this.joyStick = joyStick;
    }

    @Override
    public void update(float elapsedSeconds) {
        if (joyStick.power > 0) {
            float distance = SPEED * joyStick.power;
            dx = (float) (distance * Math.cos(joyStick.angle_radian));
            dy = (float) (distance * Math.sin(joyStick.angle_radian));
            angle = (float) Math.toDegrees(joyStick.angle_radian);
        } else {
            dx = dy = 0;
        }

        super.update(elapsedSeconds);

        bulletCoolTime -= elapsedSeconds;
        if (bulletCoolTime <= 0) {
            Bullet bullet = new Bullet(x, y, (float) Math.toRadians(angle));
            Scene.top().add(bullet);
            bulletCoolTime = BULLET_INTERVAL;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle + 90, x, y);
        super.draw(canvas);
        canvas.restore();
    }
}
