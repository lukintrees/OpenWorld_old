package com.lukin.openworld.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Character extends Actor {
    public static final int SPEED = 100;
    public Texture texture;
    public Touchpad touchpad;
    public OrthographicCamera camera;
    public boolean centerCamera;

    public Character(Texture texture, Touchpad touchpad, OrthographicCamera camera, boolean centerCamera) {
        this.texture = texture;
        this.touchpad = touchpad;
        this.camera = camera;
        this.centerCamera = centerCamera;
    }

    public Character(Touchpad touchpad, OrthographicCamera camera, boolean centerCamera) {
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.drawRectangle(0, 0, 16, 16);
        texture = new Texture(pixmap);
        this.touchpad = touchpad;
        this.camera = camera;
        this.centerCamera = centerCamera;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        float x = SPEED * delta * touchpad.getKnobPercentX();
        float y = SPEED * delta * touchpad.getKnobPercentY();
        moveBy(x, y);
        touchpad.moveBy(x, y);
        camera.translate(x, y);
    }

    @Override
    protected void positionChanged() {
        if (centerCamera) {
            camera.position.x = getX() + texture.getWidth();
            camera.position.y = getY() + texture.getHeight();
            camera.update();
        }
    }
}
