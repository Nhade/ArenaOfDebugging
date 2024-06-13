package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class HPBar {
    private float HEALTH_BAR_WIDTH = 272.0f;
    private float segmentHeight = 21.0f;
    private boolean friendly;
    private float currentHealth;
    private float totalHealth;
    private float healthBarX;
    private float healthBarY;
    private Texture hpDisplaytexture;
    private Texture backgroundTexture;
    private Texture barBorderTexture;
    private TextureRegion region;
    private Sprite HPSprite;
    private Sprite backgroundSprite;
    private Sprite barBorderSprite;
    private float multipleSize;

    public HPBar(boolean friendly, float totalHealth, float multipleSize, Texture hpDisplaytexture) {
        this.friendly = friendly;
        this.totalHealth = totalHealth;
        this.multipleSize = multipleSize;

        HEALTH_BAR_WIDTH = HEALTH_BAR_WIDTH * multipleSize;
        segmentHeight = segmentHeight * multipleSize;

        this.hpDisplaytexture = hpDisplaytexture;
    }

    public void updateHealth(float currentHealth, float totalHealth) {
        this.currentHealth = currentHealth;
        this.totalHealth = totalHealth;
    }

    public void updatePosition(float x, float y) {
        healthBarX = x - HEALTH_BAR_WIDTH / 2;
        healthBarY = y + 70;
    }

    public Sprite HPBarDisplay() {
        float healthPercentage = currentHealth / totalHealth;
        int filledSegments = (int) (HEALTH_BAR_WIDTH * healthPercentage);
        region = new TextureRegion(hpDisplaytexture, 0, 0, filledSegments, 21);
        HPSprite = new Sprite(region);
        HPSprite.setPosition(healthBarX, healthBarY);

        return HPSprite;
    }

    public Sprite HPBarBackground() {

        backgroundTexture = new Texture("BarV1Background.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setPosition(healthBarX, healthBarY);
        return backgroundSprite;
    }

    public Sprite HPBarBorder() {
        barBorderTexture = new Texture("BarV1_ProgressBarBorder.png");
        barBorderSprite = new Sprite(barBorderTexture);
        barBorderSprite.setPosition(healthBarX, healthBarY);
        return barBorderSprite;
    }
}
