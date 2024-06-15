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
    private Texture attackRangeRedTexture = new Texture("attackRangeRed.png");
    private Texture attackRangeGreenTexture = new Texture("attackRangeGreen.png");
    private Sprite towerSprite;
    private Sprite attackRangeGreenSprite;
    private Sprite attackRangeRedSprite;
    private SpriteBatch batch = new SpriteBatch();
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

    public Tower(World world, float x, float y, float hp, float currentHp, boolean isMainCastle, Texture towerTexture, Texture hpBarTexture) {
        this.hp = hp;
        this.currentHp = hp;
        this.isMainCastle = isMainCastle;
        this.attackRange = 530; // Example range, adjust as needed
        this.detectionRange = 550; // Example range, adjust as needed
        this.isAttacking = false;
        this.isPlayerInDetectionRange = false;
        this.towerTexture = towerTexture;

        // Initialize HPBar
        this.hpBar = new HPBar(false, hp, 1f, hpBarTexture);
        this.hpBar.setOffset(50f);
        // Load the tower texture
        towerSprite = new Sprite(towerTexture);
        towerSprite.setOriginCenter();

        // Load the attack range textures
        attackRangeGreenSprite = new Sprite(attackRangeGreenTexture);
        attackRangeGreenSprite.setSize(detectionRange, detectionRange);
        attackRangeGreenSprite.setOriginCenter();

        attackRangeRedSprite = new Sprite(attackRangeRedTexture);
        attackRangeRedSprite.setSize(attackRange, attackRange);
        attackRangeRedSprite.setOriginCenter();

        // Define the body of the tower
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        // Define the shape of the tower as an octagon
        PolygonShape shape = new PolygonShape();
        Vector2[] vertices = new Vector2[8];
        float radius = (towerSprite.getWidth()) / (2f * (float) Math.cos(Math.toRadians(22.5)) * PPM);
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians((i * 45) - 22.5); // Offset by 22.5 degrees for an even distribution
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

    //ToDO: conbine this method to server
    public void update(Vector2 playerPosition, String playerId) {
        float distanceToPlayer = body.getPosition().dst(playerPosition);
//        System.out.println("distanceToPlayer: " + distanceToPlayer);
        if (distanceToPlayer < detectionRange) {
            isPlayerInDetectionRange = true;
            render();
        } else {
            isPlayerInDetectionRange = false;
        }

        if (playerPosition.dst(body.getPosition()) < attackRange && !isAttacking) {
            isAttacking = true;
            render();
        } else if (playerPosition.dst(body.getPosition()) >= attackRange) {
            isAttacking = false;
        }
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

    public void render() {
        batch.begin();
        if (isPlayerInDetectionRange) {
            attackRangeGreenSprite.setPosition(body.getPosition().x * PPM - attackRangeGreenSprite.getWidth() / 2,
                    body.getPosition().y * PPM - attackRangeGreenSprite.getHeight() / 2);
            attackRangeGreenSprite.draw(batch);
        }

        if (isAttacking) {
            attackRangeRedSprite.setPosition(body.getPosition().x * PPM - attackRangeRedSprite.getWidth() / 2,
                    body.getPosition().y * PPM - attackRangeRedSprite.getHeight() / 2);
            attackRangeRedSprite.draw(batch);
        }

        batch.draw(towerSprite, body.getPosition().x * PPM - towerSprite.getWidth() / 2,
                body.getPosition().y * PPM - towerSprite.getHeight() / 2);

        // Draw HP Bar
        batch.draw(hpBar.HPBarBackground(), hpBar.getHealthBarX(), hpBar.getHealthBarY());
        batch.draw(hpBar.HPBarDisplay(), hpBar.getHealthBarX(), hpBar.getHealthBarY());
        batch.draw(hpBar.HPBarBorder(), hpBar.getHealthBarX(), hpBar.getHealthBarY());

        batch.end();
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
        batch.dispose();
        towerSprite.getTexture().dispose();
        towerTexture.dispose();
        attackRangeGreenTexture.dispose();
        attackRangeRedTexture.dispose();
        attackRangeGreenSprite.getTexture().dispose();
        attackRangeRedSprite.getTexture().dispose();
    }
}
