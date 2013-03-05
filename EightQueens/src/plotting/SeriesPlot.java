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
	
	@Override
	public String getTitle()
	{
		return title;
	}
}