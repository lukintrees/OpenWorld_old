package com.lukin.openworld.actors;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {
    public Rectangle hitbox;

    public Animation<Texture> loadAnimation(int[][][] tilesID, TiledMapTileSets tileSets, float frameDuration) {
        Texture[] frames = new Texture[tilesID.length];

        Pixmap tileSetPixmap = getPixmapFromTileSet(tileSets);

        for (int i = 0; i < tilesID.length; i++) {
            if (tilesID[i].length == 1) {
                if (tilesID[i][0].length == 1) {
                    frames[i] = getSingleTileTexture(tilesID[i][0][0], tileSets);
                } else {
                    frames[i] = getMultipleTilesTexture(tilesID[i][0], tileSets, tileSetPixmap);
                }
            } else {
                frames[i] = get2DTilesTexture(tilesID[i], tileSets, tileSetPixmap);
            }
        }

        Animation<Texture> animation = new Animation<>(frameDuration, frames);
        return animation;
    }

    private Pixmap getPixmapFromTileSet(TiledMapTileSets tileSets) {
        TextureData temp = tileSets.getTile(1).getTextureRegion().getTexture().getTextureData();
        temp.prepare();
        return temp.consumePixmap();
    }

    private Texture getSingleTileTexture(int tileID, TiledMapTileSets tileSets) {
        return tileSets.getTile(tileID).getTextureRegion().getTexture();
    }

    private Texture getMultipleTilesTexture(int[] tilesID, TiledMapTileSets tileSets, Pixmap tileSetPixmap) {
        Pixmap pixmap = new Pixmap(16, tilesID.length * 16, Pixmap.Format.RGBA8888);
        for (int j = 0; j < tilesID.length; j++) {
            TextureRegion tileTexture = tileSets.getTile(tilesID[j]).getTextureRegion();
            pixmap.drawPixmap(tileSetPixmap,
                    tileTexture.getRegionX(), tileTexture.getRegionY(),
                    16, 16,
                    0, (tilesID.length - j - 1) * 16, tileTexture.getRegionWidth(), tileTexture.getRegionHeight());
        }
        return new Texture(pixmap);
    }

    private Texture get2DTilesTexture(int[][] tilesID, TiledMapTileSets tileSets, Pixmap tileSetPixmap) {
        //TODO: Draw 2 dimensional textures
        throw new UnsupportedOperationException("Method not implemented yet");
    }
}
