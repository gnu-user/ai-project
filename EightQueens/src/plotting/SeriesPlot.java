/**
 * Artificial Intelligence Project -- Eight Queens Puzzle
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
package plotting;

import java.awt.BasicStroke;
import java.util.ArrayList;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;

public class SeriesPlot extends Panel
{
	private static final long serialVersionUID = 42L;
	private String title;
	private String XAxisLabel;
	private String YAxisLabel;
	
	
	/**
	 * Create an instance of a simple time series plot.
	 * 
	 * @param title The title of the plot
	 * @param XAxisLabel The label for the X axis
	 * @param YAxisLabel The label for the Y axis
	 */
	public SeriesPlot(String title, String XAxisLabel, String YAxisLabel)
	{
		this.title = title;
		this.XAxisLabel = XAxisLabel;
		this.YAxisLabel = YAxisLabel;
	}
	
	/**
	 * Plots the time series data provided and displays the plot
	 * 
	 * @param data The time series data to plot on the Y axis
	 */
	@SuppressWarnings("unchecked")
	public void plot(ArrayList<Double> data)
	{
		/* Add the data provided to the data table series */
		DataTable dataTable = new DataTable(Integer.class, Double.class);
		
		for (Integer i = 0; i < data.size(); ++i)
		{
			dataTable.add(i, data.get(i));
		}
		
		/* Create a time series plot */
		XYPlot seriesPlot = new XYPlot(dataTable);
		
		/* Configure the line for the plot */
		LineRenderer line = new DefaultLineRenderer2D();
		
		/* Configure the line colour, thickness, spacing between points */
		line.setSetting(LineRenderer.COLOR, BLUE);
		line.setSetting(LineRenderer.STROKE, new BasicStroke(1f));
		line.setSetting(LineRenderer.GAP, 0.0);
		line.setSetting(LineRenderer.GAP_ROUNDED, true);
		
		seriesPlot.setLineRenderer(dataTable, line);
		
		
		/* Configure the points for the plot */
		PointRenderer points = seriesPlot.getPointRenderer(dataTable);
		points.setSetting(PointRenderer.COLOR, BLUE);
		points.setSetting(PointRenderer.SHAPE, null);
		
		/* Specify the spacing for the plot */
		seriesPlot.setInsets(new Insets2D.Double(20.0, 60.0, 60.0, 20.0));
		
		/* Set the title and label the axes */
		seriesPlot.setSetting(BarPlot.TITLE, title);
		seriesPlot.getAxisRenderer(XYPlot.AXIS_X).setSetting(AxisRenderer.LABEL, XAxisLabel);
		seriesPlot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(AxisRenderer.LABEL, YAxisLabel);
		
		
		/* Display the plot */
		InteractivePanel panel = new InteractivePanel(seriesPlot);
		add(panel);
		displayPlot();
	}
	
	
	/**
	 * Plots two time series datasets on the same plots
	 * 
	 * @param data1 The first time series dataset to plot on the Y axis
	 * @param data2 The second time series dataset to plot on the Y axis
	 */
	public void plot(ArrayList<Double> data1, ArrayList<Double> data2)
	{
		/* Add the data provided to the data table series */
		DataTable dataTable1 = new DataTable(Integer.class, Double.class);
		DataTable dataTable2 = new DataTable(Integer.class, Double.class);
		
		for (Integer i = 0; i < data1.size(); ++i)
		{
			dataTable1.add(i, data1.get(i));
		}
		for (Integer i = 0; i < data2.size(); ++i)
		{
			dataTable2.add(i, data2.get(i));
		}
		
		
		/* Create a time series plot of the two datasets */
		XYPlot seriesPlot = new XYPlot(dataTable1, dataTable2);
		
		/* Configure the line for the plot */
		LineRenderer line1 = new DefaultLineRenderer2D();
		LineRenderer line2 = new DefaultLineRenderer2D();
		
		/* Configure the line colour, thickness, spacing between points */
		line1.setSetting(LineRenderer.COLOR, BLUE);
		line2.setSetting(LineRenderer.COLOR, RED);
		line1.setSetting(LineRenderer.STROKE, new BasicStroke(1f));
		line2.setSetting(LineRenderer.STROKE, new BasicStroke(1f));
		line1.setSetting(LineRenderer.GAP, 0.0);
		line2.setSetting(LineRenderer.GAP, 0.0);
		line1.setSetting(LineRenderer.GAP_ROUNDED, true);
		line2.setSetting(LineRenderer.GAP_ROUNDED, true);
		
		seriesPlot.setLineRenderer(dataTable1, line1);
		seriesPlot.setLineRenderer(dataTable2, line2);
		
		
		/* Configure the points for the plot */
		PointRenderer points = seriesPlot.getPointRenderer(dataTable1);
		PointRenderer points2 = seriesPlot.getPointRenderer(dataTable2);
		points.setSetting(PointRenderer.COLOR, BLUE);
		points2.setSetting(PointRenderer.COLOR, RED);
		points.setSetting(PointRenderer.SHAPE, null);
		points2.setSetting(PointRenderer.SHAPE, null);
		
		/* Specify the spacing for the plot */
		seriesPlot.setInsets(new Insets2D.Double(20.0, 60.0, 60.0, 20.0));
		
		/* Set the title and label the axes */
		seriesPlot.setSetting(BarPlot.TITLE, title);
		seriesPlot.getAxisRenderer(XYPlot.AXIS_X).setSetting(AxisRenderer.LABEL, XAxisLabel);
		seriesPlot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(AxisRenderer.LABEL, YAxisLabel);
		
		
		/* Display the plot */
		InteractivePanel panel = new InteractivePanel(seriesPlot);
		add(panel);
		displayPlot();
	}
	
	@Override
	public String getTitle()
	{
		return title;
	}
}