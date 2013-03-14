/**
 * Artificial Intelligence Project -- Eight Queens Puzzle
 *
 * Copyright (C) 2013, Jonathan Gillett, Joseph Heron, and Daniel Smullen
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
package algorithm;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Performs a pivot operation on a chromosome and returns the
 * rotation of the chromosomes by 90 degrees, which results in a new
 * solution to the eight queens problem.
 */
public abstract class Pivot
{

	/**
	 * Rotate the coordinates of the eight queens stored in the chromosome 
	 * by 90 degrees and return a new chromosome of the transposition.
	 * 
	 * @param The chromosome to transpose
	 * @return The transposed chromosome
	 */
	public static Chromosome rotate(Chromosome chromosome)
	{
		/* Create a square binary matrix to represent the chessboard and queens */
		Integer length = chromosome.size();
		ArrayList<Integer> coordinates = new ArrayList<Integer>(chromosome.get());
		boolean[][] chessboard = new boolean[length][length];
		boolean temp = false;
		
		/* Set each of the queen's positions as true on the chessboard */
		for (int i = 0; i < coordinates.size(); ++i)
		{
			chessboard[i][coordinates.get(i)] = true;
		}
		
		/* Rotate the matrix by 90 degrees */
		for (int i = 0; i < length / 2; ++i)
		{
			for (int j = 0; j < length / 2; ++j)
			{
				temp = chessboard[i][j];
			    chessboard[i][j] = chessboard[j][length-1-i];
	    	    chessboard[j][length-1-i] = chessboard[length-1-i][length-1-j];
			    chessboard[length-1-i][length-1-j] = chessboard[length-1-j][i];
			    chessboard[length-1-j][i] = temp;
			}
		}
		
		/* Get the new coordinates from the rotated matrix and return chromosome */
		coordinates.clear();
		for (int i = 0; i < length; ++i)
		{
			for (int j = 0; j < length; ++j)
			{
				if (chessboard[i][j])
				{
					coordinates.add(j);
				}
			}
		}
		
		/* Return the new chromosome */
		return new Chromosome(coordinates);
	}
}