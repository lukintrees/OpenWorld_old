package com.lukin.openworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lukin.openworld.actors.Character;


public class LKGame extends ApplicationAdapter {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
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
        Gdx.input.setInputProcessor(stage);
        Character character = new Character();
        character.setBounds(map.getProperties().get("spawnX", Integer.class) * 16, (40 - map.getProperties().get("spawnY", Integer.class)) * 16, 16, 16);
        stage.addActor(character);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        mapRenderer.render();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}