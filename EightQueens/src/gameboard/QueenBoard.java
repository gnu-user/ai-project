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

public class QueenBoard extends GameBoard
{	
	private static final int NUMBER_OF_QUEENS = 8;
	
	public QueenBoard (int[] queenPosition) throws IllegalArgumentException
	{
		row = 8; 
		column = 8;
		piece = new QueenPiece[8];
		tiles = new Tile[column][row];
			
		gridOn = true;
		center = true;
		pieceMove = -1;
		
		setBoard(queenPosition);
	}
	
	@Override
	public void setBoard(Object position) throws IllegalArgumentException {
		
		int a = 0;
		for (int i = 0; i < column; i ++)
		{
			for (int j = 0; j < row; j ++)
			{
				if (j%2 == a)
				{					
					tiles[j][i] = new Tile(false);
				}
			}
			if (a == 0)
				a = 1;
			else
				a = 0;
		}
		
		int[] queenPosition = (int[])position;
		int size = queenPosition.length;
		
		if (size > NUMBER_OF_QUEENS)
		{
			throw new IllegalArgumentException("Array size must be less than 8");
		}
		for(int i = 0; i < size ; i++)
		{
			QueenPiece newPos = new QueenPiece(queenPosition[i], i);
			if(newPos.getRow() >= NUMBER_OF_QUEENS)
			{
				throw new IllegalArgumentException("Elements in array must be between 0 and 7");
			}
			
			piece[i] = newPos;
			tiles[queenPosition[i]][i] = new Tile(true);
		}
		
	}

	@Override
	public boolean move(int player, int col, int row, int newCol, int newRow) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getLost() {
		// TODO Auto-generated method stub
		return 0;
	}
}