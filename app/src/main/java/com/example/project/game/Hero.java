package com.example.project.game;

import com.example.project.R;
import com.example.project.framework.objects.Sprite;
import com.example.project.framework.view.Metrics;

public class Hero extends Sprite{
    private static final float RADIUS = 1.25f;
    public Hero(){
        super(R.mipmap.hero_face);
        x = Metrics.width / 2;
        y = 2 * Metrics.height / 3;
        setPosition(x,y,RADIUS);
    }

}
