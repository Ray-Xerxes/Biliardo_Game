package com.mygdx.game.Screens.BallInfo;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Screens.GameScreen;

public class Edge {
    Vector2 FromPostion,ToPostion;
    BodyDef Egdedef;
    FixtureDef fixturedef;
    ChainShape shape;
    Sprite EdgeSprite;

    public Edge(Vector2 FromPos, Vector2 ToPos, Vector2 Pos)
    {
        this.FromPostion=FromPos;
        this.ToPostion=ToPos;

        Egdedef = new BodyDef();
        Egdedef.type= BodyDef.BodyType.StaticBody;
        Egdedef.position.set(Pos.x,Pos.y);

        shape = new ChainShape();
        shape.createChain(  new Vector2[]{FromPos,ToPos}  );// the length of the chain

        fixturedef = new FixtureDef();
        fixturedef.density=2.5f;
        fixturedef.friction=0.5f;
        fixturedef.shape=shape;
        fixturedef.restitution=0;
        GameScreen.world.createBody(Egdedef).createFixture(fixturedef);

    }
}
