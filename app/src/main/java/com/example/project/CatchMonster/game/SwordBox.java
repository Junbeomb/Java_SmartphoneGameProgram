package com.example.project.CatchMonster.game;

import android.graphics.RectF;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.interfaces.IRecyclable;
import com.example.project.framework.objects.Button;
import com.example.project.framework.objects.Sprite;
import com.example.project.framework.scene.RecycleBin;
import com.example.project.framework.scene.Scene;

public class SwordBox extends Sprite implements IBoxCollidable, IRecyclable {
    private static final float SwordBox_WIDTH = 1.0f;
    private static final float SwordBox_HEIGHT = SwordBox_WIDTH * 40 / 28;

    private float lifeTime = 0.f;
    private float life = 1.f;
    public float currentX;
    public SwordBox(float x, float y) {
        super(R.mipmap.arrow_right);
        setPosition(x, y, SwordBox_WIDTH, SwordBox_HEIGHT);
        currentX = x;
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);

        lifeTime = lifeTime+ elapsedSeconds;
        if(lifeTime >= life){ //1 초 되 삭제
            Scene.top().remove(MainScene.Layer.collisionBox, this);
        }
    }

    public static SwordBox get(float x, float y) {
        SwordBox swordbox = (SwordBox) RecycleBin.get(SwordBox.class);
        if (swordbox != null) {
            swordbox.setPosition(x, y, SwordBox_WIDTH, SwordBox_HEIGHT);
            return swordbox;
        }

        return new SwordBox(x, y);
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }
    public void onRecycle() {

    }


}
