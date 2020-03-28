package com.mygdx.game.Screens.BallInfo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.mygdx.game.Screens.GameScreen;

public class Stick {
    BodyDef bodyDef;
    FixtureDef fixDef;
    Body stick;
    CueBall cueball;
    PolygonShape stk;
    public Vector3 vv;//Projection vector to translate mouse position coordinates to our world coordinates
    public float adj;
    public float opp;
    public float angle;
    public static final int  StickCueBallDistance =14;//distance between stick and WhiteBall
    Sprite StickSprite;



    public Stick(CueBall cueball, Sprite StickSprite){
        this.StickSprite=StickSprite;
        this.cueball=cueball;
        bodyDef = new BodyDef();
        fixDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(-20f,0f));

        //stick shape
        stk = new PolygonShape();
        stk.setAsBox(3f, .2f);

        //fixtureDef
        fixDef.shape = stk;
        fixDef.friction = 0;
        fixDef.restitution = 0;
        fixDef.density = 10;

        //collision detection
        fixDef.filter.categoryBits=Constants.BIT_STICK;//what it belongs to
        fixDef.filter.maskBits=Constants.BIT_CUE_BALL;//the only thing it collides with
        //fixDef.filter.groupIndex=1;
        stick = GameScreen.world.createBody(bodyDef);
        stick.createFixture(fixDef);
        vv = new Vector3((Gdx.input.getX()), Gdx.input.getY(), 0);
        GameScreen.camera.unproject(vv);
        adj = vv.x-stick.getWorldCenter().x ;
        opp =vv.y- stick.getWorldCenter().y ;
        angle = (float) Math.atan2(opp, adj);

        //create sprite
        StickSprite.setSize(30f,  4f);
        StickSprite.setRotation(angle);
        StickSprite.setOrigin(StickSprite.getWidth() / 2, StickSprite.getHeight() / 2);
        stick.setUserData(StickSprite);

    }
    public void updateStickRotation() {
        vv = new Vector3((Gdx.input.getX()), Gdx.input.getY(), 0);//get mouse position
        GameScreen.camera.unproject(vv);//translate this position to our world coordinate
        adj = vv.x-stick.getWorldCenter().x ;//line between mouse point x and stick center in x-axis
        opp =vv.y- stick.getWorldCenter().y ;//line between mouse point y and stick center in Y-axis
        angle = (float) Math.atan2(opp, adj);//get angle of transformation
        //use angle to transform position of stick and its angle to look towards the mouse
        stick.setTransform(cueball.getBall().getPosition().x-(float)(Math.cos(angle)*StickCueBallDistance),
                cueball.getBall().getPosition().y-(float)(Math.sin(angle)*StickCueBallDistance), angle);

    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public void setBodyDef(BodyDef bodyDef) {
        this.bodyDef = bodyDef;
    }

    public FixtureDef getFixDef() {
        return fixDef;
    }

    public void setFixDef(FixtureDef fixDef) {
        this.fixDef = fixDef;
    }

    public Body getStick() {
        return stick;
    }

    public void setStick(Body stick) {
        this.stick = stick;
    }

    public CueBall getCueball() {
        return cueball;
    }

    public void setCueball(CueBall cueball) {
        this.cueball = cueball;
    }

    public PolygonShape getStk() {
        return stk;
    }

    public void setStk(PolygonShape stk) {
        this.stk = stk;
    }

    public Vector3 getVv() {
        return vv;
    }

    public void setVv(Vector3 vv) {
        this.vv = vv;
    }

    public float getAdj() {
        return adj;
    }

    public void setAdj(float adj) {
        this.adj = adj;
    }

    public float getOpp() {
        return opp;
    }

    public void setOpp(float opp) {
        this.opp = opp;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public static int getStickCueBallDistance() {
        return StickCueBallDistance;
    }
}
