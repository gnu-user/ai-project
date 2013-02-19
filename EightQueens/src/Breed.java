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
import java.util.Random;


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
		return null;
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
		int i = 0;
		while (chromosomes.size() < 2)
		{
			Chromosome mutated = selection.next();
			
			System.out.println("BEFORE: " + mutated.get().toString());
			
			/* Mutate one of the genes in the chromosome */
			mutated.set(random.nextInt(mutated.size()), 
						random.nextInt(mutated.size()));
			
			chromosomes.add(mutated);
			
			System.out.println("AFTER:  " + chromosomes.get(i++).get().toString());
		}
		
		return chromosomes;
	}
}
