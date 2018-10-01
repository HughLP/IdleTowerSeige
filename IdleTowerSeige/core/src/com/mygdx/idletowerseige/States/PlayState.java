package com.mygdx.idletowerseige.States;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.idletowerseige.IdleTowerSeige;
import com.mygdx.idletowerseige.Progress.Progression;
import com.mygdx.idletowerseige.Scenes.Hud;
import com.mygdx.idletowerseige.Sprites.AddFloor;
import com.mygdx.idletowerseige.Sprites.Backgrounds;
import com.mygdx.idletowerseige.Sprites.Battle;
import com.mygdx.idletowerseige.Sprites.Castle;
import com.mygdx.idletowerseige.Sprites.Catapult;
import com.mygdx.idletowerseige.Sprites.Engineroom;
import com.mygdx.idletowerseige.Sprites.Explosion;
import com.mygdx.idletowerseige.Sprites.Misc;
import com.mygdx.idletowerseige.Sprites.SiegeCell;
import com.mygdx.idletowerseige.Sprites.Wheels;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Hugh on 28/09/2016.
 */
public class PlayState extends State {
    private static final int CASTLE_SPACING = 1000; //spacing between the towers
    private static final int BATTLE_SPACING = 750;
    private static final int MISC_SPACING = 400;
    private static final int SCROLL_SPEED = 8; //speed at which you can scroll up and down
    private static final int STARTING_X = 172; // x and y of engine room
    private static final int STARTING_Y = 16;
    public static final int GRASS_HEIGHT = 8;
    private static final int NUM_MISCS = 6; // number of miscs at one time

