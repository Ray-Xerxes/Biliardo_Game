package com.mygdx.game.Screens.BallInfo;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.SpaceGame;

public class MyContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fa=contact.getFixtureA();
        Fixture fb=contact.getFixtureB();

        if(fa==null||fb==null)return;
        if(fa.getUserData()==null||fb.getUserData()==null)return;

        if(fa.getUserData().equals("cueball") && fb.getUserData().equals("Blackball")) {
            GameScreen.foulsystem.foulnumber = 1;
            //System.out.println("blacktouched");

        }


        else if(fa.getUserData().equals("cueball") && fb.getUserData().toString().matches("0|1|2|3|4|5|6")) {
            GameScreen.foulsystem.foulnumber = 2;
            //System.out.println("solidtouched");

        }

        else if(fa.getUserData().equals("cueball") && fb.getUserData().toString().matches("7|8|9|10|11|12|13")) {
            GameScreen.foulsystem.foulnumber = 3;
        //System.out.println("stripetouched");
        }



    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
