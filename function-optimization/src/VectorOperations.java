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
import java.util.Random;


public abstract class VectorOperations
{
	public static Vector mutation(Vector positiveV, Vector negativeV, Vector positiveV2)
	{
		Vector noisyVector = new Vector(positiveV.getBounds());
		for(int i = 0; i < ControlVariables.DIMENSIONS; i++)
		{
			// Catch out of bounds exception and generate new one.
			Double d = positiveV2.get(i) + 
					ControlVariables.MUTATION_RATE * (positiveV.get(i) - negativeV.get(i));
			//System.out.println(positiveV2.get(i) + " + " + ControlVariables.MUTATION_RATE + " * (" + (positiveV.get(i) - negativeV.get(i)) + ") = " + d);
			
			noisyVector.addParameter(d);
		}
		return noisyVector;
	}
		
	public static Vector crossover(Vector targetVector, Vector noisyVector, Random random)
	{
		Vector trialVector = new Vector(targetVector.getBounds());
		
		for(int i = 0; i < ControlVariables.DIMENSIONS; i++)
		{
			if(random.nextDouble() <= ControlVariables.CROSSOVER_RATE)
			{
				trialVector.addParameter(noisyVector.get(i));
			}
			else
			{
				trialVector.addParameter(targetVector.get(i));
			}				
		}
		
		// TODO calculate fitness
		return trialVector;
	}
	
	public static Vector selection(Vector target, Vector trial)
	{
		if(target.getFitness() <= trial.getFitness())
		{
			return target;
		}
		else
		{
			return trial;
		}
	}
}
