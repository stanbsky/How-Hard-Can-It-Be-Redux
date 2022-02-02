package com.ducks.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/***
 * Content Manager which manage and stores textures
 */
public class Content {

    private HashMap<String, Texture> textures;

    /**
     * Constructor
     */
    public Content() {
        textures = new HashMap<String, Texture>();
    }

    /**
     * load and stores the targeted texture as a key value pair
     * @param path to the Texture
     * @param key to refer the Texture
     */
    public void loadTexture(String path, String key) {
        Texture texture = new Texture(Gdx.files.internal(path));
        textures.put(key, texture);
    }

    /**
     * Get the loaded Texture as a key value pair
     * @param key to the Texture
     * @return the targeted Texture
     */
    public Texture getTexture(String key) {
        return textures.get(key);
    }

    /**
     * Dispose any unwanted texture
     * @param key to the texture
     */
    public void disposeTexture(String key) {
        Texture texture = textures.get(key);
        if(texture !=null)
            texture.dispose();
    }
}
