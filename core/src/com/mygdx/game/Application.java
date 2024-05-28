package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class Application extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Sprite imgSprite;
	private World world;
	private Body body;
	private Box2DDebugRenderer debugRenderer;
	private Socket socket;
    private Vector2 lastPosition = new Vector2(0, 0);

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		imgSprite = new Sprite(img);

		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();

		// Create kinematic body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		bodyDef.position.set(400, 240);

		body = world.createBody(bodyDef);

		// Create a circle shape and attach it to the body
		CircleShape circle = new CircleShape();
		circle.setRadius(32); // Radius in pixels, scale if necessary

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.5f;

		body.createFixture(fixtureDef);
		circle.dispose();

		// Initialize socket connection
		try {
			socket = IO.socket("http://localhost:3000");

			socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("Connected to the server");
					JSONObject data = new JSONObject();
					try {
						data.put("player", "player1");
					} catch (JSONException e) {
						throw new RuntimeException(e);
					}
					socket.emit("joinGame", data);
				}
			}).on("gameUpdate", new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					JSONObject data = (JSONObject) args[0];
					System.out.println("Game update received: " + data);
					// handleGameUpdate(data);
				}
			}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("Disconnected from the server");
				}
			});

			socket.connect();

		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void render() {
		update(Gdx.graphics.getDeltaTime());
		ScreenUtils.clear(0, 0, 0, 1);

		batch.begin();
		batch.draw(imgSprite, body.getPosition().x - imgSprite.getWidth() / 2, body.getPosition().y - imgSprite.getHeight() / 2);
		batch.end();

		debugRenderer.render(world, batch.getProjectionMatrix());
	}

	private void update(float delta) {
		handleInput(delta);
        updatePosition();
		world.step(delta, 6, 2);
	}

    private void updatePosition() {
        if (body.getPosition().x != lastPosition.x || body.getPosition().y != lastPosition.y) {
            lastPosition = new Vector2(body.getPosition().x, body.getPosition().y);
            socket.emit("playerMove", body.getPosition());
        }
    }

	private void handleInput(float delta) {
		float speed = 200;
		Vector2 velocity = new Vector2();
		velocity.x = 0;
		velocity.y = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			velocity.y += speed * delta;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			velocity.y -= speed * delta;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			velocity.x -= speed * delta;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			velocity.x += speed * delta;
		}

		// Normalize velocity to ensure consistent speed in all directions
		if (velocity.len() > 0) {
			velocity.nor().scl(speed);
			body.setLinearVelocity(velocity);
		} else {
			body.setLinearVelocity(0, 0);
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
		world.dispose();
		debugRenderer.dispose();
		if (socket != null) {
			socket.disconnect();
			socket.close();
		}
	}
}
