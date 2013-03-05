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
import java.util.ArrayList;


/**
 * Provides basic statistical functionality such as calculating the mean
 * and standard deviation.
 */
public abstract class Statistics
{
	/**
	 * Calculates the mean of an array of values
	 * @param values An array of values
	 * @return The mean of the values
	 */
	public static Double mean(ArrayList<Double> values)
	{
		Double sum = 0.0;
		
		for (Double value : values)
		{
			sum += value;
		}
		
		return (Double) sum / values.size();
	}
	
	/**
	 * Calculates the standard deviation of an array of values
	 * @param values An array of values
	 * @return The standard deviation of the values
	 */
	public static Double std(ArrayList<Double> values)
	{
		Double sum = 0.0;
		Double mean = 0.0;
		Double variance = 0.0;
		
		/* Calculate the mean, u */
		for (Double value : values)
		{
			sum += value;
		}
		mean = (Double) sum / values.size();
		
		/* Calculate the standard deviation */
		for (Double value : values)
		{
			variance += Math.pow((value - mean), 2.0);
		}
		return Math.sqrt( (Double) variance / values.size() );
	}
}
