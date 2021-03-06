/**
 * Artificial Intelligence Project -- Differential Evolution
 *
 * Copyright (C) 2013, Joseph Heron, Jonathan Gillett, and Daniel Smullen
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


public class Vector
{
	private ArrayList<Double> input;
	private Double fitness;
	private Range<Double> bounds;

	/**
	 * Constructor to create random parameters in the vector. The fitness value
	 * is set to infinite since it still needs to be calculated.
	 * 
	 * @param random The random number object.
	 * @param bounds The bounds of the vector.
	 */
	public Vector(Random random, Range<Double> bounds)
	{
		this.input = new ArrayList<Double>(ControlVariables.DIMENSIONS);
		
		// Set to infinity
		this.fitness = Double.MAX_VALUE;
		
		this.bounds = bounds;
				
		while(input.size() < ControlVariables.DIMENSIONS)
		{
			this.input.add(	new Double(((this.bounds.upperEndpoint() - this.bounds.lowerEndpoint()) * 
					random.nextDouble()) + this.bounds.lowerEndpoint()));
		}
	}
	
	/**
	 * Default constructor for a vector. Sets the fitness value to infinity.
	 * 
	 * @param bounds The bounds for the vector.
	 */
	public Vector(Range<Double> bounds)
	{
		this.input = new ArrayList<Double>(ControlVariables.DIMENSIONS);
		
		// Set to infinity
		this.fitness = Double.MAX_VALUE;
		
		this.bounds = bounds;
		
		
	}
	
	/**
	 * Accessor method for getting the fitness value.
	 * 
	 * @return The fitness value.
	 */
	public Double getFitness()
	{
		return this.fitness;
	}
	
	/**
	 * Mutator method for setting the fitness of the vector.
	 * 
	 * @param fitness The new fitness value of the vector.
	 * @throws IllegalArgumentException
	 */
	public void setFitness(Double fitness) throws IllegalArgumentException
	{
		this.fitness = fitness;
	}
	
	/**
	 * Accessor method for the list of parameters.
	 * 
	 * @return The list of parameters.
	 * @throws IndexOutOfBoundsException
	 */
	public ArrayList<Double> get() throws IndexOutOfBoundsException
	{
		return this.input;
	}
	
	/**
	 * Accessor method for the list of parameters.
	 * 
	 * @param index The index of the element desired.
	 * @return The element in the list of parameters at the given index.
	 * @throws IndexOutOfBoundsException
	 */
	public Double get(int index) throws IndexOutOfBoundsException
	{
		return this.input.get(index);
	}
	
	/**
	 * Sets the parameter to the given value, if this value is outside of the
	 * vector's defined bounds the parameter will equal the bound which it exceeded.
	 * 
	 * @param index The index of the parameter to set.
	 * @param parameter The new parameter to change the previous one to.
	 * @throws IllegalArgumentException
	 */
	public void setParameter(int index, Double parameter) throws IllegalArgumentException
	{
		this.input.set(index, parameter);
	}
	
	/**
	 * Get the size of the parameters list.
	 * 
	 * @return The size.
	 */
	public int size()
	{
		return this.input.size();
	}
	
	/**
	 * Get the bounds of the vector.
	 * 
	 * @return The bounds of the given vector.
	 */
	public Range<Double> getBounds()
	{
		return bounds;
	}
	
	/**
	 * Add a parameter to the end of the list, increasing the size by 1. If the
	 * parameter is outside the vector's defined bounds then it will be set to 
	 * the bound that it exceeded.
	 * 
	 * @param parameter The new parameter.
	 * @throws IllegalArgumentException
	 */
	public void addParameter(Double parameter) throws IllegalArgumentException
	{
		this.input.add(parameter);
	}
}
