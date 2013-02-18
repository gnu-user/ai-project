package gameboard;

public abstract class Piece
{
	protected int column;
	protected int row;
	
	public Piece()
	{
		column = 0;
		row = 0;
	}
	
	/**
	 * A Piece that can be put on a GameBoard
	 * @param column an integer, the column the Piece is in
	 * @param row an integer, the row that the Piece is in
	 * @param owner an integer the owner of the Piece
	 */
	public Piece (int column, int row)
	{
		this.column = column;
		this.row = row;
	}
	
	/**
	 * Set the location of the piece
	 * @param col : integer, the new column 
	 * @param row : integer, the new row
	 */
	protected void setLoc (int newCol, int newRow)
	{
		this.column = newCol;
		this.row = newRow;
	}
	
	/**
	 * Returns the column of the piece
	 * @return : integer
	 */
	protected int getColumn()
	{
		return column;
	}
	
	/**
	 * Returns the row of the piece
	 * @return : integer
	 */
	protected int getRow()
	{
		return row;
	}
		
	/** 
	 * The distance the piece can travel from its original position
	 * @return : the number of columns the piece can move
	 */
	protected abstract int distX ();

	/** 
	 * The distance the piece can travel from its original position
	 * @return : the number of rows the piece can move
	 */
	protected abstract int distY ();
		
	protected void equal (Piece p)
	{
		this.column = p.column;
		this.row = p.row;
	}
	
	/**
	 * The direction that the piece can move in
	 * @param direct the direction to be checked if it is valid
	 * @return : and integer that tells which direction
	 */
	protected abstract boolean validDirection(int direct) ;
	
	/**
	 * Checks whether the given new position is valid for the piece
	 * @param column : integer the column the Piece is moving to
	 * @param row : integer the row the Piece is moving to
	 * @return a boolean value whether the move is valid or not
	 */
	public boolean validMove(int column, int row)
	{
		return false;
	}	
}