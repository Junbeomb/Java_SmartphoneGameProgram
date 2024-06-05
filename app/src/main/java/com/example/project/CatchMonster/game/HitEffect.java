package com.example.project.CatchMonster.game;

import android.graphics.Rect;

import com.example.project.CatchMonster.R;
import com.example.project.framework.objects.SheetSprite;
import com.example.project.framework.scene.Scene;

public class HitEffect extends SheetSprite {
    protected Rect[][] srcRectsArray = {
            makeRects(101, 103, 105), // State.running
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 200) * 200;
            int t = 0;
            rects[i] = new Rect(l, t, l + 200, t + 200);
        }
        return rects;
    }
    public HitEffect(int imageId,float x, float y) {
        super(imageId, 8);
        setPosition(x, 6.5f, 1.0f, 1.0f);
        srcRects = makeRects(200,201,202,203,204,205,206,207,208,209,210,211);
    }

    private float seconds = 0.f;
    @Override
    public void update(float elapsedSeconds) {
        seconds = seconds + elapsedSeconds;
        if(seconds > 0.7f)
            Scene.top().remove(MainScene.Layer.effect, this);
    }

}
