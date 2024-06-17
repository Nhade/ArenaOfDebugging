package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private HashMap<String, com.mygdx.game.Tower> towers = new HashMap<>();

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Create map and camera follower
        mapImg = new Texture("FinalProjectGameMap.png");
        camera = new OrthographicCamera(WINDOW_WIDTH * 1.5f, WINDOW_HEIGHT * 1.5f);
        // Create player object
        world = new World(new Vector2(0, 0), true);
        obstacle = new Obstacle(world);
        debugRenderer = new Box2DDebugRenderer();
        Texture texture1 = new Texture("circle_trans_100px.png");
        Texture texture2 = new Texture("badlogic.jpg");
        Texture friendlyHpDisplay = new Texture("BarV9BLUE_ProgressBar.png");
        Texture enemyHpDisplay = new Texture("BarV5RED_ProgressBarBorder.png");
        Texture friendlyTower = new Texture("towerBlue.png");
        Texture enemyTower = new Texture("towerRed.png");
        // Create towers
        initializeTowers(friendlyTower, friendlyHpDisplay, enemyTower, enemyHpDisplay);

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
                                if (false) { // TODO: Change logic after we implement team
                                    players.put(playerId, new Player(world, 0, 0, true, texture1, friendlyHpDisplay, 1000, 1000, false));
                                } else {
                                    players.put(playerId, new Player(world, 0, 0, false, texture1, enemyHpDisplay, 1000, 1000, false));
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
                }//TODO:render the attackRange
            }).on("towerUpdate", new Emitter.Listener() {
                public void call(Object... args) {
                    String socketId;
                    float currentHealth;
                    boolean isDestroyed;
                    boolean isOver;
                    try {
                        JSONObject obj = (JSONObject) args[0];
                        socketId = obj.getString("id");
                        currentHealth = (float) obj.getDouble("health");
                        isDestroyed = obj.getBoolean("destroyed");

                        Tower tower = towers.get(socketId);
                        tower.updateHP(currentHealth, tower.hp);
                        if (isDestroyed) {
                            tower.setDestroyed(true);
                            if (tower.isMainCastle) {
                                tower.gameOver(true);
                            }
                        } else {
                            tower.setDestroyed(false);
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

    private void initializeTowers(Texture friendlyTower, Texture friendlyHpDisplay, Texture enemyTower, Texture enemyHpDisplay) {
        towers.put("U1", new Tower(world, 839, 3006, 20000, 20000, false, true, friendlyHpDisplay));
        towers.put("U2", new Tower(world, 909, 5855, 20000, 20000, false, true, friendlyHpDisplay));
        towers.put("U3", new Tower(world, 1010, 8806, 20000, 20000, false, true, friendlyHpDisplay));
        towers.put("U4", new Tower(world, 2999, 10516, 20000, 20000, false, false, enemyHpDisplay));
        towers.put("U5", new Tower(world, 5700, 10556, 20000, 20000, false, false, enemyHpDisplay));
        towers.put("U6", new Tower(world, 8360, 10576, 20000, 20000, false, false, enemyHpDisplay));

        towers.put("M1", new Tower(world, 2660, 2700, 20000, 20000, false,true, friendlyHpDisplay));
        towers.put("M2", new Tower(world,3890 ,3920 , 20000, 20000, false, true, friendlyHpDisplay));
        towers.put("M3", new Tower(world, 5110, 5090, 20000, 20000, false, true, friendlyHpDisplay));
        towers.put("M4", new Tower(world, 6160, 6120, 20000, 20000, false, false, enemyHpDisplay));
        towers.put("M5", new Tower(world, 7460, 7350, 20000, 20000, false, false, enemyHpDisplay));
        towers.put("M6", new Tower(world, 8820, 8810, 20000, 20000, false, false, enemyHpDisplay));

        towers.put("D1", new Tower(world, 3010, 835, 20000, 20000, false, true, friendlyHpDisplay));
        towers.put("D2", new Tower(world, 5960, 915, 20000, 20000, false, true, friendlyHpDisplay));
        towers.put("D3", new Tower(world, 8690, 1005, 20000, 20000, false, true, friendlyHpDisplay));
        towers.put("D4", new Tower(world, 10639, 2936, 20000, 20000, false, false, enemyHpDisplay));
        towers.put("D5", new Tower(world, 10689, 6094, 20000, 20000, false, false, enemyHpDisplay));
        towers.put("D6", new Tower(world, 10749, 8335, 20000, 20000, false, false, enemyHpDisplay));

        towers.put("B", new Tower(world, 1660, 1775, 20000, 20000, true, true, friendlyHpDisplay));
        towers.put("R", new Tower(world, 9729, 9726, 20000, 20000, true, false, enemyHpDisplay));
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
            renderPlayer(myPlayer);
        }
        if (!players.isEmpty()) {
            players.forEach((id, player) -> {
                if (player.isVisible() && !id.equals(mySocketId)) {
                    renderPlayer(player);
                }
            });
        }
        updateAndRenderTowers(myPlayer.getBody().getPosition()); // Call the new combined method here
        batch.setProjectionMatrix(camera.combined);
        batch.end();

        debugRenderer.render(world, batch.getProjectionMatrix());
    }


    private void updateAndRenderTowers(Vector2 playerPosition) {
        if (!towers.isEmpty()) {
            towers.forEach((id, tower) -> {
                if (!tower.isDestroyed()) {
                    // Update tower
                    float distanceToPlayer = tower.getBody().getPosition().dst(playerPosition);
                    if (distanceToPlayer < tower.getDetectionRange()) {
                        tower.setPlayerInDetectionRange(true);
                    } else {
                        tower.setPlayerInDetectionRange(false);
                    }

                    if (playerPosition.dst(tower.getBody().getPosition()) < tower.getAttackRange() && !tower.isAttacking() && !tower.isFriendly) {
                        tower.setAttacking(true);
                    } else if (playerPosition.dst(tower.getBody().getPosition()) >= tower.getAttackRange()) {
                        tower.setAttacking(false);
                    }

                    // Render attack range indicators
                    if (tower.isPlayerInDetectionRange()) {
                        tower.getAttackRangeGreenSprite().setPosition(
                                tower.getBody().getPosition().x * PPM - tower.getAttackRangeGreenSprite().getWidth() / 2,
                                tower.getBody().getPosition().y * PPM - tower.getAttackRangeGreenSprite().getHeight() / 2
                        );
                        tower.getAttackRangeGreenSprite().draw(batch);
                    }

                    if (tower.isAttacking()) {
                        tower.getAttackRangeRedSprite().setPosition(
                                tower.getBody().getPosition().x * PPM - tower.getAttackRangeRedSprite().getWidth() / 2,
                                tower.getBody().getPosition().y * PPM - tower.getAttackRangeRedSprite().getHeight() / 2
                        );
                        tower.getAttackRangeRedSprite().draw(batch);
                    }

                    // Render tower
                    batch.draw(tower.getSprite(),
                            tower.getBody().getPosition().x * PPM - tower.getSprite().getWidth() / 2,
                            tower.getBody().getPosition().y * PPM - tower.getSprite().getHeight() / 2);

                    // Render HP bar
                    tower.updateHpBar();
                    tower.getHpBarBackground().draw(batch);
                    tower.getHpBarDisplay().draw(batch);
                    tower.getHpBarBorder().draw(batch);
                    if (tower.hp >= 10 * 1000) {
                        font.draw(batch, String.format("%d k", (int) (tower.currentHp / 1000)) + " / " + String.format("%d k", (int) (tower.hp / 1000)),
                                tower.getHPBarX() + 83, (tower.getHPBarY() - 70) + 90 * tower.getHPBarScale() + tower.getHPBarOffset());
                    } else {
                        font.draw(batch, String.format("%.1f k", tower.currentHp / 1000) + " / " + String.format("%.1f k", tower.hp / 1000),
                                tower.getHPBarX() + 77, (tower.getHPBarY() - 70) + 90 * tower.getHPBarScale() + tower.getHPBarOffset());
                    }
                }
            });

        }
    }



    private void renderPlayer(Player player) {
        batch.draw(player.getSprite(), player.getCenterX(), player.getCenterY());
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

        // Movement
        float speed = 16f;
        Vector2 velocity = new Vector2();
        velocity.x = 0;
        velocity.y = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.y += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.y -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x += speed * delta;
        }

        if (velocity.len() > 0) {
            velocity.nor().scl(speed);
            myPlayer.getBody().setLinearVelocity(velocity);
        } else {
            myPlayer.getBody().setLinearVelocity(0, 0);
        }

        // Attack
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            long currentTime = System.currentTimeMillis();
            long lastAttackTime = myPlayer.getLastAttackTime();
            if (currentTime - lastAttackTime > 600) {
                myPlayer.setLastAttackTime(currentTime);
                Vector2 playerPosition = new Vector2(myPlayer.getBody().getPosition().x, myPlayer.getBody().getPosition().y);
                players.forEach((id, player) -> {
                    if (!player.isVisible() || id.equals(mySocketId) || player.isFriendly()) {
                        return;
                    } else {
                        System.out.println(player.getBody().getPosition().dst(playerPosition));
                        if (player.getBody().getPosition().dst(playerPosition) <= 7.8) {
                            JSONObject data = new JSONObject();
                            try {
                                data.put("targetId", id);
                                data.put("attackId", 0);
                                socket.emit("playerAttack", data);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                });
            }
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
