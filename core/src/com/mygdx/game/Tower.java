package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


import static com.mygdx.game.Constant.PPM;

public class Tower {
    private Body body;
    private Texture towerTexture;
    private Texture attackRangeRedTexture = new Texture("RedTowerRange530px4pxbold.png");
    private Texture attackRangeGreenTexture = new Texture("GreenTowerRange530px4pxbold.png");
    Texture friendlyTower = new Texture("towerBlue.png");
    Texture enemyTower = new Texture("towerRed220px.png");
    private Sprite towerSprite;
    private Sprite attackRangeGreenSprite;
    private Sprite attackRangeRedSprite;
    private HPBar hpBar;

    public float hp;
    public float currentHp;
    private boolean isAttacking;
    private float attackRange;
    private float detectionRange;
    private boolean isPlayerInDetectionRange;

    public boolean isMainCastle;
    public boolean destroyed = false;
    public boolean isOver = false;
    public boolean isFriendly = false;

    public Tower(World world, float x, float y, float hp, float currentHp, boolean isMainCastle, boolean isFriendly, Texture hpBarTexture) {
        this.hp = hp;
        this.currentHp = hp;
        this.isMainCastle = isMainCastle;
        this.attackRange = 20; // Example range, adjust as needed
        this.detectionRange = 26; // Example range, adjust as needed
        this.isAttacking = false;
        this.isPlayerInDetectionRange = false;
        this.isFriendly = isFriendly;
        if(isFriendly) {
            this.towerTexture = friendlyTower;
        }else{
            this.towerTexture = enemyTower;
        }


        // Initialize HPBar
        this.hpBar = new HPBar(false, hp, 1f, hpBarTexture);
        this.hpBar.setOffset(50f);
        // Load the tower texture
        towerSprite = new Sprite(towerTexture);
        towerSprite.setOriginCenter();

        // Load the attack range textures
        attackRangeGreenSprite = new Sprite(attackRangeGreenTexture);
        attackRangeGreenSprite.scale(1.25f);
        attackRangeGreenSprite.setOriginCenter();

        attackRangeRedSprite = new Sprite(attackRangeRedTexture);
        attackRangeRedSprite.scale(1.25f);
        attackRangeRedSprite.setOriginCenter();

        // Define the body of the tower
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        // Define the shape of the tower as a hexagon
        PolygonShape shape = new PolygonShape();
        Vector2[] vertices = new Vector2[6];
        float radius = (towerSprite.getWidth()) / (2f * PPM); // Adjust the angle for a hexagon
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(i * 60); // 60 degrees for each segment of the hexagon
            vertices[i] = new Vector2((float) Math.cos(angle) * radius, (float) Math.sin(angle) * radius);
        }
        shape.set(vertices);

        // Create the fixture and attach it to the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void updateHP(float currentHp, float hp) {
        this.currentHp = currentHp;
        this.hp = hp;
    }

    public void updateHpBar() {
        hpBar.updatePosition(body.getPosition().x * PPM, body.getPosition().y * PPM);
        hpBar.updateHealth(currentHp, hp);
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



    public Sprite getSprite() {
        return towerSprite;
    }

    public Body getBody() {
        return body;
    }

    public float getHPBarOffset(){
        return hpBar.getOffset();
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public void gameOver(boolean gameOver) {
        this.isOver = true;
    }

    public void dispose() {
        towerSprite.getTexture().dispose();
        towerTexture.dispose();
        attackRangeGreenTexture.dispose();
        attackRangeRedTexture.dispose();
        attackRangeGreenSprite.getTexture().dispose();
        attackRangeRedSprite.getTexture().dispose();
    }

    public boolean isDestroyed() {
        return destroyed;
    }


    public float getDetectionRange() {
        return detectionRange;
    }

    public void setPlayerInDetectionRange(boolean b) {
        isPlayerInDetectionRange = b;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public void setAttacking(boolean b) {
        isAttacking = b;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isPlayerInDetectionRange() {
        return isPlayerInDetectionRange;
    }

    public Sprite getAttackRangeGreenSprite() {
        return attackRangeGreenSprite;
    }

    public Sprite getAttackRangeRedSprite() {
        return attackRangeRedSprite;
    }
}
