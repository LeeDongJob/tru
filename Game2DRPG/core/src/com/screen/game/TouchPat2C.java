package com.screen.game;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


public class TouchPat2C {
	private Stage stage;
	private Touchpad touchpadRight;
	private Touchpad touchpadLeft;
	private TouchpadStyle touchpadStyle;
	private Skin touchpadSkin;
	private Drawable touchBackground;
	private Drawable touchKnob;
	private Texture blockTexture;
	GameScreen screen;

	public TouchPat2C(GameScreen screen,SpriteBatch batch, int xl, int yl, int wl, int hl,
			int xr, int yr, int wr, int hr) {
		this.screen = screen;
		touchpadSkin = new Skin();
		Texture tb = new Texture("contro/key_pad_bg_v1.png");
		tb.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		touchpadSkin.add("touch_bg", tb);

		Texture tk = new Texture("contro/pad_v1.png");
		tk.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		touchpadSkin.add("touch_kob", tk);

		touchpadStyle = new TouchpadStyle();

		touchBackground = touchpadSkin.getDrawable("touch_bg");
		touchKnob = touchpadSkin.getDrawable("touch_kob");

		touchpadStyle.background = touchBackground;
		touchpadStyle.knob = touchKnob;
		touchpadRight = new Touchpad(10, touchpadStyle);
		touchpadRight.setBounds(xr, yr, wr, hr);

		touchpadLeft = new Touchpad(10, touchpadStyle);
		touchpadLeft.setBounds(xl, yl, wl, hl);

		stage = screen.getStage();									
		stage.addActor(touchpadRight);
		stage.addActor(touchpadLeft);
		Gdx.input.setInputProcessor(stage);
	}

	public Touchpad getTouchRight() {
		return touchpadRight;
	}

	public Touchpad getTouchLeft() {
		return touchpadLeft;
	}

	public void draw(SpriteBatch batch) {

		stage.draw();
	}

	public void dispose() {
		stage.dispose();
		touchpadSkin.dispose();
		blockTexture.dispose();
	}

}
