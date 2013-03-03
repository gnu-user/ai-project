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
			this.input.add(	new Double(((this.bounds.upperEndpoint() - this.bounds.lowerEndpoint()) * 
					random.nextDouble()) + this.bounds.lowerEndpoint()));
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
			if(this.input.get(index) < this.bounds.lowerEndpoint())
			{
				this.input.set(index, this.bounds.lowerEndpoint());
			}
			else if(this.input.get(index) > this.bounds.upperEndpoint())
			{
				this.input.set(index, this.bounds.upperEndpoint());
			}
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
	
	//TODO document that if a vector's parameter is out of bounds it will be set to the bound.
	public void addParameter(Double parameter) throws IllegalArgumentException
	{
		if (bounds.contains(parameter))
		{
			this.input.add(parameter);
		}
		else
		{
			if(parameter < this.bounds.lowerEndpoint())
			{
				this.input.add(this.bounds.lowerEndpoint());
			}
			else if(parameter > this.bounds.upperEndpoint())
			{
				this.input.add(this.bounds.upperEndpoint());
			}
		}
	}
	
	/*public void setParameter(ArrayList<Double> parameter) throws IllegalArgumentException
	{
		this.input = parameter;
	}*/
}
