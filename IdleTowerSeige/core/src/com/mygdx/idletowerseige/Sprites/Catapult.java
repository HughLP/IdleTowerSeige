package com.mygdx.idletowerseige.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.idletowerseige.States.PlayState;

import java.util.Random;

//import static oracle.jrockit.jfr.events.Bits.floatValue;

/**
 * Created by Hugh on 23/11/2016.
 */

public class Catapult {
    public static final int CATAPULT_WIDTH = 128;

    private Texture catapult;
    private Animation reload;
    private Vector2 position;
    private Rectangle bounds;
    private static final int GAP = 64;
    private boolean fire;

    private float speed;
    private Vector2 ballPosition;
    private Texture ball;
    private Vector2 target;
    private Sound catapultSound;

    //vector from ball to target
    private Vector2 toTargetVec;
    private Vector2 directionalVec;
    private Vector2 shootPos;

    private double radians;
    private float deltaX;
    private float deltaY;
    private double angle;



    public Catapult(float x, float y, int cells){

        catapult = new Texture("Rooms/catapult.png");
        position = new Vector2(x, y + ((GAP * cells) + GAP));
        bounds = new Rectangle(position.x, position.y, CATAPULT_WIDTH, catapult.getHeight());
        fire = false;
        reload = new Animation(new TextureRegion(catapult), catapult.getWidth() / CATAPULT_WIDTH, 1.9f);

        ball = new Texture("Misc/ball.png");
        ballPosition = new Vector2(x + CATAPULT_WIDTH, y + catapult.getHeight());
        speed = 1;
        catapultSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/catapult.mp3"));
        toTargetVec = new Vector2();
        directionalVec = new Vector2();
        shootPos = new Vector2();
        target = new Vector2();


    }

    public void update(float dt, float x, float y, int cells) {
        position.x = x;
        position.y = y + ((GAP * cells) + GAP);
        bounds.setPosition(position.x, position.y);
        shootPos.x = (position.x + CATAPULT_WIDTH - 12);
        shootPos.y = (position.y + (catapult.getHeight() / 2) + 10);

        if (reload.checkFin()) {
            fire = false;
        }

        if (fire) {
            reload.update(dt);

            double dx = target.x - position.x; //800/2
            double dy = target.y - position.y; //240/2
            double dir = (float) Math.atan2(dy, dx);

            //nx = speed * Math.cos(radians);
            //ny = speed * Math.sin(radians);

            if(reload.getFrameNum() == 7) {
                //System.out.println("direction: " + degrees);
                ballPosition.x = shootPos.x;
                ballPosition.y = shootPos.y;

            }
            if(reload.getFrameNum() >= 8) {
                move();
            }
        }

        if (!fire || reload.getFrameNum() <= 6) {
            ballPosition.x = position.x;
            ballPosition.y = -50;
        }






    }
    public void move(){
        ballPosition.x = directionalVec.x * speed;
        ballPosition.y = directionalVec.y * speed;

        System.out.println(ballPosition.x + " : " + ballPosition.y);
    }

    public void fire(Vector3 target){
        if (!fire && shootPos.x <= target.x) {
            fire = true;
            this.target.x = target.x;
            this.target.y = target.y;
            catapultSound.play(0.1f);

            float deltaX = target.x  - shootPos.x;
            float deltaY = target.y  - shootPos.y;
            angle = Math.atan2(deltaY, deltaX);

            toTargetVec = this.target.sub(shootPos);
            directionalVec = toTargetVec.nor();


            if (reload.checkFin()) {
                reload.resetFrame();
            }
            //System.out.println("angle" + toTargetVec.angle() * 10);


        }
    }

    public TextureRegion getTexture() {
        return reload.getFrame();
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getBallTexture() {
        return ball;
    }

    public Vector2 getBallPosition() {
        return ballPosition;
    }


    public boolean clicked(Vector3 player){
        return  bounds.contains(player.x, player.y);
    }

    public void dispose(){

        catapult.dispose();
        catapultSound.dispose();
    }
}
