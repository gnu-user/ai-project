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

public class QueenTester
{
	public static boolean android = false;
	
	public static void main (String[] args) throws InterruptedException
	{
		int[] positions = new int[]{ 0, 3, 2, 4, 6, 7, 1, 5};
		QueenGame myGame = null;
		try
		{
			myGame = new QueenGame (new QueenBoard(positions, 8));
			myGame.playGame("~/test.png");
		}
		catch (Exception e)
		{
			System.out.println("Bad set of Queens");
		}
	}
}
