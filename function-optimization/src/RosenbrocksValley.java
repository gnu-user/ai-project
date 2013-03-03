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
import com.google.common.collect.Range;
import java.lang.Math;


/**
 * Calculates the fitness of Rosenbrock's Valley benchmark function. This function
 * is known as the Bannana function, it is a non-convex unimodal classic optimization
 * problem that is very challenging for many optimizers.
 */
public class RosenbrocksValley implements FitnessFunction
{
	/* The bounds of Rosenbrock's Valley */
	private static final Range<Double> BOUNDS = Range.closed(-2.0, 2.0);
	
	/* The number of function calls, includes the sum of all function calls
	 * for all instances of the object
	 */
	private static Integer NFC = 0;
	
	
	public RosenbrocksValley()
	{
	}

	/**
	 * Returns the bounds for Rosenbrock's Valley, which is, [-2, 2].
	 * 
	 * @return Range The bounds of Rosenbrock's Valley
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
	 * Evaluates the fitness of Rosenbrock's Valley benchmark function, which is known 
	 * as the Bannana function, it is a non-convex unimodal classic optimization
	 * problem that is very challenging for many optimizers.
	 * 
	 * @return The fitness value
	 * @throws IllegalArgumentException if one of the parameters in the vector
	 * is outside the bounds of Rosenbrock's Valley.
	 */
	@Override
	public Double evaluate(Vector vector)
	{
		Double fitness = 0.0;
		
		/* First check that the parameters in the vector are within the bounds */
		for (Double parameter : vector.get())
		{
			if (! BOUNDS.contains(parameter))
			{
				throw new IllegalArgumentException("A vector parameter was outside the bounds of the function.");
			}
		}
		
		/* Compute the fitness function for Rosenbrock's Valley:
		 * 
		 * f(X) = sigma,n-1 [100(Xi+1 - Xi^2)^2 + (1 - Xi)^2]
		 * 
		 */
		for (int i = 0; i < vector.size() - 1; ++i)
		{
			/* Calculate the fitness */
			fitness +=   100 * Math.pow((vector.get(i + 1) - Math.pow(vector.get(i), 2.0)),  2.0)
					   + Math.pow((1 - vector.get(i)), 2.0);
		}
		
		++NFC;
		return fitness;
	}
}
