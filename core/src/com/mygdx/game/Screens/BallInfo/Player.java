package com.mygdx.game.Screens.BallInfo;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    private boolean StripeBall;
    private boolean SolidBall;
    private boolean Black_touched;
    private boolean Black_in_pocket;
    private boolean checkdropedaball;
    public Sprite playerspirte;
    public static boolean playeroneturn=true;
    private int balls_in_pocket;


    public Player(Sprite playerspirte)
    {
        this.playerspirte=playerspirte;
        StripeBall=false;
        SolidBall=false;
        Black_touched=false;
        Black_in_pocket=false;
        checkdropedaball=false;
        balls_in_pocket=0;
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
}
