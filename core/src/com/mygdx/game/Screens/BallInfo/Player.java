package com.mygdx.game.Screens.BallInfo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.InputHandle;
import com.mygdx.game.SpaceGame;

public class Player {
    private Stick stick;
    private boolean StripeBall;
    private boolean SolidBall;
    private boolean Black_touched;
    private boolean Black_in_pocket;
    private boolean checkdropedaball;
    public Sprite playerspirte;
    public static boolean playeroneturn=true;
    private int balls_in_pocket;
    private int power;
    private int disx = 0,disy = 0;
    private Vector3 vv;
    public  boolean stopcuewhilehitting=true;
    public  boolean cueballreleased=false;

    public Player(Sprite playerspirte,Stick stick,Vector2 pos,Vector2 size)
    {
        this.stick=stick;
        stick.getStick().setTransform(10, 30, 0); // puts the stick away
        playerspirte.setPosition(pos.x,pos.y);
        playerspirte.setSize(size.x,size.y);

        this.playerspirte=playerspirte;
        StripeBall=false;
        SolidBall=false;
        Black_touched=false;
        Black_in_pocket=false;
        checkdropedaball=false;
        balls_in_pocket=0;
    }

    public void Controlthecuestick()
    {

        power= (int) Math.sqrt((Gdx.input.getX()-disx)*(Gdx.input.getX()-disx) + (Gdx.input.getY()-disy)*(Gdx.input.getY()-disy));

        if(stopcuewhilehitting) {
            stick.updateStickRotation(); //if false stops the cuestick while hitting the ball to aim better
        }
        Gdx.input.setInputProcessor(new InputHandle() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                stopcuewhilehitting=false;
                cueballreleased=true;
                power++;
                disx=Gdx.input.getX();
                disy=Gdx.input.getY();
                return super.touchDown(screenX, screenY, pointer, button);

            }




            @Override
            public boolean touchDragged ( int screenX, int screenY, int pointer){
                if(cueballreleased) {
                    power+=5;
                    stopcuewhilehitting = false;
                }
                return super.touchDragged(screenX, screenY, pointer);

            }

            @Override
            public boolean touchUp ( int screenX, int screenY, int pointer, int button){
                if(cueballreleased) {
                    if(GameScreen.foulsystem.Checkallballsmovement()) {
                        GameScreen.table.getCueball().getBall().applyForceToCenter(new Vector2((float) (Math.cos(stick.angle) * 100 * power), (float) (Math.sin(stick.angle) * 100 * power)), true);
                    }
                    stopcuewhilehitting = true;
                    GameScreen.foulsystem.foulnumber=0;
                    checkdropedaball=false;

                }
                power=5;

                cueballreleased=false;
                return super.touchUp(screenX, screenY, pointer, button);
            }



        });

        //if(Gdx.input.isTouched())
        //power+=2;



    }
    public void Movethecueballfreely()
    {
        if(!GameScreen.foulsystem.Checkallballsmovement()) {
            GameScreen.table.getCueball().getBall().setTransform(10, 30,0);
            return;
        }

        if(GameScreen.foulsystem.checkchangetheplayerturn)
        {
            Player.playeroneturn= !Player.playeroneturn;
            GameScreen.foulsystem.checkchangetheplayerturn=false;
            GameScreen.foulsystem.foulnumber=5;
            checkdropedaball=true;

        }
        //body.setTransform(0,0,0);
        GameScreen.table.getCueball().getBall().setLinearVelocity(0,0);
        GameScreen.table.getCueball().getBall().setAngularVelocity(0);
        GameScreen.table.getCueball().getFixturedef().isSensor=true;
        GameScreen.table.getCueball().getBall().setActive(false);
        //body.setTransform(0,0,0);


       stick.getStick().setTransform(10, 30, 0); // puts the stick away


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


        GameScreen.table.getCueball().getBall().setTransform(vv.x,vv.y,0);

        if(Gdx.input.isTouched()) {
            GameScreen.table.getCueball().getBall().setActive(true);

            GameScreen.checkcueball=false;
        }
        stopcuewhilehitting=true;
    }



    public boolean isStripeBall() {
        return StripeBall;
    }

    public void setStripeBall(boolean stripeBall) {
        StripeBall = stripeBall;
    }

    public boolean isSolidBall() {
        return SolidBall;
    }

    public void setSolidBall(boolean solidBall) {
        SolidBall = solidBall;
    }

    public boolean isBlack_touched() {
        return Black_touched;
    }

    public void setBlack_touched(boolean black_touched) {
        Black_touched = black_touched;
    }

    public boolean isBlack_in_pocket() {
        return Black_in_pocket;
    }

    public void setBlack_in_pocket(boolean black_in_pocket) {
        Black_in_pocket = black_in_pocket;
    }

    public Sprite getPlayerspirte() {
        return playerspirte;
    }

    public void setPlayerspirte(Sprite playerspirte) {
        this.playerspirte = playerspirte;
    }

    public static boolean isPlayeroneturn() {
        return playeroneturn;
    }

    public static void setPlayeroneturn(boolean playeroneturn) {
        Player.playeroneturn = playeroneturn;
    }

    public int getBalls_in_pocket() {
        return balls_in_pocket;
    }

    public void setBalls_in_pocket(int balls_in_pocket) {
        this.balls_in_pocket = balls_in_pocket;
    }

    public boolean isCheckdropedaball() {
        return checkdropedaball;
    }

    public void setCheckdropedaball(boolean checkdropedaball) {
        this.checkdropedaball = checkdropedaball;
    }

    public Stick getStick() {
        return stick;
    }

    public void setStick(Stick stick) {
        this.stick = stick;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
