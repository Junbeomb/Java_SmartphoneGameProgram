package com.example.project.CatchMonster.game;

import android.graphics.Matrix;
import android.graphics.RectF;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.interfaces.IRecyclable;
import com.example.project.framework.objects.Sprite;
import com.example.project.framework.scene.Scene;

public class Enemy2Bullet extends Sprite implements IBoxCollidable, IRecyclable {
    private static final float BULLET_WIDTH = 1.0f;
    private static final float BULLET_HEIGHT = 1.0f;
    private static final float SPEED = 5.f;

    private float currentDx;

    private float bounceTimer=0.f;
    private float bounceSize = BULLET_WIDTH;  // Added to track size during bounce state
    private float rotationAngle = 0.f;

    public enum State {
        idle,bounce,bomb
    }
    protected Enemy2Bullet.State state = Enemy2Bullet.State.idle;

    protected MainScene scene;

    public Enemy2Bullet(float x, float y, float direction) {
        super(R.mipmap.catchmonster_enemy2bullet);
        setState(Enemy2Bullet.State.idle);
        setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        dx = direction * 100;
        currentDx = dx;
    }
    public Enemy2Bullet(float x, float y, float direction, boolean bool) {
        super(R.mipmap.catchmonster_enemy2bullet_reverse);
        setState(Enemy2Bullet.State.idle);
        setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        dx = direction * 100;
        currentDx = dx;
    }
    public static Enemy2Bullet get(float x, float y, float direction) {

//        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
//        if (bullet != null) {
//            bullet.setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
//            return bullet;
//        }
        if(direction < 0)
            return new Enemy2Bullet(x, y,direction);
        else
            return new Enemy2Bullet(x,y,direction,true);
    }

    private void setState(Enemy2Bullet.State state) {
        this.state = state;
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);

        if (dstRect.right < 0.f || dstRect.left > 20.f) {
            Scene.top().remove(MainScene.Layer.bullet, this);
        }

        switch(this.state){
            case idle:
                break;
            case bounce:
                bounceTimer +=elapsedSeconds;

                dx = currentDx-1;
                dy = -4;

                bounceSize = BULLET_WIDTH * (1.0f - (bounceTimer / 1.0f));
                rotationAngle += 360 * elapsedSeconds;

                // Apply rotation and scaling to dstRect
                float centerX = dstRect.centerX();
                float centerY = dstRect.centerY();
                Matrix matrix = new Matrix();
                matrix.postRotate(rotationAngle, centerX, centerY);
                matrix.postScale(bounceSize / BULLET_WIDTH, bounceSize / BULLET_HEIGHT, centerX, centerY);

                RectF newRect = new RectF(0, 0, BULLET_WIDTH, BULLET_HEIGHT);
                matrix.mapRect(newRect);
                newRect.offset(centerX - newRect.centerX(), centerY - newRect.centerY());
                dstRect.set(newRect);

                //setPosition(dstRect.centerX(), dstRect.centerY(), bounceSize, bounceSize);

                if(bounceTimer > 1.0f){
                    bounceTimer=0.f;
                    Scene.top().remove(MainScene.Layer.bullet, this);
                }
                break;
            case bomb:
                break;
        }
    }


    public void bounceBullet(){
        setState(Enemy2Bullet.State.bounce);
    }

    public void bombBullet(float x,MainScene scene){
        setState(Enemy2Bullet.State.bomb);

        HitEffect he = new HitEffect(R.mipmap.catchmonster_bombeffect,x,6.5f);
        scene.add(MainScene.Layer.effect,he);

        Scene.top().remove(MainScene.Layer.bullet, this);
    }

    @Override
    public RectF getCollisionRect() {
        //return new RectF(0,0,0,0);
        if(state == State.bounce) return new RectF(0,0,0,0);

        return dstRect;
    }

    @Override
    public void onRecycle() {

    }
}
