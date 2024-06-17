package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ProgressBar {
    private final float progressBarWidth = 272f;
    private final float progressBarHeight = 15f;
    private boolean isKeyPressed;
    private float timeElapsed;
    private float progressBarMaxTime;
    private static Texture barTexture = new Texture("progressBar.png");
    private static Texture barBackgroundTexture = new Texture("progressBarBackground.png");
    private TextureRegion region;
    private TextureRegion backGroundTextureRegion;


    public ProgressBar(boolean isKeyPressed, float progressBarMaxTime) {
        this.isKeyPressed = isKeyPressed;
        this.timeElapsed = 0;
        this.progressBarMaxTime = progressBarMaxTime;
    }

    public TextureRegion progressBarDisplay(float deltaTime) {
        timeElapsed += deltaTime;
        if (timeElapsed > progressBarMaxTime) {
            timeElapsed = progressBarMaxTime; // Cap the time elapsed to max time
            isKeyPressed = false; // Stop progressing once max time is reached
        }
        int filledSegments = (int) (progressBarWidth * (timeElapsed / progressBarMaxTime));
        region = new TextureRegion(barTexture, 0, 0, filledSegments, (int) progressBarHeight);

        return region;
    }

    public TextureRegion progressBarBackground() {
        backGroundTextureRegion = new TextureRegion(barBackgroundTexture, 0, 0, (int) progressBarWidth, (int) progressBarHeight);
        return backGroundTextureRegion;
    }

    public void setIsKeyPressed(boolean isKeyPressed) {
        this.isKeyPressed = isKeyPressed;
    }

    public boolean getIsKeyPressed() {
        return isKeyPressed;
    }

    public void setTimeElapsed(float timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}
