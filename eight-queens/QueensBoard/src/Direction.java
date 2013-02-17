
public class Direction {
	
	public final static int UP = 1;
	public final static int UP_LEFT = 2;
	public final static int LEFT = 3;
	public final static int DOWN_LEFT = 4;
	public final static int DOWN = 5;
	public final static int DOWN_RIGHT = 6;
	public final static int RIGHT = 7;
	public final static int UP_RIGHT = 8;
	
	/**
	 * Calculates the direction that the piece is moving in
	 * @param disX : integer, the x distance moved
	 * @param disY : integer, the y distance moved
	 * @return the direction piece is moved
	 */
	public static int direction (int disX,int disY)
	{
		if (disX == 0 && disY > 0)
		{
			return UP;
		}
		else if (disX > 0 && disY > 0)
		{
			return UP_RIGHT;
		}
		else if (disX > 0 && disY == 0)
		{
			return RIGHT;
		}
		else if (disX > 0 && disY < 0)
		{
			return DOWN_RIGHT;
		}
		else if (disX == 0 && disY < 0)
		{
			return DOWN;
		}
		else if (disX < 0 && disY < 0)
		{
			return DOWN_LEFT;
		}
		else if (disX < 0 && disY == 0)
		{
			return LEFT;
		}
		else if (disX < 0 && disY > 0)
		{
			return UP_LEFT;
		}
		
		return 0;
	}
}
