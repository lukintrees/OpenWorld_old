package com.lukin.openworld.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Character extends Actor {
    public Texture texture;

    public Character(Texture texture) {
        this.texture = texture;
    }

    public Character() {
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.drawRectangle(0, 0, 16, 16);
        texture = new Texture(pixmap);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
    }
}
