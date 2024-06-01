package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class Application extends ApplicationAdapter {
	private SpriteBatch batch;
	private World world;
	private Player myPlayer;
	private HashMap<String, Player> players = new HashMap<>();
	private Box2DDebugRenderer debugRenderer;
	private Socket socket;
	private Vector2 lastPosition = new Vector2(0, 0);
	private String mySocketId;
	private ArrayList<String> ids = new ArrayList<>();
	private Texture mapImg;//*****
	private OrthographicCamera camera;//*****

	@Override
	public void create() {
		batch = new SpriteBatch();
		// Create map and camera follower
		mapImg = new Texture("FinalProjectGameMap.kra-autosave.png");
		camera = new OrthographicCamera(2000, 2000);//*****
		// Create player object
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();
		Texture texture1 = new Texture("circle_trans_100px.png");
		Texture texture2 = new Texture("badlogic.jpg");
		myPlayer = new Player(world, 400, 240, true, texture1);

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
						data.put("player", "myPlayer");
					} catch (JSONException e) {
						throw new RuntimeException(e);
					}
					socket.emit("joinGame", data);
				}
			}).on("playerJoin", new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					JSONObject obj = (JSONObject) args[0];
					try {
						JSONArray array = obj.getJSONArray("ids");
						for (int i = 0; i < array.length(); i++) {
							if (!ids.contains(array.getString(i)) && !array.getString(i).equals(mySocketId)) {
								players.put(array.getString(i), new Player(world, 0, 0, true, texture1));
								ids.add(array.getString(i));
							}
						}
					} catch (JSONException e) {
						throw new RuntimeException(e);
					}
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
							handlePlayerUpdate(socketId, position);
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

	private void handlePlayerUpdate(String socketId, Vector2 position) {
		Player player = players.get(socketId);
		System.out.println(socketId);
		System.out.println(players.keySet());
		player.getBody().setTransform(position, player.getBody().getAngle());
	}

	@Override
	public void render() {
		update(Gdx.graphics.getDeltaTime());
		ScreenUtils.clear(0, 0, 0, 1);

		batch.begin();
			batch.draw(mapImg, 0, 0);
			camera.position.set(myPlayer.getBody().getPosition().x, myPlayer.getBody().getPosition().y, 0);
			camera.update();
			batch.draw(myPlayer.getSprite(), myPlayer.getBody().getPosition().x - myPlayer.getSprite().getWidth() / 2, myPlayer.getBody().getPosition().y - myPlayer.getSprite().getHeight() / 2);
			if (!players.isEmpty()) {
				players.forEach((id, player) -> batch.draw(player.getSprite(), player.getBody().getPosition().x - player.getSprite().getWidth() / 2, player.getBody().getPosition().y - player.getSprite().getHeight() / 2));
			}
			batch.setProjectionMatrix(camera.combined);
		batch.end();

		debugRenderer.render(world, batch.getProjectionMatrix());
	}

	private void update(float delta) {
		handleInput(delta);
		updatePosition();
		world.step(delta, 6, 2);
	}

	private void updatePosition() {
		if (myPlayer.getBody().getPosition().x != lastPosition.x || myPlayer.getBody().getPosition().y != lastPosition.y) {
			lastPosition = new Vector2(myPlayer.getBody().getPosition().x, myPlayer.getBody().getPosition().y);
			socket.emit("playerMove", myPlayer.getBody().getPosition());
		}
	}

	private void handleInput(float delta) {
		float speed = 20000;
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
			myPlayer.getBody().setLinearVelocity(velocity);
		} else {
			myPlayer.getBody().setLinearVelocity(0, 0);
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		myPlayer.dispose();
		world.dispose();
		debugRenderer.dispose();
		if (socket != null) {
			socket.disconnect();
			socket.close();
		}
	}
}
