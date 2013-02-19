/**
 * Artificial Intelligence Project -- Eight Queens Puzzle
 *
 * Copyright (C) 2013, Joseph Heron, Jonathan Gillett, and Daniel Smullen
 * All rights reserved.
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gameboard;

import javax.imageio.ImageIO;
import java.net.URL; 
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Graphics;			//Used to create a 2D graphic
import java.awt.Graphics2D;			//Used to Draw a Rectangle and a name
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;


public class GameBoardComponent extends JComponent
{
	public GameBoard myGame;
	private Image image;
	
	public GameBoardComponent (GameBoard myGame)
	{
		this.myGame = myGame;
		
		// TODO use this ONLY if creating a JAR file
		//URL imageurl = getClass().getResource("/image/queen.png");
		//this.image = Toolkit.getDefaultToolkit().getImage(imageurl);
		//new JLabel(new ImageIcon( image ));
	}
	
	/**
	 * Draws lines that make up the grid
	 * Draws the squares of colour to colour the spots on the grid.
	 * @param g2 : Graphics2D
	 * @throws IOException 
	 */
	public void setGrid (Graphics2D g2) throws IOException
	{	
		//int player =-1;
		int col = -1;
		int row = -1;
		int [][] myMove = new int[0][2];
		
		// TODO Use this if NOT using JAR for better looking icons
		image = ImageIO.read(new File("img/queen.png"));
	
		//Draw order
		//	Squares
		// 	Valid moves
		//	Circles 
		myGame.setSize(getWidth(), getHeight());
		
		if (myGame.getGridOn())	
		{
			for (int i = 0 ; i < myGame.getColumn(); i++)
			{
				for (int j = 0; j < myGame.getRow(); j++)
				{
					if (myGame.isTileNull(i, j))
					{
						g2.setColor(Color.WHITE);
					}
					else 
					{	
						/* Tiling the colour of the board correctly */
						if ( 	(i % 2 == 0 && j % 2 == 1)
							 || (i % 2 == 1 && j % 2 == 0)
						   )
						{
							g2.setColor(Color.WHITE);
						}
						else
						{
							g2.setColor(Color.GRAY);
						}
					}
					
					g2.fillRect(myGame.getX(i+1), myGame.getY(j+1), myGame.getWidth(), myGame.getHeight());
					
					if (!myGame.isTileNull(i, j) && myGame.getTileOwner(i,j))
					{
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
		try
		{
			setGrid(g2);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* Anti-aliasing so it looks better */
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	}
}