package com.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Shoot {

	Vector2 pos;
	Vector2 dir;
	public Rectangle bound;
	boolean isDebug = false;

	Texture texture;
	TextureRegion[] animFrame;
	Animation animation;
	float frameTime = 0f;
	private static final int FRAME_COLS_E1 = 3;
	private static final int FRAME_ROWS_E1 = 1;

	Player1 player;
	GameScreen game;
	Particle par;
	ShapeRenderer sr;

	public Shoot(GameScreen game, Vector2 pos, Vector2 dir, Player1 player) {
		this.game = game;
		this.pos = pos;
		this.dir = dir;
		this.player = player;

		sr = new ShapeRenderer();
		par = new Particle();

		texture = new Texture(Gdx.files.internal("game/ball_fire.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion[][] teRegions = TextureRegion.split(texture, 32,32);
		animFrame = new TextureRegion[FRAME_ROWS_E1 * FRAME_COLS_E1];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS_E1; i++) {
			for (int j = 0; j < FRAME_COLS_E1; j++) {
				animFrame[index++] = teRegions[i][j];
			}
		}
		animation = new Animation(0.1f, animFrame);

		bound = new Rectangle(pos.x, pos.y,30, 30);
	}

	public void Draw(SpriteBatch batch) {
		frameTime += Gdx.graphics.getDeltaTime();
		update();
		bound.set(pos.x, pos.y, 30, 30);
		batch.begin();
		batch.draw(animation.getKeyFrame(frameTime, true), pos.x, pos.y, 30, 30);
		par.draw(batch);
		batch.end();

		if (isDebug) {
			sr.setProjectionMatrix(game.getCam().combined);
			sr.begin(ShapeType.Line);
			sr.setColor(Color.GREEN);
			sr.rect(bound.x, bound.y, bound.width, bound.height);
			sr.end();
		}

	}

	public int RemoveGun() {
		if (pos.x > player.getPosition().x + 250) {

			return +1;
		} else {
			if (pos.x + 250 < player.getPosition().x) {

				return -1;
			}
		}

		if (pos.y > player.getPosition().y + 250) {

			return +2;
		} else {
			if (pos.y + 250 < player.getPosition().y) {

				return -2;
			}
		}

		return 0;
	}

	private void update() {
		pos.add(dir);
		par.getExhaust().setPosition(pos.x + 30 / 2, pos.y + 30 / 2);

	}

	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}
	public void Dispose(){
		texture.dispose();	
	}

}
