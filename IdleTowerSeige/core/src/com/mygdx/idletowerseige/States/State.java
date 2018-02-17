package com.mygdx.idletowerseige.States;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by Hugh on 11/09/2016.
 */
public abstract class State{
    protected OrthographicCamera cam; //camera
    protected Vector3 mouse; //camera position
    protected GameStateManager gsm; //game state manager

    protected State(GameStateManager gsm){ // manages the game states
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();


    }

    protected abstract void handleInput();
    public abstract void update (float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();


}
