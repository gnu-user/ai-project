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

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;			//Used to create a 2D graphic
import java.awt.Graphics2D;			//Used to Draw a Rectangle and a name
import java.awt.Image;


public class GameBoardComponent extends JComponent
{
	public static final Color PASS = Color.BLACK;
	public static final Color MOVE = Color.GREEN;
	public static final Color NOPASS = Color.WHITE;
	public static final Color PLAYER1 = Color.RED;
	public GameBoard myGame;
	
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
						g2.setColor(PLAYER1);
						
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