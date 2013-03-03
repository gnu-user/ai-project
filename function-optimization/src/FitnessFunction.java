/**
 * Artificial Intelligence Project -- Differential Evolution
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
import com.google.common.collect.Range;


/**
 * Interface for the fitness functions applied to each of the five
 * benchmark functions that are evaluated
 */
public interface FitnessFunction
{
	/**
	 * Returns the bounds for the benchmark function that is being
	 * evaluated.
	 * 
	 * @return Range The bounds of the benchmark function
	 */
	public Range<Double> getBounds();
	
	/**
	 * Returns the number of function calls (NFC) this is a count of
	 * the number of times the fitness function has been called.
	 * 
	 * @return The number of function calls NFC
	 */
	public Integer getNFC();
	
	/**
	 * Evaluates the fitness of the benchmark function and returns
	 * the fitness value.
	 * 
	 * @param The vector parameter that the benchmark function is applied to
	 * @return The fitness value
	 */
	public Double evaluate(Vector vector);
}
