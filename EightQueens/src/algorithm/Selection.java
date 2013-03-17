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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

import com.google.common.collect.Range;


/**
 * Selection method for the chromosomes which uses the roulette wheel
 * selection method, it takes a fitness distribution of chromosomes and
 * operates as an iterator to select chromosomes from the population.
 */
public class Selection
{
	/* Epsilon for ranges defined as the MIN value for Double */
	private static final Double EPSILON = 1.0E-15;
	
	/* The chromosome selection ranges used for roulette wheel */
	
	/* NOTE LinkedHashMap MUST be used to maintain the order of items
	 * (stable) data structure, HashMap does NOT guarantee the order!!!!
	 */
	private LinkedHashMap<Chromosome, Range<Double>> selection;
	
	/* The left and right bound of the roulette wheel range */
	private final Double leftBound;
	private Double rightBound;
	
	private final Random random;
	
	
	/** 
	 * Create an instance of the selection iterator which uses roulette
	 * wheel selection to select the chromosomes.
	 * 
	 * @param random The random number generator to use for the selection.
	 */
	public Selection(Random random)
	{
		this.random = random;
		this.selection = new LinkedHashMap<Chromosome, Range<Double>>();
		
		/* Initialize the initial left and right bounds */
		this.leftBound = 0.0;
		this.rightBound = 0.0 + EPSILON;
	}
	
	
	/**
	 * Initialize the selection iterator with the fitness distribution of the 
	 * chromosomes which is used to generate the selection range of chromosomes
	 * for the roulette wheel selection method.
	 * 
	 * Each range is defined as the left closed and right open, e.g. [0, 3)
	 * 
	 * @param fitness The fitness distribution for the chromosomes to be used
	 * for generating the selection ranges.
	 */
	public void init(HashMap<Chromosome, Double> fitness)
	{
		/* Lower closed  bound and upper open bound for each interval */
		Double lower = leftBound;
		Double upper = leftBound + EPSILON;
		
		/* Calculate right bound for the roulette wheel, include the epsilon offset */
		for (Double value : fitness.values())
		{
			rightBound += value + EPSILON;
		}
		
		/* Add each chromosome and interval to roulette wheel selection */
		for (Chromosome chromosome : fitness.keySet())
		{
			upper = lower + fitness.get(chromosome);
			selection.put(chromosome, Range.closedOpen(lower, upper));
			
			/* Increase the lower closed bound to previous right bound + EPSILON */
			lower = upper + EPSILON;
		}
	}
	
	/**
	 * The iterator method which uses roulette wheel selection to get the next
	 * chromosome to be used for the genetic algorithm.
	 * 
	 * NOTE: The selection method creates a DEEP copy of the object rather than by
	 * references to prevent crossover/mutation collision if the same chromosome
	 * is selected multiple times.
	 * 
	 * @return The next chromosome selected using roulette wheel selection
	 */
	public Chromosome next() throws ArithmeticException
	{
		/* Generate a uniform random number and select a chromosome where the
		 * random number is within the chromosome's selection interval.
		 */
		Double randomNum = random.nextDouble() * rightBound;
		
		/* Find the chromosome where the random number is within the interval */
		for (Chromosome chromosome : selection.keySet())
		{
			if (selection.get(chromosome).contains(randomNum))
			{				
				/* Return a DEEP copy of the chromosome */
				return new Chromosome(new ArrayList<Integer>(chromosome.get()));
			}
		}
		
		/* Arithmetic/rounding error number not found within any interval */
		throw new ArithmeticException("Arithmetic/Rounding error no chromosome" +
				" interval found that satisfied the random number");
	}
}