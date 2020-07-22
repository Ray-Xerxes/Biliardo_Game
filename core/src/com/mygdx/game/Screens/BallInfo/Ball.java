package com.mygdx.game.Screens.BallInfo;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Screens.GameScreen;


public class Ball {

    private final float RADIUS = 0.65f;


    private Vector2 Position;
    private Body Ball;
    private BodyDef balldef;
    private FixtureDef fixturedef;
    private CircleShape shape;
    private Sprite ballSprite;
    private boolean isout;

    Ball(Vector2 Pos, Sprite ballSprite) // takes the x , y
    {
        isout=false;
        this.ballSprite = ballSprite;
        this.Position = Pos;
        balldef = new BodyDef();
        balldef.type = BodyDef.BodyType.DynamicBody;
        balldef.position.set(Position.x, Position.y);
        balldef.bullet = true;


        shape = new CircleShape();
        shape.setRadius(RADIUS); // small bodies works better in box2d

        fixturedef = new FixtureDef();
        // the ball physics
        fixturedef.density = 2.5f;
        fixturedef.friction = 0.2f;
        fixturedef.shape = shape;
        fixturedef.restitution = 0.8f;

        Ball = GameScreen.world.createBody(balldef);
        Ball.setUserData(ballSprite);


        // both used like the friction
        Ball.setLinearDamping(0.7f);
        Ball.setAngularDamping(0.7f);

        Ball.createFixture(fixturedef);

        //create sprite
        ballSprite.setSize(RADIUS * 2, RADIUS * 2);
        ballSprite.setOrigin(ballSprite.getWidth() / 2, ballSprite.getHeight() / 2);


    }
    public boolean Checkifballstopped(){
        if (this.getBall().getLinearVelocity().len()<0.2) {
            this.getBall().setLinearVelocity(0,0);
            this.getBall().setAngularVelocity(0);

            return true;
        }
        else return false;


    }
    public Body getBall() {
        return Ball;
    }

    public void setBall(Body ball) {
        Ball = ball;
    }


    public float getRADIUS() {
        return RADIUS;
    }

    public Vector2 getPosition() {
        return Position;
    }

    public void setPosition(Vector2 position) {
        Position = position;
    }

    public BodyDef getBalldef() {
        return balldef;
    }

    public void setBalldef(BodyDef balldef) {
        this.balldef = balldef;
    }

    public FixtureDef getFixturedef() {
        return fixturedef;
    }

    public void setFixturedef(FixtureDef fixturedef) {
        this.fixturedef = fixturedef;
    }

    public CircleShape getShape() {
        return shape;
    }

    public void setShape(CircleShape shape) {
        this.shape = shape;
    }

    public Sprite getBallSprite() {
        return ballSprite;
    }

    public void setBallSprite(Sprite ballSprite) {
        this.ballSprite = ballSprite;
    }

    public boolean isIsout() {
        return isout;
    }

    public void setIsout(boolean isout) {
        this.isout = isout;
    }
}
