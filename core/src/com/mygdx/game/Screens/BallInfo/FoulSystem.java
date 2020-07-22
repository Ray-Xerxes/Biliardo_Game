package com.mygdx.game.Screens.BallInfo;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Screens.GameScreen;

public class FoulSystem {
    public boolean checkfirstballtouched=false;
    public boolean checkfirstround=false;
    public boolean checknoballs=false;
    public static boolean checkchangetheplayerturn=false;
    public static int foulnumber=0;


    public void Checkfouls()
    {
        if(GameScreen.turnfinished)
        {
            System.out.println(foulnumber);
            System.out.println("player 1 : "+GameScreen.player1.isSolidBall()+" " + GameScreen.player1.isStripeBall());
            System.out.println("player 2 : "+GameScreen.player2.isSolidBall()+" " + GameScreen.player2.isStripeBall());

            if(foulnumber==0&&checkfirstround) {
                GameScreen.checkcueball = true;
                checkchangetheplayerturn=false;
                Player.playeroneturn= !Player.playeroneturn;
                System.out.println("the cueball didnt touch anyball");
            }
            else if (Player.playeroneturn&&!GameScreen.player1.isCheckdropedaball())
            {
                checkchangetheplayerturn=false;
                Player.playeroneturn= !Player.playeroneturn;
                checknoballs=true;
                System.out.println("noballs1");

            }
            else if (!Player.playeroneturn&&!GameScreen.player2.isCheckdropedaball())
            {
                checkchangetheplayerturn=false;
                Player.playeroneturn= !Player.playeroneturn;
                checknoballs=true;
                System.out.println("noballs2");
            }


            if(checkchangetheplayerturn)
            {

                if(!checkfirstround){

                    Player.playeroneturn= !Player.playeroneturn;
                    checkchangetheplayerturn=false;
                    System.out.println("turn changed");

                }
                else {
                   checkfirstround = false;
                    checkchangetheplayerturn=false;
                }

            }
            checkchangetheplayerturn=false;
            GameScreen.turnfinished=false;// the turn started
        }

        checkfirstballtouched=false;

    }

    public void Detectifanyfoulsishappening()
    {
        if(checkfirstballtouched==false&&checkfirstround)
        {

            if(Player.playeroneturn)
            {
                if(foulnumber==1&& GameScreen.player1.getBalls_in_pocket()!=7)
                {
                    GameScreen.checkcueball=true;
                    System.out.println("the player1 touched the black");
                }
                else if (foulnumber==2&& !GameScreen.player1.isSolidBall())
                {
                    GameScreen.checkcueball=true;
                    System.out.println("the player1 touched the solid");

                }
                else if (foulnumber==3&& !GameScreen.player1.isStripeBall()&&GameScreen.player1.getBalls_in_pocket()!=7)
                {
                    GameScreen.checkcueball=true;
                    System.out.println("the player1 touched the stripe");

                }
                else
                {
                    GameScreen.checkcueball=false;
                }



            }
            else

            {
                if(foulnumber==1&&GameScreen.player2.getBalls_in_pocket()!=7)
                {
                    GameScreen.checkcueball=true;
                    System.out.println("the player2 touched the black");

                }
                else if (foulnumber==2&& !GameScreen.player2.isSolidBall())
                {
                    GameScreen.checkcueball=true;
                    System.out.println("the player2 touched the solid");

                }
                else if (foulnumber==3&& !GameScreen.player2.isStripeBall()&&GameScreen.player2.getBalls_in_pocket()!=7)
                {
                    GameScreen.checkcueball=true;
                    System.out.println("the player2 touched the stripe");

                }
                else
                {
                    GameScreen.checkcueball=false;
                }

            }


            if(foulnumber!=0)
                checkfirstballtouched=true;
        }




    }

