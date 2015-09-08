package com.menu.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.main.game.MainGame;

public class MenuOption implements Screen {

	MainGame game;

	Stage stage;
	Skin skin2, skin3;
	Slider sound;
	Slider music;
	Slider menu_sound;
	LabelStyle lbs_txt_1;
	LabelStyle lbs_txt_2;
	LabelStyle lbs_txt_3;
	Viewport viewport;
	TextureAtlas atlas, atlas2;
	

	float sound_v = 0, music_v = 0, menu_v = 0;
	boolean check_fps = false;


	public MenuOption(MainGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		viewport = new FitViewport(game.width, game.height);
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		
		setUI();
	}

	private void setUI() {

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("font/impact.ttf"));

		FreeTypeFontParameter parameter_1 = new FreeTypeFontParameter();
		parameter_1.size = 40;
		parameter_1.borderColor = Color.BLACK;
		parameter_1.borderWidth = 2;
		parameter_1.color = Color.RED;
		BitmapFont font1 = generator.generateFont(parameter_1);

		FreeTypeFontParameter parameter_2 = new FreeTypeFontParameter();
		parameter_2.size = 25;
		parameter_2.shadowColor = Color.BLACK;
		parameter_2.shadowOffsetX = 3;
		parameter_2.shadowOffsetY = 3;
		parameter_2.color = Color.WHITE;
		BitmapFont font2 = generator.generateFont(parameter_2);

		FreeTypeFontParameter parameter_3 = new FreeTypeFontParameter();
		parameter_3.size = 20;
		parameter_3.borderColor = Color.BLACK;
		parameter_3.borderWidth = 1;
		parameter_3.color = Color.GREEN;
		BitmapFont font3 = generator.generateFont(parameter_3);

		atlas = new TextureAtlas(Gdx.files.internal("ui/ui-gray.pack"));
		atlas2 = new TextureAtlas(Gdx.files.internal("ui/ui-blue.pack"));

		skin2 = new Skin();
		skin2.addRegions(atlas);

		skin3 = new Skin();
		skin3.addRegions(atlas2);

		lbs_txt_1 = new LabelStyle();
		lbs_txt_1.font = font1;

		lbs_txt_2 = new LabelStyle();
		lbs_txt_2.font = font2;

		lbs_txt_3 = new LabelStyle();
		lbs_txt_3.font = font3;

		// table ground
		Texture texture = new Texture("ui/bg_select_level.png");
		Drawable drawable = new TextureRegionDrawable(
				new TextureRegion(texture));
		Label label_ground = new Label("Options", lbs_txt_1);

		Table table_ground = new Table();
		table_ground.pack();
		//table_ground.debug();
		//table_ground.debugTable();
		table_ground.setSize(stage.getWidth(), stage.getHeight());
		table_ground.setBackground(drawable);
		table_ground.center().top().padTop(10);
		table_ground.add(label_ground);

		// table1
		Table table_1 = new Table();
		table_1.setFillParent(true);
		table_1.right();
		table_1.pack();
//		table_1.debug();
//		table_1.debugTable();
		table_1.left().center().top().padTop(120);
		//แถวแรก เพื่ม ข้อความ  MUSIC GAME
		table_1.row().width(130).height(30);
		Label la1 = new Label("MUSIC GAME", lbs_txt_2);
		table_1.add(la1);

		final Label txt_music = new Label("0.0 Volume", lbs_txt_2);
		txt_music.setText(String.valueOf(music_v) + " Volume");

