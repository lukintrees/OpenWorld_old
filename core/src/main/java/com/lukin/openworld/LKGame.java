package com.lukin.openworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lukin.openworld.actors.Character;
import com.lukin.openworld.utils.EntityLoader;


public class LKGame extends ApplicationAdapter {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final boolean DEBUG = false;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Stage stage;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(20 * 16 * 1.3f, 20 * 16, camera);
        viewport.apply();
        TiledMap map = new TmxMapLoader().load("map/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, batch);
        mapRenderer.setView(camera.combined, 0, 0, WIDTH + 16f, HEIGHT + 16f);
        stage = new Stage(viewport, batch);
        addActors();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        mapRenderer.render();
        stage.act();
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            stage.clear();
            addActors();
        }
    }

    public void addActors() {
        TiledMap map = mapRenderer.getMap();
        Touchpad touchpad = new Touchpad(10, new Touchpad.TouchpadStyle(new TextureRegionDrawable(new Texture(Gdx.files.internal("JoystickResized.png"))),
                new TextureRegionDrawable(new Texture(Gdx.files.internal("KnobResized.png")))));
        EntityLoader loader = new EntityLoader();
        Character character = new Character(touchpad, map, camera, loader.getEntities().get(0).animation);
        character.setBounds(map.getProperties().get("spawnX", Integer.class) * 16, (40 - map.getProperties().get("spawnY", Integer.class)) * 16, 16, 16);
        Vector3 touchpadPos = camera.unproject(new Vector3(0, HEIGHT, 0));
        touchpad.setPosition(touchpadPos.x + 10, touchpadPos.y + 10);
        stage.addActor(character);
        stage.addActor(touchpad);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}