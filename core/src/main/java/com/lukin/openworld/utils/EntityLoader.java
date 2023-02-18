package com.lukin.openworld.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.lukin.openworld.actors.Entity;

public class EntityLoader {
    private String fileName = "entities.json";
    private Array<EntityJson> entities;

    public EntityLoader() {
        String text = Gdx.files.internal(fileName).readString();
        JsonValue root = new JsonReader().parse(text);
        entities = new Array<>(root.size);
        for (JsonValue value : root.iterator()) {
            EntityJson entity = createEntityFromJsonValue(value);
            entities.add(entity);
        }
    }

    private EntityJson createEntityFromJsonValue(JsonValue value) {
        EntityJson entity = new EntityJson();
        entity.id = value.getInt("id");
        entity.name = value.getString("name");
        entity.animation = createAnimationArray(value.get("animation"));
        return entity;
    }

    private int[][][] createAnimationArray(JsonValue animation) {
        int maxWidth = 0;
        int maxHeight = 0;
        for (int i = 0; i < animation.size; i++) {
            for (int j = 0; j < animation.get(i).size; j++) {
                if (maxWidth < animation.get(i).size) {
                    maxWidth = animation.get(i).size;
                }
                if (maxHeight < animation.get(i).get(j).size) {
                    maxHeight = animation.get(i).get(j).size;
                }
            }
        }
        int[][][] animationArray = new int[animation.size][maxWidth][maxHeight];
        for (int i = 0; i < animation.size; i++) {
            for (int j = 0; j < animation.get(i).size; j++) {
                for (int k = 0; k < animation.get(i).get(j).size; k++) {
                    animationArray[i][j][k] = animation.get(i).get(j).getInt(k) + 1;
                }
            }
        }
        return animationArray;
    }


    public Entity loadEntity() {
        Entity entity = new Entity();
        //TODO: Entities should be loaded from a json file.
        return entity;
    }

    public Array<EntityJson> getEntities() {
        return entities;
    }

    public static class EntityJson {
        public int id;
        public String name;
        public int[][][] animation;
    }
}
