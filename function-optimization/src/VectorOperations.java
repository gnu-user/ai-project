import java.util.Random;


public abstract class VectorOperations {

	/**
	 * Mutation operation, used to create the noisyVector. The equation will be: 
	 * postiveV2 + F * (positiveV - negativeV). 
	 * 
	 * Note that the fitness is not calculated in this operation.
	 * 
	 * @param positiveV Randomly chosen vector 1.
	 * @param negativeV Randomly chosen vector 2.
	 * @param positiveV2 Randomly chosen vector 3.
	 * @return The noisy vector to be used for crossover.
	 */
	public static Vector mutation(Vector positiveV, Vector negativeV, Vector positiveV2)
	{
		Vector noisyVector = new Vector(positiveV.getBounds());
		for(int i = 0; i < ControlVariables.DIMENSIONS; i++)
		{
			Double d = positiveV2.get(i) + 
					ControlVariables.MUTATION_RATE * (positiveV.get(i) - negativeV.get(i));
			
			noisyVector.addParameter(d);
		}
		return noisyVector;
	}
	
	/**
	 * Crossover operation, used to create the trial vector.
	 * Note the fitness is not calculated in this operation
	 * 
	 * @param targetVector The target vector, from the parent population.
	 * @param noisyVector The noisy vector, generated from 3 randomly selected mutually exclusive parents.
	 * @param random The random number object.
	 * @return The trial vector.
	 */
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
		
		return trialVector;
	}
	
	/**
	 * Selection operation, determines based on the fitness values which will
	 * pass on to the next generation.
	 * 
	 * @param target The target vector.
	 * @param trial The trial vector.
	 * @return The most fit vector.
	 */
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
