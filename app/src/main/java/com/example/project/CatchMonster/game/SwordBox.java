package com.example.project.CatchMonster.game;

import android.graphics.Rect;
import android.graphics.RectF;

import com.example.project.CatchMonster.R;
import com.example.project.framework.interfaces.IBoxCollidable;
import com.example.project.framework.interfaces.IRecyclable;
import com.example.project.framework.objects.Button;
import com.example.project.framework.objects.SheetSprite;
import com.example.project.framework.objects.Sprite;
import com.example.project.framework.scene.RecycleBin;
import com.example.project.framework.scene.Scene;

public class SwordBox extends SheetSprite implements IBoxCollidable, IRecyclable {
    private static final float SwordBox_WIDTH = 1.0f;
    private static final float SwordBox_HEIGHT = SwordBox_WIDTH * 40 / 28;

    private float lifeTime = 0.f;
    private float life = 0.2f;
    public float currentX;

    private boolean removeCollideBox = false;
    private boolean reverseSprite = false;
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = idx * 100;
            int t = 0;

            if(!reverseSprite){
                rects[i] = new Rect(l, t, l + 100, t + 150);
            }
            else{
                rects[i] = new Rect(l+ 100, t, l, t + 150);
            }
        }
        return rects;
    }
    public SwordBox(float x, float y, Player player) {
        super(R.mipmap.catchmonster_slash,30);
        setPosition(x, y, SwordBox_WIDTH, SwordBox_HEIGHT);
        currentX = x;

        if(x > player.dx){
            reverseSprite = false;
        }
        else{
            reverseSprite = true;
        }

        srcRects = makeRects(0, 1, 2, 3, 4, 5);
    }
    public void removeCollision(int imageId,MainScene scene, float effectDx){
        HitEffect he = new HitEffect(imageId,effectDx,6.5f);
        scene.add(MainScene.Layer.effect, he);

        removeCollideBox = true;
    }
    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);

        lifeTime = lifeTime+ elapsedSeconds;
        if(lifeTime >= life){ //1 초 뒤 삭제
            Scene.top().remove(MainScene.Layer.collisionBox, this);
        }
    }

    @Override
    public RectF getCollisionRect() {
        if(removeCollideBox) return new RectF(0,0,0,0);

        return dstRect;
    }
    public void onRecycle() {

    }


}
