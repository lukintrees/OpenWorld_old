package com.lukin.openworld.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.lukin.openworld.utils.EntityLoader;

public class Enemy extends Entity{

    public Enemy(TiledMap map, EntityLoader.EntityJson entityJson){
        hitbox.set(0,0, 16, 14);
        animation = loadAnimation(entityJson.animation, map.getTileSets(), 0.25f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(animation.getKeyFrame(animationTime, true), getX(), getY());
    }

    @Override
    public void act(float delta) {
        animationTime += delta;
    }
}
