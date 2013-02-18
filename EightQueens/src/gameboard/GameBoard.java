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


/**
 * Creates a game board of given size which can be adjusted. Some of the 
 * important options while using GameBoard are the ability to keep the 
 * board centred.
 * 
 * @author Joseph Heron
 */
public abstract class GameBoard 
{	
	protected int column;		//The x size of the grid, the number of columns
	protected int row;			//The y size of the grid, the number of rows
	protected Piece piece[];	//The Pieces that occupy the spots on the grid
	protected Tile tiles[][];
	private int sizeX;			//The x size of the grid spots
	private int sizeY;			//The y size of the grid spots
	private int adjX;			//The adjustment on the y axis to keep the grid in the middle
	private int adjY; 			//The adjustment on the x axis to keep the grid in the middle
	protected boolean center;	//Make so that margins can be made.
	protected boolean gridOn;
	protected int pieceMove;
	
	
	/**
	 * default constructor
	 */
	public GameBoard ()
	{
		
	}
	
	/**
	 * Sets the owner of the grid spots
	 * where 0 marks a coordinate that can be occupied
	 */
	protected abstract void setBoard(Object positions);	
	
	/**
	 * Moves the Piece at the given position to the second position.
	 * Checks if the move is valid.
	 * @param column : integer, the column number for the original position
	 * @param row : integer, the row number for the original position
	 * @param newColumn : integer, the column number for the new position
	 * @param newRow : integer, the row number for the new position
	 * @return : boolean, whether the move can be made 
	 */
	public abstract boolean move (int player, int col, int row, int newCol, int newRow);
	
	/**
	 * Calculates the player that has lost
	 * @return the player that has lost
	 */
	public abstract int getLost ();
	
	/**
	 * The piece that is being currently moved
	 * @return : integer, the index for the piece that is being used
	 */
	public int getPiece ()
	{ 
		return this.pieceMove;
	}
	
	/**
	 * The piece's column 
	 * @param pieceNum : integer, the index number for the current piece
	 * @return : integer, the column that the piece is in
	 */
	public int getPieceX (int pieceNum)
	{
		return piece[pieceNum].getColumn();
	}
	
	/**
	 * The piece's row 
	 * @param pieceNum : integer, the index number for the current piece
	 * @return : integer, the row that the piece is in
	 */
	public int getPieceY (int pieceNum)
	{
		return piece[pieceNum].getRow();
	}
	
	/**
	 * The tile at the given column and row
	 * @param column : integer
	 * @param row : integer
	 * @return : Tile
	 */
	public Tile getTiles(int column, int row)
	{
		return tiles[column][row];	
	}
	
	/**
	 * Checks if the tile is null (not possible)
	 * @param col : integer, the column of the tile to be tested
	 * @param row : integer, the row of the tile to be tested
	 * @return : boolean, true if the tile is null, false if the tile is not null
	 */
	public boolean isTileNull (int col, int row)
	{
		if (tiles[col][row] == null)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the tile is occupied
	 * @param col : integer, the column of the tile
	 * @param row : integer, the row of the tile
	 * @return : boolean, true if the tile is occupied, otherwise false
	 */
	public boolean getTileOwner (int col, int row)
	{
		return tiles[col][row].getOccupy();
	}
	
	/**
	 * Get Column
	 * @return the number of columns : integer
	 */
	public int getColumn ()
	{
		return column;
	}
	
	/**
	 * Get row
	 * @return the number of rows : integer
	 */
	public int getRow ()
	{
		return row;
	}
	
	/**
	 * Set whether the grid is shown
	 * @return
	 */
	public boolean getGridOn ()
	{
		return gridOn;
	}
	
	/**
	 * Calculates the size of the height of the size of each spot on the grid
	 * @return : an integer, the height of the spots
	 */
	public int getHeight ()
	{
		return sizeY-1;
	}
	
	/**
	 * Calculates the size of the width of the size of each spot on the grid
	 * @return : an integer, the width of the spots
	 */
	public int getWidth ()
	{
		return sizeX-1;
	}
	
	/**
	 * Calculates the y coordinate of the given row
	 * @param row : integer, the row
	 * @return the y coordinate for the row
	 */
	public int getY (int row)
	{
		return (sizeY/2)+ (row-1)*sizeY+1 + adjY;
	}
	
	/**
	 * Calculates the x coordinate of the given column
	 * @param column : integer, the column
	 * @return the x coordinate for the column
	 */
	public int getX (int column)
	{
		return (sizeX/2)+(column-1)*sizeX+1 + adjX;
	}
	
	/**
	 * Calculates the column from the given x coordinate
	 * @param x : integer, x coordinate
	 * @return the column that the x coordinate is in
	 */
	public int getColumn (int x)
	{
		return (int) ((x - (sizeX/2) - adjX - 1) / ((double) sizeX))+1;
	}
	
	/**
	 * Calculates the row from the given y coordinate
	 * @param y : integer, y coordinate
	 * @return the row that the y coordinate is in
	 */
	public int getRow (int y)
	{
		return (int) ((y - (sizeY/2) - adjY - 1)/ ((double) sizeY))+1 ;
	}

	/**
	 * Adjusts sizeX and sizeY based on the new screen
	 * @param width : integer the width of the window
	 * @param height : integer the height of the window
	 */
	public void setSize(int width, int height)
	{
		sizeX = (width)/(column+1);
		sizeY = (height)/(row+1);
		if (center)						//Maybe instead of just a centre option also allow for it to have a margin on 1 side or something
		{
			adjX = (width%(column+1))/2; 	
			adjY = (height)%(row+1)/2;		
		}
		else
		{
			adjX = 0;
			adjY = 0;
		}
	}
}