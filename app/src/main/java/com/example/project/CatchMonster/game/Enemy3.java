package com.example.project.CatchMonster.game;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.objects.SheetSprite;

public class Enemy3 extends SheetSprite implements IBoxCollidable{
    protected float dx = 5.f;

    protected MainScene scene;
    private Context context;
    private Sound soundPlayer;
    private RectF collisionRect = new RectF();

    public enum State {
        idle, walk, attack1, attack2,attack3, hurt, die
    }
    protected Enemy3.State state = Enemy3.State.idle;
    protected float maxHp = 100.f;
    protected float currentHp;
    private float skillCooltime = 4.f;
    private float skillCoolCurrent = 0.f;
    private boolean skill3Toggle = false;
    protected Rect[][] srcRectsArray = {
            makeRects(300, 301, 302, 303, 304), // State.running
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];

            if(idx/100 == 6){
                int l = (idx % 300) * 400;
                int t = ((idx/300)-1) * 300;
                rects[i] = new Rect(l , t, l+400, t + 300);
            }
            else{
                int l = (idx % 300) * 300;
                int t = ((idx/300)-1) * 300;
                rects[i] = new Rect(l , t, l+300, t + 300);
            }


        }
        return rects;
    }
    public Enemy3(int imageId, MainScene scene, Context context) {
        super(imageId, 5);
        this.scene = scene;
        currentHp = maxHp;
        setState(State.idle);
        fixCollisionRect();
        this.context = context;

        setPosition(10.0f, 5.0f, 6.0f, 6.0f);
        srcRects = srcRectsArray[0];
    }

    private void fixCollisionRect() {
        collisionRect.set(
                dstRect.left + 1.5f,
                dstRect.top,
                dstRect.right - 1.5f,
                dstRect.bottom);
    }

    private void setState(Enemy3.State state) {
        this.state = state;
    }


    private float skill1Rate = 0.3f;
    private float skill2Rate = 0.6f;
    @Override
    public void update(float elapsedSeconds) {

        if(!skill3Toggle){
            skillCoolCurrent = skillCoolCurrent + elapsedSeconds;
            if(skillCoolCurrent > skillCooltime){
                skillCoolCurrent = 0.f;

                float tempRandom = (float)Math.random();
                if(tempRandom < skill1Rate){
                    skill1Rate = 0.33f;
                    skill2Rate = 0.66f;
                    setState(State.attack1);
                }
                else if(tempRandom < skill2Rate){
                    skill2Rate = 0.77f;
                    skill1Rate = 0.66f;
                    setState(State.attack2);
                }
                else{
                    skill2Rate = 0.999f;
                    skill1Rate = 0.45f;
                    skill3Toggle = true;
                    setState(State.attack3);
                }

            }
        }

        switch(state){
            case idle:
                srcRects = makeRects(300, 301, 302, 303, 304);
                break;
            case attack1:

                soundPlayer = new Sound();
                soundPlayer.playSound(context, R.raw.bosslightning,1);

                for(int i=0;i<5;i++){
                    float randomX = (float)Math.random() * 16;
                    BossLigtningSkill lSkill = new BossLigtningSkill(randomX, y, this.context);
                    scene.add(MainScene.Layer.bossLighting, lSkill);
                }

                setState(State.idle);
                break;
            case attack2:
                for(int i=0;i<3;i++){
                    float randomX = (float)Math.random() * 16;
                    BossFireSkill fSkill = new BossFireSkill(randomX, y);
                    scene.add(MainScene.Layer.bossFire, fSkill);
                }
                skillCoolCurrent = skillCooltime;
                setState(State.idle);
                break;
            case attack3:
                skillCoolCurrent = skillCoolCurrent + elapsedSeconds;

                srcRects = makeRects(1200);

                if(skillCoolCurrent > 1.5f){
                    skillCoolCurrent = 0.f;
                    skill3Toggle = false;

                    BossBombSkill fSkill = new BossBombSkill(10.0f, 5.f, this.context);
                    scene.add(MainScene.Layer.bossBomb, fSkill);

                    setState(State.idle);
                }
                break;
            case die:
                break;
        }
        fixCollisionRect();
    }

    public void receiveDamage(float damageAmount,SwordBox tempSb){
        if(state == Enemy3.State.die) return;

        currentHp = currentHp - damageAmount;

        if(currentHp <= 0){
            this.scene.remainMonster -= 1;

            soundPlayer = new Sound();
            soundPlayer.playSound(context, R.raw.bossdeath,1);

            setState(State.die);
            scene.remove(MainScene.Layer.enemy3, this);
        }
    }

    @Override
    public RectF getCollisionRect() {
        if(state == Enemy3.State.die) return new RectF(0,0,0,0);

        return collisionRect;
    }

}
