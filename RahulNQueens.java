//Program for Interactive N-Queens Problem
//Submitted by : Rahul Shandilya
//Block : 1-3

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

//class that contains the main program logic
public class RahulNQueens extends JFrame implements Runnable
{
	public ChessSquare [][] squares;
	public boolean [] saferow;       // is the row empty?  true or false
     	public boolean [] safeleftdiag; // is the left (from upper right to lower left)
					// diagonal unthreatened?  true or false
	public boolean [] saferightdiag; // is the right (from upper left to lower right)
					// diagonal unthreatened?  true or false
        private ShapePanel drawPanel;   // panel for the board to be drawn 
	private JLabel info;		// informative label
	private JButton runDemo;	// button to allow interaction
	private Thread runThread;	// thread to allow "motion"
	private int delay;		// delay between moves
	private PauseClass pauser;	// class to allow execution to pause in between solutions
	private boolean paused;		// is execution paused?  true or false
	private int sol, calls;
        int n;

     public RahulNQueens(int delta,int num) //Constructor with 2 parameters delay and the dimensions of chessboard
     {
            super("Interactive N-Queens Problem"); //creates a JFrame which which is the superclass 
            n=num;                                  //dimensions of the board
	    delay = delta;
            drawPanel = new ShapePanel(450, 450);  //draw a new chessboard panel
            runDemo = new JButton("See Solutions");// Set up button to see solutions of n-queens problem
            ButtonHandler bhandler = new ButtonHandler();
            runDemo.addActionListener(bhandler); //Add action listener to button
            info = new JLabel("The N-Queens Problem", (int) CENTER_ALIGNMENT); 
            Container c = getContentPane();			// Set up layout
            c.add(drawPanel, BorderLayout.CENTER);
            c.add(info, BorderLayout.NORTH);
            c.add(runDemo, BorderLayout.SOUTH);
            
	    squares = new ChessSquare[n][n];	// initialize variables
            saferow = new boolean[n];
            safeleftdiag = new boolean[(2*n)-1];
            saferightdiag = new boolean[(2*n)-1];      
            int size=40; //size of each cell on the board
            int offset=40; //offset from where to start creating the board
			
            for (int row = 0; row < num; row++) //For each row
            {
                saferow[row] = true;  // Initialize all rows as safe
                for (int col = 0; col < num; col++) //For each column
                {
                    //create an empty rectangle with given dimensions
                    squares[row][col] = new ChessSquare(offset + col*size, offset + row*size,size, size); 
                }
            }
            for (int i = 0; i < (2*num)-1; i++)
            {   // initialize all diagonals to safe
                 safeleftdiag[i] = true;
                 saferightdiag[i] = true;
            }
            sol = 0; //Initially number of solutions is 0
            calls = 0; //Number of recursive function calls made initially are 0
            runThread = null;
            setSize(475, 525); //Set size of the window or frame to 475 by 525
            setVisible(true);  //Make the frame visible
     }

	// Is the current location safe?  We check the row and both diagonals.
	// The column does not have to be checked since our algorithm proceeds in
	// a column by column manner.
	public boolean safe(int row, int col)
	{
            //No 2 queens in same row
            //No 2 queens in same left diagonal
            //No 2 queens in same right diagonal 
            if(saferow[row] && safeleftdiag[row+col] && saferightdiag[row-col+(n-1)]) //This automaticallu checks for no 2 queens in same column
                return true;
            else
                return false;
	}

	// This recursive method does most of the work to solve the problem.  Note
	// that it is called for each column tried in the board, but, due to
	// backtracking, will overall be called many many times.  Each call is from
	// the point of view of the current column, col.
	public void trycol(int col)
	{
		calls++; // increment number of calls made
		for (int row = 0; row < n; row++)    // try all rows if necessary
		{
			// This test is what does the "pruning" of the execution tree --
			// if the location is not safe we do not bother to make a recursive
			// call from that position, saving overall many thousands of calls.
			// See notes from class for details.
			if (safe(row, col))
			{
                            // if the current position is free from a threat, put a queen
                            // there and mark the row and diags as unsafe
                            saferow[row] = false; //Mark that row as unsafe where this queen is placed
                            safeleftdiag[row+col] = false; //Mark that left diagonal as unsafe where this queen is placed
                            saferightdiag[row-col+(n-1)] = false; //Mark that right diagonal as unsafe where this queen is placed
                            (squares[row][col]).occupy(); //Place a queen at the position row,col
                            repaint(); //repaint the board
                            if (col == n-1)    // queen safely on every column, announce
                            {                  // solution and pause execution
                            sol++;         // increment number of solution
                            info.setText("Solution " + sol + " Found After " + calls + " Calls");
                            runDemo.setText("Click to Continue"); //runDemo is button to move to the next solution
                            runDemo.setEnabled(true);
                            pauser.pause(); //pause execution after placing a queen
                            }
                            else
                            {
                                // Still more columns left to fill, so delay a bit and then
                                // try the next column recursively
                                try
                                {
                                        Thread.sleep(delay); //Add a delay between moves
                                }
                                catch (InterruptedException e)
                                {
                                        System.out.println("Thread error B");
                                }

                                trycol(col+1);  // try to put a queen onto the next column
                            }

                            saferow[row] = true;               // remove the queen from
                            safeleftdiag[row+col] = true;      // the current row and
                            saferightdiag[row-col+(n-1)] = true;   // unset the threats. The
                            (squares[row][col]).remove();      // loop will then try the

                    }	
		}
		// Once all rows have been tried, the method finishes, and execution
		// backtracks to the previous call.
	}

