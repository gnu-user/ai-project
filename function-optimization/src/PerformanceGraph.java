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
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;

/**
 * Plots the performance graph of the best fitness value so far versus the
 * number of function calls.
 */
public abstract class PerformanceGraph
{
	/**
	 * Plots the performance graph of the best fitness value so far versus the
	 * number of function calls (NFC).
	 * 
	 * @param bestFitness A linked hashmap mapping the NFC to the best fitness value
	 * found so far.
	 * @param fitnessFunction The name of the fitness function, used for the title and the
	 * name of the file that is saved, e.g. "De Jong".
	 */
	public static void plot(LinkedHashMap<Integer, Double> bestFitness, String fitnessFunction)
	{
		/* Create an XYSeries plot */
		XYSeries series = new XYSeries("Best Fitness Value Vs. Number of Function Calls");
		
		/* Add the NFC and best fitness value data to the series */
		for (Integer NFC : bestFitness.keySet())
		{	
			/* Jfreechart crashes if double values are too large! */
			if (bestFitness.get(NFC) <= 10E12)
			{
				series.add(NFC.doubleValue(), bestFitness.get(NFC).doubleValue());
			}
		}
		
		/* Add the x,y series data to the dataset */
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		
		/* Plot the data as an X,Y line chart */
		JFreeChart chart = ChartFactory.createXYLineChart(
								"Best Fitness Value Vs. Number of Function Calls",
								"Number of Function Calls (NFC)", 
								"Best Fitness Value",
								dataset,
								PlotOrientation.VERTICAL,
								true,
								true,
								false
							);
		
		/* Configure the chart settings such as anti-aliasing, background colour */
		chart.setAntiAlias(true);
		
		XYPlot plot = chart.getXYPlot();
		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.black);
		plot.setDomainGridlinePaint(Color.black);
		
		
		/* Set the domain range from 0 to NFC */
		NumberAxis domain = (NumberAxis) plot.getDomainAxis();
		domain.setRange(0.0, ControlVariables.MAX_FUNCTION_CALLS.doubleValue());
		
		/* Logarithmic range axis */
		plot.setRangeAxis(new LogAxis());
		
		
		/* Set the thickness and colour of the lines */
		XYItemRenderer renderer = plot.getRenderer();
		BasicStroke thickLine = new BasicStroke(3.0f);
		renderer.setSeriesStroke(0, thickLine);
		renderer.setPaint(Color.BLACK);

		
		/* Display the plot in a JFrame */
		ChartFrame frame = new ChartFrame(fitnessFunction + " Best Fitness Value", chart);
		frame.setVisible(true);
		frame.setSize(1000, 600);
		
		/* Save the plot as an image named after fitness function */
		try
		{
			ChartUtilities.saveChartAsJPEG(new File("plots/" + fitnessFunction + ".jpg"), chart, 1000, 600);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