		SliderStyle music_style = new SliderStyle();
		music_style.background = skin2.getDrawable("slider_back_ver");
		music_style.knob = skin3.getDrawable("knob_02");
		music = new Slider(0, 100, 1, false, music_style);
		music.setValue(music_v);
		music.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				txt_music.setText("" + music.getValue() + " Volume");
				music_v = music.getValue();
			}
		});
		table_1.add(music);
		table_1.add(txt_music).padLeft(10);

		// แถวที่2 เพื่ม SOUND GAME 
		table_1.row().width(130).height(50);
		Label la2 = new Label("SOUND GAME", lbs_txt_2);
		table_1.add(la2);

		final Label txt_sound = new Label("0.0 Volume", lbs_txt_2);
		txt_sound.setText(String.valueOf(sound_v) + " Volume");

		SliderStyle sound_style = new SliderStyle();
		sound_style.background = skin2.getDrawable("slider_back_ver");
		sound_style.knob = skin3.getDrawable("knob_02");
		sound = new Slider(0, 100, 1, false, sound_style);
		sound.setValue(sound_v);
		sound.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				txt_sound.setText("" + sound.getValue() + " Volume");
				sound_v = sound.getValue();
			}
		});

		table_1.add(sound);
		table_1.add(txt_sound).padLeft(10);

		// แถวที่3 เพืม  MUSIC MENU 
		table_1.row().width(130).height(40);
		Label la3 = new Label("MUSIC MENU", lbs_txt_2);
		table_1.add(la3);

		final Label txt_menu_sound = new Label("0.0 Volume", lbs_txt_2);
		txt_menu_sound.setText(String.valueOf(menu_v) + " Volume");

		SliderStyle menu_sound_style = new SliderStyle();
		menu_sound_style.background = skin2.getDrawable("slider_back_ver");
		menu_sound_style.knob = skin3.getDrawable("knob_02");
		menu_sound = new Slider(0, 100, 1, false, menu_sound_style);
		menu_sound.setValue(menu_v);
		menu_sound.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				txt_menu_sound.setText("" + menu_sound.getValue() + " Volume");
				menu_v = menu_sound.getValue();
			}
		});

		table_1.add(menu_sound);
		table_1.add(txt_menu_sound).padLeft(10);

		// table2
		Table table_2 = new Table();
		table_2.setFillParent(true);
		table_2.pack();
//		table_2.debug();
//		table_2.debugTable();
		table_2.right().center().top().padTop(225);
		table_2.row().width(97).height(80);
		
		Label la_fps = new Label("SHOW FPS: ", lbs_txt_2);
		table_2.add(la_fps).left();

		CheckBoxStyle cbs = new CheckBoxStyle();
		cbs.checkboxOff = skin3.getDrawable("checkbox_off");
		cbs.checkboxOn = skin3.getDrawable("checkbox_on");
		cbs.font = new BitmapFont();

		final CheckBox ch_fps = new CheckBox("", cbs);
		ch_fps.setChecked(check_fps);
		ch_fps.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				System.out.println("check fps " + ch_fps.isChecked());
				check_fps = ch_fps.isChecked();
			}
		});

		Label la_ch_fps = new Label("off/on", lbs_txt_2);
		table_2.add(ch_fps);
		table_2.add(la_ch_fps);
		table_2.row().width(97).height(50);
		
		Label la_txt = new Label("Setting in Show World Game", lbs_txt_3);
		table_2.add(la_txt).width(20).center();
		table_2.row().height(40);

		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = skin3.getDrawable("button_02");
		tbs.down = skin3.getDrawable("button_01");
		tbs.font = new BitmapFont();

		TextButton btn_exit = new TextButton("BLACK TO MENU AND SAVE", tbs);
		btn_exit.setWidth(10);
		btn_exit.setHeight(20);
		btn_exit.addListener(new ClickListener() {
			public void clicked(
					com.badlogic.gdx.scenes.scene2d.InputEvent event, float x,
					float y) {
				System.out.println(" save ok ");
				game.setScreen(new Menu(game));
			}
		});
		table_2.add(btn_exit).left();

		stage.addActor(table_ground);
		stage.addActor(table_1);
		stage.addActor(table_2);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

		menu_v = menu_sound.getValue();
		sound_v = sound.getValue();
		music_v = music.getValue();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin2.dispose();
		skin3.dispose();
		atlas.dispose();
		atlas2.dispose();
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

}
