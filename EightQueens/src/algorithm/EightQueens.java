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

import gameboard.QueenBoard;
import gameboard.QueenGame;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;


public class EightQueens
{
	private static Integer POPULATION = 64;
	private static Double INBREEDING_THRESHOLD = 0.15;
	private static final Integer NUM_DISPLAY = 10;
	
	private static String OutputDir;
	private static Integer RunNumber;
	private static Integer NumQueens = 8;
	private static Double MutationRate;	
	
	private static ArrayList<Chromosome> population;
	private static ArrayList<Chromosome> solutions;
	private static Chromosome rotation;
	
	private static ArrayList<Double> avgFitness;
	private static ArrayList<Double> bestFitness;
	
	private static ArrayList<Double> similarity;
	private static ArrayList<Double> mutationRate;

	/* Ratio of similarity to mutation and mutation to similarity */
	private static ArrayList<Double> similarityMutation;
	private static ArrayList<Double> mutationSimilarity;
	
	private static Random random = new Random();
	private	static DescriptiveStatistics stats;
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
		int similar = 0;
		boolean matched = false;
		BigInteger value = BigInteger.valueOf(0);
		
		
		/* Create an array with a single numeric value for each chromosome */
		ArrayList<BigInteger> numericChromosomes = new ArrayList<BigInteger>(POPULATION);
		
		for (Chromosome chromosome : chromosomes)
		{
		    value = BigInteger.valueOf(0);
		    
		    /* For each gene compute the numeric value */
		    for (int i = 0, j = chromosome.size() - 1; i < chromosome.size(); ++i)
		    {
		        BigInteger gene = BigInteger.valueOf(chromosome.get(i).intValue());
		        value = value.add(gene.multiply(BigInteger.TEN.pow(j - i)));
		    }
		    
		    numericChromosomes.add(value);
		}
		

		/* Count the number of similar chromosomes based on their numeric value */
		Collections.sort(numericChromosomes);
		for (int i = 0; i < numericChromosomes.size() - 1;  ++i)
		{
		    if (numericChromosomes.get(i).compareTo(numericChromosomes.get(i+1)) == 0)
		    {
	            ++similar;
	            matched = true;
		    }
		    /* Add an additional match for the last match in a set */
		    else if (matched)
		    {
		        ++similar;
		        matched = false;
		    }
		    
	        /* Add an additional match if there is a match with the last item in list */
            if(matched && (i + 1 == numericChromosomes.size() - 1))
            {
                ++similar;
            }
		}
		
