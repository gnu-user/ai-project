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

import gameboard.QueenGame;
import gameboard.QueenBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


import com.google.common.primitives.Ints;


public class EightQueens
{
	private static final Integer POPULATION = 8;
	private static final Double INBREEDING_THRESHOLD = 0.15;
	private static final Integer NUM_DISPLAY = 10;
	
	private static ArrayList<Chromosome> population;
	private static ArrayList<Chromosome> solutions;

	private static Random random = new Random();
	private static Integer numGenerations = 0;
	
	/**
	 * Determines if the solution to the eight queens puzzle is unique if
	 * so returns true.
	 * 
	 * @param chromosome The potential unique solution chromosome
	 * @return True if the chromosome is a unique solution
	 */
	public static boolean uniqueSolution(Chromosome chromosome)
	{
		boolean unique = true;
		
		for (Chromosome uniqChromosome : solutions)
		{
			for (int i = 0; i < uniqChromosome.size(); ++i)
			{
				/* If any of the chromosome genes differ the genes are unique */
				if (chromosome.get(i).compareTo(uniqChromosome.get(i)) == 0)
				{
					/* Current genes match */
					unique = false;
				}
				else
				{
					unique = true;
					break;
				}
			}
			
			/* If the current chromosome is not unique don't continue checking */
			if (! unique)
			{
				break;
			}
		}
		
		return unique;
	}
	
	
	/**
	 * Calculates the percentage of chromosomes that are similar, this is an indication
	 * about how much in-breeding has occurred.
	 * 
	 * @return Percentage of the number of chromosomes that are similar
	 */
	public static Double similarChromosomes(ArrayList<Chromosome> chromosomes)
	{
		boolean unique = true;
		
		int similar = 0;
		int curSimilar = 0;
		
		/* Create a copy of the chromosome arraylist */
		CopyOnWriteArrayList<Chromosome> localChromosomes = new CopyOnWriteArrayList<Chromosome>(chromosomes);
		
		for (Chromosome chromosomeA : localChromosomes)
		{
			curSimilar = 0;
			
			/* Check each chromosome to see if there are any similar */
			for (Chromosome chromosomeB : localChromosomes)
			{	
				/* If the current iterator index is not ourselves */
				if (chromosomes.indexOf(chromosomeA) != chromosomes.indexOf(chromosomeB))
				{
					/* Check to see if the chromosomes are similar */
					for (int i = 0; i < chromosomeB.size(); ++i)
					{
						/* If any of the chromosome genes differ the genes are unique */
						if (chromosomeA.get(i).compareTo(chromosomeB.get(i)) == 0)
						{
							/* Current genes match */
							unique = false;
						}
						else
						{
							unique = true;
							break;
						}
					}
					
					/* If the chromosome is not unique increment counter, remove chromosome */
					if (! unique)
					{
						++curSimilar;
						localChromosomes.remove(chromosomeB);
					}
				}
			}
			
			/* If there were any similar chromosomes found, remove chromosomeA */
			if (curSimilar > 0)
			{
				++curSimilar;
				localChromosomes.remove(chromosomeA);
				
				similar += curSimilar;
			}
		}
		
		/* Calculate the percentage of chromosomes that were similar */
		if (similar > 0)
		{
			return (double) similar / (double) chromosomes.size();
		}
		
		return 0.0;
	}
	
	
	/**
	 * Creates an initial population of uniformly random chromosomes
	 */
	static public void initPopulation()
	{
		/* Create an array of uniformly random chromosomes for initial population */
		population =  new ArrayList<Chromosome>(POPULATION);
		
		
		while (population.size() < POPULATION)
		{
			population.add(new Chromosome(random));
		}
	}
	
	
	public static void main(String[] args)
	{
		solutions = new ArrayList<Chromosome>();
		
		/* Create an initial population of uniformly random chromosomes */
		initPopulation();
		
		/*
		for (Chromosome chromosome : initPopulation)
		{
			System.out.println(chromosome.get().toString());
		}*/
		
		/*
		solutions.add(new Chromosome(new ArrayList<Integer>(Arrays.asList(4, 2, 0, 6, 1, 7, 5, 3))));
		solutions.add(new Chromosome(new ArrayList<Integer>(Arrays.asList(4, 3, 0, 6, 1, 7, 5, 3))));
		
		Chromosome temp = new Chromosome(new ArrayList<Integer>(Arrays.asList(4, 3, 0, 6, 1, 7, 5, 3)));
		
		if (uniqueSolution(temp))
		{
			System.out.println("UNIQUE!");
			System.exit(0);
		}
		else
		{
			System.out.println("NOT UNIQUE!");
			System.exit(0);
		}*/	
		
		/*
		ArrayList<Chromosome> test = new ArrayList<Chromosome>();
		
		test.add(new Chromosome(new ArrayList<Integer>(Arrays.asList(4, 3, 0, 6, 1, 7, 5, 3))));
		test.add(new Chromosome(new ArrayList<Integer>(Arrays.asList(4, 3, 0, 6, 1, 7, 5, 3))));
		test.add(new Chromosome(new ArrayList<Integer>(Arrays.asList(3, 3, 0, 6, 1, 7, 5, 3))));
		
		System.out.println(similarChromosomes(test));
		System.exit(0);
		*/
		
		
		while (solutions.size() < 92)
		{	
			/* If the percentage of similar chromosomes due to in-breeding exceeds
			 * the minimum threshold value, increase the amount of mutation
			 */
			if (similarChromosomes(population) >= INBREEDING_THRESHOLD)
			{
				Breed.inBreeding(true);
			}
			
			else
			{
				Breed.inBreeding(false);
			}
			
			
			/* Calculate the fitness distribution of the current population */
			HashMap<Chromosome, Double> fitness = Fitness.calculate(population);			
			
			
			/* Instantiate the selection iterator using the fitness distribution,
			 * the selection iterator uses roulette wheel selection to select
			 * each chromosome.
			 */
			Selection selection = new Selection(new Random());
			selection.init(fitness);
			
			
			/* Generate the next population by selecting chromosomes from the current
			 * population using selection iterator and applying the cloning, crossover,
			 * and mutation operations.
			 */
			ArrayList<Chromosome> nextPopulation =  new ArrayList<Chromosome>(POPULATION);
			ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>(2);
			Breed.init(new Random());
			
			while (nextPopulation.size() < POPULATION)
			{
				/* Select a random number and apply the breeding operation */
				Integer randomNum = random.nextInt(100);
				
				/* Pair of parent chromosomes continue on to the next generation.*/
				if (Breed.CLONING.contains(randomNum))
				{
					chromosomes.addAll(Breed.cloning(selection));
				}
				/* Pair of parent chromosomes are cross-overed to create new pair */
				else if (Breed.CROSSOVER.contains(randomNum))
				{
					chromosomes.addAll(Breed.crossover(selection));
				}
				
				/* Apply the background mutation operator to the chromosomes */
				for (Chromosome chromosome : chromosomes)
				{
					randomNum = random.nextInt(100);
					
					if (Breed.MUTATION.contains(randomNum))
					{
						nextPopulation.add(Breed.mutation(chromosome));
					}
					else
					{
						nextPopulation.add(chromosome);
					}
				}
				chromosomes.clear();
			}
			
			
			/* If there are any solutions (fitness of 1) that are unique save them */
			for (Chromosome chromosome : fitness.keySet())
			{		
				//System.out.println("\nCHROMOSOME: " + chromosome.get().toString());
				//System.out.println("FITNESS: " + fitness.get(chromosome));
				
				if (fitness.get(chromosome) == 1.0 && uniqueSolution(chromosome))
				{
					/* Save a copy of the chromosome */
					solutions.add(new Chromosome(new ArrayList<Integer>(chromosome.get())));
					
					System.out.println("\nNUMBER OF SOLUTIONS:   " + solutions.size());
					System.out.println("NUMBER OF GENERATIONS: " + numGenerations);
					
					/* Create a new initial initial population of uniformly random chromosomes */
					/*initPopulation();
					System.out.println("NEW POPULATION!");
					
					for (Chromosome chromosomeB : population)
					{
						System.out.println(chromosomeB.get().toString());
					}*/
				}
			}
			
			/* Set the current population as the NEXT population */
			fitness.clear();
			population = nextPopulation;
			
			++numGenerations;
		}
		
		
		/* Display the solutions to eight queens puzzle */
		for (Chromosome solution : solutions)
		{
			/* Only display the specified number of solutions rather than all */
			if (solutions.indexOf(solution) < NUM_DISPLAY)
			{
				QueenGame myGame = null;
				try
				{
					myGame = new QueenGame (new QueenBoard(Ints.toArray(solution.get())));
					myGame.playGame();
				}
				catch (Exception e)
				{
					System.out.println("Bad set of Queens");
				}
			}
			else
			{
				break;
			}
		}
	}
}