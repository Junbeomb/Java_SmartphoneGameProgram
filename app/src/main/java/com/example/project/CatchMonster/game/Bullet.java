package com.example.project.CatchMonster.game;

import android.graphics.RectF;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.interfaces.IRecyclable;
import com.example.project.framework.objects.Sprite;
import com.example.project.framework.scene.RecycleBin;
import com.example.project.framework.scene.Scene;

public class Bullet extends Sprite implements IBoxCollidable, IRecyclable {
    private static final float BULLET_WIDTH = 1.0f;
    private static final float BULLET_HEIGHT = 1.0f;
    private static final float SPEED = 5.f;

    public Bullet(float x, float y, float direction) {
        super(R.mipmap.catchmonster_enemy2bullet);
        if(direction < 0)
            setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        else
            setPosition(x, y, -BULLET_WIDTH, BULLET_HEIGHT);
        dx = direction * 100;
    }
    public static Bullet get(float x, float y, float direction) {
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if (bullet != null) {
            bullet.setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
            return bullet;
        }
        return new Bullet(x, y,direction);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.right < 0.f || dstRect.left > 16.f) {
            //Scene.top().remove(MainScene.Layer.bullet, this);
        }
    }

    @Override
    public RectF getCollisionRect() {
        //return new RectF(0,0,0,0);
        return dstRect;
    }

    @Override
    public void onRecycle() {

    }
}
