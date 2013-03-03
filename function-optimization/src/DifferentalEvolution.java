import java.util.ArrayList;
import java.util.Random;

import com.google.common.collect.Range;


public class DifferentalEvolution {

	private static ArrayList<Vector> population;
	private static ArrayList<Vector> solutions;
	private static Random random = new Random();
	
	public static void initPopulation(Range<Double> bounds)
	{
		population = new ArrayList<Vector>(ControlVariables.POPULATION_SIZE);
		Vector newVector;
		while(population.size() < ControlVariables.POPULATION_SIZE)
		{
			newVector = new Vector(random, bounds);
			//newVector.setFitness(fitness.eval(newVector.get()));
			population.add(newVector);
		}
	}
	
	public static void main (String[] args)
	{
		initPopulation(null);
		
		while(1 < ControlVariables.MAX_FUNCTION_CALLS)
		{
			for (int i = 0; i < ControlVariables.POPULATION_SIZE; i++)
			{
				// Select 3 parents
					// Mutually Exclusive Parents i != a != b != c
				int a = 1; 
				int b = 2; 
				int c = 3;
				
				// TODO catch invalid vectors
				Vector noisyVector = VectorOperations.mutation(population.get(c), population.get(b), population.get(a));
				
				Vector trialVector = VectorOperations.crossover(population.get(i), noisyVector, random);
				
				// TODO calc fitness of values
				population.set(i, VectorOperations.selection(population.get(i), trialVector));
				
				// TODO get best so far
			}			
		}
	}
}
