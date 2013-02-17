/**
 * Creates a game board of given size which can be adjusted.
 * Some of the important options while using GameBoard are 
 * the ability to keep the board centred.
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
	 * Creates a grid that will fit on the screen and can be used to can be
	 * resized to change the number of rows and columns
	 * @param column : integer
	 * @param row : integer
	 * @param center : boolean keeps the board in the centre 
	 * @param gridOn : boolean turns the grid on and off
	 */
	/*public GameBoard2 (int column, int row, boolean center, boolean gridOn)
	{
		
		this.column = column;
		this.row = row;
		this.owner= new Piece [column][row];
		this.center = center;
		this.gridOn = gridOn;
		//setOwner();
	}*/
	
	/**
	 * default constructor
	 */
	public GameBoard ()
	{
		
	}
	
	/**
	 * Find the index of the piece at the given column and row
	 * @param column : integer, the column of the piece 
	 * @param row : integer, the row of the piece
	 * @return : integer, the index for the piece at the given column and row or -1 if no piece is found
	 */
	/*protected int findPiece(int column, int row)
	{
		for (int i = 0; i < piece.length; i++)
		{
			if (piece[i].getColumn() == column && piece[i].getRow() == row)
			{
				if (piece[i].getOwner() > 0)
				{
					return i;
				}
			}
		}
		return -1;
	}*/
	
	/*protected boolean removePiece(int pieceNum)
	{
		if (pieceNum < 0)
		{
			return false;
		}
		Piece p [] = new Piece[piece.length-1];
		for (int i = 0; i < piece.length-1; i ++)
		{
			if (i >= pieceNum)
			{
				p[i].equal(piece[i+1]);
			}
			else 
			{
				p[i].equal(piece[i]);
			}
		}
		piece = p;
		return true;
	}*/
	
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
	 * Calculates the number of Pieces owned by the given player
	 * @param playerNumber an integer, the player that owns the Pieces
	 * @return an integer, the number of Pieces the player owns
	 */
	/*protected int getPieceCount (int playerNumber)
	{
		int count = 0;
		for (int i = 0; i < piece.length; i++)
		{
			if (piece[i].getOwner() == playerNumber)
			{
				count++;
			}
		}
		return count;
	}*/
	
	/**
	 * Find the index of the piece that is being moved
	 * @param col : integer, the column of the piece 
	 * @param row : integer, the row of the piece
	 */
	/*public void setPieceMove(int col, int row)
	{
		this.pieceMove = findPiece(col,row);
	}*/
	
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
	 * Checks if the given column and row are on the GameBoard
	 * @param col : integer
	 * @param row : integer
	 * @return : boolean, if the column and row are > 0 and less then the size
	 * of the GameBoard then it will return true
	 */
	public boolean onBoard(int col, int row)
	{
		if (col >= 0 && col < this.column && row >= 0 && row < this.row) 
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Changes the number of rows in the grid
	 * @param newRow : integer
	 */
	/*protected void setRows (int newRow)
	{
		row = newRow;
		resetArray();
	}*/
	
	/**
	 * Changes the number of columns in the grid
	 * @param newColumn : integer
	 */
	/*protected void setColums (int newCol)
	{
		column = newCol;
		resetArray();
	}*/
	
	/**
	 * Get row
	 * @return the number of rows : integer
	 */
	public int getRow ()
	{
		return row;
	}
	
	/**
	 * get the owner of the coordinate
	 * @param column : integer
	 * @param row : integer
	 * @return owner : integer 
	 */
	/*public int getOwner (int column, int row)
	{
		if (findPiece(column, row) >= 0)
		{
			return piece[findPiece(column,row)].getOwner();
		}
		return findPiece(column,row);
	}*/
	
	/**
	 * get the owner of the coordinate
	 * @param pieceNum : integer
	 * @return owner : integer 
	 */
	/*public int getOwner (int pieceNum)
	{
		return piece[pieceNum].getOwner();
	}*/
	
	/**
	 * Changes the size of the gridSpot array to fit the changed column or row
	 * Adds the colours from the previous grid to the new one
	 * Initialises the colour of the new spots to be white
	 */
	/*private void resetArray ()
	{
		Piece newOwner[][] = new Piece [column][row];
		
		//Transfer colours to new grid
		for (int i = 0; i < piece.length; i++ )
		{
			if (i >= column)
				break;
			for (int j = 0; j < piece[0].length; j++)
			{
				if (j >= row)
					break;
				newOwner[i][j]  = piece[i][j];
			}
		}
		
		//Initialise the new spots to white
		if ( ((column+1)-  piece.length) > 0 || ((row+1) - piece[0].length) > 0)
		{	
			if (((column+1)-  piece.length) > 0)
			{
				for (int i = ((column+1) - ((column+1) -  piece.length)); i < column; i++)
				{
					for (int j = 0; j < row; j++)
					{
						//System.out.println ("NO");
						newOwner[i][j].setOwner(0);
					}
				}
			}
			if (((row+1) - piece[0].length) > 0)
			{
				for (int i = 0; i < ((column+1) -  ((column+1) -  piece.length)); i++)
				{
					for (int j = ((row+1) - ((row+1) - piece[0].length)); j < row; j++)
					{
						newOwner[i][j].setOwner(0);
					}
				}
			}
		}
		//Change the grid size
		piece = newOwner;
	}*/
	
	/**
	 * Creates a grid on the window
	 * @param gridOn : boolean
	 */
	/*public void setGridVisible (boolean gridOn)
	{
		this.gridOn = gridOn;
	}*/
	
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