package com.example.project.CatchMonster.game;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.objects.SheetSprite;

public class Enemy2 extends SheetSprite implements IBoxCollidable{

    private static final String TAG = MainScene.class.getSimpleName();
    protected float dx = 1.f;

    public enum State {
        idle,walk,attack,hurt,die
    }
    private Context context;
    private Sound soundPlayer;

    protected Enemy2.State state = Enemy2.State.idle;

    protected float speed = 0.f;

    protected MainScene scene;
    protected float maxHp = 40.f;
    protected float currentHp;

    protected float WaitingTime;
    protected float CurrentTime;
    private RectF collisionRect = new RectF();

    private boolean attackToggle = false;

    public Player player;
    protected Rect[][] srcRectsArray = {
            makeRects(200), //State.idle
            makeRects(200, 201, 202, 203,204,205), // State.running
            makeRects(400, 401, 402, 403, 404), // State.attack
            makeRects(600, 601, 602, 603, 604) // State.die
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 200) * 200;
            int t = ((idx/200)-1) * 200;

            if(speed > 0.f){
                rects[i] = new Rect(l , t, l+200, t + 200);
            }
            else{
                rects[i] = new Rect(l+200, t, l, t + 200);
            }
        }
        return rects;
    }
    public Enemy2(int imageId, MainScene scene, Player player, Context context) {
        super(imageId, 8);
        this.scene = scene;
        this.player = player;
        currentHp = maxHp;
        CurrentTime = 0.f;
        WaitingTime = 2.f;
        setState(State.idle);

        this.context = context;
        fixCollisionRect();

        speed = (float)Math.random();
        if(speed > 0.5f) speed = -0.02f;
        else speed = 0.02f;

        setPosition(dx, 6.5f, 2.0f, 2.0f);
        srcRects = makeRects(200);
    }

    private void setState(Enemy2.State state) {
        this.state = state;
    }

    @Override
    public void update(float elapsedSeconds) {
        fixCollisionRect();
        CurrentTime += elapsedSeconds;

        switch(state){
            case idle:
                if(CurrentTime > WaitingTime){
                    CurrentTime = 0.f;
                    setState(Enemy2.State.walk);
                }
                break;
            case walk:

                //player를 바라보고 있고 일정거리 내에 있으면 공격
                if((speed < 0 && dx-player.dx > 0) || (speed> 0 && dx-player.dx < 0)){
                    if(Math.abs(dx - player.dx) <= 5.f){
                        CurrentTime=0.f;
                        attackToggle = false;
                        setState(State.attack);
                        break;
                    }
                }

                srcRects = makeRects(200, 201, 202, 203,204,205);
                //아니면 좌우로 이동
                if(speed > 0.f){
                    dx = dx + (float) speed * elapsedSeconds * 60;
                    setPosition(dx, 6.5f, 2.0f, 2.0f);

                    if(dx >= 16.f){
                        speed = -0.02f;
                    }
                }
                else{
                    dx = dx + (float) speed * elapsedSeconds * 60;
                    setPosition(dx,6.5f,2.0f,2.0f);

                    if(dx <= 0.f){
                        speed = 0.02f;
                    }
                }
                break;
            case attack:
                if(attackToggle == false && CurrentTime > 2.0f){
                    attackToggle = true;
                    attackBullet();
                }
                if(CurrentTime > 2.5f){
                    this.fps = 8;
                    attackToggle = false;
                    CurrentTime = 0.f;
                    setState(Enemy2.State.walk);
                }
                this.fps = 4;
                srcRects = makeRects(401,403,403,403, 404,404,404,404,404,404);
                break;
            case die:
                break;
        }

    }


    private void fixCollisionRect() {
        collisionRect.set(
                dstRect.left + 0.3f,
                dstRect.top + 0.3f,
                dstRect.right - 0.2f,
                dstRect.bottom - 0.2f);
    }
    public void attackBullet(){
        Enemy2Bullet bullet = Enemy2Bullet.get(x, y , (float)speed);
        scene.add(MainScene.Layer.bullet, bullet);
    }

    public void receiveDamage(float damageAmount){
        currentHp = currentHp - damageAmount;

        if(currentHp <= 0){
            this.fps = 8;
            setState(State.die);

            soundPlayer = new Sound();
            soundPlayer.playSound(context, R.raw.enemy1death,1);

            srcRects = srcRectsArray[3];
            this.scene.remainMonster -= 1;
        }
    }

    @Override
    public RectF getCollisionRect() {
        if(state == State.die) return new RectF(0,0,0,0);

        return collisionRect;
    }

}
