package com.mygdx.idletowerseige.States;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Hugh on 15/09/2016.
 */
public class GameStateManager {

    private Stack<State> states;

    public GameStateManager(){
        states = new Stack<State>();

    }

    //adds a new state to top of stack
    public void push(State state){
        states.push(state);
    }

    //remove current state (state on top of stack)
    public void pop(){
        states.pop().dispose();
    }

    //removes current and adds new state
    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void sort(String st){

    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
}
