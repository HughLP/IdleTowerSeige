package com.mygdx.idletowerseige;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.idletowerseige.States.GameStateManager;
import com.mygdx.idletowerseige.States.MenuState;
import com.mygdx.idletowerseige.States.PlayState;

public class IdleTowerSeige extends ApplicationAdapter {
	public static final int WIDTH = 800; //height and width of view
	public static final int HEIGHT = 480;

	public static final String TITLE = "Idle Siege Tower"; //title
	private GameStateManager gsm;
	public static SpriteBatch batch;

	private Music music;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		music = Gdx.audio.newMusic(Gdx.files.internal("Music/timeOfSolitude.mp3"));


		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();
		Gdx.gl.glClearColor(0, 0, 0, 1); //wipes screen clear so sprite batch can re-draw everything
		gsm.push(new PlayState(gsm));
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void pause () {
		System.out.println("paused");

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
	}


}
