package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
	private World world;
	private Player player1;
	private Player player2;
	private Box2DDebugRenderer debugRenderer;
	private Socket socket;
	private Vector2 lastPosition = new Vector2(0, 0);
	private String mySocketId;

	@Override
	public void create() {
		batch = new SpriteBatch();

		// Create player object
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();
		player1 = new Player(world, 400, 240, true);
		player2 = new Player(world, 400, 240, false);

		// Initialize socket connection
		try {
			socket = IO.socket("http://localhost:3000");

			socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("Connected to the server");
					mySocketId = socket.id();
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
			}).on("playerMove", new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					String socketId;
					try {
						JSONObject obj = (JSONObject) args[0];
						socketId = obj.getString("id");
						String[] positionString = obj.getString("data").replaceAll("[)(]", "").split(",");
						Vector2 position = new Vector2(Float.parseFloat(positionString[0]), Float.parseFloat(positionString[1]));
						System.out.println("Move data received: " + String.format("id: %s, position: %s, %s", socketId, position.x, position.y));
						if (!socketId.equals(mySocketId)) {
							handlePlayerUpdate(position, socketId);
						}
					} catch (JSONException e) {
						throw new RuntimeException(e);
					}
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

	private void handlePlayerUpdate(Vector2 data, String socketId) {
		// Update player position or other properties based on the server update
	}

	@Override
	public void render() {
		update(Gdx.graphics.getDeltaTime());
		ScreenUtils.clear(0, 0, 0, 1);

		batch.begin();
		batch.draw(player1.getSprite(), player1.getBody().getPosition().x - player1.getSprite().getWidth() / 2, player1.getBody().getPosition().y - player1.getSprite().getHeight() / 2);
		batch.draw(player2.getSprite(), player2.getBody().getPosition().x - player2.getSprite().getWidth() / 2, player2.getBody().getPosition().y - player2.getSprite().getHeight() / 2);
		batch.end();

		debugRenderer.render(world, batch.getProjectionMatrix());
	}

	private void update(float delta) {
		handleInput(delta);
		updatePosition();
		world.step(delta, 6, 2);
	}

	private void updatePosition() {
		if (player1.getBody().getPosition().x != lastPosition.x || player1.getBody().getPosition().y != lastPosition.y) {
			lastPosition = new Vector2(player1.getBody().getPosition().x, player1.getBody().getPosition().y);
			socket.emit("playerMove", player1.getBody().getPosition());
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
			player1.getBody().setLinearVelocity(velocity);
		} else {
			player1.getBody().setLinearVelocity(0, 0);
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		player1.dispose();
		player2.dispose();
		world.dispose();
		debugRenderer.dispose();
		if (socket != null) {
			socket.disconnect();
			socket.close();
		}
	}
}
