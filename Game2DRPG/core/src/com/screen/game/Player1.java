package com.screen.game;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player1 {

	GameScreen game;
	TouchPat2C touch;

	Texture texture;
	public TextureRegion playerFrame;
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

	boolean KEY_LEFT = true;
	boolean KEY_RIGHT = false;
	boolean KEY_UP = false;
	boolean KEY_DOWN = false;
	boolean KEY_SHOOT = false;
	public String isStatus;

	public Vector2 position;
	public Vector2 size;
	public Rectangle bounds;

	Shoot shoot;
	private long lastFire;
	public Array<Shoot> shoots;
	float speedShoot = 4f;

	public Player1(Vector2 position, Vector2 size, GameScreen game,
			TouchPat2C touch) {
		this.game = game;
		this.position = position;
		this.size = size;
		this.touch = touch;

		texture = new Texture(Gdx.files.internal("game/player.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		LeftFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			System.out.println("--------------- "+i);
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
		shoots = new Array<Shoot>();
	}

	// LEFT
	public void updateLeftFrame(float dt) {
		time_left += dt;
		while (time_left > run_delay) {
			time_left -= run_delay;
			current_left++;
			if (current_left == LeftFrames.length)
				current_left = 0;
//			if (current_left == LeftFrames.length - 1) {
//				
//			}
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
//			if (current_right == RightFrames.length - 1) {
//
//			}
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
		Input();
		batch.begin();
		batch.draw(playerFrame, position.x, position.y, size.x, size.y);
		batch.end();

		bounds.set(position.x, position.y, size.x, size.y);
	}

	public void reAdjust() {
		if (isStatus == "U") {
			position.y -= 1f;
		}
		if (isStatus == "D") {
			position.y += 1f;
		}
		if (isStatus == "R") {
			position.x -= 1f;
		}
		if (isStatus == "L") {
			position.x += 1f;
		}
	}

	private void Input() {
		// key board
		if (Gdx.input.isKeyPressed(Keys.W)) {
			KEY_UP = true;
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			KEY_DOWN = true;
		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			KEY_LEFT = true;
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			KEY_RIGHT = true;
		} else {
			KEY_RIGHT = false;
			KEY_UP = false;
			KEY_LEFT = false;
			KEY_DOWN = false;
		}
		
		if (Gdx.input.isKeyPressed(Keys.SPACE )) {
			if (System.currentTimeMillis() - lastFire >= 600) {
				if (isStatus == "R") {
					shoots.add(new Shoot(game,new Vector2(position.cpy().x, position
							.cpy().y), new Vector2(speedShoot, 0), this));
				}
				if (isStatus == "L") {
					shoots.add(new Shoot(game,new Vector2(position.cpy().x, position
							.cpy().y), new Vector2(-speedShoot, 0), this));
				}
				if (isStatus == "U") {
					shoots.add(new Shoot(game,new Vector2(position.cpy().x, position
							.cpy().y), new Vector2(0, +speedShoot), this));
				}
				if (isStatus == "D") {
					shoots.add(new Shoot(game,new Vector2(position.cpy().x, position
							.cpy().y), new Vector2(0, -speedShoot), this));
				}

				lastFire = System.currentTimeMillis();
			}
		}
		

		// Control touch left
		if (touch.getTouchLeft().getKnobX() > 100) {// RIGHT
			KEY_RIGHT = true;
		}
		if (touch.getTouchLeft().getKnobX() < 40) {// LEFT
			KEY_LEFT = true;
		}
		if (touch.getTouchLeft().getKnobY() > 100) {// UP
			KEY_UP = true;
		}
		if (touch.getTouchLeft().getKnobY() < 40) {// DOWN
			KEY_DOWN = true;
		}
		// Control touch right
		if (touch.getTouchRight().getKnobX() > 100) {// RIGHT
			KEY_SHOOT = true;
		}else{
			KEY_SHOOT = false;
		}
	

	}

	private void updateFrame() {
		if (KEY_LEFT) {
			isStatus = "L";
			position.x -= 2f;
			updateLeftFrame(Gdx.graphics.getDeltaTime());
			playerFrame = LeftFrames[current_left];
		} else if (KEY_RIGHT) {
			isStatus = "R";
			position.x += 2f;
			updateRightFrame(Gdx.graphics.getDeltaTime());
			playerFrame = RightFrames[current_right];
		} else if (KEY_UP) {
			isStatus = "U";
			position.y += 2f;
			updateUpFrame(Gdx.graphics.getDeltaTime());
			playerFrame = UpFrames[current_up];
		} else if (KEY_DOWN) {
			isStatus = "D";
			position.y -= 2f;
			updateDownFrame(Gdx.graphics.getDeltaTime());
			playerFrame = DownFrames[current_down];
		}
		if(KEY_SHOOT){
			System.out.println("time "+System.currentTimeMillis());
			if (System.currentTimeMillis() - lastFire >= 600) {
				if (isStatus == "R") {
					shoots.add(new Shoot(game,new Vector2(position.cpy().x, position
							.cpy().y), new Vector2(speedShoot, 0), this));
				}
				if (isStatus == "L") {
					shoots.add(new Shoot(game,new Vector2(position.cpy().x, position
							.cpy().y), new Vector2(-speedShoot, 0), this));
				}
				if (isStatus == "U") {
					shoots.add(new Shoot(game,new Vector2(position.cpy().x, position
							.cpy().y), new Vector2(0, +speedShoot), this));
				}
				if (isStatus == "D") {
					shoots.add(new Shoot(game,new Vector2(position.cpy().x, position
							.cpy().y), new Vector2(0, -speedShoot), this));
				}

				lastFire = System.currentTimeMillis();
			}
		}
	}

	public Vector2 getPosition() {
		return position;
	}

//	public void setPosition(Vector2 position) {
//		this.position = position;
//	}

	public Vector2 getSize() {
		return size;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public Array<Shoot> getShoots() {
		return shoots;
	}

//	public String getIsStatus() {
//		return isStatus;
//	}
	public void Dispose(){
		texture.dispose();
		shoot.Dispose();
	}

}
