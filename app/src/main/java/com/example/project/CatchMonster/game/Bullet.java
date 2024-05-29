package com.example.project.CatchMonster.game;

import android.graphics.RectF;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.interfaces.IRecyclable;
import com.example.project.framework.objects.Sprite;
import com.example.project.framework.scene.RecycleBin;
import com.example.project.framework.scene.Scene;

public class Bullet extends Sprite implements IBoxCollidable, IRecyclable {
    private static final float BULLET_WIDTH = 5.0f;
    private static final float BULLET_HEIGHT = 5.0f;
    private static final float SPEED = 5.f;

    public Bullet(float x, float y, float direction) {
        super(R.mipmap.hero_face);
        setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        dx = -direction * 2;
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
        if (dstRect.bottom < 0) {
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
