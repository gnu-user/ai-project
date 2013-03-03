import java.util.Random;


public abstract class VectorOperations {

	public static Vector mutation(Vector positiveV, Vector negativeV, Vector positiveV2)
	{
		Vector noisyVector = new Vector(positiveV.getBounds());
		for(int i = 0; i < ControlVariables.POPULATION_SIZE; i++)
		{
			// Catch out of bounds exception and generate new one.
			noisyVector.addParameter(positiveV2.get(i) + 
					ControlVariables.MUTATION_RATE * (positiveV.get(i) - negativeV.get(i)));
		}
		return noisyVector;
	}
		
	public static Vector crossover(Vector targetVector, Vector noisyVector, Random random)
	{
		Vector trialVector = new Vector(targetVector.getBounds());
		
		for(int i = 0; i < ControlVariables.POPULATION_SIZE; i++)
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
