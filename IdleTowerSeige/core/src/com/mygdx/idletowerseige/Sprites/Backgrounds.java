package com.mygdx.idletowerseige.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by Hugh on 28/09/2016.
 */
public class Backgrounds {
    //this class manages backgrounds

    //movement speeds
    private static final int MOVEMENT_Trees = 50;
    private static final int MOVEMENT_SKY = 70;

    private static final int GROUND_Y_OFFSET = -100;
    private Texture trees;
    private Texture sky;
    private Texture ground;
    private Vector2 treesPos1, treesPos2;
    private Vector2 groundPos1, groundPos2;
    private Vector2 skyPos1, skyPos2;

    public Backgrounds(float x, float width){

        //textures
        ground = new Texture("Backgrounds/grass1.png");
        sky = new Texture("Backgrounds/sky.png");
        trees = new Texture("Backgrounds/trees2.png");

        //positioning
        groundPos1 = new Vector2((x - width) / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2(((x - width) / 2) + ground.getWidth(), GROUND_Y_OFFSET);
        skyPos1 = new Vector2((x - width) / 2, 0);
        skyPos2 = new Vector2(((x - width) / 2) + ground.getWidth(), 0);
        treesPos1 = new Vector2((x - width) / 2, 0);
        treesPos2 = new Vector2(((x - width) / 2) + ground.getWidth(), 0);
    }

    public void update(float dt){

        //update movement
        treesPos1.add(MOVEMENT_Trees * dt, 0);
        skyPos1.add(MOVEMENT_SKY * dt, 0);
        treesPos2.add(MOVEMENT_Trees * dt, 0);
        skyPos2.add(MOVEMENT_SKY * dt, 0);
    }
    public void updateSky(float dt){

        //if engine has stopped, sky most move inderpendently
        skyPos1.add(-10 * dt, 0);
        skyPos2.add(-10 * dt, 0);
    }

    //get positions
    public Vector2 getGroundPos1() {
        return groundPos1;
    }
    public Vector2 getGroundPos2() {
        return groundPos2;
    }
    public Vector2 getSkyPos1() {
        return skyPos1;
    }
    public Vector2 getSkyPos2() {
        return skyPos2;
    }
    public Vector2 getTreesPos1() {
        return treesPos1;
    }
    public Vector2 getTreesPos2() {
        return treesPos2;
    }

    //get textures
    public Texture getTextureSky() {
        return sky;
    }
    public Texture getTextureTrees() {
        return trees;
    }
    public Texture getTextureGround() {
        return ground;
    }


    public void dispose(){
        ground.dispose();
        trees.dispose();
        sky.dispose();

    }

    public void updateBackgrounds(float x, float w){

        //update positioning
        if((x - w / 2) > groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if((x - w / 2) > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth() * 2, 0);
        }
        if((x - w / 2) > skyPos1.x + sky.getWidth()){
            skyPos1.add(sky.getWidth() * 2, 0);
        }
        if((x - w / 2) > skyPos2.x + sky.getWidth()){
            skyPos2.add(sky.getWidth() * 2, 0);
        }
        if((x - w / 2) > treesPos1.x + trees.getWidth()){
            treesPos1.add(trees.getWidth() * 2, 0);
        }
        if((x - w / 2) > treesPos2.x + trees.getWidth()){
            treesPos2.add(trees.getWidth() * 2, 0);
        }
    }
}