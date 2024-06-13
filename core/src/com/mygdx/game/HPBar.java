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
    private Texture texture;
    private Texture backgroundTexture;
    private Texture barBorderTexture;
    private TextureRegion region;
    private Sprite HPSprite;
    private Sprite backgroundSprite;
    private Sprite barBorderSprite;
    private float multipleSize;

    public HPBar(boolean friendly, float totalHealth, float multipleSize){
        this.friendly = friendly;
        this.totalHealth = totalHealth;
        this.multipleSize = multipleSize;

        HEALTH_BAR_WIDTH = HEALTH_BAR_WIDTH * multipleSize;
        segmentHeight = segmentHeight * multipleSize;

        if (friendly)
            texture = new Texture("BarV9BLUE_ProgressBar.png");// to do
        else
            texture = new Texture("BarV5RED_ProgressBarBorder.png");// to do
    }
    public Sprite HPBarDisplay(){
        float healthPercentage = currentHealth / totalHealth;
        int filledSegments = (int) (HEALTH_BAR_WIDTH * healthPercentage);
        region = new TextureRegion(texture, 0, 0, filledSegments, 21);
        HPSprite = new Sprite(region);
        HPSprite.setPosition(healthBarX, healthBarY);

        return HPSprite;
    }
    public Sprite HPBarBackground(float positionX, float positionY, float remainHealth){
        healthBarX = positionX - HEALTH_BAR_WIDTH/2;
        healthBarY = positionY + 70;
        currentHealth = remainHealth;

        backgroundTexture = new Texture("BarV1Background.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setPosition(healthBarX, healthBarY);
        return backgroundSprite;
    }
    public Sprite HPBarBorder(){
        barBorderTexture = new Texture("BarV1_ProgressBarBorder.png");
        barBorderSprite = new Sprite(barBorderTexture);
        barBorderSprite.setPosition(healthBarX, healthBarY);
        return barBorderSprite;
    }
}
