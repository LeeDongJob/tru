package com.menu.game;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.main.game.MainGame;
import com.screen.game.GameScreen;


public class Menu implements Screen {

	MainGame game;

	Stage stage;
	BitmapFont font;
	SpriteBatch batch;
	TextureAtlas altas;
	Skin skin;
	TextButton btn_play, btn_option, btn_exit;
	Label label;
	Sprite bg_sp;
	Viewport viewport;

	public Menu(MainGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		viewport = new FitViewport(game.width, game.height);
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);

		setBackground();
		setLable();
		setButton();
		
		stage.addActor(btn_play);
		stage.addActor(btn_option);
		stage.addActor(btn_exit);
		stage.addActor(label);
	}

	private void setButton() {

		altas = new TextureAtlas(Gdx.files.internal("ui/button.pack"));
		skin = new Skin();
		skin.addRegions(altas);

		// Button Play
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("btn_up");
		style.down = skin.getDrawable("btn_dow");
		style.font = font;

		btn_play = new TextButton("", style);
		btn_play.setWidth(200);
		btn_play.setHeight(90);
		btn_play.setPosition(game.width / 2 - 200 / 2, game.height / 2 - 90 / 2
				+ 30);

		// Button Option
		TextButtonStyle style_option = new TextButtonStyle();
		style_option.up = skin.getDrawable("btn_option_up");
		style_option.down = skin.getDrawable("btn_option_dow");
		style_option.font = font;

		btn_option = new TextButton("", style_option);
		btn_option.setWidth(200);
		btn_option.setHeight(90);
		btn_option.setPosition(game.width / 2 - 200 / 2, game.height / 2 - 90
				/ 2 - 100 + 30);

		// Button Exit
		TextButtonStyle style_exit = new TextButtonStyle();
		style_exit.up = skin.getDrawable("btn_exit_up");
		style_exit.down = skin.getDrawable("btn_exit_dow");
		style_exit.font = font;

		btn_exit = new TextButton("", style_exit);
		btn_exit.setWidth(200);
		btn_exit.setHeight(90);
		btn_exit.setPosition(game.width / 2 - 200 / 2, game.height / 2 - 90 / 2
				- 200 + 30);

		// Event Button Play
		btn_play.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {	
				game.setScreen(new GameScreen(game));
			}
		});

		// Event Button Option
		btn_option.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				
				game.setScreen(new MenuOption(game));
			}
		});

		// Event Button Exit
		btn_exit.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
			}
		});
	}

	private void setLable() {
		LabelStyle ls = new LabelStyle(font, Color.WHITE);
		label = new Label("MY GAME TEST ", ls);
		label.setPosition(0, game.height / 2 + 100);
		label.setWidth(game.width);
		label.setAlignment(com.badlogic.gdx.utils.Align.center);

	}

	private void setBackground() {
		Texture bg = new Texture("ui/background.png");
		bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg_sp = new Sprite(bg);
		bg_sp.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bg_sp.setPosition(Gdx.graphics.getWidth() / 2 - bg_sp.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - bg_sp.getHeight() / 2);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		bg_sp.draw(batch);
		batch.end();

		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		font.dispose();
		batch.dispose();
		altas.dispose();
		skin.dispose();
	}

}
