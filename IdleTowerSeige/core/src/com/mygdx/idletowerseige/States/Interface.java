package com.mygdx.idletowerseige.States;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.idletowerseige.IdleTowerSeige;
import com.mygdx.idletowerseige.Sprites.Animation;





/**
 * Created by Hugh on 28/09/2016.
 */
public class Interface{
    private static final int INTERFACE_WIDTH = 360;
    private static final int OPEN_POSITION = 120;
    private static final int CLOSE_POSITION = 400;
    private Vector3 position;
    private Rectangle bounds;
    private Texture texture;
    private int profession;
    private int level;
    private Animation animate;
    private Vector3 velocity;
    private boolean open;
    private boolean stopped;



    public Interface(float x, float y, int prof){


        position = new Vector3(x + 400, y, 0);
        velocity = new Vector3(0, 0, 0);
        this.profession = prof;
        level = 1;
        open = false;
        stopped = false;
        texture = new Texture(getProfession(profession));
        animate = new Animation(new TextureRegion(texture), texture.getWidth() / INTERFACE_WIDTH, 0.7f);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());


    }


    public void update(float dt, int movement, float posx, Vector3 camPos){
        animate.update(dt);
        position.y = camPos.y - 90;



        //opening
        if(open && position.x > posx + OPEN_POSITION ){
            velocity.scl(dt);
            position.add(-(movement * dt) * 8, 0, 0);
            velocity.scl(1/dt);
            //System.out.println("state 1: ");
        }
        else if(open && position.x <= posx + OPEN_POSITION){
            position.x = posx + OPEN_POSITION;
            //System.out.println("state 2: ");
        }


        //closing
        else if(!open && position.x < posx + CLOSE_POSITION){
            velocity.scl(dt);
            position.add((movement * dt) * 8, 0, 0);
            velocity.scl(1/dt);
            //System.out.println("state 3: ");
        }
        else if(!open && stopped && position.x >= posx + CLOSE_POSITION){
            position.x = posx + CLOSE_POSITION;
            //System.out.println("state 4: ");
        }


        //if other
        else {
            velocity.scl(dt);
            position.add(movement * dt, 0, 0);
            velocity.scl(1/dt);
            //System.out.println("state 5: " + posx);
        }

        bounds.setPosition(position.x, position.y);
    }



    public void open(){
        open = true;
    }
    public void close(){
        open = false;
    }
    public void stopped(){
        stopped = true;
    }
    public void started(){
        stopped = false;
    }


    public Vector3 getPosition() {
        return position;
    }


    public TextureRegion getTexture() {
        return animate.getFrame();
    }

    public void clicked(){
        System.out.println("clicked siege cell");

    }

    public Rectangle getBounds(){
        return bounds;
    }

    public boolean checkClick(Vector3 player){
        return  bounds.contains(player.x, player.y);
    }

    public void dispose(){
        texture.dispose();

    }

    public String getProfession(int prof){

        //gets texture based on profession of coresponding room
        if(prof == 1){
            return ("Rooms/forgeroominter.png");
        }
        else if(prof == 2){
            return ("Rooms/trainingroominter.png");
        }
        else if(prof == 3){
            return ("Rooms/drinkingroominter.png");
        }
        else if(prof == 4){
            return ("Rooms/princessroomintern.png");
        }
        else if(prof == 5){
            return ("Rooms/libraryroomintern.png");
        }
        else if(prof == 6){
            return ("Rooms/apothroomintern.png");
        }
        else if(prof == 7){
            return ("Rooms/firejailintern.png");
        }
        else if(prof == 8){
            return ("Rooms/jailoctoroomintern.png");
        }

        else {
            return ("Rooms/engineroominter.png");
        }
    }




}