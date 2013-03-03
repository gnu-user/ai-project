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
 * Calculates the fitness of the axis parallel hyper-ellipsoid benchmark function, 
 * an easy, unimodal, scalable, convex function.
 */
public class HyperEllipsoid implements FitnessFunction
{
	/* The bounds of the axis parallel hyper-ellipsoid benchmark function */
	private static Range<Double> BOUNDS = Range.closed(-5.12, 5.12);
	
	public HyperEllipsoid()
	{
	}

	/**
	 * Returns the bounds for the axis parallel hyper-ellipsoid benchmark function
	 * which is, [-5.12, 5.12].
	 * 
	 * @return Range The bounds of the axis parallel hyper-ellipsoid benchmark function
	 */
	@Override
	public Range<Double> getBounds()
	{
		return BOUNDS;
	}

	/**
	 * Evaluates the fitness of the axis parallel hyper-ellipsoid benchmark function, 
	 * an easy, unimodal, scalable, convex function.
	 * 
	 * @return The fitness value
	 * @throws IllegalArgumentException if one of the parameters in the vector
	 * is outside the bounds of the axis parallel hyper-ellipsoid function.
	 */
	@Override
	public Double evaluate(Vector vector)
	{
		Double fitness = 0.0;
		Double parameter = 0.0;
		
		/* Compute the fitness function for axis parallel hyper-ellipsoid:
		 * 
		 * f(X) = sigma (i * xi^2)
		 * 
		 */
		for (int i = 0; i < vector.size(); ++i)
		{
			parameter = vector.get(i);
			
			if (! BOUNDS.contains(parameter))
			{
				throw new IllegalArgumentException("A vector parameter was outside the bounds of the function.");
			}
			
			/* Increment i by one, summation starts at i = 1 */
			fitness += (Double) ((i + 1) * Math.pow(parameter, 2.0));
		}
	
		return fitness;
	}
}
