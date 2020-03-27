package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.GameScreen;

public class SpaceGame extends Game {

	public SpriteBatch batch;
	//the game width and height
	public static int VirtualWidth=1095;
	public static int VirtualHeight=600;

	@Override
	public void create() {

          batch=new SpriteBatch();
          this.setScreen(new GameScreen(this));
	}
	@Override
	public void render() {
		super.render();
	}
}
