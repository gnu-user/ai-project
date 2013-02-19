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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	public static final Range<Integer> CLONING = Range.closedOpen(0, 30);
	public static final Range<Integer> CROSSOVER = Range.closedOpen(30, 99);
	public static final Range<Integer> MUTATION = Range.closedOpen(99, 100);
	
	
	/**
	 * Initialize the breeding selection with a uniform random number
	 */
	static public void init(Random random)
	{
		Breed.random = random;
	}
	
	
	/**
	 * Performs the cloning operation which simple returns the pair
	 * of parent chromosomes, these parents continue on to the next
	 * generation.
	 * 
	 * @param selection The selection iterator containing the chromosomes
	 * to be selected.
	 * 
	 * @return The pair of parent chromosomes
	 */
	static public ArrayList<Chromosome> cloning(Selection selection)
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
	static public ArrayList<Chromosome> crossover(Selection selection)
	{
		ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>(2);

		Chromosome parent1 = selection.next();
		Chromosome parent2 = selection.next();
		
		//System.out.println("PARENT 1 BEFORE: " + parent1.get().toString());
		//System.out.println("PARENT 2 BEFORE: " + parent2.get().toString());
		
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
		
		//System.out.println("CROSSOVER: " + crossover); 
		
		/* Perform crossover with the pair of genes from the chromosomes */
		parent1.set(crossover, parent1.size() - 1, genes2);
		parent2.set(crossover, parent2.size() - 1, genes1);
		
		chromosomes.add(parent1);
		chromosomes.add(parent2);
		
		//System.out.println("PARENT 1 AFTER:  " + chromosomes.get(0).get().toString());
		//System.out.println("PARENT 2 AFTER:  " + chromosomes.get(1).get().toString());
		
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
	static public ArrayList<Chromosome> mutation(Selection selection)
	{		
		ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>(2);
		
		/* Mutate each chromosome */
		//int i = 0;
		while (chromosomes.size() < 2)
		{
			Chromosome mutated = selection.next();
			
			//System.out.println("BEFORE: " + mutated.get().toString());
			
			/* Mutate one of the genes in the chromosome */
			mutated.set(random.nextInt(mutated.size()), 
						random.nextInt(mutated.size()));
			
			chromosomes.add(mutated);
			
			//System.out.println("AFTER:  " + chromosomes.get(i++).get().toString());
		}
		
		return chromosomes;
	}
}
