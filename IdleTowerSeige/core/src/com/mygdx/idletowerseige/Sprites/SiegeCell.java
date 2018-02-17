package com.mygdx.idletowerseige.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Hugh on 28/09/2016.
 */
public class SiegeCell {
//this class is for an array of siege cells

    private static final int CELL_WIDTH = 128;
    private static final int GAP = 64;
    private static final int NUM_COUNT = 10;
    private Vector3 position;
    private Rectangle bounds;
    private Animation animateRoom;
    private Animation animateChar;
    private Animation animateQuirk;
    private int cycle;
    private Texture room;
    private Texture character;
    private Texture quirk;
    private Array<TextureRegion> frames;
    private int profession;
    private int level;

    //the textures for the floor levels
    private Texture numbers;
    private TextureRegion number;


    public SiegeCell(float x, float y, int prof, String l){
        profession = prof;
        position = new Vector3(x, y + (GAP * profession), 0);
        level = Integer.parseInt(l);
        room = new Texture(getRoom(profession));
        character = new Texture(getChar(profession));
        quirk = new Texture("Rooms/newSword64.png");
        numbers = new Texture("Hub/numbersline.png");
        number = new TextureRegion(numbers);
        frames = new Array<TextureRegion>();

        //configuring floor level textures
        int frameWidth = number.getRegionWidth() / NUM_COUNT;
        for(int i = 0; i < NUM_COUNT; i ++){
            frames.add(new TextureRegion(number, i * frameWidth, 0, frameWidth, number.getRegionHeight()));
        }

        cycle = 0;
        animateRoom = new Animation(new TextureRegion(room), room.getWidth() / CELL_WIDTH, 0.6f);
        animateChar = new Animation(new TextureRegion(character), character.getWidth() / CELL_WIDTH, 0.6f);
        animateQuirk = new Animation(new TextureRegion(quirk), quirk.getWidth() / CELL_WIDTH, 0.6f);
        bounds = new Rectangle(x, position.y, CELL_WIDTH, room.getHeight());

    }

    public void update(float dt, Vector3 pos){
        animateRoom.update(dt);
        animateChar.update(dt);
        if (cycle >= 30) {
            animateQuirk.update(dt);
        }
        position.x = pos.x;
        bounds.setPosition(position.x, position.y);


    }


    public Vector3 getPosition() {
        return position;
    }

    public int getLevel() {
        return level;
    }
    public int leveUp() {
        //level up floor
        return level ++;
    }

    public TextureRegion getTextureRoom() {
        return animateRoom.getFrame();
    }
    public TextureRegion getTextureChar() {
        if (animateChar.checkFin()){ //if animation is finished loop
            cycle ++;
        }

        if (cycle >= 30) {

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

    public TextureRegion getFirstUnitTexture() {

        //if the level is less than 10, we want there to be a 0 first, e.g ( 5 will be: 05)
        if(level >= 10) {
            return frames.get(firstDigit(level));
        }
        else {
            return frames.get(0);
        }
    }
    public TextureRegion getSecondUnitTexture() {

        //we want to show the last digit in the number, or if number is less than 10, the number itself
        if(level >= 10) {
            return frames.get(secondDigit(level));
        }
        else {
            return frames.get(level);
        }
    }

    public  int firstDigit(int n) {

        //finds the first digit
        while (n < -9 || 9 < n) n /= 10;
        return Math.abs(n);
    }

    public int secondDigit(int n) {

        //finds the second digit
        return n % 10; }

    public void clicked(){
        System.out.println("clicked siege cell:" + profession);

    }

    public Rectangle getBounds(){

        //not currently used (for future use)
        return bounds;
    }

    public boolean checkClick(Vector3 player){

        //check is the cell is clicked by user (overlaps)
        return  bounds.contains(player.x, player.y);
    }

    public void dispose(){
        room.dispose();
        character.dispose();
        quirk.dispose();
        numbers.dispose();

    }

    public String getRoom(int prof){

        //get texture for floor, based on what profession (in future the user can choose which floor)
        if(prof == 1){
            return ("Rooms/forgeroom64.png");
        }
        /**
        else if(prof == 2){
            return ("Room/hammer64.png");
        }
        else if(prof == 3){
            return ("Room/drinkingroom.png");
        }
        else if(prof == 4){
            return ("Room/princessroom.png");
        }
        else if(prof == 5){
            return ("Room/libraryroom.png");
        }
        else if(prof == 6){
            return ("Room/apothroom.png");
        }
        else if(prof == 7){
            return ("Room/jailroomfirebeast.png");
        }
        else if(prof == 8){
            return ("Room/jailroomoctopus.png");
        }
         */

        else {
            return ("Room/forgeroom64.png");
        }
    }

    public String getChar(int prof){

        //get texture for floor, based on what profession (in future the user can choose which floor)
        if(prof == 1){
            return ("Rooms/hammer64.png");
        }


        else {
            return ("Rooms/hammer64.png");
        }
    }




}