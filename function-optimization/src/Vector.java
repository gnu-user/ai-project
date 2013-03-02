import java.util.ArrayList;
import java.util.Random;


public class Vector {
	ArrayList<Double> input;
	Double fitness;

	public Vector(Random random, Double fitness)
	{
		this.input = new ArrayList<Double>(ControlVariables.DIMENSIONS);
		this.fitness = fitness;
		
		while(input.size() < ControlVariables.DIMENSIONS)
		{
			input.add(random.nextDouble());
		}
	}
}
