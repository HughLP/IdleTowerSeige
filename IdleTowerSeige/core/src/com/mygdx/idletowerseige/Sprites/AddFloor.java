package com.mygdx.idletowerseige.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Hugh on 11/10/2016.
 */

public class AddFloor {
    //this object is the add floor button

    private static final int ADD_WIDTH = 120;
    private static final int GAP = 64;
    private Vector3 position;
    private Rectangle bounds;
    private Texture texture;



    public AddFloor(float x, float y, int cells){

        //set position to be above the highest cell
        position = new Vector3(x, y + ((GAP * cells) + GAP), 0);
        texture = new Texture("Hub/addfloor.png");
        bounds = new Rectangle(x, position.y, ADD_WIDTH, texture.getHeight());
    }

    public void update(float x, float y, int cells){

        //set position to be above the highest cell
        position.x = x;
        position.y = y + ((GAP * cells) + GAP);
        bounds.setPosition(position.x, position.y);
    }


    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

    public void clicked(){
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public boolean checkClick(Vector3 player){

        //check of object overlaps
        return  bounds.contains(player.x, player.y);
    }

    public void dispose(){
        texture.dispose();
    }






}