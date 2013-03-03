import java.util.ArrayList;
import java.util.Random;


public class DifferentalEvolution {

	private static ArrayList<Vector> population;
	private static ArrayList<Vector> solutions;
	private static Random random = new Random();
	
	public static void initPopulation()
	{
		population = new ArrayList<Vector>(ControlVariables.POPULATION_SIZE);
		
		while(population.size() < ControlVariables.POPULATION_SIZE)
		{
			// TODO add bounds
			population.add(new Vector(random, Double.valueOf(0), null));
		}
	}
	
	public static void main (String[] args)
	{
		
	}
}
