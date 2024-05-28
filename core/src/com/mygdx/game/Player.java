package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Player {
    private Body body;
    private boolean friendly;
    private boolean visible;
    private Texture texture1=new Texture("circle_trans_100px.png");
    private Texture texture2=new Texture("badlogic.jpg");
    private Sprite sprite;

    public Player(World world, float x, float y,boolean friendly) {
        // Create and configure body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(50); // Set to 50 to match our temporary player texture
        circle.dispose();

        // according to friendly value to determine which sprite to use
        if(friendly) {
            sprite = new Sprite(texture1);
        }else{
            sprite = new Sprite(texture2);
        }
        this.friendly = true;
        this.visible = true;
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
        texture1.dispose();
        texture2.dispose();
    }
}
