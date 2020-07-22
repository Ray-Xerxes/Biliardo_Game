package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Screens.BallInfo.*;
import com.mygdx.game.SpaceGame;

public class GameScreen implements Screen {

    public static World world;
    public static Box2DDebugRenderer debugRenderer;
    public static OrthographicCamera camera;
    SpaceGame spaceGame;

    public static Player player1;
    public static Player player2;
    public static Table table;
    public static FoulSystem foulsystem;

    public static Array<Body> tmpBodies;

    public GameScreen(SpaceGame spaceGame) {
       this.spaceGame = spaceGame;
   }
    @Override
    public void show() {
        world = new World(new Vector2(0,0),true); // x= 0 and y =0 means no gravity
        debugRenderer = new Box2DDebugRenderer();
        camera =new OrthographicCamera(SpaceGame.VirtualWidth /20, SpaceGame.VirtualHeight /20);
        world.setContactListener(new MyContactListener());
        // initialize the objects and sets the initial position
        table=new Table(new Sprite(new Texture("Assets/table.png")));
        player1 = new Player(new Sprite(new Texture("Assets/p1.png")),new Stick(table.getCueball(),new Sprite(new Texture("Assets/Stick.png"))),new Vector2 (-SpaceGame.VirtualWidth/50,-SpaceGame.VirtualHeight/40 +23.5f),new Vector2(SpaceGame.VirtualWidth/60,SpaceGame.VirtualHeight/60));
        player2 = new Player(new Sprite(new Texture("Assets/p2.png")),new Stick(table.getCueball(),new Sprite(new Texture("Assets/Stick.png"))),new Vector2(-SpaceGame.VirtualWidth/50+25,-SpaceGame.VirtualHeight/40+26.6f),new Vector2(SpaceGame.VirtualWidth/65,SpaceGame.VirtualHeight/130));
        foulsystem=new FoulSystem();
        tmpBodies= new Array<Body>();
    }
    public static boolean checkcueball=false;
    public static boolean turnfinished=false;
    public static boolean checkdropedaball=false;
    @Override

    public void render(float delta) {

                 if(checkcueball&&!turnfinished) // when the cueball enters any hole
        {

        if(Player.playeroneturn)
            player1.Movethecueballfreely(); // moves the cueball in all the table freely
        else
            player2.Movethecueballfreely();

        }

            else  if(foulsystem.Checkallballsmovement()) //if cueball stopped moving
            {
        foulsystem.Checkfouls(); // check if there are any fouls from the last round and changes the turn if there are

       if(Player.playeroneturn)
        player1.Controlthecuestick();// power and the movement of the stick
       else
        player2.Controlthecuestick();
            }


            else    //if ball is moving
                {

            player1.getStick().getStick().setTransform(10, 30, 0); // puts the stick away
            player2.getStick().getStick().setTransform(10, 30, 0); // puts the stick away

            turnfinished = true; // when u hit the ball the turn is finished
                      foulsystem.Detectifanyfoulsishappening(); //detects the fouls

                }


        // camera and draw stuff
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1f/60f, 5, 8); // to make the world feel the time
       spaceGame.batch.setProjectionMatrix(camera.combined);
       spaceGame. batch.begin();
       table.getTablesprite().draw(spaceGame.batch);
        //



       if(Player.playeroneturn) //change player turn sprites
       player1.playerspirte.draw(spaceGame.batch);
       else
       player2.playerspirte.draw(spaceGame.batch);

        foulsystem.Checkifanyballtouchestheholes(); //detects if and ball touched the hole


        for(Body body:tmpBodies) //draws the sprites of every body
        {
            if(body.getUserData()!=null && body.getUserData() instanceof Sprite)
            {
                Sprite sprite=(Sprite)body.getUserData();
                sprite.setPosition(body.getPosition().x-sprite.getWidth()/2,body.getPosition().y-sprite.getHeight()/2);
                sprite.setRotation(body.getAngle()* MathUtils.radiansToDegrees);
                sprite.draw(spaceGame.batch);
            }
        }


       spaceGame.batch.end();
        //debugRenderer.render(world,camera.combined);  // to render the bodies

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
      spaceGame.batch.dispose();
    }

}
