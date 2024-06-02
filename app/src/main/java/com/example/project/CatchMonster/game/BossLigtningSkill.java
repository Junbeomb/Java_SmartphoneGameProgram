package com.example.project.CatchMonster.game;

import android.graphics.Rect;

import com.example.project.CatchMonster.R;
import com.example.project.framework.objects.SheetSprite;
import com.example.project.framework.scene.Scene;

public class BossLigtningSkill extends SheetSprite {
    protected Rect[][] srcRectsArray = {
            makeRects(300, 301, 302, 303, 304, 305, 306, 307), // State.running
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 300) * 200;
            int t = 0;
            rects[i] = new Rect(l, t, l + 200, t + 300);
        }
        return rects;
    }
    public BossLigtningSkill(float x, float y) {
        super(R.mipmap.catchmonster_lightning, 4);
        setPosition(x, 4.0f, 1.5f, 11.f);
        srcRects = makeRects(300, 301, 302, 303, 304, 305, 306, 307, 308);
    }

    private float seconds = 0.f;
    @Override
    public void update(float elapsedSeconds) {
        seconds = seconds + elapsedSeconds;
        if(seconds >= 2.0f)
            Scene.top().remove(MainScene.Layer.bossLighting, this);
    }

}