    private Engineroom engine;
    private Wheels wheel;
    private Explosion explode;
    private Backgrounds backgrounds;
    private Texture numbers;
    private Castle tower;
    private Battle battle;
    private float tilt;
    private float newTilt;
    private boolean order;
    private Array<SiegeCell> cells;
    private Array<Interface> interfaces;
    private Array<Misc> miscs;
    private IdleTowerSeige game;
    private Hud hud;
    private Progression player;
    private AddFloor addFloor;
    private Catapult catapult;



    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, IdleTowerSeige.WIDTH / 2, IdleTowerSeige.HEIGHT / 2); //set up camera


        //if there is a save folder load the game
        if(Gdx.files.local("player.dat").exists()){
            try {
                player = Progression.readProgression(); //load file


            }catch(ClassNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }

            //load previous saved data
            hud = new Hud(game.batch, player.getBank(), player.getDistance(), player.getWorld());
            engine = new Engineroom(STARTING_X, STARTING_Y, player.getEngineLevel());
            cells = new Array<SiegeCell>();
            interfaces = new Array<Interface>();
            ArrayList<String>  sCells = player.getCells();

            for (int i = 0; i < sCells.size(); i++) {
                addCell(sCells.get(i));
            }
        }


        //if there is not a save folder set objects to 0
        else {
            player = new Progression();

            try {
                Progression.saveProgression(player); //save new default file
            }catch (IOException e) {
                e.printStackTrace();
            }
            //set objects variables to 0
            hud = new Hud(game.batch, 0, 0, 0);
            engine = new Engineroom(STARTING_X, STARTING_Y, 0);
            cells = new Array<SiegeCell>();
            interfaces = new Array<Interface>();
        }

        //initilise object positions
        wheel = new Wheels(STARTING_X, GRASS_HEIGHT);
        tower = new Castle(CASTLE_SPACING + Castle.CASTLE_WIDTH);
        battle = new Battle(BATTLE_SPACING + Battle.BATTLE_WIDTH);
        explode = new Explosion(tower.getPosition().x, tower.getPosition().y);
        backgrounds = new Backgrounds(cam.position.x, cam.viewportWidth);
        numbers = new Texture("Hub/numbers.png");
        addFloor = new AddFloor(STARTING_X, engine.getPosition().y, cells.size);
        catapult = new Catapult(STARTING_X, engine.getPosition().y, cells.size);
        this.game = game;

        miscs = new Array<Misc>();
        for(int i = 0; i < NUM_MISCS; i ++){
            miscs.add(new Misc(MISC_SPACING + Misc.MISC_WIDTH));
        }


    }

    //add a new tower cell
    private void addCell(String level){
        if(cells.size <= 18){
            int profession = cells.size  + 1;
            //add new floor
            cells.add(new SiegeCell(engine.getPosition().x, engine.getPosition().y, profession, level));
            // add corresponding interface to floor
            interfaces.add(new Interface(cam.position.x, cam.position.y - 90, profession));

        }
    }

    @Override
    protected void handleInput() {

        //if the screen is touched (not held)
        if(Gdx.input.justTouched()) {
            //gsm.push(new DeckState(gsm));
            order = false;

            //save phone tilt position
            tilt = Gdx.input.getAccelerometerX();

            //save clicked coordinates
            Vector3 click = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            //System.out.println(click.x + " : " + click.y);
            cam.unproject(click);

            catapult.fire(click);

            //if the engine is clicked
            if (engine.checkClick(click)) {
                engine.clicked();
                order = true;
            }

            //if the addroom is clicked
            if (addFloor.checkClick(click)) {
                if (hud.getBank() >= (200 * cells.size) + 200) {
                    hud.purchase((200 * cells.size) + 200);
                    addFloor.clicked();
                    addCell("1");
                    System.out.println("added new floor");
                }
                else {
                    System.out.println("not enough gold to add floor");
                }
                order = true;
            }

            //if the siege cells are clicked
            for(int i = 0; i <cells.size; i++) {
                SiegeCell currentCell = cells.get(i);
                Interface currentInter = interfaces.get(i);
                if (currentCell.checkClick(click)) {
                    for(int i2 = 0; i2 <interfaces.size; i2++) {
                        Interface currentInter2 = interfaces.get(i2);
                        currentInter2.close();
                    }
                    currentCell.clicked();
                    //open the coresponding interface
                    currentInter.open();
                    order = true;

                }

                //if interface is clicked
                if (currentInter.checkClick(click)) {
                    if (hud.getBank() >= (100 * currentCell.getLevel()) && currentCell.getLevel() < 99) {
                        hud.purchase(100 * currentCell.getLevel());
                        //level up room
                        currentCell.leveUp();
                        System.out.println("leveled up room");
                    }
                    else{
                        System.out.println("not enough gold to level up");
                    }
                    order = true;
                }
            }
            //if the castle is clicked

        }

        //if the screen is being held down and none of the other objects have been touched
        if(Gdx.input.isTouched() && !order) {
            for(int i = 0; i <interfaces.size; i++) {
                Interface currentInter = interfaces.get(i);
                currentInter.close();
            }

                //tilt screen depending on direction
                if (tilt < newTilt -0.75 && (cam.position.y - (cam.viewportHeight / 2)) >= SCROLL_SPEED) {//-1
                    cam.position.y -= SCROLL_SPEED;
                }
                if (tilt > newTilt +0.75 && (cam.position.y + (cam.viewportHeight / 2)) <= (backgrounds.getTextureSky().getHeight() - SCROLL_SPEED)) {//+1
                    cam.position.y += SCROLL_SPEED;
                }
            }
        }




    @Override
    public void update(float dt) {
        //save tile position
        newTilt = Gdx.input.getAccelerometerX();
        //check inputs
        handleInput();

        //if engine room is stopped by a tower, don't update the following
        if(!engine.getStop()) {
            engine.update(dt);
            wheel.update(dt, engine.getPosition().x);
            backgrounds.update(dt);
            hud.distance(dt);

        }
        else{
            //sky will move regardless of if engine is moving
            backgrounds.updateSky(dt);

        }

        backgrounds.updateBackgrounds(cam.position.x, cam.viewportWidth);


        if (tower.crashed(catapult.getBallPosition())) {
            hud.towerKill();

            explode.start(tower.getPosition());
            tower.reposition(tower.getPosition().x + ((Castle.CASTLE_WIDTH + CASTLE_SPACING)));
            //save progress to file
            
            try {
                Progression.saveProgression(player);
            }catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("game saved");


            //order = true;
        }




        explode.update(dt);

        //update cam
        cam.position.x = engine.getPosition().x + STARTING_X;

        // if tower goes to far move it back
        if(cam.position.x - (cam.viewportWidth / 2) > tower.getPosition().x + tower.getTexture().getWidth()){
                tower.reposition(tower.getPosition().x + ((Castle.CASTLE_WIDTH + CASTLE_SPACING)));
        }

        battle.update(dt);
        if(cam.position.x - (cam.viewportWidth / 2) > battle.getPosition().x + Battle.BATTLE_WIDTH){
            battle.reposition(battle.getPosition().x + ((Battle.BATTLE_WIDTH + BATTLE_SPACING)));

        }
        for(int i = 0; i < NUM_MISCS; i ++) {
            Misc currentMisc = miscs.get(i);
            currentMisc.update(dt);
            if (cam.position.x - (cam.viewportWidth / 2) > currentMisc.getPosition().x + Misc.MISC_WIDTH) {
                currentMisc.reposition(currentMisc.getPosition().x + ((Misc.MISC_WIDTH + MISC_SPACING)));

            }
        }


        //if engine room hits tower, stop engine room
        //if(tower.collides(engine.getBounds())){
        //    engine.setStop(true);
        //}
        //else{
        //    engine.setStop(false);
        //}

        //update cells and interfaces
        ArrayList<String> levels = new ArrayList<String>();
        for(int i = 0; i <cells.size; i++){
            SiegeCell currentCell = cells.get(i);
            currentCell.update(dt, engine.getPosition());
            Interface currentInter = interfaces.get(i);
            currentInter.update(dt, engine.getMovement(), engine.getPosition().x, cam.position);
            levels.add(String.valueOf(currentCell.getLevel()));

            if(!engine.getStop()) {
                hud.gps(currentCell.getLevel());
                currentInter.started();
            }
            else {
                currentInter.stopped();
            }

        }

        addFloor.update(engine.getPosition().x, engine.getPosition().y, cells.size);
        catapult.update(dt, engine.getPosition().x, engine.getPosition().y, cells.size);
        hud.update(dt);
        player.update(hud.getBank(), hud.getDistance(), hud.getWorld(), engine.getLevel(), levels);
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);

        //begin drawing in order
        sb.begin();

        //backgrounds
        sb.draw(backgrounds.getTextureSky(), backgrounds.getSkyPos1().x, backgrounds.getSkyPos1().y);
        sb.draw(backgrounds.getTextureSky(), backgrounds.getSkyPos2().x, backgrounds.getSkyPos2().y);
        sb.draw(backgrounds.getTextureTrees(), backgrounds.getTreesPos1().x, backgrounds.getTreesPos1().y);
        sb.draw(backgrounds.getTextureTrees(), backgrounds.getTreesPos2().x, backgrounds.getTreesPos2().y);

        //draw numbers if screen is touched
        if(Gdx.input.isTouched() && !order) {
            sb.draw(numbers, cam.position.x + (cam.viewportWidth / 2) -55, -12);
        }

        //objects to draw
        sb.draw(engine.getTextureRoom(), engine.getPosition().x, engine.getPosition().y);
        sb.draw(engine.getTextureChar(), engine.getPosition().x, engine.getPosition().y);
        sb.draw(wheel.getTexture(), wheel.getPosition().x, wheel.getPosition().y);
        sb.draw(explode.getTexture(), explode.getPosition().x, explode.getPosition().y);
        sb.draw(tower.getTexture(), tower.getPosition().x, tower.getPosition().y);


        for(int i = 0; i < NUM_MISCS; i ++) {
            Misc currentMisc = miscs.get(i);
            sb.draw(currentMisc.getTexture(), currentMisc.getPosition().x, currentMisc.getPosition().y);
        }
        //sb.draw(battle.getTexture(), battle.getPosition().x, battle.getPosition().y);

        //cells to draw
        for (SiegeCell currentCell : cells) {
            sb.draw(currentCell.getTextureRoom(), currentCell.getPosition().x, currentCell.getPosition().y);
            sb.draw(currentCell.getTextureChar(), currentCell.getPosition().x, currentCell.getPosition().y);
            sb.draw(currentCell.getFirstUnitTexture(), cam.position.x - (cam.viewportWidth / 2) - 2, currentCell.getPosition().y + 20);
            sb.draw(currentCell.getSecondUnitTexture(), cam.position.x - (cam.viewportWidth / 2) + 15, currentCell.getPosition().y + 20);
        }

        //interfaces to draw
        for (Interface currentInter : interfaces) {
            sb.draw(currentInter.getTexture(), currentInter.getPosition().x, currentInter.getPosition().y);
        }

        //draw add floor of you have enough gold
        if (hud.getBank() >= (200 * cells.size) + 200) {
            //sb.draw(addFloor.getTexture(), addFloor.getPosition().x, addFloor.getPosition().y);
        }
        sb.draw(catapult.getTexture(), catapult.getPosition().x, catapult.getPosition().y);
        sb.draw(catapult.getBallTexture(), catapult.getBallPosition().x, catapult.getBallPosition().y);

        //draw foreground
        sb.draw(backgrounds.getTextureGround(), backgrounds.getGroundPos1().x, backgrounds.getGroundPos1().y);
        sb.draw(backgrounds.getTextureGround(), backgrounds.getGroundPos2().x, backgrounds.getGroundPos2().y);

        //end draw screen
        sb.end();

        //draw hud stage
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void dispose() {

        //dispose list
        engine.dispose();
        wheel.dispose();
        explode.dispose();
        backgrounds.dispose();
        tower.dispose();
        battle.dispose();
        numbers.dispose();
        hud.dispose();
        addFloor.dispose();
        catapult.dispose();

        for(SiegeCell currentCell : cells){
            currentCell.dispose();
        }
        for (Interface currentInter : interfaces) {
            currentInter.dispose();
        }

        for(Misc currentMisc : miscs){
            currentMisc.dispose();
        }
    }
}