package com.mygdx.idletowerseige.Sprites;

/**
 * Created by Hugh on 30/09/2016.
 */


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Hugh on 30/09/2016.
 */
public class Explosion {
    //this class is for the explosion of the towers

    private Animation explode;
    private Texture texture;
    private static final int TOWER_WIDTH = 200;
    private Vector3 position;
    private Sound smash;

    public Explosion(float x, float y){
        position = new Vector3(x, y, 0);
        texture = new Texture("Misc/towerexplode.png");
        explode = new Animation(new TextureRegion(texture), texture.getWidth() / TOWER_WIDTH, 0.5f);
        smash = Gdx.audio.newSound(Gdx.files.internal("Sounds/blow.mp3"));
    }

    public void update(float dt){

        //if there is currently an explosion called, update animation
        if (explode.checkFin() == false) {
            explode.update(dt);
        }

    }
    public void start(Vector2 pos){

        //start new explosion
        position.x = pos.x;
        position.y = pos.y;
        explode.resetFrame();
        smash.play(0.1f);
    }

    public TextureRegion getTexture() {
        return explode.getFrame();
    }

    public Vector3 getPosition() {
        return position;
    }

    public void dispose(){
        texture.dispose();
        smash.dispose();
    }



}
