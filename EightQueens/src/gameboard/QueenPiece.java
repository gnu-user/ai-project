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

public class QueenPiece extends Piece
{
	public QueenPiece(int column, int row)
	{
		this.column = column;
		this.row = row;
	}
	
	@Override
	protected int distX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int distY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean validDirection(int direct) {
		// TODO Auto-generated method stub
		return false;
	}
}
