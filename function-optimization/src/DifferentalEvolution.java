import java.util.ArrayList;
import java.util.Random;

import com.google.common.collect.Range;


public class DifferentalEvolution {

	private static FitnessFunction fitnessFunction;
	private static ArrayList<Vector> population;
	private static ArrayList<Vector> solutions;
	private static Random random = new Random();
	private static Double lowestFit = Double.MAX_VALUE;
	
	public static void initPopulation(Range<Double> bounds)
	{
		population = new ArrayList<Vector>(ControlVariables.POPULATION_SIZE);
		Vector newVector;
		while(population.size() < ControlVariables.POPULATION_SIZE)
		{
			newVector = new Vector(random, bounds);
			newVector.setFitness(fitnessFunction.evaluate(newVector));
			population.add(newVector);
			
			if(population.get(population.size()-1).getFitness() < lowestFit)
			{
				lowestFit = population.get(population.size()-1).getFitness();
				System.out.println("New Lowest = " + lowestFit);
			}
		}
	}
	
	public static int getRandomIndex()
	{
		return random.nextInt(ControlVariables.POPULATION_SIZE-1);
	}
	
	public static void main (String[] args)
	{
		//fitnessFunction = new DeJong();
		fitnessFunction = new RosenbrocksValley();
		
		initPopulation(fitnessFunction.getBounds());
		
		//Vector lowestFit = new Vector(fitnessFunction.getBounds());
		
		
		int a;
		int b;
		int c;
		boolean validVector = false;
		Vector noisyVector = null;
		
		while(fitnessFunction.getNFC() < ControlVariables.MAX_FUNCTION_CALLS)
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
					//System.out.println ("Using a = " + a + " b = " + b + " c = " + c);
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
				
				//System.out.println();
				validVector = false;
				
				Vector trialVector = VectorOperations.crossover(population.get(i), noisyVector, random);
				
				trialVector.setFitness(fitnessFunction.evaluate(trialVector));
				
				population.set(i, VectorOperations.selection(population.get(i), trialVector));
				
				// TODO get best so far
				/*if(population.get(i).getFitness() < lowestFit.getFitness())
				{
					lowestFit = population.get(i);
					System.out.println("New Lowest = " + lowestFit.getFitness());
				}*/
				
				if(population.get(i).getFitness() < lowestFit)
				{
					lowestFit = population.get(i).getFitness();
					System.out.println("New Lowest = " + lowestFit);
				}

			}			
		}
	}
}
