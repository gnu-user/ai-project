/*
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
	 * Constructs a new chromosome given the array of integers provided.
	 * 
	 * @param The arraylist of vertical coordinates to construct 
	 * the chromosome with, the maximum size of the array is 8 and no value
	 * in the array can be greater than 7.
	 * 
	 * @throws IllegalArgumentException If an arraylist of coordinates that does
	 * not contain 8 elements is given, or if any of the coordinates is not [0, 7]
	 */
	public Chromosome(ArrayList<Integer> coordinates) throws IllegalArgumentException
	{
		/* If the length is not 8 throw an exception */
		if (coordinates.size() != 8)
		{
			throw new IllegalArgumentException("Invalid coordinates size, must be 8 elements");
		}
		
		/* If any coordinate is not [0, 7] throw exception */
		for (Integer coordinate : coordinates)
		{
			if (! Range.closed(0, 7).contains(coordinate))
			{
				throw new IllegalArgumentException("Invalid coordinate value, must be less than 8");
			}
		}
		
		this.chromosome = coordinates;
	}
	
	
	/**
	 * Construct a new chromosome with random initial values for each of the genes,
	 * use this constructor to create the initial population of chromosomes.
	 * 
	 * @param A secure random number generator used to seed the generation of 
	 * a uniform random distribution of coordinates for the genes of the chromosome.
	 */
	public Chromosome(Random random)
	{	
		/* Generate random coordinates for the genes within [0, 7] range */
		chromosome = new ArrayList<Integer>(NUMBER_GENES);
		
		while (chromosome.size() < 8)
		{
			chromosome.add(random.nextInt(8));
		}
	}
	
	
	/**
	 * Gets the chromosome
	 */
	public ArrayList<Integer> get()
	{
		return chromosome;
	}
	
	/**
	 * Gets either an index or a range of genes from the chromosome
	 * 
	 * @param The index of the gene to get from the chromosome
	 * 
	 * @throws IndexOutOfBoundsException  If the index is out of bounds
	 */
	public Integer get(int index) throws IndexOutOfBoundsException
	{
		return chromosome.get(index);
	}
	
	
	/**
	 * Gets either an index or a range of genes from the chromosome
	 * 
	 * @param The index of the gene to get from the chromosome
	 * 
	 * @throws IllegalArgumentException If the index is out of bounds
	 */
	public ArrayList<Integer> get(int start, int end) throws IndexOutOfBoundsException
	{
		return (ArrayList<Integer>) chromosome.subList(start, end);
	}
}