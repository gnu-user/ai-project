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
import gameboard.QueenGame;
import gameboard.QueenBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.google.common.primitives.Ints;


public class EightQueens
{
	private static final Integer POPULATION = 64;
	private static boolean solved = false;
	private static ArrayList<Chromosome> solutions;
	
	/**
	 * Determines if the solution to the eight queens puzzle is unique if
	 * so returns true.
	 * 
	 * @param chromosome The potential unique solution chromosome
	 * @return True if the chromosome is a unique solution
	 */
	public static boolean uniqueSolution(Chromosome chromosome)
	{
		boolean unique = false;
		
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
	
	public static void main(String[] args)
	{
		solutions = new ArrayList<Chromosome>();
		
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
		
		
		while (!solved)
		{
			/* Create an array of uniformly random chromosomes for initial population */
			ArrayList<Chromosome> initPopulation =  new ArrayList<Chromosome>(POPULATION);
			
			while (initPopulation.size() < POPULATION)
			{
				initPopulation.add(new Chromosome(new Random()));
			}
			
			/*
			for (Chromosome chromosome : initPopulation)
			{
				System.out.println(chromosome.get().toString());
			}*/
			
			/* Calculate the fitness distribution */
			HashMap<Chromosome, Double> fitness = Fitness.calculate(initPopulation);
			
			Selection test = new Selection(new Random());
			
			test.init(fitness);
			
			System.out.println(test.next().get().toString());
			
			Breed.init(new Random());
			
			Breed.mutation(test);
			
			
			System.out.println("DONE");
			
			System.exit(0);
			
			/* Display the fitness for each */
			for (Chromosome chromosome : fitness.keySet())
			{
				System.out.println("\nCHROMOSOME: " + chromosome.get().toString());
				System.out.println("FITNESS: " + fitness.get(chromosome));
				
				
				if (fitness.get(chromosome) == 1.0)
				{
					QueenGame myGame = null;
					try{
						myGame = new QueenGame (new QueenBoard(Ints.toArray(chromosome.get())));
						myGame.playGame();
						solved = true;
						
						System.out.println("DONE!!!!");
						break;
					}
					catch (Exception e)
					{
						System.out.println("Bad set of Queens");
					}
				}
			}
		}
	}
}
