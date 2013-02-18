import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;			//Used to create a 2D graphic
import java.awt.Graphics2D;			//Used to Draw a Rectangle and a name
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class GameBoardComponent extends JComponent  {

	public static final Color PASS = Color.BLACK;
	public static final Color MOVE = Color.GREEN;
	public static final Color NOPASS = Color.WHITE;
	public static final Color PLAYER1 = Color.RED;
	public static final Color PLAYER2 = Color.BLUE;
	public GameBoard myGame;
	
	public static int difference = 6;
	
	public GameBoardComponent (GameBoard myGame)
	{
		this.myGame = myGame;
		
	}
	
	/**
	 * Draws lines that make up the grid
	 * Draws the squares of colour to colour the spots on the grid.
	 * @param g2 : Graphics2D
	 */
	public void setGrid (Graphics2D g2)
	{	
		//int player =-1;
		int col = -1;
		int row = -1;
		int [][] myMove = new int[0][2];
		Image image = new ImageIcon("img/queen.png").getImage();
		
		//Draw order
		//	Squares
		// 	Valid moves
		//	Circles 
		myGame.setSize(getWidth(), getHeight());
		/*if (myGame.getPiece()>=0)
		{
			player = myGame.getOwner(myGame.getPiece());
			col = myGame.getPieceX(myGame.getPiece()); 
			row = myGame.getPieceY(myGame.getPiece());
			myMove = ((CheckerBoard) myGame).validMoves(player, col, row);
		}*/
		
		if (myGame.getGridOn())	
		{
			for (int i = 0 ; i < myGame.getColumn(); i++)
			{
				for (int j = 0; j < myGame.getRow(); j++)
				{
					if (myGame.isTileNull(i, j))
					{
						g2.setColor(NOPASS);
					}
					else 
					{						
						g2.setColor(PASS);
						
						if (i == col && j == row )
						{
							g2.setColor(MOVE);
						}
						else 
						{
							for (int k = 0; k < myMove.length; k++)
							{
								if (myMove[k][0] == i && myMove[k][1] == j)
								{
									g2.setColor(MOVE);
								}
							}
						}
					}
					
					g2.fillRect(myGame.getX(i+1), myGame.getY(j+1), myGame.getWidth(), myGame.getHeight());
					
					if (!myGame.isTileNull(i, j) && myGame.getTileOwner(i,j))
					{
						//if (i == 0 && j == 4|| j == 5 && i == 1)
							//System.out.println(myGame.getTiles(i,j).getOccupy() + "HELLO?" + i + " " + j);
						//if (myGame.getOwner(i, j) == 1)
						g2.setColor(PLAYER1);
						//else if (myGame.getOwner(i, j) == 2)
							//g2.setColor(PLAYER2);
						
						g2.fillOval(myGame.getX(i+1), myGame.getY(j+1), myGame.getWidth(), myGame.getHeight());
						
						g2.drawImage(image, myGame.getX(i+1), myGame.getY(j+1), 
								myGame.getWidth(), myGame.getHeight(), null);
					}
				}
			}
		}
	}
	
	public void paintComponent (Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		setGrid(g2);
	}
}
