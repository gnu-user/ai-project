

public class QueenTester {
	public static boolean android = false;
	
	public static void main (String[] args) throws InterruptedException
	{
		//CheckerBoard myChecker = new CheckerBoard();
		
		//CheckerGame myGame = new CheckerGamePC(new CheckerBoard());
		int[] positions = new int[]{ 0, 1, 2, 3, 4, 5, 6, 7};
		QueenGame myGame = null;
		try{
			myGame = new QueenGame (new QueenBoard(positions));
			myGame.playGame();
		}
		catch (Exception e)
		{
			System.out.println("Bad set of Queens");
		}
		
		
	}
}
