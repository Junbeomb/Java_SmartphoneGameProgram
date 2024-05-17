package com.example.project.CatchMonster.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import com.example.project.framework.interfaces.IGameObject;
import com.example.project.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final MainScene scene;
    private final Player player;

    public CollisionChecker(MainScene scene, Player player) {
        this.scene = scene;
        this.player = player;
    }

    @Override
    public void update(float elapsedSeconds) {
        ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        for (int e = enemies.size() - 1; e >= 0; e--) {
            Enemy enemy = (Enemy)enemies.get(e);
            if (CollisionHelper.collides(player, enemy)) {
                Log.d(TAG, "Collision !!");
                player.hurt();
                //scene.remove(MainScene.Layer.enemy, enemy);
                break;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
