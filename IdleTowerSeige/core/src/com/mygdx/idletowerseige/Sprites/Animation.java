package com.mygdx.idletowerseige.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Hugh on 28/09/2016.
 */
public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    private boolean finished;

    public Animation(TextureRegion region, int frameCount, float cycleTime){

        //creates texture regions of image by cutting it up into small frames
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for(int i = 0; i < frameCount; i ++){
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt){

        //moves to next frame
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount){
            frame = 0;
        }
    }

    public TextureRegion getFrame(){

        //get current frame
        return frames.get(frame);
    }

    public boolean checkFin(){
        finished = false;
        //check if animation has finished
        if(frame == frameCount - 1){
            finished = true;
        }

        return finished;
    }

    public int getFrameNum(){
        return frame;
    }

    public void resetFrame(){
        //reset animation
        this.frame = 0;
    }
    public void setFrame(int frame){
        //set animation
        this.frame = frame;
    }

}
