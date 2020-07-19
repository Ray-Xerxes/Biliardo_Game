package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Screens.BallInfo.*;
import com.mygdx.game.SpaceGame;
import jdk.tools.jlink.internal.Utils;

public class GameScreen implements Screen {

    public static World world;
    public static Box2DDebugRenderer debugRenderer;
    public static OrthographicCamera camera;

    SpaceGame spaceGame;
    // makes the world move by the time (final)

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

    public Player player1;
    public Player player2;

    //drawing
    public Array<Body> tmpBodies;
    public Sprite table;

    public int counter;


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
        camera =new OrthographicCamera(spaceGame.VirtualWidth/20,spaceGame.VirtualHeight/20);
        world.setContactListener(new MyContactListener());
        // initialize the objects and sets the initial position
        cueball = new CueBall(	new Vector2(-15,0),	new Sprite(new Texture("Assets/whiteBall.png")));
        stick=new Stick(cueball,new Sprite(new Texture("Assets/Stick.png")));
        edge1 =  new Edge(new Vector2(-1,-9.6f),new Vector2(0.02f,-11f ),new Vector2(-1,9.6f),new Vector2(0.02f,11f),new Vector2(24.5f,0));
        edge2 =  new Edge(new Vector2(1,-9.6f),new Vector2(0.02f,-11f ),new Vector2(1,9.6f),new Vector2(0.02f,11f),new Vector2(-24.5f,0));
        edge3 =  new Edge(new Vector2(-10.5f,1.1f),new Vector2(-11,0.02f ),new Vector2(9.8f,1.1f),new Vector2(11,0.02f),new Vector2(11.8f,-12.65f));
        edge4 =  new Edge(new Vector2(-9.8f,1.1f),new Vector2(-11,0.02f ),new Vector2(10.3f,1.1f),new Vector2(10.8f,0.02f),new Vector2(-12.3f,-12.65f));
        edge5 =  new Edge(new Vector2(-10.5f,-1.1f),new Vector2(-11,0.02f ),new Vector2(9.8f,-1.1f),new Vector2(11,0.02f),new Vector2(11.8f,12.65f));
        edge6 =  new Edge(new Vector2(-9.8f,-1.1f),new Vector2(-11,0.02f ),new Vector2(10.3f,-1.1f),new Vector2(10.8f,0.02f),new Vector2(-12.3f,12.65f));

        player1 = new Player(new Sprite(new Texture("Assets/p1.png")));
        player2 = new Player(new Sprite(new Texture("Assets/p2.png")));

        player1.playerspirte.setPosition(-SpaceGame.VirtualWidth/50,-SpaceGame.VirtualHeight/40 +23.5f);
        player1.playerspirte.setSize(SpaceGame.VirtualWidth/60,SpaceGame.VirtualHeight/60);

        player2.playerspirte.setPosition(-SpaceGame.VirtualWidth/50+25,-SpaceGame.VirtualHeight/40+26.6f);
        player2.playerspirte.setSize(SpaceGame.VirtualWidth/65,SpaceGame.VirtualHeight/130);



        tmpBodies= new Array<Body>();
        int count=0; // used as index for the balls

