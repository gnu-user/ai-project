/**
 * Genetic Algorithm Research -- N Queens Puzzle
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

/**
 * Performs a pivot operation on a chromosome and returns the
 * rotation of the chromosomes by 90 degrees, which results in a new
 * solution to the eight queens problem.
 */
public abstract class Transformation
{
	/**
	 * Rotate the coordinates of the N queens stored in the chromosome 
	 * by 90 degrees and return a new chromosome of the transformation.
	 * 
	 * @param The chromosome to rotate by 90 degrees
	 * @return The rotated chromosome
	 */
	public static Chromosome rotate(Chromosome chromosome)
	{
		/* Create a square binary matrix to represent the chessboard and queens */
		Integer length = chromosome.size();
		ArrayList<Integer> coordinates = new ArrayList<Integer>(chromosome.get());
		boolean[][] chessboard = new boolean[length][length];
		
		/* Set each of the queen's positions as true on the chessboard */
		for (int i = 0; i < coordinates.size(); ++i)
		{
			chessboard[i][coordinates.get(i)] = true;
		}
		
		/* Rotate the matrix by 90 degrees and get the new coordinates */
	    for (int row = 0; row < length; ++row)
	    {
	        for (int col = 0; col < length; ++col)
	        {
	            if (chessboard[row][col])
	            {
	                coordinates.set(col, length-1-row);
	            }
	        }
	    }
		
		/* Return the new chromosome */
		return new Chromosome(coordinates, chromosome.size());
	}

	
	/**
     * Reflect the coordinates of the N queens stored in the chromosome 
     * vertically and return a new chromosome of the transformation.
     * 
     * @param The chromosome to be reflected vertically (left to right)
     * @return The reflected chromosome
     */
    public static Chromosome reflect(Chromosome chromosome)
    {
        /* Create a square binary matrix to represent the chessboard and queens */
        Integer length = chromosome.size();
        ArrayList<Integer> coordinates = new ArrayList<Integer>(chromosome.get());
        boolean[][] chessboard = new boolean[length][length];
        
        /* Set each of the queen's positions as true on the chessboard */
        for (int i = 0; i < coordinates.size(); ++i)
        {
            chessboard[i][coordinates.get(i)] = true;
        }
        
        /* Reflect the matrix vertically and get the new coordinates */
        for (int row = 0; row < length; ++row)
        {
            for (int col = 0; col < length; ++col)
            {
                if (chessboard[row][col])
                {
                    coordinates.set(row, length-1-col);
                }
            }
        }
        
        /* Return the new chromosome */
        return new Chromosome(coordinates, chromosome.size());
    }
}