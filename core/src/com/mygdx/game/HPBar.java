package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class HPBar {
    private final float HEALTH_BAR_WIDTH = 272.0f;
    private final float segmentHeight = 21.0f;
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
    private float scale;
    private float offset = 0f;

    public HPBar(boolean friendly, float totalHealth, float multipleSize, Texture hpDisplaytexture) {
        this.friendly = friendly;
        this.totalHealth = totalHealth;
        this.multipleSize = multipleSize;
        this.hpDisplaytexture = hpDisplaytexture;
        this.scale = (float) (0.75 + multipleSize / 4);
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
        int filledSegments = (int) (HEALTH_BAR_WIDTH * healthPercentage * multipleSize);
        region = new TextureRegion(hpDisplaytexture, 0, 0, filledSegments, (int) segmentHeight);
        HPSprite = new Sprite(region);
        HPSprite.setScale(1, scale);
        HPSprite.setPosition((healthBarX + HEALTH_BAR_WIDTH / 2) - (HEALTH_BAR_WIDTH * multipleSize) / 2, (healthBarY - 70) + 70 * scale + offset);

        return HPSprite;
    }

    public Sprite HPBarBackground() {
        backgroundTexture = new Texture("BarV1Background.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setPosition(healthBarX, (healthBarY - 70) + 70 * scale + offset);
        backgroundSprite.setScale(multipleSize, scale);
        return backgroundSprite;
    }

    public Sprite HPBarBorder() {
        barBorderTexture = new Texture("BarV1_ProgressBarBorder.png");
        barBorderSprite = new Sprite(barBorderTexture);
        barBorderSprite.setPosition(healthBarX - 2, (healthBarY - 70) + (70 * scale) - 2 + offset);
        barBorderSprite.setScale(multipleSize, scale);
        return barBorderSprite;
    }

    public float hPBarScale() {
        return scale;
    }

    public float getHealthBarX() {
        return healthBarX;
    }

    public float getHealthBarY() {
        return healthBarY;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }
}
