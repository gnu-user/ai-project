import java.util.ArrayList;
import java.util.Random;

import com.google.common.collect.Range;


public class Vector {
	private ArrayList<Double> input;
	private Double fitness;
	private Range<Double> bounds;

	public Vector(Random random, Double fitness, Range<Double> bounds)
	{
		this.input = new ArrayList<Double>(ControlVariables.DIMENSIONS);
		this.fitness = fitness;
		this.bounds = bounds;
				
		while(input.size() < ControlVariables.DIMENSIONS)
		{
			this.input.add(((bounds.upperEndpoint() - bounds.lowerEndpoint()) * 
					random.nextDouble()) + bounds.lowerEndpoint());
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
