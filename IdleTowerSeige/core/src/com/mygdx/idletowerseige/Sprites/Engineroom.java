package com.mygdx.idletowerseige.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Hugh on 28/09/2016.
 */
public class Engineroom {

    private static final int MOVEMENT1 = 80;
    private static final int MOVEMENT2 = 100;
    private static final int MOVEMENT3 = 110;
    private static final int TOWER_WIDTH = 144; //120 old
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation animateRoom;
    private Animation animateChar;
    private Animation animateQuirk;
    private Texture room;
    private Texture character;
    private Texture quirk;
    private int level;
    private int movement;
    private boolean stop = false;
    private int cycle;


    public Engineroom(int x, int y, int level){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        this.level = level;
        room = new Texture("Rooms/wizardRoom64.png");
        character = new Texture("Rooms/wizard64.png");
        quirk = new Texture("Rooms/wizardQuirk64.png");
        animateRoom = new Animation(new TextureRegion(room), room.getWidth() / TOWER_WIDTH, 0.6f);
        animateChar = new Animation(new TextureRegion(character), character.getWidth() / TOWER_WIDTH, 0.6f);
        animateQuirk = new Animation(new TextureRegion(quirk), quirk.getWidth() / TOWER_WIDTH, 0.6f);
        bounds = new Rectangle(x, y, TOWER_WIDTH, room.getHeight());
        cycle = 0;

    }

    public void update(float dt){

        //updates
        animateRoom.update(dt);
        animateChar.update(dt);
        if (cycle >= 20) {
            animateQuirk.update(dt);
        }
        velocity.scl(dt);
        position.add(getMovement(level) * dt, velocity.y, 0);
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }

    public void upgrade(){

        //this method is currently unused (for future use)
        level ++;
        getMovement();
    }

    public int getLevel(){
        return level;
    }

    public void setStop(boolean stop){
        this.stop = stop;
    }
    public boolean getStop(){

        //check is engine has stopped
        return stop;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTextureRoom() {
        return animateRoom.getFrame();
    }
    public TextureRegion getTextureChar() {
        if (animateChar.checkFin()){ //if animation is finished loop
            cycle ++;

        }

        if (cycle >= 20) {

            if (animateQuirk.checkFin()){
                animateQuirk.resetFrame();
                cycle = 0;
            }
            return animateQuirk.getFrame();
        }
        else {
            return animateChar.getFrame();
        }
    }

    public int getMovement() {
        return movement;
    }


    public void clicked(){
        //this method is currently unused (for future use)
        System.out.println("clicked tower");

    }

    public Rectangle getBounds(){
        return bounds;
    }

    public boolean checkClick(Vector3 player){
        //check if it overlaps
        return  bounds.contains(player.x, player.y);
    }

    public void dispose(){
        room.dispose();
        character.dispose();
        quirk.dispose();

    }

    public int getMovement(int lev){

        //this method is currently un useful (for future use)
        if( lev == 0){
            movement = MOVEMENT1;
        }
        else if( lev == 1){
            movement = MOVEMENT2;
        }
        else if( lev == 2){
            movement = MOVEMENT3;
        }
        else{
            movement = MOVEMENT1;
        }
        return movement;
    }


}