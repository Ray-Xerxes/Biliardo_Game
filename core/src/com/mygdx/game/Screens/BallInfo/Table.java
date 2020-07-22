package com.mygdx.game.Screens.BallInfo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.SpaceGame;

public class Table {
    private Sprite tablesprite;
    private CueBall cueball; // white ball
    private BlackBall blackball;
    private SolidBall solidball[]= new SolidBall[7];
    private StripeBall stripeball[]= new StripeBall[7];
    private Edge edge1 ;
    private Edge edge2 ;
    private Edge edge3 ;
    private Edge edge4 ;
    private Edge edge5 ;
    private Edge edge6 ;

    public Table(Sprite tablesprite)
    {
        this.tablesprite=tablesprite;
        tablesprite.setPosition(-SpaceGame.VirtualWidth/40,-SpaceGame.VirtualHeight/40);
        tablesprite.setSize(SpaceGame.VirtualWidth/20,SpaceGame.VirtualHeight/20);
        SetupTable();
        makeatriangle();
    }

public void SetupTable()
    {
        cueball = new CueBall(	new Vector2(-15,0),	new Sprite(new Texture("Assets/whiteBall.png")));
        edge1 =  new Edge(new Vector2(-1,-9.6f),new Vector2(0.02f,-11f ),new Vector2(-1,9.6f),new Vector2(0.02f,11f),new Vector2(24.5f,0));
        edge2 =  new Edge(new Vector2(1,-9.6f),new Vector2(0.02f,-11f ),new Vector2(1,9.6f),new Vector2(0.02f,11f),new Vector2(-24.5f,0));
        edge3 =  new Edge(new Vector2(-10.5f,1.1f),new Vector2(-11,0.02f ),new Vector2(9.8f,1.1f),new Vector2(11,0.02f),new Vector2(11.8f,-12.65f));
        edge4 =  new Edge(new Vector2(-9.8f,1.1f),new Vector2(-11,0.02f ),new Vector2(10.3f,1.1f),new Vector2(10.8f,0.02f),new Vector2(-12.3f,-12.65f));
        edge5 =  new Edge(new Vector2(-10.5f,-1.1f),new Vector2(-11,0.02f ),new Vector2(9.8f,-1.1f),new Vector2(11,0.02f),new Vector2(11.8f,12.65f));
        edge6 =  new Edge(new Vector2(-9.8f,-1.1f),new Vector2(-11,0.02f ),new Vector2(10.3f,-1.1f),new Vector2(10.8f,0.02f),new Vector2(-12.3f,12.65f));
    }
    public void makeatriangle()
    {

        int count=0; // used as index for the balls
        int num=1;
        // a function to set the balls position
        for (int i=0;i<5;i++)
        {
            float x= (float)(i * (Math.sqrt(5) * 0.55))+8; // gets the x position

            for(float y : rowXs(i)) { //gets the y position ( loops on every item in royXs(i) )

                if(count<7)
                {
                    solidball[count++] = new SolidBall( new Vector2(x, y),new Sprite(new Texture(String.format("Assets/ball %d.png",num))));
                    num++;

                }

                else if(count==7) {

                    blackball = new BlackBall(new Vector2(x, y),new Sprite(new Texture("Assets/ball 8.png")));
                    count++;
                    num++;

                }
                else
                {
                    stripeball[count++%8]= new StripeBall(new Vector2(x, y),new Sprite(new Texture(String.format("Assets/ball %d.png",num))));
                    num++;
                }



            }

        }


    }



    public static float[] rowXs(int rowNumber) { // a function to make the triangle balls shape
        float R = 0.75f; // radius of the ball
        switch (rowNumber) {
            case 0: return new float[] {0};
            case 1: return new float[] {-R, R};
            case 2: return new float[] {-2*R, 0, 2*R};
            case 3: return new float[] {-3*R, -R, R, 3*R};
            case 4: return new float[] {-4*R, -2*R, 0, 2*R, 4*R};

            default: throw new IllegalArgumentException("no more than 5 rows");
        }
    }


    public Sprite getTablesprite() {
        return tablesprite;
    }

    public void setTablesprite(Sprite tablesprite) {
        this.tablesprite = tablesprite;
    }

    public CueBall getCueball() {
        return cueball;
    }

    public void setCueball(CueBall cueball) {
        this.cueball = cueball;
    }

    public BlackBall getBlackball() {
        return blackball;
    }

    public void setBlackball(BlackBall blackball) {
        this.blackball = blackball;
    }

    public SolidBall[] getSolidball() {
        return solidball;
    }

    public void setSolidball(SolidBall[] solidball) {
        this.solidball = solidball;
    }

    public StripeBall[] getStripeball() {
        return stripeball;
    }

    public void setStripeball(StripeBall[] stripeball) {
        this.stripeball = stripeball;
    }

    public Edge getEdge1() {
        return edge1;
    }

    public void setEdge1(Edge edge1) {
        this.edge1 = edge1;
    }

    public Edge getEdge2() {
        return edge2;
    }

    public void setEdge2(Edge edge2) {
        this.edge2 = edge2;
    }

    public Edge getEdge3() {
        return edge3;
    }

    public void setEdge3(Edge edge3) {
        this.edge3 = edge3;
    }

    public Edge getEdge4() {
        return edge4;
    }

    public void setEdge4(Edge edge4) {
        this.edge4 = edge4;
    }

    public Edge getEdge5() {
        return edge5;
    }

    public void setEdge5(Edge edge5) {
        this.edge5 = edge5;
    }

    public Edge getEdge6() {
        return edge6;
    }

    public void setEdge6(Edge edge6) {
        this.edge6 = edge6;
    }
}