        int num=1;
        // a function to set the balls position
        for (int i=0;i<5;i++)
        {
            float x= (float)(i * (Math.sqrt(5) * 0.55))+8; // gets the x position

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






    boolean stopcuewhilehitting=true;
    boolean cueballreleased=false;
    boolean checkcueball=false;
    boolean turnfinished=false;
    boolean checkfirstballtouched=false;
    boolean checkchangetheplayerturn=false;
    boolean checkdropedaball=false;
    boolean checkfirstround=false;
    int disx,disy;
    Vector3 vv;
    public static int foulnumber=0;
    @Override



    public void render(float delta) {

    if(checkcueball) // when the cueball enters any hole
    {
    Movethecueballfreely(); // moves the cueball in all the table freely
    }

      else  if(Checkallballsmovement()) {//if cueball stopped moving
          if(turnfinished)
          {
              System.out.println(foulnumber);
              System.out.println("player 1 : "+player1.isSolidBall()+" " + player1.isStripeBall());
              System.out.println("player 2 : "+player2.isSolidBall()+" " + player2.isStripeBall());

                 if(foulnumber==0) {
                  checkchangetheplayerturn = true;
                  System.out.println("the cueball didnt touch anyball");
              }
                 else if (Player.playeroneturn&&!player1.isCheckdropedaball())
                 {
                     checkchangetheplayerturn=false;
                     Changeplayerturn();
                     System.out.println("noballs1");

                 }
                 else if (!Player.playeroneturn&&!player2.isCheckdropedaball())
                 {
                     checkchangetheplayerturn=false;
                     Changeplayerturn();
                    System.out.println("noballs2");
                 }


              if(checkchangetheplayerturn)
              {

                  if(!checkfirstround){

                  Changeplayerturn();
                  checkchangetheplayerturn=false;
                  System.out.println("turn changed");

              }
                  else
                      checkfirstround=false;

              }

                  turnfinished=false;// the turn started
          }

        checkfirstballtouched=false;




        Controlthecuestick();// power and the movement of the stick
        }


        else { //if ball is moving
        stick.getStick().setTransform(10, 30, 0); // put the stick away
        turnfinished=true; // when u hit the ball the turn is finished


        if(checkfirstballtouched==false)
        {

            if(Player.playeroneturn)
            {
                if(foulnumber==1&&player1.getBalls_in_pocket()!=7)
                {
                    checkchangetheplayerturn=true;
                    System.out.println("the player1 touched the black");
                }
                else if (foulnumber==2&& !player1.isSolidBall())
                {
                    checkchangetheplayerturn=true;
                    System.out.println("the player1 touched the solid");

                }
                else if (foulnumber==3&& !player1.isStripeBall())
                {
                    checkchangetheplayerturn=true;
                    System.out.println("the player1 touched the stripe");

                }



            }
            else

            {
                if(foulnumber==1&&player2.getBalls_in_pocket()!=7)
                {
                    checkchangetheplayerturn=true;
                    System.out.println("the player2 touched the black");

                }
                else if (foulnumber==2&& !player2.isSolidBall())
                {
                    checkchangetheplayerturn=true;
                    System.out.println("the player2 touched the solid");

                }
                else if (foulnumber==3&& !player2.isStripeBall())
                {
                    checkchangetheplayerturn=true;
                    System.out.println("the player2 touched the stripe");

                }

            }


            if(foulnumber!=0)
            checkfirstballtouched=true;
        }


        }




        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1f/60f, 5, 8); // to make the world feel the time
       spaceGame.batch.setProjectionMatrix(camera.combined);
       spaceGame. batch.begin();
       table.draw(spaceGame.batch);




       if(Player.playeroneturn) //change player turn
       player1.playerspirte.draw(spaceGame.batch);
       else
       player2.playerspirte.draw(spaceGame.batch);




        //System.out.println(player1.isSolidBall() +" "+ player1.isStripeBall());
        Checkifanyballtouchestheholes();




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

    public boolean Checkallballsmovement()
    {
        if(!cueball.Checkifballstopped()||!blackball.Checkifballstopped())
            return false;

            for(int i=0;i<7;i++)
            {
                if(!solidball[i].Checkifballstopped())
                    return false;

                if(!stripeball[i].Checkifballstopped())
                    return false;
            }
        return true;
    }

    public void Movethecueballfreely()
    {
        vv = new Vector3((Gdx.input.getX()), Gdx.input.getY(), 0);//get mouse position
        GameScreen.camera.unproject(vv);//translate this position to our world coordinate
        if(vv.x>0)
            vv.x=Math.min(vv.x,23);
        else
            vv.x=Math.max(vv.x,-23);

        if(vv.y>0)
            vv.y=Math.min(vv.y,11);
        else
            vv.y=Math.max(vv.y,-11);


        cueball.getBall().setTransform(vv.x,vv.y,0);

        if(Gdx.input.isTouched()) {
            cueball.getBall().setActive(true);

            checkcueball=false;
        }
        stopcuewhilehitting=true;
    }



    public void Controlthecuestick()
    {
        counter= (int) Math.sqrt((Gdx.input.getX()-disx)*(Gdx.input.getX()-disx) + (Gdx.input.getY()-disy)*(Gdx.input.getY()-disy));

        if(stopcuewhilehitting) stick.updateStickRotation(); //if false stops the cuestick while hitting the ball to aim better

        Gdx.input.setInputProcessor(new InputHandle() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                stopcuewhilehitting=false;
                cueballreleased=true;
                counter++;
                disx=Gdx.input.getX();
                disy=Gdx.input.getY();
                return super.touchDown(screenX, screenY, pointer, button);

            }




            @Override
            public boolean touchDragged ( int screenX, int screenY, int pointer){
                if(cueballreleased) {
                    counter+=5;

                    stopcuewhilehitting = false;
                }
                return super.touchDragged(screenX, screenY, pointer);

            }

            @Override
            public boolean touchUp ( int screenX, int screenY, int pointer, int button){
                if(cueballreleased) {
                    if(Checkallballsmovement())
                        cueball.getBall().applyForceToCenter(new Vector2((float) (Math.cos(stick.angle) * 100 * counter), (float) (Math.sin(stick.angle) * 100 * counter)), true);
                    stopcuewhilehitting = true;
                    foulnumber=0;
                    player1.setCheckdropedaball(false);
                    player2.setCheckdropedaball(false);

                }
                counter=5;

                cueballreleased=false;
                return super.touchUp(screenX, screenY, pointer, button);
            }



        });

