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
 * Calculates the fitness of the De Jong benchmark function, an easy
 * unimodal, scalable, convex function.
 */
public class DeJong implements FitnessFunction
{
	/* The bounds of the De Jong benchmark function */
	private static Range<Double> BOUNDS = Range.closed(-5.12, 5.12);
	
	public DeJong()
	{
	}

	
	/**
	 * Returns the bounds for the De Jong benchmark function which is,
	 * [-5.12, 5.12].
	 * 
	 * @return Range The bounds of the De Jong benchmark function
	 */
	@Override
	public Range<Double> getBounds()
	{
		return BOUNDS;
	}

	/**
	 * Evaluates the fitness of the De Jong benchmark function, an easy
	 * unimodal, scalable, convex function.
	 * 
	 * @return The fitness value
	 * @throws IllegalArgumentException if one of the parameters in the vector
	 * is outside the bounds of the De Jong function.
	 */
	@Override
	public Double evaluate(Vector vector) throws IllegalArgumentException
	{
		Double fitness = 0.0;
	
		/* Compute the fitness function for De Jong:
		 * 
		 * f(X) = sigma (xi^2)
		 * 
		 */
		for (Double parameter : vector.get())
		{
			if (! BOUNDS.contains(parameter))
			{
				throw new IllegalArgumentException("A vector parameter was outside the bounds of the function.");
			}
			
			fitness += Math.pow(parameter, 2.0);
		}
	
		return fitness;
	}
}
