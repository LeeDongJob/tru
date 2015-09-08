package com.screen.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.main.game.MainGame;
import com.menu.game.Menu;


public class GameScreen implements Screen {

	MainGame game;

	ShapeRenderer sr;
	SpriteBatch batch;
	public OrthographicCamera cam;
	OrthographicCamera camHud;
	BitmapFont font;
	public Stage stage;
	Viewport viewport;

	int numLife = 3;
	TouchPat2C touch;
	Player1 player1;
	Array<Shoot> shoot;
	MapTiled map;
	OrthogonalTiledMapRenderer randerMap;
	ArrayList<Rectangle> boundMap;
	Array<AI_1> ais;

	// HUD
	Texture textureHud;
	Sprite spriteHud;

	private float delay = 1.5f;
	private float time = 0;
	boolean hp = true;
	String status = "LIFE";
	int numkill = 0;

	Music music;
	Sound sound_shoot;

	public GameScreen(MainGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		viewport = new FitViewport(game.width, game.height);
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);
		
		sound_shoot = Gdx.audio.newSound(Gdx.files.internal("sound/shoot.wav"));
		music = Gdx.audio.newMusic(Gdx.files.internal("sound/music.wav"));
		
		music.play();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, game.width, game.height);

		camHud = new OrthographicCamera();
		camHud.setToOrtho(false, game.width, game.height);

		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		font = new BitmapFont();
		font.setColor(Color.WHITE);

		touch = new TouchPat2C(this, batch, 30, 5, 150, 150, 600, 5, 150, 150);

		map = new MapTiled("map/map.tmx");
		randerMap = new OrthogonalTiledMapRenderer(map.getMap());

		setPlayer_and_Ai();
		setUPMap();
		setUpHud();
	}

	private void setPlayer_and_Ai() {
		player1 = new Player1(new Vector2(200, 200), new Vector2(32, 48), this,
				touch);

		ais = new Array<AI_1>();
		ais.add(new AI_1(new Vector2(800, 350), new Vector2(32, 48), player1,
				sr, this));
		ais.add(new AI_1(new Vector2(200, 100), new Vector2(32, 48), player1,
				sr, this));
		ais.add(new AI_1(new Vector2(300, 400), new Vector2(32, 48), player1,
				sr, this));

	}

	private void setUpHud() {
		textureHud = new Texture(Gdx.files.internal("game/hud.png"));
		spriteHud = new Sprite(textureHud);
		spriteHud.setPosition(10, 480 - (textureHud.getHeight() - 25));
		spriteHud.setSize(textureHud.getWidth() - 35,
				textureHud.getHeight() - 25);

	}

	private void setUPMap() {
		boundMap = new ArrayList<Rectangle>();

		for (int i = 0; i < 40; i++) {
			for (int j = 0; j < 20; j++) {
				TiledMapTileLayer cur = (TiledMapTileLayer) map.getMap()
						.getLayers().get(1);
				Cell cell = new Cell();
				if (cur.getCell(i, j) != null) {
					// cell = cur.getCell(i, j);
					// System.out.println(i + "," + j +","+
					// cell.getTile().getId());
					boundMap.add(new Rectangle(i * 32, j * 32, 32, 32));

				}
			}

		}

	}

	private void updateMap() {
		for (int i = 0; i < boundMap.size(); i++) {
			// PLAYER
			if (boundMap.get(i).overlaps(player1.getBounds())) {
				player1.reAdjust();

				int x = (int) boundMap.get(i).x / 32;
				int y = (int) boundMap.get(i).y / 32;
				TiledMapTileLayer cur = (TiledMapTileLayer) map.getMap()
						.getLayers().get(1);
				Cell cell = cur.getCell(x, y);
				if (cell.getTile().getProperties().containsKey("box")) {
					// System.out.println("play hist box");
					map.getMap().getLayers().get(5).setVisible(false);
					map.getMap().getLayers().get(4).setVisible(false);
				}
				if (cell.getTile().getProperties().containsKey("ground")) {
					// System.out.println("play hist ground");
				}
			}
			// AI
			for (int j = 0; j < ais.size; j++) {

				if (boundMap.get(i).overlaps(ais.get(j).getBounds())) {
					// System.out.println("ai hit map");
					ais.get(j).reAdjust();

					int x = (int) boundMap.get(i).x / 32;
					int y = (int) boundMap.get(i).y / 32;
					TiledMapTileLayer cur = (TiledMapTileLayer) map.getMap()
							.getLayers().get(1);
					Cell cell = cur.getCell(x, y);
					if (cell.getTile().getProperties().containsKey("box")) {
						// System.out.println("ai hist box");
					}
					if (cell.getTile().getProperties().containsKey("ground")) {
						// System.out.println("ai hist ground");
					}
				}
			}

		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.position.set(player1.getPosition().x + (player1.getSize().x),
				player1.getPosition().y + (player1.getSize().y), 0);
		cam.update();

		batch.setProjectionMatrix(cam.combined);

		randerMap.setView(cam);
		int[] backgroundLayers = { 0, 1, 4, 5 };
		int[] foregroundLayers = { 2, 3 };
		randerMap.render(backgroundLayers);
		updateMap();
		player1.drawFrame(batch);
		Update_Collide_and_Draw();
		randerMap.render(foregroundLayers);
		Update_HUD_and_Draw();

		//touch.draw(batch);
		
		if (Gdx.input.isKeyPressed(Keys.BACK)
				|| Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			game.setScreen(new Menu(game));
			music.stop();
		}
	}

	private void Update_HUD_and_Draw() {
		//camHud.update();
		batch.setProjectionMatrix(camHud.combined);
		touch.draw(batch);
		batch.begin();
		spriteHud.draw(batch);
		font.draw(batch, "" + status + " HP :" + numLife, 65, 480 - 10);
		font.draw(batch, "" + "KILL :" + numkill, 65, 480 - 65);
		if (numLife <= 0) {
			status = "DEATH";
		}
		batch.end();

	}

	private void Update_Collide_and_Draw() {
		// draw shoot
		shoot = player1.getShoots();
		for (Shoot sh : shoot) {
			sh.Draw(batch);
		}
		// collide ai and shoot
		for (AI_1 ai : ais) {
			for (Shoot s : shoot) {
				if (s.getBound().overlaps(ai.getBounds())) {

					if (ai.HPRemove(1)) {
						ais.removeValue(ai, false);
						numkill++;
					}
					shoot.removeValue(s, false);
					sound_shoot.play();
				}
			}

		}
		// hp player
		for (AI_1 a : ais) {
			if (player1.getBounds().overlaps(a.getBounds())) {
				HP();
			}
		}
		// remove shoot
		for (Shoot shh : this.shoot) {
			if (shh.RemoveGun() == 1 || shh.RemoveGun() == -1) {
				shoot.removeValue(shh, false);
			}
			if (shh.RemoveGun() == 2 || shh.RemoveGun() == -2) {
				shoot.removeValue(shh, false);
			}
		}
		// draw ai
		for (int i = 0; i < ais.size; i++) {
			ais.get(i).drawFrame(batch);
		}

	}

	private void HP() {
		time += Gdx.graphics.getDeltaTime();
		while (time >= delay) {
			time -= delay;
			numLife -= 1;
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
		music.pause();
	}

	@Override
	public void resume() {
		music.play();
	}

	@Override
	public void hide() {
		music.pause();

	}

	@Override
	public void dispose() {
		player1.Dispose();
		ais.clear();
		touch.dispose();
		map.getMap().dispose();
		music.dispose();
		sound_shoot.dispose();
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
