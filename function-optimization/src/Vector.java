import java.util.ArrayList;
import java.util.Random;

import com.google.common.collect.Range;


public class Vector {
	private ArrayList<Double> input;
	private Double fitness;
	private Range<Double> bounds;

	public Vector(Random random, Range<Double> bounds)
	{
		this.input = new ArrayList<Double>(ControlVariables.DIMENSIONS);
		
		// Set to infinity
		this.fitness = Double.MAX_VALUE;
		
		this.bounds = bounds;
				
		while(input.size() < ControlVariables.DIMENSIONS)
		{
			this.input.add(((this.bounds.upperEndpoint() - this.bounds.lowerEndpoint()) * 
					random.nextDouble()) + this.bounds.lowerEndpoint());
		}
	}
	
	public Vector(Range<Double> bounds)
	{
		this.input = new ArrayList<Double>(ControlVariables.DIMENSIONS);
		
		// Set to infinity
		this.fitness = Double.MAX_VALUE;
		
		this.bounds = bounds;
		
		
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
		if (bounds.contains(parameter))
		{
			this.input.set(index, parameter);
		}
		else
		{
			throw new IllegalArgumentException("A vector parameter was outside the bounds of the function.");
		}
	}
	
	public int size()
	{
		return this.input.size();
	}
	
	public Range<Double> getBounds()
	{
		return bounds;
	}
	
	public void addParameter(Double parameter) throws IllegalArgumentException
	{
		if (bounds.contains(parameter))
		{
			this.input.add(parameter);
		}
		else
		{
			throw new IllegalArgumentException("A vector parameter was outside the bounds of the function.");
		}
	}
	
	/*public void setParameter(ArrayList<Double> parameter) throws IllegalArgumentException
	{
		this.input = parameter;
	}*/
}
