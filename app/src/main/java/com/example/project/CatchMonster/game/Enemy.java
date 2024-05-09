package com.example.project.CatchMonster.game;

import android.graphics.RectF;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.interfaces.IRecyclable;
import com.example.project.framework.objects.AnimSprite;
import com.example.project.framework.scene.RecycleBin;
import com.example.project.framework.scene.Scene;
import com.example.project.framework.view.Metrics;

public class Enemy extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float SPEED = 3.0f;
    private static final float RADIUS = 0.9f;
    private static final int[] resIds = {
            R.mipmap.enemy_01
    };
    public static final int MAX_LEVEL = resIds.length - 1;
    public static final float ANIM_FPS = 10.0f;
    protected RectF collisionRect = new RectF();
    private int level;

    private Enemy(int level, int index) {
        super(resIds[level], ANIM_FPS);
        this.level = level;
        setPosition(Metrics.width / 10 * (2 * index + 1), -RADIUS, RADIUS);
        dy = SPEED;
    }
    public static Enemy get(int level, int index) {
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if (enemy != null) {
            enemy.setAnimationResource(resIds[level], ANIM_FPS);
            enemy.level = level;
            enemy.setPosition(Metrics.width / 10 * (2 * index + 1), -RADIUS, RADIUS);
            return enemy;
        }
        return new Enemy(level, index);
    }
    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.top > Metrics.height) {
            Scene.top().remove(MainScene.Layer.enemy, this);
        }
        collisionRect.set(dstRect);
        collisionRect.inset(0.11f, 0.11f);
    }
    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void onRecycle() {

    }

    public int getScore() {
        return (level + 1) * 100;
    }
}
