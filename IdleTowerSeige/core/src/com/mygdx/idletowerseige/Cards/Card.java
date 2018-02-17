package com.mygdx.idletowerseige.Cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.idletowerseige.Sprites.Animation;

/**
 * Created by Hugh on 13/02/2018.
 */


public class Card {
//this class is for an array of siege cells

    private static final int HEIGHT = 64;
    private static final int WIDTH = 50;
    private Texture art;
    private int ID;
    private String Name;
    private int HP;
    private int ATK;
    private int DEF;
    private String Style;
    private int Hunger;
    private Array<String> stats; //ID; Name; HP; ATK; DEF; Style; Hunger; Rarity

    private Vector3 position;
    private Rectangle bounds;


    public Card(int id, float x, float y){
        setStats(id);
        position = new Vector3(x, y, 0);
        bounds = new Rectangle(position.x, position.y, WIDTH, art.getHeight());

        }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position = new Vector3(x, y, 0);
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
        art.dispose();
    }

    public int getHP(){
        return this.HP;
    }

    public void setStats(int id){
        this.ID = id;

        //get stats based on id
        if(ID == 0){
            Name = "Kaitlin Olan";
            HP = 30;
            ATK = 12;
            DEF = 5;
            Style = "melee";
            Hunger = 8;
            art = new Texture("Cards/KaitlinOlan.png");
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
            Name = "Kaitlin Olan";
            HP = 30;
            ATK = 12;
            DEF = 5;
            Style = "melee";
            Hunger = 8;
            art = new Texture("Cards/KaitlinOlan.png");
        }
    }



}
