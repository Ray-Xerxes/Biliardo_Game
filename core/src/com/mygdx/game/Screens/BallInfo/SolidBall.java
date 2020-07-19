package com.mygdx.game.Screens.BallInfo;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SolidBall extends Ball {
    public static int ballscoun=0;
    public SolidBall(Vector2 Pos, Sprite ballSprite) {
        super(Pos, ballSprite);
        this.getFixturedef().filter.categoryBits=Constants.BIT_OTHER_BALLS;//what it belongs to *collision Detection*
        this.getFixturedef().filter.groupIndex=1;//to make sure that they collide with the white ball
        this.getBall().createFixture(this.getFixturedef()).setUserData(ballscoun++);

    }

}
