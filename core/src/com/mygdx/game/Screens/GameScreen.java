package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Screens.BallInfo.*;
import com.mygdx.game.SpaceGame;

public class GameScreen implements Screen {

    public static World world;
    public static Box2DDebugRenderer debugRenderer;
    public static OrthographicCamera camera;
    SpaceGame spaceGame;
    // makes the world move by the time (final)
    private final float TIMESTEP=1/60f;
    private final int VELOCITYITERATIONS = 8 , POSTIONITERATIONS =3;
    public static CueBall cueball; // white ball
    public static BlackBall blackball;
    public static SolidBall solidball[]= new SolidBall[7];
    public static StripeBall stripeball[]= new StripeBall[7];
    public Stick stick;
    public Edge edge1 ;
    public Edge edge2 ;
    public Edge edge3 ;
    public Edge edge4 ;
    public Edge edge5 ;
    public Edge edge6 ;


    //drawing
    public Array<Body> tmpBodies;
    public Sprite table;


   public GameScreen(SpaceGame spaceGame) {
       this.spaceGame = spaceGame;
   }
    @Override
    public void show() {

        table=new Sprite(new Texture("Assets/table.png"));
        table.setPosition(-SpaceGame.VirtualWidth/40,-SpaceGame.VirtualHeight/40);
        table.setSize(SpaceGame.VirtualWidth/20,SpaceGame.VirtualHeight/20);
        world = new World(new Vector2(0,0),true); // x= 0 and y =0 means no gravity
        debugRenderer = new Box2DDebugRenderer();
        camera =new OrthographicCamera(spaceGame.VirtualWidth/20,spaceGame.VirtualHeight/20);  // zooms in 20 times from the fullscreen

        // initialize the objects and sets the initial position
        cueball = new CueBall(	new Vector2(-15,0),	new Sprite(new Texture("Assets/whiteBall.png")));
        stick=new Stick(cueball,new Sprite(new Texture("Assets/Stick.png")));
        edge1 =  new Edge(new Vector2(-1,-9.6f),new Vector2(0.02f,-11f ),new Vector2(-1,9.6f),new Vector2(0.02f,11f),new Vector2(24.5f,0));
        edge2 =  new Edge(new Vector2(1,-9.6f),new Vector2(0.02f,-11f ),new Vector2(1,9.6f),new Vector2(0.02f,11f),new Vector2(-24.5f,0));
        edge3 =  new Edge(new Vector2(-10.5f,1.1f),new Vector2(-11,0.02f ),new Vector2(9.8f,1.1f),new Vector2(11,0.02f),new Vector2(11.8f,-12.65f));
        edge4 =  new Edge(new Vector2(-9.8f,1.1f),new Vector2(-11,0.02f ),new Vector2(10.3f,1.1f),new Vector2(10.8f,0.02f),new Vector2(-12.3f,-12.65f));
        edge5 =  new Edge(new Vector2(-10.5f,-1.1f),new Vector2(-11,0.02f ),new Vector2(9.8f,-1.1f),new Vector2(11,0.02f),new Vector2(11.8f,12.65f));
        edge6 =  new Edge(new Vector2(-9.8f,-1.1f),new Vector2(-11,0.02f ),new Vector2(10.3f,-1.1f),new Vector2(10.8f,0.02f),new Vector2(-12.3f,12.65f));




        tmpBodies= new Array<Body>();
        int count=0; // used as index for the balls

        int num=1;
        // a function to set the balls position
        for (int i=0;i<5;i++)
        {
            float x= (float)(i * (Math.sqrt(5) * 0.65))+8; // gets the x position

            for(float y : rowXs(i)) { //gets the y position ( loops on every item in royXs(i) )

                if(count<7)
                {
                    solidball[count++] = new SolidBall( new Vector2(x, y),new Sprite(new Texture(String.format("Assets/ball %d.png",num))));
                    num++;

                }

                else if(count==7) {

                    blackball = new BlackBall(new Vector2(x, y),new Sprite(new Texture("Assets/ball 8.png")));
                    count++;
                    num++;

                }
                else
                {
                    stripeball[count++%8]= new StripeBall(new Vector2(x, y),new Sprite(new Texture(String.format("Assets/ball %d.png",num))));
                    num++;
                }



            }

        }
    }
    boolean check=true;

    @Override
    public void render(float delta) {

        if(cueball.CheckBallMovement()) {//if cueball stopped moving
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) { // press a to push the cueball
                check=false;
                //I took the same angle used in updateStickRotation() to position the force in the direction of the mouse
                cueball.getBall().applyForceToCenter(new Vector2( (float)(Math.cos(stick.angle) * 70000),(float) (Math.sin(stick.angle) * 70000)), true);

                //want to delay disappearance of stick till after the cueball is hit by 2 seconds

                Timer.Task task = new Timer.Task() {//Specify the task I want to delay
                    @Override

                    public void run() {
                        //System.out.println("MEOW");
                        check=true;
                    }

                };
                new Timer().schedule(task, 0.5f);//delay time



            }
            else {stick.updateStickRotation();}//if nothing is pressed update stick position

        }
        else{if(check)stick.getStick().setTransform(39,30,0);}//stick disappear



        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        world.step(TIMESTEP,VELOCITYITERATIONS,POSTIONITERATIONS); // to make the world feel the time

       spaceGame.batch.setProjectionMatrix(camera.combined);
       spaceGame. batch.begin();
       table.draw(spaceGame.batch);
        world.getBodies(tmpBodies);


        for(Body body:tmpBodies)
        {
            if((body.getPosition().y>=11.8f||body.getPosition().y<=-11.8f)&&body.getLinearDamping()!=0)
                body.setTransform(100,100,0);
        }

        for(Body body:tmpBodies)
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
    public static float[] rowXs(int rowNumber) {
        float R = 0.75f; // radius of the ball
        switch (rowNumber) {
            case 0: return new float[] {0};
            case 1: return new float[] {-R, R};
            case 2: return new float[] {-2*R, 0, 2*R};
            case 3: return new float[] {-3*R, -R, R, 3*R};
            case 4: return new float[] {-4*R, -2*R, 0, 2*R, 4*R};

            default: throw new IllegalArgumentException("no more than 5 rows");
        }
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
