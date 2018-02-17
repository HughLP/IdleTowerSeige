package com.mygdx.idletowerseige.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.idletowerseige.IdleTowerSeige;
import java.util.Locale;


/**
 * Created by Hugh on 5/10/2016.
 */

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;
    private Integer distance;
    private boolean move;
    private float timeCount;
    private double score;

    //top layer
    private Label bankLabel;
    private Label gpsLabel;
    private Label distanceLabel;
    private Label worldLabel;
    //bottom layer
    private Label goldLabel;
    private Label gpsScoreLabel;
    private Label countDistLabel;
    private Label levelLabel;

    //bank calculations
    private double bank;
    private int world;
    private double rate;

    public Hud(SpriteBatch sb, double bank, Integer distance, int world){
        timeCount = 0;
        score = 0.0;
        move = true;

        //bank
        this.bank = bank;
        this.world = world;
        this.distance = distance;
        this.rate = 0.0;

        //viewport
        viewport = new FitViewport(IdleTowerSeige.WIDTH, IdleTowerSeige.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //set up table
        Table table = new Table();
        table.top();//put it at the top of stage
        table.setFillParent(true);

        //top layer
        bankLabel = new Label("BANK", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        gpsLabel = new Label("GOLD PER/SEC", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        distanceLabel = new Label("DISTANCE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //bottom layer
        goldLabel = new Label(String.format(Locale.ENGLISH, "%.2f", this.bank), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        gpsScoreLabel = new Label(String.format(Locale.ENGLISH, "%.2f", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        countDistLabel = new Label(String.format(Locale.ENGLISH, "%6d", this.distance), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(String.format(Locale.ENGLISH,"%1d", this.world), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add to table, first row
        table.add().expandX().padTop(10);
        table.add().expandX().padTop(10);
        table.add().expandX().padTop(10);
        table.add().expandX().padTop(10);
        table.add(bankLabel).expandX().padTop(10);
        table.add(gpsLabel).expandX().padTop(10);
        table.add(distanceLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);

        //new row
        table.row();

        //second row
        table.add().expandX().padTop(10);
        table.add().expandX().padTop(10);
        table.add().expandX().padTop(10);
        table.add().expandX().padTop(10);
        table.add(goldLabel).expandX();
        table.add(gpsScoreLabel).expandX();
        table.add(countDistLabel).expandX();
        table.add(levelLabel).expandX();

        //add tbale to stage
        stage.addActor(table);

    }

    public void update(float dt){

        //update hud with current game values
        timeCount += dt;
        if(timeCount >= 1){

            //update bank and gps
            bank += score;
            goldLabel.setText(String.format(Locale.ENGLISH, "%.2f", bank));
            gpsScoreLabel.setText(String.format(Locale.ENGLISH, "%.2f", score));
            score = 0.00;

            //update distance if engine is moving
            if (move) {
                distance++;
                move = false;
            }
            countDistLabel.setText(String.format(Locale.ENGLISH, "%6d", distance));

            //update world (currently not used in game, until further expansion)
            levelLabel.setText(String.format(Locale.ENGLISH, "%1d", world + 1));

            //reset time
            timeCount = 0;
        }
    }

    public double getBank(){
        return bank;
    }
    public Integer getDistance(){
        return distance;
    }
    public int getWorld(){
        return world;
    }
    public void purchase(double p){
        //made purchase, detract from bank
        bank -= p;
    }

    public void gps(int level){
        //update gold earned in that second
        score += (level / worldExchange(world));

    }

    public void distance(float dt){
            move = true;
        }

    public void towerKill(){
        bank += 100;
    }
    public void setWorld(int world){
        //this method is currently unused, will have future use
        this.world = world;
    }

    private double worldExchange(int world){

        //the further you progress in the game the more you will earn (currently unused, here for future use)
        if(world == 0){
            return rate = 10.0;
        }
        else if(world == 1){
            return rate = 7.5;
        }
        else if(world == 2){
            return rate = 5;
        }
        else {
            return rate = 10.0;
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
