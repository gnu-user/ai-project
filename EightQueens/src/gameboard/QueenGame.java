package gameboard;

import javax.swing.JFrame;


public class QueenGame extends Game
{
	private JFrame frame;
	private GameBoardComponent myDrawer; 
	protected GameBoard myGame;	
	
	
	public QueenGame(QueenBoard chessBoard)
	{
		this.myGame = chessBoard;
		
		frame = new JFrame ();
		frame.setSize(500,500);
		frame.setTitle("8 Queens Problem");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void drawGameBoard() 
	{
		frame.setVisible(false);
		frame.add(myDrawer);
		frame.setVisible(true);
	}

	@Override
	public void playGame()
	{
		myDrawer = new GameBoardComponent(myGame);
		drawGameBoard();
	}
}
