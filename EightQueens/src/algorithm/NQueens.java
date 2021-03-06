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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    
    @Option(name="-g", usage="The maximum number of generations before terminating, the default is 10 Million")
    private static Integer maxGenerations = 10000000;
    
    @Option(name="-o", required=true, metaVar="OUTPUT", usage="The output directory to store the results")
    private static String outputDir;
    
    @Option(name="-r", required=true, usage="The program run number, a unique identifier")
    private static Integer runNumber;
    
    @Option(name="-n", usage="Multi-run mode, the number of times to re-run the program, default is 1")
    private static Integer numRuns = 1;
    
    @Option(name="-s", usage="Number of solutions to display, default is 10")
    private static Integer numDisplay = 10;
	
    @Argument
    private static List<String> arguments = new ArrayList<String>();
	
    /* Mapping of the distinct solutions for the N Queens problem, sequence A000170 from OEIS */
    private static int[] distinctSolutions = {1, 0, 0, 2, 10, 4, 40, 92, 352, 724, 2680, 14200, 
        73712, 365596, 2279184, 14772512, 95815104, 666090624};
    
   	private static ArrayList<Chromosome> population;
	private static ArrayList<Chromosome> solutions = new ArrayList<Chromosome>();
	private static Chromosome rotation;
	private static Chromosome reflection;
	
	/* Buffers for storing the fitness, mutation, and similarity for multiple generations */
	private static ArrayList<Double> fitnessBuffer = new ArrayList<Double>();
	private static ArrayList<Double> similarityBuffer = new ArrayList<Double>();
	private static ArrayList<Double> mutationBuffer = new ArrayList<Double>();
	private static HashMap<Integer, Integer> duplicateBuffer = new HashMap<Integer, Integer>();
	
	
	/* Data for calculating descriptive statistics */
	private static LinkedHashMap<Integer, Integer> solutionGeneration = new LinkedHashMap<Integer, Integer>();
	private static LinkedHashMap<Integer, Integer> rotationMiss = new LinkedHashMap<Integer, Integer>();
	private static LinkedHashMap<Integer, Integer> reflectionMiss = new LinkedHashMap<Integer, Integer>();
	private static LinkedHashMap<Integer, ArrayList<Double>> duplicateStats = new LinkedHashMap<Integer, ArrayList<Double>>();
	private static LinkedHashMap<Integer, Double> fitnessStats = new LinkedHashMap<Integer, Double>();
	private static LinkedHashMap<Integer, Double> similarityStats = new LinkedHashMap<Integer, Double>();
	private static LinkedHashMap<Integer, Double> mutationStats = new LinkedHashMap<Integer, Double>();
	
	private static Random random = new Random();
	private static Integer numGenerations = 1;
	private static Double curSimilarity = 0.0;
	private static Integer curIndex = 0;
	
	
	/**
	 * Calculates statistics for the current data in the fitness, similarity, and mutation rate
	 * buffers. After the statistics have been calculated they are added to the appropriate
	 * fitnessStats, similarityStats, and mutationStats class members.
	 * 
	 * @param units The units for generations such as thousands (1000) or millions.
	 */
	@SuppressWarnings("serial")
	public static void calcStatistics(int units)
	{
	    /* Calculate fitness stats */
	    DescriptiveStatistics descStats = new DescriptiveStatistics(Doubles.toArray(fitnessBuffer));
        fitnessStats.put((int) Math.ceil(numGenerations / (double) units), descStats.getMean());
        
        /* Calculate chromosome similarity stats */
        descStats = new DescriptiveStatistics(Doubles.toArray(similarityBuffer));
        similarityStats.put((int) Math.ceil(numGenerations / (double) units), descStats.getMean());
        
        /* Calculate mutation rate stats for variable mutation */
        if (mutation == null)
        {
            descStats = new DescriptiveStatistics(Doubles.toArray(mutationBuffer));
            mutationStats.put((int) Math.ceil(numGenerations / (double) units), descStats.getMean());
        }
        
        /* Calculate the duplicate solutions statistics */
        if (duplicateBuffer.size() > 0)
        {
            final DescriptiveStatistics descDupStats = new DescriptiveStatistics(Doubles.toArray(duplicateBuffer.values()));
            duplicateStats.put((int) Math.ceil(numGenerations / (double) units), 
                                new ArrayList<Double>() {{ 
                                           add((double) duplicateBuffer.size()); 
                                           add(descDupStats.getMean());
                                    }}
            );
        }
        else
        {
            duplicateStats.put((int) Math.ceil(numGenerations / (double) units), 
                    new ArrayList<Double>() {{ 
                            add(0.0); 
                            add(0.0);
                        }}
            );
        }
        
        /* Clear the buffers */
		mutationBuffer.clear();
        fitnessBuffer.clear();
        similarityBuffer.clear();
        duplicateBuffer.clear();
	}
	
	
	/**
	 * Writes the current results for the number of solutions found, as well as the record of
	 * population fitness, mutation rate, and chromosome similarity to disk. After the results
	 * have been saved the fitness, mutation rate, and chromosome similarity arrays are reset.
	 */
	@SuppressWarnings("serial") 
	public static void writeResults()
	{
	    OutputWriter ow = new OutputWriter(outputDir);
	    
	    try
	    {
    	    /* Write the number of solutions found and the generation */
    	    ow.saveResults(solutionGeneration,  
    	                   new ArrayList<String>() {{ add("solution"); add("generation");}}, 
    	                   "solution_generation_" + runNumber + ".csv");
    	    
    	    
    	    /* Write the number of duplicate statistics aggregated for every 1000 generations */
    	    ow.saveResultsMul(duplicateStats, 
    	                      new ArrayList<String>() {{ add("generation"); add("n"); add("duplicates");}}, 
    	                      "duplicate_solutions_" + runNumber + ".csv");
    	    
    	    
    	    /* Write the number of rotation misses found for each generation */
            ow.saveResults(rotationMiss, 
                           new ArrayList<String>() {{ add("generation"); add("misses");}}, 
                           "rotation_misses_" + runNumber + ".csv");
            
            
            /* Write the number of reflection misses found for each generation */
            ow.saveResults(reflectionMiss, 
                           new ArrayList<String>() {{ add("generation"); add("misses");}}, 
                           "reflection_misses_" + runNumber + ".csv");
            
            
            /* Write the fitness statistics aggregated for every 1000 generations */
            ow.saveResults(fitnessStats, 
                    new ArrayList<String>() {{ add("generation"); add("fitness");}}, 
                    "fitness_stats_" + runNumber + ".csv");
    	 
            
            /* Write the chromosome similarity statistics aggregated for every 1000 generations */
            ow.saveResults(similarityStats, 
                           new ArrayList<String>() {{ add("generation"); add("similarity");}}, 
                           "chromosome_similarity_" + runNumber + ".csv");
            
            
            /* Write the mutation rate statistics aggregated for every 1000 generations */
            if (mutation == null)
            {
                ow.saveResults(mutationStats, 
                               new ArrayList<String>() {{ add("generation"); add("mutation");}}, 
                               "mutation_rate_" + runNumber + ".csv");
            }
	    }
	    /* Exit if a write error occurs, no point in continuing with missing data */
	    catch (IOException e)
        {
	        System.err.println("FATAL ERROR: Failed to write results to file!");
            e.printStackTrace();
            System.exit(1);
        }
        
        /* Clear all of the data */
        solutionGeneration.clear();
        duplicateStats.clear();
        rotationMiss.clear();
        reflectionMiss.clear();
        fitnessStats.clear();
        similarityStats.clear();
        mutationStats.clear();
	}
	
	
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
	public static void initPopulation()
	{
		/* Create an array of uniformly random chromosomes for initial population */
		population =  new ArrayList<Chromosome>(populationSize);
		
		
		while (population.size() < populationSize)
		{
			population.add(new Chromosome(random, numQueens));
		}
	}
	
	
	/**
	 * In the case of mult-run mode resets the current program state so that another
	 * fresh execution can be done.
	 */
	public static void reset()
	{
	    /* Reset chromosome population and solutions */
	    population.clear();
	    solutions.clear();
	    
	    /* Reset buffers */
	    fitnessBuffer.clear();
	    similarityBuffer.clear();
	    mutationBuffer.clear();
	    duplicateBuffer.clear();
	    
	    /* Reset statistics */
	    solutionGeneration.clear();
	    rotationMiss.clear();
	    reflectionMiss.clear();
	    duplicateStats.clear();
	    fitnessStats.clear();
	    similarityStats.clear();
	    mutationStats.clear();
	    
	    /* Rest counters, indexes */
	    random = new Random();
	    numGenerations = 1;
	    curSimilarity = 0.0;
	    curIndex = 0;
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
        }
        catch (CmdLineException e) 
        {
            System.err.println(e.getMessage());
            System.err.println("java NQueens [options...] arguments...");
            parser.printUsage(System.err);

            /* Print program sample showing all of the options */
            System.err.println("\n Example: java NQueens"+parser.printExample(ALL));
            System.exit(1);
        }
        
        try
        {
        	String resultantPath = "/" + numQueens + "_q" + "/";
        	if (mutation == null)
        	{
        		resultantPath += "variable/";
        	}
        	else
        	{
        		resultantPath += mutation.toString() + "/";
        	}
        	
        	outputDir += resultantPath;
        	
        	File dir = new File(outputDir);
        	File figureDir = new File(outputDir + "/figures/");
        	/* 
        	 * Returns true if all the directories are created
        	 * Returns false and the directories may have been made
        	 * (so far returns false every other time and it works every time)
        	 */        	
        	dir.mkdirs();
        	figureDir.mkdirs();
        }
        catch (Exception e)
        {
        	System.err.println(e.getMessage());
        	System.exit(1);
        }

        
        for (int count = 0; count < numRuns; ++count)
        {
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
    		while (solutions.size() < distinctSolutions[numQueens - 1] && numGenerations <= maxGenerations)
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
    				if (fitness.get(chromosome) == 1.0)
    				{
    				    if (uniqueSolution(chromosome))
    				    {
        					/* Save a copy of the chromosome */
        				    Chromosome solution = new Chromosome(new ArrayList<Integer>(chromosome.get()), chromosome.size());
        					solutions.add(solution);
        					solutionGeneration.put(solutions.size(), numGenerations);
        					
        					/* Perform three rotations then a reflection followed by three more rotations */
        					for (int i = 0; i < 6; ++i)
        					{
        						rotation = Transformation.rotate(solutions.get(solutions.size() - 1));
        						
        						if (uniqueSolution(rotation))
        						{ 
        							solutions.add(rotation);
        							solutionGeneration.put(solutions.size(), numGenerations);
        						}
                                else
                                {
                                    if (rotationMiss.containsKey(numGenerations))
                                    {
                                        rotationMiss.put(numGenerations, rotationMiss.get(numGenerations) + 1);
                                    }
                                    else
                                    {
                                        rotationMiss.put(numGenerations, 1);
                                    }
                                }
        					
        						if (i == 2)
        						{
                					reflection =  Transformation.reflect(solution);
                					
                                    if (uniqueSolution(reflection))
                                    {
                                        solutions.add(reflection);
                                        solutionGeneration.put(solutions.size(), numGenerations);
                                    }
                                    else
                                    {
                                        if (reflectionMiss.containsKey(numGenerations))
                                        {
                                            reflectionMiss.put(numGenerations, reflectionMiss.get(numGenerations) + 1);
                                        }
                                        else
                                        {
                                            reflectionMiss.put(numGenerations, 1);
                                        }
                                    }
        						}
        					}
        				}
        				else
        				{
                            if (duplicateBuffer.containsKey(numGenerations))
                            {
                                duplicateBuffer.put(numGenerations, duplicateBuffer.get(numGenerations) + 1);
                            }
                            else
                            {
                                duplicateBuffer.put(numGenerations, 1);
                            }
        				}
    				}
    			}
    						
    			/* Save average fitness for the current generation */
    			DescriptiveStatistics descStats = new DescriptiveStatistics(Doubles.toArray(fitness.values()));
    			fitnessBuffer.add(descStats.getMean());
    			
    			/* Save chromosome similarity and mutation rate for current generation */
    			similarityBuffer.add(curSimilarity);
    			
    			/* Save the variable mutation rate */
    			if (mutation == null)
                {
    			    mutationBuffer.add((Breed.MUTATION.upperEndpoint() - Breed.MUTATION.lowerEndpoint()) / 100.0);
                }
    			
    			/* Calculate statistics for the fitness, similarity, and mutation buffer every 1000, generations */
    			if ((numGenerations % 1000) == 0)
    			{
    			    calcStatistics(1000);
    			}
    			
                /* Write the current results to file every 10,000 generations */
                if ((numGenerations % 10000) == 0)
                {
                    writeResults();
                }
    			
    			/* Set the current population as the NEXT population */
    			fitness.clear();
    			population = nextPopulation;			
    			
    			++numGenerations;			
    		}
    		
    		
            /* Calculate statistics and write any remaining results */
    		if (fitnessBuffer.size() > 0)
    		{
    		    calcStatistics(1000);
    		}
    	    writeResults();
    		
    		
    		/* Display random solutions for the number of solutions specified */
    		for (int j = 0; j < numDisplay; ++j)
    		{
    		    /* Display a random solution */
    		    Chromosome solution = solutions.get(random.nextInt(solutions.size()));
    		    
    			try
    			{
    			    QueenGame myGame = new QueenGame (new QueenBoard(Ints.toArray(solution.get()), numQueens));
    				myGame.playGame(outputDir + "/figures/" + "figure_run_" + String.valueOf(runNumber) + "_" + j + ".png");
    			}
    			catch (Exception e)
    			{
    				System.out.println("Bad set of Queens");
    			}
    		}
    		
    		
    		/* Reset the current state for the next run and increment run number */
    		reset();
    		++runNumber;
        }
	}
}