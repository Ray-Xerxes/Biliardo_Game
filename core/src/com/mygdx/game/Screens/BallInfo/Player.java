package com.mygdx.game.Screens.BallInfo;

public class Player {
    private boolean StripeBall;
    private boolean SolidBall;
    private boolean Black_touched;
    private boolean Black_in_pocket;


    private int balls_in_pocket;


    Player()
    {
        StripeBall=false;
        SolidBall=false;
        Black_touched=false;
        Black_in_pocket=false;
        balls_in_pocket=0;
    }

}
