package com.screen.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AI_1 {
    
	Texture texture;
	public TextureRegion aiFrame;
	TextureRegion[] UpFrames;
	TextureRegion[] DownFrames;
	TextureRegion[] LeftFrames;
	TextureRegion[] RightFrames;
	private float time_up;
	private float time_down;
	private float time_right;
	private float time_left;
	public int current_up;
	public int current_down;
	public int current_left;
	public int current_right;
	float run_delay = 1 / 6f;

	boolean KEY_LEFT = false;
	boolean KEY_RIGHT = false;
	boolean KEY_UP = false;
	boolean KEY_DOWN = true;
	String isStatus;
	int act = 1;

	public Vector2 position;
	public Vector2 size;
	public Rectangle bounds;
	public Rectangle see_bounds;
	private float delay = 2;
	private float time = 0;

	boolean ch = true;
	Player1 player;
	GameScreen game;
	ShapeRenderer sr;
	boolean isDebug = false;
	int num;

	public AI_1(Vector2 position, Vector2 size, Player1 player,
			ShapeRenderer sr, GameScreen game) {
		this.position = position;
		this.size = size;
		this.player = player;
		this.sr = sr;
		this.game = game;
		
		texture = new Texture(Gdx.files.internal("game/ai.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		LeftFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			LeftFrames[i] = new TextureRegion(texture, i * 32, 48, 32, 48);
		}
		RightFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			RightFrames[i] = new TextureRegion(texture, i * 32, 96, 32, 48);
		}
		UpFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			UpFrames[i] = new TextureRegion(texture, i * 32, 148, 32, 48);
		}
		DownFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			DownFrames[i] = new TextureRegion(texture, i * 32, 0, 32, 48);
		}

		bounds = new Rectangle(position.x, position.y, size.x, size.y);
		see_bounds = new Rectangle(position.x - 90, position.y - 90,
				size.x + 200, size.y + 200);
		Random_action();
	}

	private void Random_action() {
		Random r = new Random();
		act = r.nextInt(4);
	}

	// LEFT
	public void updateLeftFrame(float dt) {
		time_left += dt;
		while (time_left > run_delay) {
			time_left -= run_delay;
			current_left++;
			if (current_left == LeftFrames.length)
				current_left = 0;
			if (current_left == LeftFrames.length - 1) {

			}
		}

	}

	// RIGHT
	public void updateRightFrame(float dt) {
		time_right += dt;
		while (time_right > run_delay) {
			time_right -= run_delay;
			current_right++;
			if (current_right == RightFrames.length)
				current_right = 0;// current_run_right =0
			if (current_right == RightFrames.length - 1) {

			}
		}
	}

	// UP
	public void updateUpFrame(float dt) {
		time_up += dt;
		while (time_up > run_delay) {
			time_up -= run_delay;
			current_up++;
			if (current_up == UpFrames.length)
				current_up = 0;
			if (current_up == UpFrames.length - 1) {

			}
		}

	}

	// DOWN
	public void updateDownFrame(float dt) {
		time_down += dt;
		while (time_down > run_delay) {
			time_down -= run_delay;
			current_down++;
			if (current_down == DownFrames.length)
				current_down = 0;// current_run_right =0
			if (current_down == DownFrames.length - 1) {

			}
		}
	}

	public void drawFrame(SpriteBatch batch) {
		updateFrame();
		Input(Gdx.graphics.getDeltaTime());
		batch.begin();
		batch.draw(aiFrame, position.x, position.y, size.x, size.y);
		batch.end();

		if (isDebug) {
			sr.setProjectionMatrix(game.getCam().combined);
			sr.begin(ShapeType.Line);
			sr.setColor(Color.BLUE);
			sr.rect(see_bounds.x, see_bounds.y, see_bounds.width,
					see_bounds.height);
			sr.setColor(Color.RED);
			sr.rect(bounds.x, bounds.y, bounds.width, bounds.height);
			sr.end();
		}

		bounds.set(position.x, position.y, size.x, size.y);

		see_bounds.set(position.x - 90, position.y - 90, size.x + 200,
				size.y + 200);

	}

	private void Input(float dt) {

		if (act == 0) {// RIGHT
			KEY_RIGHT = true;
			KEY_LEFT = false;
			KEY_UP = false;
			KEY_DOWN = false;
		}
		if (act == 1) {// LEFT
			KEY_RIGHT = false;
			KEY_LEFT = true;
			KEY_UP = false;
			KEY_DOWN = false;
		}
		if (act == 2) {// UP
			KEY_RIGHT = false;
			KEY_LEFT = false;
			KEY_UP = true;
			KEY_DOWN = false;
		}
		if (act == 3) {// DOWN
			KEY_RIGHT = false;
			KEY_LEFT = false;
			KEY_UP = false;
			KEY_DOWN = true;
		}

		time += dt;
		while (time >= delay) {
			time -= delay;
			Random_action();
		}
	}

	private void updateFrame() {
		if (ch) {
			if (KEY_LEFT) {
				isStatus = "L";
				position.x -= 0.5f;
				updateLeftFrame(Gdx.graphics.getDeltaTime());
				aiFrame = LeftFrames[current_left];
			} else if (KEY_RIGHT) {
				isStatus = "R";
				position.x += 0.5f;
				updateRightFrame(Gdx.graphics.getDeltaTime());
				aiFrame = RightFrames[current_right];
			} else if (KEY_UP) {
				isStatus = "U";
				position.y += 0.5f;
				updateUpFrame(Gdx.graphics.getDeltaTime());
				aiFrame = UpFrames[current_up];
			} else if (KEY_DOWN) {
				isStatus = "D";
				position.y -= 0.5f;
				updateDownFrame(Gdx.graphics.getDeltaTime());
				aiFrame = DownFrames[current_down];
			}
		}

		if (player.getBounds().overlaps(see_bounds)) {
			if (player.getPosition().x < this.position.x) {// left
				ch = false;
				isStatus = "L";
				position.x -= 0.9f;
				updateLeftFrame(Gdx.graphics.getDeltaTime());
				aiFrame = LeftFrames[current_left];
			}
			if (player.getPosition().y < this.position.y) {// down
				ch = false;
				isStatus = "D";
				position.y -= 0.9f;
				updateDownFrame(Gdx.graphics.getDeltaTime());
				aiFrame = DownFrames[current_down];
			}

			if (player.getPosition().x > this.position.x) {// right
				ch = false;
				isStatus = "R";
				position.x += 0.9f;
				updateRightFrame(Gdx.graphics.getDeltaTime());
				aiFrame = RightFrames[current_right];
			}
			if (player.getPosition().y > this.position.y) {// up
				ch = false;
				isStatus = "U";
				position.y += 0.9f;
				updateUpFrame(Gdx.graphics.getDeltaTime());
				aiFrame = UpFrames[current_up];
			}
		} else {
			ch = true;
		}

	}

	public void reAdjust() {
		if (isStatus == "U") {
			position.y -= 0.5f;
			Random_action();
		}
		if (isStatus == "D") {
			position.y += 0.5f;
			Random_action();
		}
		if (isStatus == "R") {
			position.x -= 0.5f;
			Random_action();
		}
		if (isStatus == "L") {
			position.x += 0.5f;
			Random_action();
		}
	}

	public Rectangle getBounds() {
		return bounds;
	}

//	public void setBounds(Rectangle bounds) {
//		this.bounds = bounds;
//	}
	
	public boolean HPRemove(int i){
		num +=i;
		return num>=3;
	}
	public void Dispose(){
		texture.dispose();
	}

}
