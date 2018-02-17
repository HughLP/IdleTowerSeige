package com.mygdx.idletowerseige.Sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Hugh on 30/09/2016.
 */
public class Wheels {
    //simple class for wheel animation

    private Animation wheels;
    private Texture texture;
    private static final int WHEEL_WIDTH = 120;
    private Vector3 position;
    private Rectangle bounds;

    public Wheels(int x, int y){
        position = new Vector3(x, y, 0);
        texture = new Texture("Rooms/wheels.png");
        wheels = new Animation(new TextureRegion(texture), texture.getWidth() / WHEEL_WIDTH, 0.5f);
        bounds = new Rectangle(x, y, WHEEL_WIDTH, texture.getHeight());
    }

    public void update(float dt, float pos){

        //update position and animates (method will only called when engine is moving)
        position.x = pos;
        wheels.update(dt);
    }

    public TextureRegion getTexture() {
        return wheels.getFrame();
    }

    public Vector3 getPosition() {
        return position;
    }

    public void dispose(){
        texture.dispose();
    }



}
