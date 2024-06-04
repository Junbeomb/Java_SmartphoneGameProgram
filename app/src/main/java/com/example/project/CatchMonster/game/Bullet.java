package com.example.project.CatchMonster.game;

import android.graphics.Matrix;
import android.graphics.Rect;
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

    private float currentDx;
    private float currentWidth;
    private float currentHeight;

    private float bounceTimer=0.f;

    public enum State {
        idle,bounce,bomb
    }
    protected Bullet.State state = Bullet.State.idle;

    protected MainScene scene;

    public Bullet(float x, float y, float direction) {
        super(R.mipmap.catchmonster_enemy2bullet);
        setState(Bullet.State.idle);
        setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        dx = direction * 100;
        currentDx = dx;
    }
    public Bullet(float x, float y, float direction, boolean bool) {
        super(R.mipmap.catchmonster_enemy2bullet_reverse);
        setState(Bullet.State.idle);
        setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        dx = direction * 100;
        currentDx = dx;
    }
    public static Bullet get(float x, float y, float direction) {

//        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
//        if (bullet != null) {
//            bullet.setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
//            return bullet;
//        }
        if(direction < 0)
            return new Bullet(x, y,direction);
        else
            return new Bullet(x,y,direction,true);
    }

    private void setState(Bullet.State state) {
        this.state = state;
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);

        if (dstRect.right < 0.f || dstRect.left > 16.f) {
            Scene.top().remove(MainScene.Layer.bullet, this);
        }

        switch(this.state){
            case idle:
                break;
            case bounce:
                bounceTimer +=elapsedSeconds;

                dx = currentDx-1;
                dy = -4;

                if(bounceTimer > 1.0f){
                    //setState(Bullet.State.idle);
                    Scene.top().remove(MainScene.Layer.bullet, this);
                }
                break;
            case bomb:
                break;
        }
    }

    public void bounceBullet(){
        setState(Bullet.State.bounce);
        //currentWidth = BULLET_WIDTH;
        //currentHeight = BULLET_HEIGHT;
    }

    public void bombBullet(float x,MainScene scene){
        setState(Bullet.State.bomb);

        HitEffect he = new HitEffect(x,6.5f);
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
