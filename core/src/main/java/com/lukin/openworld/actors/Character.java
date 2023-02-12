package com.lukin.openworld.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import java.util.HashMap;

public class Character extends Actor {
    public static final int SPEED = 100;
    public Texture texture;
    public Touchpad touchpad;
    public OrthographicCamera camera;
    public boolean centerCamera;
    public TiledMapTileLayer layer;
    public Rectangle hitbox;
    private boolean skipMove = true;
    public static final boolean DEBUG = false;
    private HashMap<Rectangle, TiledMapTileLayer.Cell> tilesHitbox;
    private Texture hitboxTexture;
    private Texture hitboxTexture2;
    private BitmapFont debugFont;


    public Character(Touchpad touchpad, OrthographicCamera camera, boolean centerCamera, TiledMapTileLayer layer) {
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.drawRectangle(0, 0, 16, 16);
        texture = new Texture(pixmap);
        hitbox = new Rectangle(0, 0, 16, 16);
        if (DEBUG) {
            tilesHitbox = new HashMap<>(25);
            pixmap.setColor(Color.RED);
            pixmap.drawRectangle(0, 0, 16, 16);
            hitboxTexture = new Texture(pixmap);
            pixmap.setColor(Color.GREEN);
            pixmap.drawRectangle(0, 0, 16, 16);
            hitboxTexture2 = new Texture(pixmap);
        }
        pixmap.dispose();
        this.touchpad = touchpad;
        this.camera = camera;
        this.centerCamera = centerCamera;
        this.layer = layer;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (DEBUG) {
            for (HashMap.Entry<Rectangle, TiledMapTileLayer.Cell> entry : tilesHitbox.entrySet()) {
                if (entry.getValue() == null) {
                    batch.draw(hitboxTexture, entry.getKey().x, entry.getKey().y, entry.getKey().width, entry.getKey().height);
                } else {
                    batch.draw(hitboxTexture2, entry.getKey().x, entry.getKey().y, entry.getKey().width, entry.getKey().height);
                }
            }
        }
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        float x = SPEED * delta * touchpad.getKnobPercentX();
        float y = SPEED * delta * touchpad.getKnobPercentY();
        hitbox.setPosition(getX() + x, getY() + y);
        skipMove = checkPosition(x, y);
        if (!skipMove) {
            moveBy(x, y);
            touchpad.moveBy(x, y);
            camera.translate(x, y);
        }
    }

    @Override
    protected void positionChanged() {
        if (centerCamera) {
            camera.position.x = getX() + texture.getWidth();
            camera.position.y = getY() + texture.getHeight();
            camera.update();
        }
    }

    public boolean checkPosition(float addX, float addY) {
        if (DEBUG) {
            tilesHitbox.clear();
        }
        Vector2 pos = localToStageCoordinates(new Vector2(addX, addY));
        pos.set(pos.x / 16, pos.y / 16);
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                TiledMapTileLayer.Cell cell = layer.getCell((int) pos.x + i, (int) (pos.y + j));
                Rectangle rect = new Rectangle((int) (pos.x + i) * 16, (int) (pos.y + j) * 16, 16, 16);
                if (DEBUG) {
                    tilesHitbox.put(rect, cell);
                }
                if (cell == null) {
                    if (hitbox.overlaps(rect)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
