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

public class Tile
{
	private boolean occupy;			//Indicates whether a the tile is occupied

	/**
	 * A tile is a spot on the gameBoard that can be occupied by a piece
	 * @param occupy : boolean, whether the tile is occupied
	 */
	public Tile(boolean occupy)
	{
		this.occupy = occupy;
	}
	
	/**
	 * Whether the tile is occupied
	 * @return occupy : boolean
	 */
	public boolean getOccupy()
	{
		return occupy;
	}
	
	/**
	 * Set the tile to be occupied or not occupied
	 * @param occupy
	 */
	public void setOccupy(boolean occupy)
	{
		this.occupy = occupy;
	}
}