package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
import java.util.Objects;

import static com.mygdx.game.Constant.*;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Application extends ApplicationAdapter {
    private SpriteBatch batch;
    private World world;
    private Player myPlayer;
    private Obstacle obstacle;
    private HashMap<String, Player> players = new HashMap<>();
    private Box2DDebugRenderer debugRenderer;
    private Socket socket;
    private Vector2 lastPosition = new Vector2(0, 0);
    private String mySocketId;
    private ArrayList<String> ids = new ArrayList<>();
    private Texture mapImg;
    private OrthographicCamera camera;
    private BitmapFont font;
    private GlyphLayout glyphLayout;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Create map and camera follower
        mapImg = new Texture("FinalProjectGameMap.kra-autosave.png");
        camera = new OrthographicCamera(WINDOW_WIDTH * 1.5f, WINDOW_HEIGHT * 1.5f);
        // Create player object
        world = new World(new Vector2(0, 0), true);
        obstacle = new Obstacle(world);
        debugRenderer = new Box2DDebugRenderer();
        Texture texture1 = new Texture("circle_trans_100px.png");
        Texture texture2 = new Texture("badlogic.jpg");
        Texture friendlyHpDisplay = new Texture("BarV9BLUE_ProgressBar.png");
        Texture enemyHpDisplay = new Texture("BarV5RED_ProgressBarBorder.png");
        // Generate a font using FreeTypeFontGenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fredoka-Medium.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        generator.dispose();


        myPlayer = new Player(world, 300, 300, true, texture1, friendlyHpDisplay, 1000, 1000, true);
        // Initialize socket connection
        try {
            socket = IO.socket("http://localhost:3000");

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Connected to the server");
                    mySocketId = socket.id();
                    players.put(mySocketId, myPlayer);
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
                        JSONObject playersObj = (JSONObject) obj.get("players");
                        JSONArray playersIds = playersObj.names();
                        for (int i = 0; i < playersIds.length(); i++) {
                            String playerId = playersIds.getString(i);
                            if (!ids.contains(playerId) && !playerId.equals(mySocketId)) {
                                if (true) { // TODO: Change logic after we implement team
                                    players.put(playerId, new Player(world, 0, 0, true, texture1, friendlyHpDisplay, 1000, 1000, false));
                                } else {
                                    players.put(playerId, new Player(world, 0, 0, true, texture1, enemyHpDisplay, 1000, 1000, false));
                                }
                                ids.add(playerId);
                            }
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).on("playerMove", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String socketId;
                    float x, y;
                    boolean isForced;
                    try {
                        JSONObject obj = (JSONObject) args[0];
                        JSONObject position = (JSONObject) obj.get("position");
                        socketId = obj.getString("id");
                        x = (float) position.getDouble("x");
                        y = (float) position.getDouble("y");
                        isForced = obj.getBoolean("isForced");
                        Vector2 positionV2 = new Vector2(x, y);
                        handlePlayerUpdate(socketId, positionV2, isForced);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).on("playerUpdate", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String socketId;
                    float currentHealth;
                    boolean isDead;
                    try {
                        JSONObject obj = (JSONObject) args[0];
                        socketId = obj.getString("id");
                        currentHealth = (float) obj.getDouble("health");
                        isDead = obj.getBoolean("dead");

                        Player player = players.get(socketId);
                        player.updateHP(currentHealth, player.totalHealth);
                        if (isDead) {
                            player.setDead(true);
                        } else {
                            player.setDead(false);
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

    private void handlePlayerUpdate(String socketId, Vector2 position, boolean isForced) {
        players.get(socketId).setPosition(position.x, position.y);
        if (isForced || !socketId.equals(mySocketId)) {
            players.get(socketId).getBody().setTransform(position, players.get(socketId).getBody().getAngle());
        }
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        batch.draw(mapImg, 0, 0);
        camera.position.set(myPlayer.getBody().getPosition().x * PPM, myPlayer.getBody().getPosition().y * PPM, 0);
        camera.update();
        if (!myPlayer.isDead()) {
//            batch.draw(myPlayer.getSprite(), myPlayer.getBody().getPosition().x * PPM - myPlayer.getSprite().getWidth() / 2, myPlayer.getBody().getPosition().y * PPM - myPlayer.getSprite().getHeight() / 2,  myPlayer.getSprite().getWidth() * 1.7f,  myPlayer.getSprite().getHeight() * 1.7f);
            batch.draw(myPlayer.getSprite(), myPlayer.getBody().getPosition().x * PPM - myPlayer.getSprite().getWidth() / 2, myPlayer.getBody().getPosition().y * PPM - myPlayer.getSprite().getHeight() / 2);
            myPlayer.updateHpBar();
            myPlayer.getHpBarBackground().draw(batch);
            myPlayer.getHpBarDisplay().draw(batch);
            myPlayer.getHpBarBorder().draw(batch);
            if (myPlayer.totalHealth >= 10 * 1000) {
                font.draw(batch, String.format("%d k", (int) (myPlayer.currentHealth / 1000)) + " / " + String.format("%d k", (int) (myPlayer.totalHealth / 1000)), myPlayer.getHPBarX() + 83, (myPlayer.getHPBarY() - 70) + 90 * myPlayer.getHPBarScale());
            } else {
                font.draw(batch, String.format("%.1f k", myPlayer.currentHealth / 1000) + " / " + String.format("%.1f k", myPlayer.totalHealth / 1000), myPlayer.getHPBarX() + 77, (myPlayer.getHPBarY() - 70) + 90 * myPlayer.getHPBarScale());
            }
        }
        if (!players.isEmpty()) {
            players.forEach((id, player) -> {
                if (player.isVisible() && !id.equals(mySocketId)) {
//                    batch.draw(player.getSprite(), player.getBody().getPosition().x * PPM - player.getSprite().getWidth() / 2, player.getBody().getPosition().y * PPM - player.getSprite().getHeight() / 2,  player.getSprite().getWidth() * 1.7f, player.getSprite().getHeight() * 1.7f);
                    batch.draw(player.getSprite(), player.getBody().getPosition().x * PPM - player.getSprite().getWidth() / 2, player.getBody().getPosition().y * PPM - player.getSprite().getHeight() / 2);
//                    player.getSprite().draw(batch);
                    player.updateHpBar();
                    player.getHpBarBackground().draw(batch);
                    player.getHpBarDisplay().draw(batch);
                    player.getHpBarBorder().draw(batch);
                    if (player.totalHealth >= 10 * 1000) {
                        font.draw(batch, String.format("%d k", (int) (player.currentHealth / 1000)) + " / " + String.format("%d k", (int) (player.totalHealth / 1000)), player.getHPBarX() + 83, (player.getHPBarY() - 70) + 90 * player.getHPBarScale());
                    } else {
                        font.draw(batch, String.format("%.1f k", player.currentHealth / 1000) + " / " + String.format("%.1f k", player.totalHealth / 1000), player.getHPBarX() + 77, (player.getHPBarY() - 70) + 90 * player.getHPBarScale());
                    }
                }

            });
        }
        batch.setProjectionMatrix(camera.combined);

        batch.end();

        debugRenderer.render(world, batch.getProjectionMatrix());
    }

    private void update(float delta) {
        world.step(delta, 6, 2);
        handleInput(delta);
        updatePosition();
    }

    private void updatePosition() {
        if (myPlayer.getBody().getPosition().x != lastPosition.x || myPlayer.getBody().getPosition().y != lastPosition.y) {
            lastPosition = new Vector2(myPlayer.getBody().getPosition().x, myPlayer.getBody().getPosition().y);
            try {
                JSONObject data = new JSONObject();
                data.put("x", myPlayer.getBody().getPosition().x);
                data.put("y", myPlayer.getBody().getPosition().y);
                socket.emit("playerMove", data);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleInput(float delta) {
        if (myPlayer.isDead()) {
            myPlayer.getBody().setLinearVelocity(new Vector2(0, 0));
            return;
        }
        float speed = 16f;
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