    public void Checkifanyballtouchestheholes()
    {

        GameScreen.world.getBodies(GameScreen.tmpBodies);

        for(Body body:GameScreen.tmpBodies)
        {

            if((body.getPosition().y>=11.7f||body.getPosition().y<=-11.7f)&&body.getLinearDamping()!=0) { //if the balls touched the holes

                if(body.equals(GameScreen.table.getCueball().getBall()))
                {

                    GameScreen.table.getCueball().getBall().setLinearVelocity(0,0);
                    GameScreen.table.getCueball().getBall().setTransform(100,100,0);
                    GameScreen.checkcueball=true;

                    System.out.println("you dropped the cueball in the hole");
                    if(!checknoballs) {
                        checkchangetheplayerturn = true;
                        checknoballs=false;
                    }
                }
                else {
                    body.setTransform(100, 100, 0);
                    if(body.equals(GameScreen.table.getBlackball().getBall())&&!GameScreen.table.getBlackball().isIsout()) {
                        if(Player.playeroneturn&&GameScreen.player1.getBalls_in_pocket()==7)
                        {
                            System.out.println("PLAYER 1 WON");
                        }
                        else if (Player.playeroneturn&&GameScreen.player1.getBalls_in_pocket()!=7)
                        {
                            System.out.println("PLAYER 2 WON");
                        }
                        else if (!Player.playeroneturn&&GameScreen.player2.getBalls_in_pocket()==7)
                        {
                            System.out.println("PLAYER 2 WON");
                        }
                        if(!Player.playeroneturn&&GameScreen.player2.getBalls_in_pocket()!=7)
                        {
                            System.out.println("PLAYER 1 WON");

                        }
                        GameScreen.table.getBlackball().setIsout(true);

                    }

                    for(int i=0;i<7;i++)
                    {

                        if(body.equals(GameScreen.table.getSolidball()[i].getBall())) {
                            if(GameScreen.table.getSolidball()[i].isIsout())
                                continue;
                            GameScreen.table.getSolidball()[i].setIsout(true);
                            body.setLinearVelocity(0,0);
                            body.setAngularVelocity(0);
                            if(Player.playeroneturn &&GameScreen.player1.getBalls_in_pocket()==0&&GameScreen.player2.getBalls_in_pocket()==0)
                            {
                                GameScreen.player1.setSolidBall(true);
                                GameScreen.player2.setStripeBall(true);
                                checkfirstround=true;
                            }
                            else if (!Player.playeroneturn &&GameScreen.player2.getBalls_in_pocket()==0&&GameScreen.player1.getBalls_in_pocket()==0)
                            {
                                GameScreen.player2.setSolidBall(true);
                                GameScreen. player1.setStripeBall(true);
                                checkfirstround=true;
                            }

                            if(GameScreen.player1.isSolidBall()) {
                                GameScreen.player1.setBalls_in_pocket(GameScreen.player1.getBalls_in_pocket() + 1);
                                if(Player.playeroneturn)
                                {
                                    GameScreen.player1.setCheckdropedaball(true);
                                    System.out.println("player1true1");

                                }

                            }
                            else if (GameScreen.player2.isSolidBall()) {
                                GameScreen.player2.setBalls_in_pocket(GameScreen.player2.getBalls_in_pocket() + 1);
                                if(!Player.playeroneturn)
                                {
                                    GameScreen.player2.setCheckdropedaball(true);
                                    System.out.println("player2true1");

                                }
                            }


                        }




                        else if (body.equals(GameScreen.table.getStripeball()[i].getBall())) {
                            if(GameScreen.table.getStripeball()[i].isIsout())
                                continue;
                            GameScreen.table.getStripeball()[i].setIsout(true);
                            body.setLinearVelocity(0,0);
                            body.setAngularVelocity(0);

                            if(Player.playeroneturn &&GameScreen.player1.getBalls_in_pocket()==0&&GameScreen.player2.getBalls_in_pocket()==0)
                            {
                                GameScreen.player1.setStripeBall(true);

                                GameScreen.player2.setSolidBall(true);
                                checkfirstround=true;
                            }
                            else if (!Player.playeroneturn &&GameScreen.player2.getBalls_in_pocket()==0&&GameScreen.player1.getBalls_in_pocket()==0)
                            {
                                GameScreen.player2.setStripeBall(true);

                                GameScreen.player1.setSolidBall(true);
                               checkfirstround=true;
                            }

                            if(GameScreen.player1.isStripeBall()) {
                                GameScreen.player1.setBalls_in_pocket(GameScreen.player1.getBalls_in_pocket() + 1);
                                if(Player.playeroneturn)
                                {
                                    GameScreen.player1.setCheckdropedaball(true);
                                    System.out.println("player1true2");

                                }

                            }
                            else if (GameScreen.player2.isStripeBall()) {
                                GameScreen.player2.setBalls_in_pocket(GameScreen.player2.getBalls_in_pocket() + 1);
                                if(!Player.playeroneturn)
                                {
                                    GameScreen.player2.setCheckdropedaball(true);
                                    System.out.println("player2true2");

                                }
                            }


                        }

                    }
                }
            }

        }
    }


    public static boolean Checkallballsmovement()
    {
        if(!GameScreen.table.getCueball().Checkifballstopped()||!GameScreen.table.getBlackball().Checkifballstopped())
            return false;

        for(int i=0;i<7;i++)
        {
            if(!GameScreen.table.getSolidball()[i].Checkifballstopped())
                return false;

            if(!GameScreen.table.getStripeball()[i].Checkifballstopped())
                return false;
        }
        return true;
    }



}
