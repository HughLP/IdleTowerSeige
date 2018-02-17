package com.mygdx.idletowerseige.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.idletowerseige.States.PlayState;

import java.util.Random;

/**
 * Created by Hugh on 22/11/2016.
 */

public class Battle {
    public static final int BATTLE_WIDTH = 256;
    private static final int FLUCTUATION = 600;

    private Texture battle;
    private Animation fight;
    private Vector2 position;
    private Rectangle bounds;
    private Random rand;

    public Battle(float x){

        battle = new Texture("Misc/fightOne64.png");
        rand = new Random();
        position = new Vector2(x + rand.nextInt(FLUCTUATION), PlayState.GRASS_HEIGHT);
        bounds = new Rectangle(position.x, position.y, BATTLE_WIDTH, battle.getHeight());

        fight = new Animation(new TextureRegion(battle), battle.getWidth() / BATTLE_WIDTH, 1.2f);
    }

    public void update(float dt){
        fight.update(dt);

    }

    public TextureRegion getTexture() {
        return fight.getFrame();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void reposition(float x){

        //reset tower position
        position.set(x + rand.nextInt(FLUCTUATION), PlayState.GRASS_HEIGHT);
        bounds.setPosition(position.x, position.y);
    }

    public boolean collides(Rectangle player){
        return  player.overlaps(bounds);
    }

    public boolean clicked(Vector3 player){
        return  bounds.contains(player.x, player.y);
    }

    public void dispose(){

        battle.dispose();
    }
}