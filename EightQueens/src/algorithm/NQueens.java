/**
 * Genetic Algorithm Research -- N Queens Puzzle
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import static org.kohsuke.args4j.ExampleMode.ALL;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;


public class NQueens
{
	/* Command line arguments */
    @Option(name="-p", usage="Population size, default is 64")
    private static Integer populationSize = 64;
    
    @Option(name="-i", usage="Inbreeding percentage threshold, default is 0.15 (15%)")
    private static Double inbreedingThreshold = 0.15;
    
    @Option(name="-m", usage="The fixed mutation rate, not used by default")
    private static Double mutation;
    
    @Option(name="-q", usage="The number of queens, the default is 8")
    private static Integer numQueens = 8;
    
    @Option(name="-o", required=true, metaVar="OUTPUT", usage="The output directory to store the results")
    private static String outputDir;
    
    @Option(name="-r", required=true, usage="The program run number, a unique identifier")
    private static Integer runNumber;
    
    @Option(name="-s", usage="Number of solutions to display, default is 10")
    private static Integer numDisplay = 10;
	
    @Argument
    private static List<String> arguments = new ArrayList<String>();
	
    
	private static ArrayList<Chromosome> population;
	private static ArrayList<Chromosome> solutions = new ArrayList<Chromosome>();
	private static Chromosome rotation;
	
	/* Descriptive statistics */
	private static ArrayList<DescriptiveStatistics> fitnessStats = new ArrayList<DescriptiveStatistics>();
	private static ArrayList<Double> similarity = new ArrayList<Double>();
	private static ArrayList<Double> mutationRate = new ArrayList<Double>();
	
	private static Random random = new Random();
	private static Integer numGenerations = 0;
	private static Double curSimilarity = 0.0;
	
	
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
		String value = "";
		
		
		/* Create an array with a single string representation for each chromosome */
		ArrayList<String> stringChromosomes = new ArrayList<String>(populationSize);
		
		for (Chromosome chromosome : chromosomes)
		{
		    value = "";
		    
		    /* For each gene compute the value */
		    for (int i = 0; i < chromosome.size(); ++i)
		    {
		        value += chromosome.get(i).toString();
		    }
		    stringChromosomes.add(value);
		}
		

		/* Count the number of similar chromosomes based on their value */
		Collections.sort(stringChromosomes);
		for (int i = 0; i < stringChromosomes.size() - 1;  ++i)
		{
		    if (stringChromosomes.get(i).compareTo(stringChromosomes.get(i+1)) == 0)
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
            if(matched && (i + 1 == stringChromosomes.size() - 1))
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
		population =  new ArrayList<Chromosome>(populationSize);
		
		
		while (population.size() < populationSize)
		{
			population.add(new Chromosome(random, numQueens));
		}
	}

	
	public static void main(String[] args) throws InterruptedException, IOException
	{
	    new NQueens().doMain(args);
	}
	
	public void doMain(String[] args) throws InterruptedException, IOException 
	{ 
	    CmdLineParser parser = new CmdLineParser(this);
	    
        try
        {
            /* Parse the arguments */
            parser.parseArgument(args);

            System.out.println(outputDir);
            System.out.println(runNumber);
        }
        catch (CmdLineException e) 
        {
            System.err.println(e.getMessage());
            System.err.println("java NQueens [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println();

            /* Print program sample showing all of the options */
            System.err.println(" Example: java NQueens"+parser.printExample(ALL));

            System.exit(1);
        }
	    
        
		/* Create an initial population of uniformly random chromosomes */
		initPopulation();

		/* Initialize the Breed operation */
        if (mutation != null)
        {
            Breed.init(new Random(), mutation);
        }
        else
        {
            Breed.init(new Random());
        }
		
        
        /* Iterate until all of the solutions for the N queens problem has been found */
		while (solutions.size() < 92)
		{	
			/* If the percentage of similar chromosomes due to in-breeding exceeds
			 * the minimum threshold value, increase the amount of mutation
			 */
		    curSimilarity = similarChromosomes(population);
			if (mutation == null)
			{
			    if(curSimilarity >= inbreedingThreshold)
    			{
    				Breed.inBreeding(true);
    			}
    			else
    			{
    				Breed.inBreeding(false);
    			}
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
			ArrayList<Chromosome> nextPopulation =  new ArrayList<Chromosome>(populationSize);
			ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>(2);
			
			
			while (nextPopulation.size() < populationSize)
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
						
			/* Save the fitness stats for the current generation */
			fitnessStats.add(new DescriptiveStatistics(Doubles.toArray(fitness.values())));

			/* Save chromosome similarity and mutation rate for current generation */
			similarity.add(curSimilarity);
			mutationRate.add((Breed.MUTATION.upperEndpoint() - Breed.MUTATION.lowerEndpoint()) / 100.0 );
			
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
		for (Chromosome solution : solutions)
		{
			/* Only display the specified number of solutions rather than all */
			if (solutions.indexOf(solution) < numDisplay)
			{
				try
				{
				    QueenGame myGame = new QueenGame (new QueenBoard(Ints.toArray(solution.get()), numQueens));
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