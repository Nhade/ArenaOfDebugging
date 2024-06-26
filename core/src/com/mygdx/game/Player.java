package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.mygdx.game.Constant.PPM;

public class Player {
    private Body body;
    private boolean friendly;
    private boolean visible;
    private Sprite sprite;
    float currentHealth;
    float totalHealth;
    private float playerX, playerY;
    private long lastAttackTime;
    public boolean dead = false;
    private HPBar hpBar;

    public Player(World world, float x, float y, boolean friendly, Texture playerTexture, Texture hpBarTexture, float currentHealth, float totalHealth, boolean isSelf) {
        this.friendly = friendly;
        this.currentHealth = currentHealth;
        this.totalHealth = totalHealth;
        this.lastAttackTime = 0;
        this.visible = true;
        playerX = x;
        playerY = y;
        hpBar = new HPBar(friendly, totalHealth, 1f, hpBarTexture);
        this.hpBar.setOffset(50f);

        // Create and configure body
        BodyDef bodyDef = new BodyDef();
        if (isSelf) {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        } else {
            bodyDef.type = BodyDef.BodyType.KinematicBody;
        }
        bodyDef.position.set(x / PPM, y / PPM);

        body = world.createBody(bodyDef);

        // Create and configure fixture (collision functionality)
        CircleShape circle = new CircleShape();
        circle.setRadius(50*1.7f / PPM); // Set to 50 to match our temporary player texture

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        circle.dispose();

        // Create the sprite
        sprite = new Sprite(playerTexture);
        //TODO: modified to be 170
        sprite.setOriginCenter();

        // Set sprite position to match the body's position
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
    }

    public void updateHP(float currentHealth, float totalHealth) {
        this.currentHealth = currentHealth;
        this.totalHealth = totalHealth;
    }

    public void updateHpBar() {
        hpBar.updatePosition(playerX * PPM, playerY * PPM);
        hpBar.updateHealth(currentHealth, totalHealth);
    }

    public Sprite getHpBarDisplay() {
        return hpBar.HPBarDisplay();
    }

    public Sprite getHpBarBackground() {
        return hpBar.HPBarBackground();
    }

    public Sprite getHpBarBorder() {
        return hpBar.HPBarBorder();
    }

    public float getHPBarScale() {
        return hpBar.hPBarScale();
    }

    public float getHPBarX() {
        return hpBar.getHealthBarX();
    }

    public float getHPBarY() {
        return hpBar.getHealthBarY();
    }

    public float getHPBarOffset(){
        return hpBar.getOffset();
    }

    public Body getBody() {
        return body;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getCenterX() {
        return playerX * PPM - sprite.getWidth() / 2;
    }

    public float getCenterY() {
        return playerY * PPM - sprite.getHeight() / 2;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public void setPosition(float x, float y) {
        playerX = x;
        playerY = y;
    }

    private void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
        setVisible(!dead);
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public boolean isDead() {
        return dead;
    }

    public void dispose() {
        // Assuming you might want to dispose of the texture, but it's not shown how texture is managed
        sprite.getTexture().dispose();
    }
}
