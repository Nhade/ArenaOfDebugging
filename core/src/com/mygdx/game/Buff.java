package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


import static com.mygdx.game.Constant.PPM;

public class Buff {
    private Body body;
    private Texture buffTexture;
    private Texture attackRangeTexture = new Texture("attackRangeBuff.png");
    Texture blueBuff = new Texture("BlueBuff.png");
    Texture redBuff = new Texture("RedBuff.png");
    private Sprite buffSprite;
    private Sprite attackRangeSprite;
    private HPBar hpBar;

    public float hp;
    public float currentHp;
    private boolean isAttacking;
    private float attackRange;

    public boolean isBlue = false;
    public boolean isDead = false;


    public Buff(World world, float x, float y, float hp, float currentHp, boolean isBlue, Texture hpBarTexture) {
        this.hp = hp;
        this.currentHp = hp;
        this.isBlue = isBlue;
        this.attackRange = 20; // Example range, adjust as needed
        this.isAttacking = false;

        if(isBlue) {
            this.buffTexture = blueBuff;
        }else{
            this.buffTexture = redBuff;
        }


        // Initialize HPBar
        this.hpBar = new HPBar(false, hp, 1f, hpBarTexture);
        this.hpBar.setOffset(50f);
        // Load the tower texture
        buffSprite = new Sprite(buffTexture);
        buffSprite.setOriginCenter();

        // Load the attack range textures
        attackRangeSprite = new Sprite(attackRangeTexture);
        attackRangeSprite.scale(1.25f);
        attackRangeSprite.setOriginCenter();


        // Define the body of the tower
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        // Define the shape of the buff as a circle
        CircleShape shape = new CircleShape();
        float radius = (buffSprite.getWidth()) / (2f * PPM); // Calculate the radius based on the sprite width
        shape.setRadius(radius);

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
        return buffSprite;
    }

    public Body getBody() {
        return body;
    }

    public float getHPBarOffset(){
        return hpBar.getOffset();
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public void dispose() {
        buffSprite.getTexture().dispose();
        buffTexture.dispose();
        attackRangeTexture.dispose();
        attackRangeSprite.getTexture().dispose();
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

    public Sprite getAttackRangeSprite() {
        return attackRangeSprite;
    }

}
