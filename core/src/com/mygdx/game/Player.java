package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;

public class Player extends CircleShape {
    private boolean friendly, visible;

    public Player(float x, float y, boolean isFriendly) {
        setPosition(new Vector2(x, y));
        setRadius(100);
        this.friendly = isFriendly;
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
}
