package com.mygdx.idletowerseige.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.idletowerseige.IdleTowerSeige;
//import android.content.SharedPreferences;
import java.io.File;


/**
 * Created by Hugh on 28/09/2016.
 */
public class MenuState extends State{

    //textures
    private Texture background;
    private Texture title;



    public MenuState(GameStateManager gsm) {
        super(gsm);
        //camera
        cam.setToOrtho(false, IdleTowerSeige.WIDTH / 2, IdleTowerSeige.HEIGHT / 2);

        //background textures
        background = new Texture("Backgrounds/bg2f.png");
        title = new Texture("Hub/title.png");
    }

    @Override
    public void handleInput() {

        //when touched start play state
        if(Gdx.input.justTouched()){
            gsm.pop();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(title, cam.position.x - title.getWidth() / 2, cam.position.y - 100);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        title.dispose();
        System.out.println("Menu State Dispose");
    }
}