	// This method executes implicitly when the Thread is started
	// its main activity is the call trycol(0), which starts the recursive
	// algorithm on its way.
    public void run()
    {
        paused = false; //The thread starts to execute
        pauser = new PauseClass(); //pause execution between solutions
        trycol(0); //try placing queen on column 0
        repaint();
        info.setText("Program Completed: " + sol + " Solutions, "+ calls + " Calls, "+ (n*calls) + " iterations ");
    }

   	// This class is used to enable the execution to pause between solutions.  
	private class PauseClass
	{
		public synchronized void pause() //to give a pause between 2 solutions
		{
                    paused = true;
                    try
                    {
                        wait(); //gives a pause between solutions(threads)
                    }
                    catch (InterruptedException e)
                    {
                        System.out.println("Pause Problem");
                    }
		}

		public synchronized void unpause() 
		{
                    paused = false;
                    notify();
		}
	}

	// Class to handle button
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == runDemo) //If we press button "Click to continue"
			{
				if (!paused) //if not paused i.e if the solution is running
				{
                                    runDemo.setEnabled(false); //disable the button to move onto next solution
                                    info.setText("Searching for Solutions"); //Print searching for solutions
                                    runThread = new Thread(RahulNQueens.this); //create a new thread
                                    runThread.start(); //start running thread
				}
                                else //if paused
				{
                                    runDemo.setEnabled(false); //disable the button to move onto next solution
                                    info.setText("Searching for Solutions"); //Print searching for solutions
                                    pauser.unpause(); //unpause the solution
				}
				repaint();
			}
		}
	}

	// Inner class to represent a square on the board.  This class extends
	// Rectangle2D.Double, which can be drawn graphically by the draw() method
	// of the Graphics2D class.  The main additions I have made in the subclass
	// are the occupied variable and the drawing of the Q if the space is
	// occupied.
	private class ChessSquare extends Rectangle2D.Double //Rectangle2D.Double creates a rectangle of given dimensions
	{
            private boolean occupied; //checks if a cell is already occupied
            
            //Creates a rectangular board with given dimensions
            public ChessSquare(double x1, double y1, double wid, double hei) 
            {
                super(x1, y1, wid, hei); //creates a rectangle of specified width aand height
                occupied = false; //initially all cells are unoccupied so occupied=false
            }
            public void draw(Graphics2D g2d) //draw method to draw the rectangle
            {
                g2d.draw(this);
                int x = (int) this.getX(); //get the x coordinate of the cell where queen has to be placed
                int y = (int) this.getY(); //get the y coordinate of the cell where queen has to be placed
                int sz = (int) this.getWidth(); //get the width of the cell

                if (occupied) //if queen has to be placed in that cel, then occupied for that cell is true
                {
                    //draw "Q" after setting the font
                    g2d.setFont(new Font("Serif", Font.BOLD, 34)); 
                    g2d.drawString("Q", x+10, y+sz-10);
                }
            }
            public void occupy() //Occupy a new cell by placing a queen
            {
                occupied = true;
            }

            public void remove() //remove a queen from an occupied place
            {
                occupied = false;
            }

            public boolean isOccupied() //check if a cell is already occupied
            {
                return occupied;
            }
    }

    // Class that allows the board to be rendered in a nice way.
private class ShapePanel extends JPanel
{
    private int prefwid, prefht;
    public ShapePanel (int pwid, int pht) //create a jpanel with given dimensions
    {
        //Set the dimensions width and height and background colour in the constructor
        //Initializing the variables
        prefwid = pwid;
        prefht = pht;
        this.setBackground(Color.YELLOW);
    }
    public Dimension getPreferredSize()
    {
        //Dimension class encapsulates the width and height of a component in a single object
        return new Dimension(prefwid, prefht); 
    }
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                (squares[i][j]).draw(g2d); //Draws the entire chessboard
            }
        }    
    }
    }

}

