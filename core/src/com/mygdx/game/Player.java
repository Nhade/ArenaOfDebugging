package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.mygdx.game.Constant.PPM;

public class Player {
    private Body body;
    private boolean friendly;
    private boolean visible;
    private Sprite sprite;

    public Player(World world, float x, float y, boolean friendly, Texture texture) {
        this.friendly = friendly;

        // Create and configure body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/PPM, y/PPM);

        body = world.createBody(bodyDef);

        // Create and configure fixture (collision functionality)
        CircleShape circle = new CircleShape();
        circle.setRadius(50/PPM); // Set to 50 to match our temporary player texture

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        circle.dispose();

        // Create the sprite
        sprite = new Sprite(texture);
        sprite.setSize(100, 100); // Assuming 100x100 size for the sprite
        sprite.setOriginCenter();

        // Set sprite position to match the body's position
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
    }

    public Body getBody() {
        return body;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public void dispose() {
        // Assuming you might want to dispose of the texture, but it's not shown how texture is managed
        sprite.getTexture().dispose();
    }
}
