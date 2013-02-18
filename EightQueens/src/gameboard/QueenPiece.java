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
