/**
 * TilePuzzle.java
 *
 * TilePuzzle Applet application
 *  --- contains no comments
 *  --- extend puzzle to 5 by 5
 *
 */
import java.applet.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TilePuzzle0 extends Applet implements KeyListener, ActionListener {
    Image[][] pic;
    Image[][] rightPic;
    Image blank;
    Rectangle[][] tile;
    int tileWidth;
    int tileHeight;
    Boolean[][] space;
    TextArea test;
    Boolean moved;
    int randX, randY;
    Button takeAway;
    Button solve;
    String buttonPressed;
    int correct;
    Boolean complete;
    AudioClip cheer;
    AudioClip swap;
    AudioClip shuffle;
    int moves;
    int steps;
    String turn;

    public void init() {
        moves=0;
        steps=0;

        cheer=getAudioClip(getCodeBase(),"applause.wav");
        swap=getAudioClip(getCodeBase(),"click.wav");
        shuffle=getAudioClip(getCodeBase(),"shuffle.wav");
        moved=false;

        takeAway=new Button("Remove a tile");
        setLayout(null);
        takeAway.setBounds(350,10,100,50);
        takeAway.addActionListener(this);
        add(takeAway);

        solve=new Button("Start to solve");
        setLayout(null);
        solve.setBounds(350,10,100,50);
        solve.addActionListener(this);

        blank=getImage(getCodeBase(),"b.jpg");

        pic=new Image[3][3];
        pic[0][0]=getImage(getCodeBase(),"c1.jpg");
        pic[0][1]=getImage(getCodeBase(),"c2.jpg");
        pic[0][2]=getImage(getCodeBase(),"c3.jpg");
        pic[1][0]=getImage(getCodeBase(),"c4.jpg");
        pic[1][1]=getImage(getCodeBase(),"c5.jpg");
        pic[1][2]=getImage(getCodeBase(),"c6.jpg");
        pic[2][0]=getImage(getCodeBase(),"c7.jpg");
        pic[2][1]=getImage(getCodeBase(),"c8.jpg");
        pic[2][2]=getImage(getCodeBase(),"c9.jpg");

        rightPic=new Image[3][3];
        for (int a=0;a<3;a++) {
            for (int b=0;b<3;b++) {
                rightPic[a][b]=pic[a][b];
            }
        }

        tileWidth=100;
        tileHeight=100;

        tile=new Rectangle[3][3];
        for (int a=0;a<3;a++) {
            for (int b=0;b<3;b++) {
                tile[a][b]=new Rectangle();
                tile[a][b].width=tileWidth;
                tile[a][b].height=tileHeight;
                tile[a][b].x=b*100;
                tile[a][b].y=a*100;
            }
        }

        addKeyListener(this);
        requestFocus();
    }

    public void paint(Graphics g) {
        if (correct!=8) {
            complete=false;
            for (int a=0;a<3;a++) {
                for (int b=0;b<3;b++) {
                    g.drawImage(pic[a][b],tile[a][b].x,tile[a][b].y,tile[a][b].width,tile[a][b].height,this);
                }
            }
        }

        if (correct==8) {
            for (int a=0;a<3;a++) {
                for (int b=0;b<3;b++) {
                    g.drawImage(rightPic[a][b],tile[a][b].x,tile[a][b].y,tile[a][b].width,tile[a][b].height,this);
                }
            }

            remove(solve);
            complete=true;
            cheer.play();
        }

        if (turn=="mix") {
            g.drawString("Mixing Moves Made: "+moves,350,80);
        }
        else if (turn=="solve") {
            g.drawString("Mixing Moves Made: "+moves,350,80);
            g.drawString("Solving Steps Made: "+steps,350,100);
        }
        requestFocus();
    }

    public void keyPressed(KeyEvent event) {
        if (complete==false) {
            int k;
            k=event.getKeyCode();
            if (complete==false) {
                if (k==39) { 
                    int a=0;
                    while (a<3&&moved==false) {
                        int b=1;
                        while (b<3&&moved==false) {
                            if (space[a][b]==true) {
                                pic[a][b]=pic[a][b-1];
                                pic[a][b-1]=blank;
                                space[a][b]=false;
                                space[a][b-1]=true;
                                moved=true;
                            }
                            b++;
                        }
                        a++;	
                    }
                } else if (k==37){ 
                    int a=0;
                    while (a<3&&moved==false) {
                        int b=0;
                        while (b<2&&moved==false) {
                            if (space[a][b]==true) {
                                pic[a][b]=pic[a][b+1];
                                pic[a][b+1]=blank;
                                space[a][b]=false;
                                space[a][b+1]=true;
                                moved=true;
                            }
                            b++;
                        }
                        a++;
                    }
                }  else if (k==38){ 
                    int a=0;
                    while (a<2&&moved==false) {
                        int b=0;
                        while (b<3&&moved==false) {
                            if (space[a][b]==true) {
                                pic[a][b]=pic[a+1][b];
                                pic[a+1][b]=blank;
                                space[a][b]=false;
                                space[a+1][b]=true;
                                moved=true;
                            }
                            b++;
                        }
                        a++;
                    }
                }  else if (k==40){
                    int a=1;
                    while (a<3&&moved==false) {
                        int b=0;
                        while (b<3&&moved==false) {
                            if (space[a][b]==true) {
                                pic[a][b]=pic[a-1][b];
                                pic[a-1][b]=blank;
                                space[a][b]=false;
                                space[a-1][b]=true;
                                moved=true;
                            }
                            b++;
                        }
                        a++;
                    }
                } 
                swap.play();
                moved=false;
            }
            if (turn=="mix") {
                moves++;
            }
            else if (turn=="solve") {
                steps++;
            }
            if (turn=="solve") {
                correct=0;
                for (int a=0;a<3;a++) {
                    for (int b=0;b<3;b++) {
                        if (pic[a][b]==rightPic[a][b])
                            correct++;
                    }
                }
            }
            repaint();
        }
    }

    public void keyReleased(KeyEvent event) {
    }

    public void keyTyped(KeyEvent event) {
    }

    public void actionPerformed(ActionEvent e) {
        buttonPressed=e.getActionCommand();
        if (buttonPressed=="Remove a tile") {
            shuffle.play();

            correct=0; 

            space=new Boolean[3][3];
            for (int a=0;a<3;a++) {
                for (int b=0;b<3;b++) {
                    space[a][b]=false;
                }
            }
            randX=(int)(Math.random()*2);
            randY=(int)(Math.random()*2);
            pic[randX][randY]=blank;
            space[randX][randY]=true;

            complete=false;

            remove(takeAway);
            add(solve);

            turn="mix"; 
        }
        else if (buttonPressed=="Start to solve"){
            turn="solve"; 
        }
        repaint();
    }
}