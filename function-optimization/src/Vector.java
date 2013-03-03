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
	
	public ArrayList<Double> getParameters()
	{
		return this.input;
	}
	
	public Double getFitness()
	{
		return this.fitness;
	}
	
	public void setFitness(Double fitness) throws IllegalArgumentException
	{
		this.fitness = fitness;
	}
	
	public ArrayList<Double> get() throws IndexOutOfBoundsException
	{
		return this.input;
	}
	
	public Double get(int index) throws IndexOutOfBoundsException
	{
		return this.input.get(index);
	}
	
	public void setParameter(int index, Double parameter) throws IllegalArgumentException
	{
		this.input.set(index, parameter);
	}
	
	public void setParameter(ArrayList<Double> parameter) throws IllegalArgumentException
	{
		this.input = parameter;
	}
}
