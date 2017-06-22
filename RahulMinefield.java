//Minefield Project
//Rahul Shandilya

import javax.swing.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

/**
 * Class RahulMinefieldproject - Minefield game
 * 10 x10 Mine Field Game where the player presses keys to navigate through the field moving left, right, up or down.
 * The object of the game is for the player to uncover the treasure (good) before uncovering the mine (evil).
 * 
 * @author (Rahul Shandilya) 
 * 
 */
public class RahulMinefield extends Applet  implements KeyListener,ActionListener 
{

    // Current state of the array, used to determine which image to draw
    char[][] currentState;

    // Current position of the mine and treasure
    int randomEvilA,randomEvilB,randomGoodA,randomGoodB;

    // 6 images used in the game for the titles
    Image field, fieldCurrent,
    fieldUncovered, fieldUncoveredCurrent,
    goodImage, evilImage;

    // Array to hold the x, y, width and height of tiles
    Rectangle[][] tile;

    // Tile width and height to use in Rectangle array
    int tileWidth, tileHeight;

    // Status of game: 0-in play, 1-won, 2-lost, 3-gave up
    int gameStatus;

    // Two buttons for Give Up and Reset
    Button giveUpButton, resetButton;

    //String to retrieve the button text
    String buttonPress;

    // Sounds used in the game
    AudioClip goodSound, evilSound, moveSound, digSound;

    //To check on which tile the player has already dugged(on which tiles enter key has been pressed)
    boolean[][] moved=new boolean[10][10];

    // Current position of the user
    int userA, userB;

    //To check if game is complete or not
    boolean complete;

    public void init() {
        // Retrieve the images
        field=getImage(getCodeBase(),"field.jpg");
        //fieldCurrent=getImage(getCodeBase(),"fieldCurrent.jpg");
        fieldCurrent=getImage(getCodeBase(),"game_player.png"); //Field current is a player
        fieldUncovered=getImage(getCodeBase(),"uncover.jpg");
        fieldUncoveredCurrent=getImage(getCodeBase(),"uncoverCurrent.jpg");
        evilImage=getImage(getCodeBase(),"evil.jpg");
        goodImage=getImage(getCodeBase(),"good.jpg");

        // Load the sound effects
        goodSound=getAudioClip(getCodeBase(),"applause.wav");
        evilSound=getAudioClip(getCodeBase(),"evil_new.wav");
        moveSound=getAudioClip(getCodeBase(),"move.wav");	
        digSound=getAudioClip(getCodeBase(),"dig.wav");	

        // Set up the buttons
        resetButton = new Button ("Reset");
        giveUpButton = new Button ("Give Up");

        // Set the location and size of buttons
        setLayout(null);
        resetButton.setBounds(550,10,100,40);
        giveUpButton.setBounds(550,50,100,40);

        // Add action listener for buttons
        resetButton.addActionListener(this);
        giveUpButton.addActionListener(this);

        // Add buttons to the applet
        add(resetButton);
        add(giveUpButton);

        // Fill the rectangle array with positions
        tileWidth=50;
        tileHeight=50;
        tile=new Rectangle[10][10];
        for (int a=0;a<10;a++) {
            for (int b=0;b<10;b++) {
                tile[a][b]=new Rectangle();
                tile[a][b].width=tileWidth;
                tile[a][b].height=tileHeight;
                tile[a][b].x=b*50;
                tile[a][b].y=a*50;
            }
        }

        // Set up the array of states to be 10 by 10
        currentState=new char[10][10];

        //calling setup field
        setupField();

        //adding key listener
        addKeyListener(this);

        //requesting focus
        requestFocus();
    }

    /*************************************************************************
     *  public void setupField()
     *    Sets up the field for play:
     *    - resets the game status to 0
     *    - populates the state character array to initial state
     *    - places the user in the bottom right by setting the state of
     *      the [9][9] tile to current uncovered and settng the user's
     *      current a and b position
     *    - randomly place the good and evil by generating the a and b
     *      positions for each. Ensure they are not the same.
     *    - play appropriate sound
     *    - call repaint
     ************************************************************************/
    public void setupField() {
        //resets the game status to 0 
        //game is in play
        gameStatus=0;
        //gae is not yet complete
        complete=false;
        //initialize moved array
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {
                moved[i][j]=false;
            }
        }

