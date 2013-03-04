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
 * Calculates the fitness of the De Jong benchmark function, an easy
 * unimodal, scalable, convex function.
 */
public class DeJong implements FitnessFunction
{
	/* The bounds of the De Jong benchmark function */
	private static final Range<Double> BOUNDS = Range.closed(-5.12, 5.12);
	
	/* The name of the fitness function */
	private static final String NAME = "De Jong";
	
	/* The number of function calls, includes the sum of all function calls
	 * for all instances of the object
	 */
	private static Integer NFC = 0;
	
	
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
	 * Returns the name of the fitness function, this is useful for plotting
	 * the fitness results and saving the image as the name of the fitness function.
	 */
	@Override
	public String getName()
	{
		return NAME;
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
	 * Resets the NFC count, this is useful when running multiple new 
	 * instances of the DE algorithm and you wish to reset the NFC.
	 */
	@Override
	public void resetNFC()
	{
		NFC = 0;
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
			/*if (! BOUNDS.contains(parameter))
			{
				throw new IllegalArgumentException("A vector parameter was outside the bounds of the function.");
			}*/
			
			fitness += Math.pow(parameter, 2.0);
		}
	
		++NFC;
		return fitness;
	}
}
