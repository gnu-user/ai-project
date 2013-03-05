/**
 * Artificial Intelligence Project -- Differential Evolution
 *
 * Copyright (C) 2013, Joseph Heron, Jonathan Gillett, and Daniel Smullen
 * All rights reserved.
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

import com.google.common.collect.Range;


public class DifferentalEvolution
{
	private static FitnessFunction fitnessFunction;
	private static ArrayList<Vector> population;
	private static ArrayList<Double> solutions;
	private static Random random = new Random();
	private static LinkedHashMap<Integer, Double> lowestFit;
	
	private static int prevAmount = 0;
	private static Double bestValue = Double.MAX_VALUE;
	
	/**
	 * Initializes the population with random parameters within the bounds. 
	 * 
	 * @param bounds The defined bounds of the fitnessFunction.
	 */
	public static void initPopulation(Range<Double> bounds)
	{
		population = new ArrayList<Vector>(ControlVariables.POPULATION_SIZE);
		Vector newVector;

		while(population.size() < ControlVariables.POPULATION_SIZE)
		{
			newVector = new Vector(random, bounds);
			newVector.setFitness(fitnessFunction.evaluate(newVector));
			population.add(newVector);

			if(population.get(population.size()-1).getFitness() < lowestFit.get(prevAmount))
			{
				prevAmount = fitnessFunction.getNFC();
				lowestFit.put(prevAmount, population.get(population.size()-1).getFitness());
			
			}
		}
	}
	
	/**
	 * Creates a random integer between 0 and the population size.
	 * @return The random integer.
	 */
	public static int getRandomIndex()
	{
		return random.nextInt(ControlVariables.POPULATION_SIZE-1);
	}
	
	public static void main (String[] args)
	{
		solutions = new ArrayList<Double>(ControlVariables.RUNS_PER_FUNCTION);

		fitnessFunction = new DeJong();
		//fitnessFunction = new RosenbrocksValley();
		//fitnessFunction = new HyperEllipsoid();
		//fitnessFunction = new Schwefel();
		//fitnessFunction = new Rastrigin();
	
		/* Execute the differential evolution algorithm a number of times per function */
		for (int runs = 0; runs < ControlVariables.RUNS_PER_FUNCTION; ++runs)
		{			
			int a;
			int b;
			int c;
			boolean validVector = false;
			Vector noisyVector = null;
			
			
			/* Reset the array of the best values found */
			prevAmount = 0;
			lowestFit = new LinkedHashMap<Integer, Double>();
			lowestFit.put(prevAmount, Double.MAX_VALUE);
		
			initPopulation(fitnessFunction.getBounds());
		
			/* Reset the fitness function NFC each time */
			fitnessFunction.resetNFC();
		
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
					
					validVector = false;
					
					Vector trialVector = VectorOperations.crossover(population.get(i), noisyVector, random);
					
					trialVector.setFitness(fitnessFunction.evaluate(trialVector));
					
					population.set(i, VectorOperations.selection(population.get(i), trialVector));
					
					/* Get the best fitness value found so far */
					if(population.get(i).getFitness() < lowestFit.get(prevAmount))
					{
						prevAmount = fitnessFunction.getNFC();
						bestValue =  population.get(i).getFitness();
						lowestFit.put(prevAmount, bestValue);
					}
				}
			}
			
			/* save the best value found for the entire DE algorithm run */
			solutions.add(bestValue);
		}
		
		for (Double value : solutions)
		{
			System.out.println(value);
		}
		
		/* Set the last value (NFC) to the best value found */
		lowestFit.put(ControlVariables.MAX_FUNCTION_CALLS, bestValue);
		
		/* Plot the best value found vs. NFC */
		PerformanceGraph.plot(lowestFit, fitnessFunction.getName());
	}
}
