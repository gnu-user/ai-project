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
import java.lang.Math;


/**
 * Calculates the fitness of Schwefel's problem benchmark function, 
 * a unimodal and scalable function.
 */
public class Schwefel implements FitnessFunction
{
	/* The bounds of Schwefel's problem */
	private static final Range<Double> BOUNDS = Range.closed(-65.0, 65.0);
	
	/* The number of function calls, includes the sum of all function calls
	 * for all instances of the object
	 */
	private static Integer NFC = 0;
	
	
	public Schwefel()
	{
	}

	/**
	 * Returns the bounds for Schwefel's problem, which is, [-65, 65].
	 * 
	 * @return Range The bounds of Schwefel's problem
	 */
	@Override
	public Range<Double> getBounds()
	{
		return BOUNDS;
	}

	/**
	 * Returns the number of function calls (NFC) this is a count of
	 * the number of times the fitness function has been called.
	 * 
	 * @return The number of function calls NFC
	 */
	@Override
	public Integer getNFC()
	{
		return NFC;
	}
	
	/**
	 * Evaluates the fitness of Schwefel's problem benchmark function, 
	 * a unimodal and scalable function.
	 * 
	 * @return The fitness value
	 * @throws IllegalArgumentException if one of the parameters in the vector
	 * is outside the bounds of Schwefel's problem.
	 */
	@Override
	public Double evaluate(Vector vector)
	{
		Double fitness = 0.0;
		Double innerSum = 0.0;
		
		/* First check that the parameters in the vector are within the bounds */
		for (Double parameter : vector.get())
		{
			if (! BOUNDS.contains(parameter))
			{
				throw new IllegalArgumentException("A vector parameter was outside the bounds of the function.");
			}
		}
		
		/* Compute the fitness function for Schwefel's problem:
		 * 
		 * f(X) = sigma ((sigma xj)^2)
		 * 
		 */
		for (int i = 0; i < vector.size(); ++i)
		{
			for (int j = 0; j <= i; ++j)
			{
				innerSum += vector.get(j);
			}
			
			/* Calculate the outer summation, which is inner summation squared */
			fitness += Math.pow(innerSum, 2.0);
		}
	
		++NFC;
		return fitness;
	}
}
