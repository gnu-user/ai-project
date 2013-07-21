/**
 * Genetic Algorithm Research -- N Queens Puzzle
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

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class QueenGame extends Game
{
	private JFrame frame;
	private GameBoardComponent myDrawer; 
	protected GameBoard myGame;	
	
	
	public QueenGame(QueenBoard chessBoard)
	{
		this.myGame = chessBoard;
		
		frame = new JFrame();
		frame.setSize(600,625);
		frame.setTitle("N Queens Problem");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void drawGameBoard() 
	{
		frame.setVisible(false);
		frame.add(myDrawer);
		frame.setVisible(true);
	}

	@Override
	public void playGame(String filepath)
	{
		myDrawer = new GameBoardComponent(myGame);
		drawGameBoard();
		saveImage(filepath);
	}
	
	private void saveImage(String filepath) {
		Container content = frame.getContentPane();
		BufferedImage img = new BufferedImage(content.getWidth(), content.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = img.createGraphics();

		content.printAll(g2d);

		g2d.dispose();
		frame.dispose();
		
		try {
			ImageIO.write(img, "png", new File(filepath));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
    }
}
