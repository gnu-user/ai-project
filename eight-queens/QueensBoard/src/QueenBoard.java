
public class QueenBoard extends GameBoard {
	
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
