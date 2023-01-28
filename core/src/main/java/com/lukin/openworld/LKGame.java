package com.lukin.openworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class LKGame extends ApplicationAdapter {
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;
    FitViewport viewport;
    SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //Создаём камеру
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(0, 700);
        //Создаём область просмотра
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        //Зарузка карты
        tiledMap = new TmxMapLoader().load("map/map.tmx");
        //Создаём отресовщик карты с увеличением размера тайла в 1,8 раз
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1.8f, batch);
        mapRenderer.setView(camera);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 0);
        camera.update();
        //Настроиваем batch для работы с камерой
        batch.setProjectionMatrix(camera.combined);
        //Рендерим всю карту
        mapRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        //Обновляем размер области просмотра
        viewport.update(width, height);
    }
}