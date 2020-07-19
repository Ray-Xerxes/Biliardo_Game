package com.mygdx.game.Screens.BallInfo;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class CueBall extends Ball {

    public CueBall(Vector2 Pos, Sprite ballSprite) {
        super(Pos,ballSprite);
        this.getFixturedef().filter.categoryBits=Constants.BIT_CUE_BALL;//what it belongs to
        this.getFixturedef().filter.groupIndex=1;//to make sure it collides with other balls
        this.getBall().createFixture(this.getFixturedef()).setUserData("cueball");
    }

}