        /* Calculate the percentage of chromosomes that were similar */
        if (similar > 1)
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
			population.add(new Chromosome(random, NumQueens));
		}
	}

	
	public static void main(String[] args) throws InterruptedException
	{
		//Example: p=100 o=/home/joseph/github/ai_project r=1 i=0.9
		//i and u are optional and are exclusive
		//q is optional, will default to 8 if not given
		//args layout = "p=\\d* o=.* r=\\d* q=\\d* u=\\d*\\.\\d* i=\\d*\\.\\d*
		System.out.println(args.length);
		if (args.length >= 3)
		{
			Pattern patternPop = Pattern.compile("p=(\\d*)");
			Matcher mPop = patternPop.matcher(args[0]);
			
			if(mPop.matches() && mPop.groupCount() == 1)
			{
				POPULATION = Integer.valueOf(mPop.group(1));
				System.out.println("Population set to " + POPULATION);
			}
			else
			{
				//Error
			}
			
			Pattern patternOut = Pattern.compile("o=(.*)");
			Matcher mOut = patternOut.matcher(args[1]);
			
			if(mOut.matches() && mOut.groupCount() == 1)
			{
				OutputDir = mOut.group(1);
				System.out.println(OutputDir);
			}
			
			Pattern patternRun = Pattern.compile("r=(\\d*)");
			Matcher mRun = patternRun.matcher(args[2]);
			
			if(mRun.matches() && mRun.groupCount() == 1)
			{
				RunNumber = Integer.valueOf(mRun.group(1));
				System.out.println(RunNumber);
			}
			
			if(args.length >= 4)
			{
				Pattern patternQueen = Pattern.compile("q=(\\d*)");
				Matcher mQueen = patternQueen.matcher(args[3]);
				
				int intOffset = 0;
				
				if(mQueen.matches() && mQueen.groupCount() == 1)
				{
					NumQueens = Integer.valueOf(mQueen.group(1));
					System.out.println(NumQueens);
					
					intOffset++;
				}
				else
				{
					System.out.println("Not given, using default 8");
				}
				
				if(mQueen.matches() && args.length == 5 || !mQueen.matches() && args.length == 4)
				{
					Pattern patternMut = Pattern.compile("u=(\\d*\\.\\d*)");
					Matcher mMutation = patternMut.matcher(args[3+intOffset]);
					
					if(mMutation.matches() && mMutation.groupCount() == 1)
					{
						MutationRate = Double.valueOf(mMutation.group(1));
						System.out.println(MutationRate);
					}
					
					Pattern patternInbreed = Pattern.compile("i=(\\d*\\.\\d*)");
					Matcher mInbreed = patternInbreed.matcher(args[3+intOffset]);
					
					if(mInbreed.matches() && mInbreed.groupCount() == 1)
					{
						INBREEDING_THRESHOLD = Double.valueOf(mInbreed.group(1));
						System.out.println(INBREEDING_THRESHOLD);
					}
				}
			}
		}
		//Population
		//Number of queens
		//Output dir
		//Run number
		//Inbreeding Or Mutation rate
		
		solutions = new ArrayList<Chromosome>();
		avgFitness = new ArrayList<Double>();
		bestFitness = new ArrayList<Double>();
		similarity = new ArrayList<Double>();
		mutationRate = new ArrayList<Double>();
		similarityMutation = new ArrayList<Double>();
		mutationSimilarity = new ArrayList<Double>();
		
		/* Create an initial population of uniformly random chromosomes */
		initPopulation();
		
		
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
				if (fitness.get(chromosome) == 1.0 && uniqueSolution(chromosome))
				{
					/* Save a copy of the chromosome */
					solutions.add(new Chromosome(new ArrayList<Integer>(chromosome.get()), chromosome.size()));
					
					System.out.println("\nNUMBER OF SOLUTIONS:   " + solutions.size());
					System.out.println("NUMBER OF GENERATIONS: " + numGenerations);
					
					/* Perform three rotations of the coordinates by 90 degrees to get new solutions */
					for (int i = 0; i < 3; ++i)
					{
						rotation = Pivot.rotate(solutions.get(solutions.size() - 1));
						
						if (uniqueSolution(rotation))
						{
							solutions.add(rotation);
							System.out.println("\nNUMBER OF SOLUTIONS:   " + solutions.size());
							System.out.println("NUMBER OF GENERATIONS: " + numGenerations);
						}
					}
				}
			}
						
			/* Save the average and best fitness values for the current generation */
			stats = new DescriptiveStatistics(Doubles.toArray(fitness.values()));
			avgFitness.add(stats.getMean());
			bestFitness.add(stats.getMax());
			
			/* Save chromosome similarity and mutation rate for current generation */
			similarity.add(similarChromosomes(nextPopulation));
			mutationRate.add((Breed.MUTATION.upperEndpoint() - Breed.MUTATION.lowerEndpoint()) / 100.0 );
			
			/* Save the ratios of chromosome similarity and mutation rates */
			similarityMutation.add(   (similarity.get(similarity.size() - 1)) 
									/ (mutationRate.get(mutationRate.size() -1)) );
			
			mutationSimilarity.add(   (mutationRate.get(mutationRate.size() -1))
									/ (similarity.get(similarity.size() - 1)) );
			
			/* Set the current population as the NEXT population */
			fitness.clear();
			population = nextPopulation;			
			
			++numGenerations;
		}
		
		
		/* Plot the average and best fitness of generations */		
		//SeriesPlot fitnessPlot = new SeriesPlot("Fitness Over Generations", "Number of Generations", "Fitness");
		//SeriesBoxPlot fitnessBoxPlot = new SeriesBoxPlot("Fitness Over Generations", "Fitness");
		
		//fitnessPlot.plot(avgFitness, "Average Fitness", bestFitness, "Best Fitness");
		//fitnessBoxPlot.plot(avgFitness, "Average Fitness", bestFitness, "Best Fitness");
		
		
		/* Plot relationship between chromosome similarity and mutation rate from inbreeding */
		//SeriesPlot mutationPlot = new SeriesPlot("Chromosome Similarity and Mutation Rate due to In-Breeding", "Number of Generations", "Rate");
		//SeriesBoxPlot mutationBoxPlot = new SeriesBoxPlot("Chromosome Similarity and Mutation Rate due to In-Breeding", "Rate");
		
		//mutationPlot.plot(similarity, "Chromosome Similarity", mutationRate, "Mutation Rate");
		//mutationBoxPlot.plot(similarity, "Chromosome Similarity", mutationRate, "Mutation Rate");
		
		
		/* Display descriptive statistics for mutation rate and chromosome similarity */
		DescriptiveStatistics similarityStats = new DescriptiveStatistics(Doubles.toArray(similarity));
		System.out.println("\nChromosome Similarity MEDIAN: " + similarityStats.getPercentile(50.0));
		System.out.println("Chromosome Similarity MEAN: " + similarityStats.getMean());
		System.out.println("STD: " + similarityStats.getStandardDeviation());
		System.out.println("Q1: " + similarityStats.getPercentile(25.0));
		System.out.println("Q3: " + similarityStats.getPercentile(75.0));

		DescriptiveStatistics mutationStats = new DescriptiveStatistics(Doubles.toArray(mutationRate));
		System.out.println("\nMutation MEDIAN: " + mutationStats.getPercentile(50.0));
		System.out.println("Mutation MEAN: " + mutationStats.getMean());
		System.out.println("STD: " + mutationStats.getStandardDeviation());
		System.out.println("Q1: " + mutationStats.getPercentile(25.0));
		System.out.println("Q3: " + mutationStats.getPercentile(75.0));
		
		
		/* Display the solutions to the eight queens puzzle */
		QueenGame myGame = null;
		for (Chromosome solution : solutions)
		{
			/* Only display the specified number of solutions rather than all */
			if (solutions.indexOf(solution) < NUM_DISPLAY)
			{
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
			Thread.sleep(2000);
		}
	}
}