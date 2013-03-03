import java.util.ArrayList;
import java.util.Random;

import com.google.common.collect.Range;


public class DifferentalEvolution {

	private static FitnessFunction fitnessFunction;
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
	
	public static int getRandomIndex()
	{
		return ControlVariables.POPULATION_SIZE * random.nextInt();
	}
	
	public static void main (String[] args)
	{
		initPopulation(null);
		
		fitnessFunction = new DeJong();
		int a;
		int b;
		int c;
		boolean validVector = false;
		Vector noisyVector = null;
		
		while(1 < ControlVariables.MAX_FUNCTION_CALLS)
		{
			for (int i = 0; i < ControlVariables.POPULATION_SIZE; i++)
			{
				// Select 3 Mutually Exclusive Parents i != a != b != c
				
				while(!validVector)
				{
					do
					{
						a = getRandomIndex(); 
					} while(a == i);
					
					do
					{
						b = getRandomIndex();
					} while(b == i || b == a);
					
					do
					{
						 c = getRandomIndex();
					} while(c == i || c == a || c == b);
					
					// Catch invalid vectors
					try
					{
						validVector = true;
						noisyVector = VectorOperations.mutation(population.get(c),
								population.get(b), population.get(a));
					}
					catch (IllegalArgumentException e)
					{
						validVector = false;
					}
				}
				
				Vector trialVector = VectorOperations.crossover(population.get(i), noisyVector, random);
				
				trialVector.setFitness(fitnessFunction.evaluate(trialVector));
				
				population.set(i, VectorOperations.selection(population.get(i), trialVector));
				
				// TODO get best so far
			}			
		}
	}
}
