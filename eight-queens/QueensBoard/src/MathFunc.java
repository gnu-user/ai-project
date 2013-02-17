
public class MathFunc {
	
	/**
	 * Calculates the distance between the first point and the second point
	 * @param x1 : integer, the x coordinate of the first point
	 * @param y1 : integer, the y coordinate of the first point
	 * @param x2 : integer, the x coordinate of the second point
	 * @param y2 : integer, the y coordinate of the second point
	 * @return
	 */
	public static int distF (int x1, int y1, int x2, int y2)
	{
		return (int) Math.sqrt((x2-x1)*(x2-x1) + (y2 - y1)*(y2 - y1));
	}
}
