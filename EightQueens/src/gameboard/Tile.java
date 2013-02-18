
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