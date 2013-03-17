/**
 * Artificial Intelligence Project -- Eight Queens Puzzle
 *
 * Copyright (C) 2013, Jonathan Gillett, Joseph Heron, and Daniel Smullen
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
package algorithm;

import java.util.HashMap;
import java.util.ArrayList;
import java.lang.Math;


/**
 * Fitness function, it calculates the fitness of a chromosome based on the
 * number of collisions of Queens that occurs. For each queen (gene) in the 
 * chromosome the number of vertical and diagonal collisions is counted,
 * repeated collisions between Queens are counted relative to each queen.
 */
public abstract class Fitness
{	
	/**
	 * Applies the fitness function and calculates the fitness of each chromosome in the
	 * population. The fitness function Calculates the fitness of each chromosome using 
	 * the following:
	 *
	 * fitness = 1 / (chromosome collisions)
	 * 
	 * Each of the fitness values is a float of the fitness of the chromosome, a chromosome
	 * without any collisions has a fitness value of 1 and is a solution.
	 * 
	 * @param chromosomes The chromosomes for the population to calculate the fitness of
	 * 
	 * @return A hash mapping each chromosome to it's fitness value
	 */
	public static HashMap<Chromosome, Double> calculate(ArrayList<Chromosome> chromosomes)
	{
		/* Mapping of each chromosome to the total number of collisions */
		HashMap<Chromosome, Integer> collisions = new HashMap<Chromosome, Integer>(chromosomes.size());
		HashMap<Chromosome, Double> fitness = new HashMap<Chromosome, Double>(chromosomes.size());
		Integer numCollisions = 0;
		
		
		/* For each chromosome calculate the number of collisions */
		for (Chromosome chromosome : chromosomes)
		{
			numCollisions = calcCollisions(chromosome);			
			collisions.put(chromosome, numCollisions);
		}
		
		/* Calculate the fitness value of each chromosome using the fitness function:
		 * 
		 * fitness = 1 / (chromosome collisions)
		 */
		for (Chromosome chromosome : chromosomes)
		{
			fitness.put(chromosome, 1.0 / (double) collisions.get(chromosome));
		}
		
		return fitness;
	}
	
	
	/**
	 * Takes a chromosome and returns the number of collisions that occurred.
	 * For each queen (gene) in the chromosome the number of vertical and diagonal
	 * collisions is counted, repeated collisions between Queens are counted relative
	 * to each queen. If no collisions occur, the base case of 1 is returned to prevent
	 * divide by zero error when calculating the fitness.
	 *
	 * @param chromosome The chromosome to calculate the number of collisions for
	 * 
	 * @return The number of collisions of queens that occurred for the chromosome
	 */
	private static int calcCollisions(Chromosome chromosome)
	{
		int numCollisions = 0;
		
		/* For each queen calculate the number of vertical and horizontal collisions
		 * that occur respectively for each queen.
		 * 
		 * TODO: Could use DP to solve this instead of duplicated collisions...
		 */
		for (int i = 0; i < chromosome.size(); ++i)
		{
			/* Wrap around the genes in the chromosome to check each one */
			for (int j = (i + 1) % chromosome.size(); j != i; ++j, j %= chromosome.size())
			{
				/* Check for vertical collision */
				if (chromosome.get(i).compareTo(chromosome.get(j)) == 0)
				{
					++numCollisions;
				}
				
				/* Check for diagnoal collision, they have a collision if their slope is 1 */
				double Yi = chromosome.get(i).doubleValue();
				double Yj = chromosome.get(j).doubleValue();
				
				if (Math.abs((double) (i - j) / (Yi - Yj)) == 1.0)
				{
					++numCollisions;
				}
			}
		}
		
		/* Return the base case of 1, to prevent divide by zero if NO collisions occur */
		if (numCollisions == 0)
		{
			return 1;
		}
		else
		{
			return numCollisions;
		}
	}
}
