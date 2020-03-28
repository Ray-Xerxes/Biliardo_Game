package com.mygdx.game.Screens.BallInfo;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.Screens.GameScreen;

public class Edge {
    Vector2 FromPostion,ToPostion;
    BodyDef Egdedef;
    FixtureDef fixturedef;
    PolygonShape shape;
    Sprite EdgeSprite;

    public Edge(Vector2 vertices1,Vector2 vertices2,Vector2 vertices3,Vector2 vertices4, Vector2 Pos)
    {
        Egdedef = new BodyDef();
        Egdedef.type= BodyDef.BodyType.StaticBody;
        Egdedef.position.set(Pos.x,Pos.y);




        shape = new PolygonShape();
        Vector2[] vertices = new Vector2[4];

        vertices[0] = vertices1;
        vertices[1] = vertices2;
        vertices[2] = vertices3;
        vertices[3] = vertices4;
        shape = new PolygonShape();
        shape.set(vertices);

        fixturedef = new FixtureDef();
        fixturedef.density=2.5f;
        fixturedef.friction=0.5f;
        fixturedef.shape=shape;
        fixturedef.restitution=0;
        GameScreen.world.createBody(Egdedef).createFixture(fixturedef);

    }
}
