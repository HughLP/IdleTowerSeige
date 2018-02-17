package com.mygdx.idletowerseige.Progress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.utils.JsonValue.ValueType.object;

/**
 * Created by Hugh on 9/10/2016.
 */

public class Progression implements Serializable{

    //this file is for saving and loading game save data

    private static final long serialVersionUID = 1L;
    private double bank;
    private Integer distance;
    private int world;
    private int engineLevel;
    private ArrayList<String> cells;

    public Progression(){

        //set variables to 0 if its a new save
        this.bank = 0;
        this.distance = 0;
        this.world = 0;
        this.engineLevel = 0;
        this.cells = new ArrayList<String>();
    }

    public void update(double bank, Integer distance, int world, int engineLevel, ArrayList<String> cells){

        //update variable to current
        this.bank = bank;
        this.distance = distance;
        this.world = world;
        this.engineLevel = engineLevel;
        this.cells = cells;


        //System.out.println("size ---------------------------------------------------: " + cells.size());
    }
    public double getBank(){
        return bank;
    }
    public int getDistance(){
        return distance;
    }
    public int getWorld(){
        return world;
    }
    public int getEngineLevel(){
        return engineLevel;
    }
    public ArrayList<String> getCells(){
        return cells;
    }

    public static void saveProgression(Progression player) throws IOException {

        //save progression to file
        FileHandle file = Gdx.files.local("player.dat");
        OutputStream out = null;
        try{
            file.writeBytes(serialize(player), false);
        }catch(Exception ex){
            System.out.println(ex.toString());
        } finally{
            if(out !=null) try{out.close();} catch(Exception ex){};
            }
        System.out.println("Saving data");
        }

    public static Progression readProgression() throws IOException, ClassNotFoundException{
        Progression player = null;
        FileHandle file = Gdx.files.local("player.dat");
        player = (Progression) deserialize(file.readBytes());
        return player;
    }


    @SuppressWarnings("unused")
    private static byte[] serialize(Object obj) throws IOException{

        //this method serializes an object to be saved to file
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {

        //this method deserializes an object to be loaded from a file
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }
}
