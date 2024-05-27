package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class Application extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Sprite imgSprite;
	Socket socket;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		imgSprite = new Sprite(img);

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
//				handleGameUpdate(data);
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
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}

	private void update(float deltaTime) {
		// make update to game according to player input, server update
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
