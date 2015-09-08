package com.screen.game;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Particle {

	ParticleEmitter  exhaust;

	public Particle(){
		exhaust = new ParticleEmitter();

		try {
			exhaust.load(Gdx.files.internal("particle/EffectShootGreen3").reader(2024));//particle/EffectShootGreen//particle/exhaust
		} catch (IOException e) {
			e.printStackTrace();
		}

		Texture ballTexture = new Texture(Gdx.files.internal("particle/particle-star.png"));////particle
		Sprite ball = new Sprite(ballTexture);
		exhaust.setSprite(ball);
		exhaust.getScale().setHigh(50);
		exhaust.flipY();
		exhaust.start();

	}

	public void draw(SpriteBatch batch){
		exhaust.draw(batch, Gdx.graphics.getDeltaTime());

	}

	public ParticleEmitter getExhaust() {
		return exhaust;
	}

}