        //if(Gdx.input.isTouched())
        //counter+=2;


    }


    public void Checkifanyballtouchestheholes()
    {

        world.getBodies(tmpBodies);

        for(Body body:tmpBodies)
        {

            if((body.getPosition().y>=11.7f||body.getPosition().y<=-11.7f)&&body.getLinearDamping()!=0) { //if the balls touched the holes

                if(body.equals(cueball.getBall()))
                {
                    body.setTransform(0,0,0);
                    cueball.getBall().setLinearVelocity(0,0);
                    cueball.getBall().setAngularVelocity(0);
                    cueball.getFixturedef().isSensor=true;
                    cueball.getBall().setActive(false);
                    body.setTransform(0,0,0);

                    checkcueball=true;

                    System.out.println("you dropped the cueball in the hole");

                    checkchangetheplayerturn=true;
                }
                else {
                    body.setTransform(100, 100, 0);
                    if(body.equals(blackball.getBall())&&!blackball.isIsout()) {
                        if(Player.playeroneturn&&player1.getBalls_in_pocket()==7)
                        {
                            System.out.println("PLAYER 1 WON");
                        }
                        else if (Player.playeroneturn&&player1.getBalls_in_pocket()!=7)
                        {
                            System.out.println("PLAYER 2 WON");
                        }
                        else if (!Player.playeroneturn&&player2.getBalls_in_pocket()==7)
                        {
                            System.out.println("PLAYER 2 WON");
                        }
                        if(!Player.playeroneturn&&player2.getBalls_in_pocket()!=7)
                        {
                            System.out.println("PLAYER 1 WON");

                        }
                        blackball.setIsout(true);

                    }

                    for(int i=0;i<7;i++)
                    {

                        if(body.equals(solidball[i].getBall())) {
                            if(solidball[i].isIsout())
                                continue;
                            solidball[i].setIsout(true);

                            if(Player.playeroneturn &&player1.getBalls_in_pocket()==0&&player2.getBalls_in_pocket()==0)
                            {
                                player1.setSolidBall(true);
                                player2.setStripeBall(true);
                                checkfirstround=true;
                            }
                            else if (!Player.playeroneturn &&player2.getBalls_in_pocket()==0&&player1.getBalls_in_pocket()==0)
                            {
                                player2.setSolidBall(true);
                                player1.setStripeBall(true);
                                checkfirstround=true;
                            }

                            if(player1.isSolidBall()) {
                                player1.setBalls_in_pocket(player1.getBalls_in_pocket() + 1);
                            if(Player.playeroneturn)
                            {
                            player1.setCheckdropedaball(true);
                                System.out.println("player1true1");

                            }

                            }
                            else if (player2.isSolidBall()) {
                                player2.setBalls_in_pocket(player2.getBalls_in_pocket() + 1);
                                if(!Player.playeroneturn)
                                {
                                    player2.setCheckdropedaball(true);
                                    System.out.println("player2true1");

                                }
                            }


                        }




                        else if (body.equals(stripeball[i].getBall())) {
                            if(stripeball[i].isIsout())
                                continue;
                            stripeball[i].setIsout(true);

                            if(Player.playeroneturn &&player1.getBalls_in_pocket()==0&&player2.getBalls_in_pocket()==0)
                            {
                                player1.setStripeBall(true);

                                player2.setSolidBall(true);
                                checkfirstround=true;
                            }
                            else if (!Player.playeroneturn &&player2.getBalls_in_pocket()==0&&player1.getBalls_in_pocket()==0)
                            {
                                player2.setStripeBall(true);

                                player1.setSolidBall(true);
                                checkfirstround=true;
                            }

                            if(player1.isStripeBall()) {
                                player1.setBalls_in_pocket(player1.getBalls_in_pocket() + 1);
                                if(Player.playeroneturn)
                                {
                                    player1.setCheckdropedaball(true);
                                    System.out.println("player1true2");

                                }

                            }
                            else if (player2.isStripeBall()) {
                                player2.setBalls_in_pocket(player2.getBalls_in_pocket() + 1);
                                if(!Player.playeroneturn)
                                {
                                    player2.setCheckdropedaball(true);
                                    System.out.println("player2true2");

                                }
                            }


                        }

                    }
                }
            }

        }
    }


    public void Changeplayerturn()
    {
        if(Player.playeroneturn)
            Player.playeroneturn=false;
        else
            Player.playeroneturn=true;
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
