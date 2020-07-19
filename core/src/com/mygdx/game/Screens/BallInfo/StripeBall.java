package com.mygdx.game.Screens.BallInfo;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class    StripeBall extends Ball {
    public static int ballscoun=7;
    public StripeBall(Vector2 Pos, Sprite ballSprite) {
        super(Pos,ballSprite);
        this.getFixturedef().filter.categoryBits=Constants.BIT_OTHER_BALLS;//what it belongs to *collision Detection*
        this.getFixturedef().filter.groupIndex=1;//to make sure it collides with other balls
        this.getBall().createFixture(this.getFixturedef()).setUserData(ballscoun++);

    }

}
