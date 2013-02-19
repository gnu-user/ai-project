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
import java.util.List;
import java.util.Random;

import com.google.common.collect.Range;


/**
 * Represents a chromosome for the 8 queens puzzle, each gene represents the 
 * position (x) of each queen in one row is based on the order of the genes in
 * the chromosome and the corresponding y value of the queen is stored in the 
 * gene of the chromosome.
 * 
 * Rather than having each queen represented as x & y, the x value of the queen
 * is the order of the gene in the chromosome.
 * 
 * The following is an example of a chromose with the 8 queens represented as genes
 * of the chromosome.
 *
 *   	-----------------------------
 *   	| Q1 | Q2 | Q3 | ..... | Q8 |
 *   	-----------------------------
 *
 */
public class Chromosome
{
	private static final Integer NUMBER_GENES = 8;
	private ArrayList<Integer> chromosome;
	
	
	/**
	 * Validate the coordinates, throws and exception  if any of the coordinates
	 * is not [0, 7]
	 * 
	 * @throws IllegalArgumentException If any of the coordinates is not [0, 7]
	*/
	private void validateCoordinates(ArrayList<Integer> coordinates) throws IllegalArgumentException
	{		
		/* If any coordinate is not [0, 7] throw exception */
		for (Integer coordinate : coordinates)
		{
			if (! Range.closed(0, 7).contains(coordinate))
			{
				throw new IllegalArgumentException("Invalid coordinate value, must be less than 8");
			}
		}
	}
	
	
	/**
	 * Validate the length, throw an exception if the length is not 8
	 * 
	 * @throws IllegalArgumentException If an arraylist of coordinates that does
	 * not contain 8 elements is given, or if any of the coordinates is not [0, 7]
	*/
	private void validateLength(ArrayList<Integer> coordinates) 
		throws IllegalArgumentException
	{		
		/* Throw an exception if the length is not 8 */
		if (coordinates.size() != 8)
		{
			throw new IllegalArgumentException("Invalid coordinates size, must be 8 elements");
		}
	}	
	
	
	/**
	 * Copy constructor, which constructs a new chromosome given the array of coordinates
	 * (genes) provided.
	 * 
	 * @param coordinates The arraylist of vertical coordinates to construct 
	 * the chromosome with, the maximum size of the array is 8 and no value
	 * in the array can be greater than 7.
	 * 
	 * @throws IllegalArgumentException If an arraylist of coordinates that does
	 * not contain 8 elements is given, or if any of the coordinates is not [0, 7]
	 */
	public Chromosome(ArrayList<Integer> coordinates) 
		throws IllegalArgumentException
	{
		/* Throw an exception of length not 8 or any coordinates not [0, 7) */
		validateLength(coordinates);
		validateCoordinates(coordinates);
		
		this.chromosome = coordinates;
	}
	
	
	/**
	 * Construct a new chromosome with random initial values for each of the genes,
	 * use this constructor to create the initial population of chromosomes.
	 * 
	 * @param random A secure random number generator used to seed the generation of 
	 * a uniform random distribution of coordinates for the genes of the chromosome.
	 */
	public Chromosome(Random random)
	{	
		/* Generate random coordinates for the genes within [0, 7] range */
		chromosome = new ArrayList<Integer>(NUMBER_GENES);
		
		while (chromosome.size() < NUMBER_GENES)
		{
			chromosome.add(random.nextInt(NUMBER_GENES));
		}
	}
	
	
	/**
	 * Returns the number of genes that the chromosome has, useful for iterating
	 * through each of the genes.
	 */
	public Integer size()
	{
		return NUMBER_GENES;
	}
	
	
	/**
	 * Gets the chromosome coordinates (genes)
	 */
	public ArrayList<Integer> get()
	{
		return chromosome;
	}
	
	
	/**
	 * Sets the chromosome to the coordinates specified
	 * 
	 * @param The arraylist of vertical coordinates to set the 
	 * chromosome to, the maximum size of the array is 8 and no value
	 * in the array can be greater than 7.
	 */
	public void set(ArrayList<Integer> coordinates) 
		throws IllegalArgumentException
	{
		/* Throw an exception if length noth 8 or any coordinates not [0, 7) */
		validateLength(coordinates);
		validateCoordinates(coordinates);
		
		chromosome = coordinates;
	}
	
	
	/**
	 * Gets a gene from the chromosome at the index specified
	 * 
	 * @param index The index of the gene to get from the chromosome
	 * 
	 * @return A gene from the chromosome
	 * 
	 * @throws IndexOutOfBoundsException  If the index is out of bounds
	 */
	public Integer get(int index) 
		throws IndexOutOfBoundsException
	{
		return chromosome.get(index);
	}
	
	
	/**
	 * Sets the gene of the chromosome to the value specified
	 * 
	 * @param The arraylist of vertical coordinates to set the 
	 * chromosome to, the maximum size of the array is 8 and no value
	 * in the array can be greater than 7.
	 */
	public void set(int index, Integer gene) 
		throws IllegalArgumentException
	{
		validateCoordinates(new ArrayList<Integer>(Arrays.asList(gene)));
		
		chromosome.set(index, gene);
	}
	
	
	/**
	 * Gets a range of genes from the chromosome
	 * 
	 * @param start The starting index of the gene to get from the chromosome
	 * @param end The ending index of the gene to get from the chromosome
	 * 
	 * @return The specified genes from the chromosome
	 * 
	 * @TODO Why does subList use "end" as the actual length rather than
	 * ending index??
	 * 
	 * @throws IllegalArgumentException If the index is out of bounds
	 */
	public List<Integer> get(int start, int end) 
		throws IndexOutOfBoundsException
	{
		/* HACK: Add one to end because subList does not use end as ending index */
		return chromosome.subList(start, end + 1);
	}
	
	
	/**
	 * Sets a range of the genes in the chromosome to the genes specifed
	 * 
	 * @param start The starting index of the gene int the chromosome to set
	 * @param end The ending index of the genes in the chromosome to set, which
	 * 
	 * 
	 * @throws IllegalArgumentException If the index is out of bounds
	 */
	public void set(int start, int end, ArrayList<Integer> coordinates) 
		throws IndexOutOfBoundsException
	{
		for (int i = 0; start <= end; ++start, ++i)
		{
			chromosome.set(start, coordinates.get(i));
		}
	}
}