        // populates the state character array to initial state
        //initially the field will be drawn
        for (int a=0; a<10; a++){
            for (int b=0; b<10;b++){
                currentState[a][b]='a';
            }
        }

        // place the user in the bottom right
        currentState[9][9]='b';
        userA=9;
        userB=9;

        //randomly place good and evil
        int Min=0,Max=8;
        randomEvilA= Min + (int)(Math.random() * ((Max - Min) + 1));
        randomEvilB= Min + (int)(Math.random() * ((Max - Min) + 1));
        randomGoodA= Min + (int)(Math.random() * ((Max - Min) + 1));
        randomGoodB= Min + (int)(Math.random() * ((Max - Min) + 1));

        // make sure they will not be the same number
        while(randomEvilA==randomGoodA && randomEvilB==randomGoodB)
        {

            randomEvilA= Min + (int)(Math.random() * ((Max - Min) + 1));
            randomEvilB= Min + (int)(Math.random() * ((Max - Min) + 1));
            randomGoodA= Min + (int)(Math.random() * ((Max - Min) + 1));
            randomGoodB= Min + (int)(Math.random() * ((Max - Min) + 1));
        }

        // call repaint
        repaint();
    }

    /*************************************************************************
     *  public void paint(Graphics g)
     *    Paints the field using states that are stored in the array
     *    - Using nested for loops and a switch statement, check each value 
     *      of the character array and depending on its state
     *      (field,fieldCurrent, uncovered, uncoveredCurrent) draw the
     *      right image
     *    - After field tiles are drawn, check the game status to determine
     *      if game has been lost or won, and if so, draw the good or evil 
     *      tile at the user's current location
     *    - Request focus
     ************************************************************************/
    public void paint(Graphics g){
        // Draw the field according to the state of the char array
        for (int a=0; a<10; a++){
            for (int b=0; b<10;b++){
                switch (currentState[a][b]){
                    case 'a':
                    g.drawImage(field,tile[a][b].x,tile[a][b].y,tile[a][b].width,tile[a][b].height,this);
                    break;

                    case 'b':
                    g.drawImage(fieldCurrent,tile[a][b].x,tile[a][b].y,tile[a][b].width,tile[a][b].height,this);
                    break;

                    case'c':
                    g.drawImage(fieldUncovered,tile[a][b].x,tile[a][b].y,tile[a][b].width,tile[a][b].height,this);
                    break;

                    case'd':
                    g.drawImage(fieldUncoveredCurrent,tile[a][b].x,tile[a][b].y,tile[a][b].width,tile[a][b].height,this);
                    break;

                    case'e':
                    g.drawImage(goodImage,tile[a][b].x,tile[a][b].y,tile[a][b].width,tile[a][b].height,this);
                    break;

                    case'f':
                    g.drawImage(evilImage,tile[a][b].x,tile[a][b].y,tile[a][b].width,tile[a][b].height,this);
                    break;

                }
            }
        }

        // Check the game status and draw good and evil if win/lose
        //game status : 
        //0 - in play
        //1 - won
        //2 - lost
        //3 - gave up
        if(gameStatus==1) //won game
        {

            complete=true;
            g.drawImage(goodImage,tile[userA][userB].x,tile[userA][userA].y,tile[userA][userB].width,tile[userA][userB].height,this);

        }
        else if(gameStatus==2) //lost game
        {

            complete=true;
            g.drawImage(evilImage,tile[userA][userB].x,tile[userA][userB].y,tile[userA][userB].width,tile[userA][userB].height,this);

        }

        // Request focus
        requestFocus();
    }

    /*************************************************************************
     *  public void keyPressed(KeyEvent event)
     *    Called in response to user key press. Takes appropriate action.
     *    - Retrieve the key code of the key that was pressed
     *    - If right, left, up or down was pressed:
     *      - First check that it is possible to move (user is not at
     *        end of grid) by comparing user's current position to end
     *        position of the grid. For example, if the user wants to
     *        go right and the current position of user is at the 10th
     *        tile on the right, this move is not possible. Do nothing.
     *      - If it is possible to move in the user indicated direction,
     *        check if the current tile is covered or uncovered. If
     *        covered, set the state of next tile (tile to be moved to)
     *        to coveredCurrent state. If tile is uncovered, set next
     *        tile to uncoveredCurrent state. This gives the appearance
     *        of moving while retaining the current tile state.
     *        - Reset the current tile to not be current anymore by checking
     *          the state of the tile.  If coveredCurrent, make covered (notCurrent). 
     *          If uncoveredCurrent, make uncovered.
     *        - Update the user's current position by adding or
     *          subtracting one to represent the direction moved.
     *    - If dig was pressed:
     *      - Check if tile is not already uncovered, which means it
     *        has already been dug, and no need to dig again.
     *      - If it is still uncovered, call checkTile()
     *    - Call repaint that will show the results of the state change
     ************************************************************************/
    public void keyPressed(KeyEvent e) 
    {

        if(complete!=true) //Only if game is not complete
        {
            //	called in response to user key press.
            int k;
            k=e.getKeyCode(); //Retrieve the keycode of the key pressed

            if (k==39) //right arrow key
            {

                //Check if it is possible to move
                if(userB!=9)
                {
                    moveSound.play();
                    //if current tile is covered 
                     if(currentState[userA][userB]=='b')

                        currentState[userA][userB]='a';
                    //If covered, set the state of next tile (tile to be moved to) to coveredCurrent state.  
                    else if 
                    (currentState[userA][userB]=='d')
                        currentState[userA][userB]='c';
                    if(currentState[userA][userB+1]=='a')

                        currentState[userA][userB+1]='b';
                    //If covered, set the state of next tile (tile to be moved to) to coveredCurrent state.  
                    else if 
                    (currentState[userA][userB+1]=='c')
                        currentState[userA][userB+1]='d';
                        userB=userB+1;
                    

                }
            }   

            if (k==37) //left arrow key
            {
                //Check if it is possible to move
                if(userB!=0)
                {
                    moveSound.play();
                    //if current tile is covered 
                    if(currentState[userA][userB]=='b')

                        currentState[userA][userB]='a';
                    //If covered, set the state of next tile (tile to be moved to) to coveredCurrent state.  
                    else if 
                    (currentState[userA][userB]=='d')
                        currentState[userA][userB]='c';
                    if(currentState[userA][userB-1]=='a')

                        currentState[userA][userB-1]='b';
                    //If covered, set the state of next tile (tile to be moved to) to coveredCurrent state.  
                    else if 
                    (currentState[userA][userB-1]=='c')
                        currentState[userA][userB-1]='d';
                        
                         userB=userB-1;

                }
            }     

            if (k==38) //up arrow key
            {
                if(userA!=0)
                {
                    moveSound.play();
                    //if current tile is covered 
                    if(currentState[userA][userB]=='b')

                        currentState[userA][userB]='a';
                    //If covered, set the state of next tile (tile to be moved to) to coveredCurrent state.  
                    else if 
                    (currentState[userA][userB]=='d')
                        currentState[userA][userB]='c';
                    if(currentState[userA-1][userB]=='a')

                        currentState[userA-1][userB]='b';
                    //If covered, set the state of next tile (tile to be moved to) to coveredCurrent state.  
                    else if 
                    (currentState[userA-1][userB]=='c')
                        currentState[userA-1][userB]='d';

                    //Reset the current tile to not be current anymore by checking the state of the tile.
                    //Update the user's current position by adding or subtracting one to represent the direction moved.
                    userA=userA-1;
                }

                //if current tile is uncovered

            }
            if (k==40) //down arrow key
            {
                if(userA!=9)
                {
                    moveSound.play();
                    //if current tile is covered 
                     if(currentState[userA][userB]=='b')

                        currentState[userA][userB]='a';
                    //If covered, set the state of next tile (tile to be moved to) to coveredCurrent state.  
                    else if 
                    (currentState[userA][userB]=='d')
                        currentState[userA][userB]='c';
                    if(currentState[userA+1][userB]=='a')

                        currentState[userA+1][userB]='b';
                    //If covered, set the state of next tile (tile to be moved to) to coveredCurrent state.  
                    else if 
                    (currentState[userA+1][userB]=='c')
                        currentState[userA+1][userB]='d';
                        userA=userA+1;
                    }

                }
            

            //If dig was pressed
            //dig is enter key

            if(k==e.VK_ENTER)
            {

                //Check if tile is not already uncovered, which means it has already been dug, and no need to dig again.
                /* - If dig was pressed:
                 *      - Check if tile is not already uncovered, which means it
                 *        has already been dug, and no need to dig again.
                 *      - If it is still uncovered, call checkTile()
                 *      */

                if(currentState[userA][userB]=='b' || currentState[userA][userB]=='d')
                {
                    digSound.play();
                    currentState[userA][userB]='d';
                    moved[userA][userB]=true;
                } 
                if(currentState[userA][userB]=='d')
                {
                    checkTile();
                }
            }         

        
        }
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {
                if(moved[i][j]==true && currentState[i][j]!='b' && currentState[i][j]!='d') //Change the state of already dugged tiles
                    currentState[i][j]='c';
            }
        }
        repaint(); 
    }

    /*************************************************************************
     *  public void checkTile()
     *    Called in response to user digging. Determines evil or good
     *    is found, thus if user wins, loses or continues.
     *    - Check to see if user wins, loses or just uncoveres a tile
     *    - If win (user's position is same as good position), set
     *      game status to win and play appropriate sound
     *    - If lose (user's position is same as evil), set game status
     *      to lose and play appropriate sound
     *    - If neither, set tile state to uncovered current to
     *      indicate it has been dug and play appropriate sound.
     *    - Call repaint to show the results of the state change
     ************************************************************************/
    public void checkTile() 
    {
        //check if the user wins, loses, or continues
        //determines if the user has lost or still in the play

        if((userA==randomGoodA && userB==randomGoodB) || currentState[userA][userB]=='e') //Win, User's position is same as good position
        { 
            gameStatus=1;
            goodSound.play();
            complete=true;

        }
        else if((userA==randomEvilA && userB==randomEvilB) || currentState[userA][userB]=='f') //Lose , User's position is same as evil position
        {
            gameStatus=2;
            evilSound.play();
            complete=true;
        }
        else//set tile state to uncovered current to indicate it has been dug and play appropriate sound
        {
            currentState[userA][userB]='d';
            digSound.play();
        }

        repaint();

    }

    /*************************************************************************
     *  public void uncoverField()
     *    Called in response to user pressing the "Give Up" button.
     *    - Using nested for loops, reset the state of each tile
     *      to be uncovered.
     *    - Set game status to GiveUp (3).
     *    - Call repaint to show the results of the state change
     ************************************************************************/
    public void uncoverField() {
        // Called in response to user pressing the "Give Up" Button
        //reset the state of each tile

        for (int a=0; a<10; a++){
            for (int b=0; b<10;b++){
                currentState[a][b]='c'; 
            }
        }
        currentState[randomGoodA][randomGoodB]='e';
        currentState[randomEvilA][randomEvilB]='f';
        //give up game
        gameStatus=3;
        repaint();
    }

    /*************************************************************************
     *  public void actionPerformed(ActionEvent e)
     *    Called in response to a user pushing a button
     *    - Retrieve the text on the button (matching it exactly
     *      as it is case-senstive)
     *    - If "Reset", call the setupField() method.
     *    - If "Give Up", call the uncoverField() method.
     ************************************************************************/
    public void actionPerformed(ActionEvent e) {
        //called in response to a user pushing a button
        buttonPress=e.getActionCommand();
        if (buttonPress=="Reset"){
            setupField();
        }
        else if(buttonPress=="Give Up"){
            uncoverField();
        }
    }

    /*************************************************************************
     *  public void keyReleased(KeyEvent event)
     *    Called in response to a user releasing a key
     *    - No action required, but method must be defined in class
     *      for code to compile.
     ************************************************************************/
    public void keyReleased(KeyEvent event) {}

    /*************************************************************************
     *  public void keyTyped(KeyEvent event)
     *    Called in response to a user typing a key
     *    - No action required, but method must be defined in class
     *      for code to compile.
     ************************************************************************/
    public void keyTyped(KeyEvent event) {}

}

