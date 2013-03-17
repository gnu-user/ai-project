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
import java.util.Arrays;
import java.util.Random;

import com.google.common.collect.Range;


/**
 * Applies the crossover and mutation operation to chromosomes which
 * are bred. For each breeding session two chromosomes are always
 * selected and the respective operation of crossover, or
 * mutation are applied to the parents.
 *
 */
public abstract class Breed
{
	private static Random random;
	
	/* The cloning, crossover, and mutation percentage intervals */
	public static  Range<Integer> CLONING = Range.closedOpen(0, 30); 		// 30 %
	public static  Range<Integer> CROSSOVER = Range.closedOpen(30, 100);	// 70 %
	public static  Range<Integer> MUTATION = Range.closedOpen(99, 100);		//  1 %
	
	
	/**
	 * Initialize the breeding selection with a uniform random number
	 */
	public static void init(Random random)
	{
		Breed.random = random;
	}
	
	
	/**
	 * Increases the amount of genetic mutations that occur, which is usually
	 * as a result of in-breeding in genetics. This simulates the negative effects
	 * of in-breeding within a population and increases the mutation rate by 5% each
	 * time up to a maximum threshold value, otherwise if the population is not in-bred
	 * the amount of mutation decreases down to the original mutation rate of 1%.
	 * 
	 * If the population is in-bred the amount of genetic mutation that occur increases,
	 * whereas if the amount of in-breeding is minimal the amount of genetic mutations
	 * that occurs is minimized.
	 * 
	 * @inBred True if the current population is in-bred and at the minimum threshold,
	 * false otherwise.
	 */
	public static void inBreeding(boolean inBred)
	{
		if (inBred)
		{
			if (MUTATION.lowerEndpoint() > 1)
			{
				/* Decrease the MUTATION lower bound by 1 */
				Breed.MUTATION = Range.closedOpen(MUTATION.lowerEndpoint() - 1, 100);
			}
		}
		else
		{
			if (MUTATION.lowerEndpoint() < 99)
			{				
				/* Increase the MUTATION lower bound by 1 */
				Breed.MUTATION = Range.closedOpen(MUTATION.lowerEndpoint() + 1, 100);
			}
		}
	}
	
	
	/**
	 * Performs the cloning operation which simply returns the pair
	 * of parent chromosomes, these parents continue on to the next
	 * generation.
	 * 
	 * @param selection The selection iterator containing the chromosomes
	 * to be selected.
	 * 
	 * @return The pair of parent chromosomes
	 */
	public static ArrayList<Chromosome> cloning(Selection selection)
	{
		return new ArrayList<Chromosome>(Arrays.asList(selection.next(), selection.next()));
	}
	
	
	/**
	 * Performs the crossover operation on the parent chromosomes and
	 * returns a pair of chromosomes.
	 * 
	 * @param selection The selection iterator containing the chromosomes
	 * to be selected.
	 * 
	 * @return A pair of chromosomes that have had crossover
	 */
	public static ArrayList<Chromosome> crossover(Selection selection)
	{
		ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>(2);

		Chromosome parent1 = selection.next();
		Chromosome parent2 = selection.next();
		
		
		/* Select a cross-over point, copy the genes, and then crossover the genes.
		 * The genes MUST be copied instead of by reference in order to copy the
		 * from each chromosome without them being altered.
		 */
		Integer crossover = random.nextInt(parent1.size());
		
		ArrayList<Integer> genes1 = new ArrayList<Integer>(
											parent1.get(crossover, 
														parent1.size() - 1)
											);
		ArrayList<Integer> genes2 = new ArrayList<Integer>(
											parent2.get(crossover, 
														parent2.size() - 1)
											);
		
		/* Perform crossover with the pair of genes from the chromosomes */
		parent1.set(crossover, parent1.size() - 1, genes2);
		parent2.set(crossover, parent2.size() - 1, genes1);
		
		chromosomes.add(parent1);
		chromosomes.add(parent2);
		
		return chromosomes;
	}

	
	/**
	 * Performs the mutation operation on the parent chromosomes and
	 * returns a pair of mutated child chromosomes.
	 * 
	 * @param selection The selection iterator containing the chromosomes
	 * to be selected.
	 * 
	 * @return A pair of mutated chromosomes
	 */
	public static Chromosome mutation(Chromosome chromosome)
	{
		/* Mutate one of the genes in the chromosome */
		chromosome.set(random.nextInt(chromosome.size()), 
					   random.nextInt(chromosome.size()));
		
		return chromosome;
	}
}
