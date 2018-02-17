package com.mygdx.idletowerseige.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.util.Random;

/**
 * Created by Hugh on 28/09/2016.
 */
public class Castle {
    public static final int CASTLE_WIDTH = 80;
    public static final int CASTLE_HEIGHT = 400;
    private static final int FLUCTUATION = 120;
    private static final int BAR = 100;
    private Texture tower;
    private Vector2 position;
    private Rectangle  bounds;
    private Random rand;

    public Castle(float x){

        tower = new Texture("Misc/tower.png");
        rand = new Random();
        position = new Vector2(x, rand.nextInt(FLUCTUATION) + BAR  - tower.getHeight());
        bounds = new Rectangle(position.x, position.y, tower.getWidth(), tower.getHeight());
    }

    public Texture getTexture() {
        return tower;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void reposition(float x){

        //reset tower position
        position.set(x, (rand.nextInt(FLUCTUATION)  + BAR)  - tower.getHeight());
        bounds.setPosition(position.x, position.y);
    }

    public boolean collides(Rectangle player){
        return  player.overlaps(bounds);
    }

    public boolean crashed(Vector2 player){
        return  bounds.contains(player.x, player.y);
    }

    public void dispose(){

        tower.dispose();
    }
}
