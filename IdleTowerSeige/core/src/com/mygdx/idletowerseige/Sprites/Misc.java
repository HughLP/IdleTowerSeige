package com.mygdx.idletowerseige.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.idletowerseige.States.PlayState;

import java.util.Random;

/**
 * Created by Hugh on 22/11/2016.
 */

public class Misc {
    public static final int MISC_WIDTH = 128;
    public static final int MISC_HEIGHT = 64;
    private static final int FLUCTUATION = 1700;
    public static final int TOTAL_MISC = 18;

    private Animation[] miscsAnima;
    private Vector2 position;
    private Rectangle bounds;
    private Random rand;
    private Texture[] miscs;
    private int n; // which misc
    private String fileName;

    public Misc(float x){
        rand = new Random();
        n = rand.nextInt(TOTAL_MISC);
        miscs = new Texture[TOTAL_MISC];
        miscsAnima = new Animation[TOTAL_MISC];
        for(int i = 0; i < TOTAL_MISC; i ++){
            fileName = String.format("Misc/misc%1d.png", i+1);
            miscs[i] = new Texture(fileName);
            miscsAnima[i] = new Animation(new TextureRegion(miscs[i]), miscs[i].getWidth() / MISC_WIDTH, 1.0f);
        }

        position = new Vector2(x + rand.nextInt(FLUCTUATION), PlayState.GRASS_HEIGHT);
        bounds = new Rectangle(position.x, position.y, MISC_WIDTH, MISC_HEIGHT);

    }

    public void update(float dt){
        miscsAnima[n].update(dt);

    }

    public TextureRegion getTexture() {
        return miscsAnima[n].getFrame();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void reposition(float x){

        //reset tower position
        position.set(x + rand.nextInt(FLUCTUATION), PlayState.GRASS_HEIGHT);
        bounds.setPosition(position.x, position.y);
        n = rand.nextInt(TOTAL_MISC);
        System.out.println(n);
    }

    public void dispose(){

        for(int i = 0; i < TOTAL_MISC; i ++){
            miscs[i].dispose();
        }
    }
}