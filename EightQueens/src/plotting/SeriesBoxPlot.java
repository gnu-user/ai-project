/**
 * Genetic Algorithm Research -- N Queens Puzzle
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
import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;

import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.BoxPlot;
import de.erichseifert.gral.plots.BoxPlot.BoxWhiskerRenderer;
import de.erichseifert.gral.plots.XYPlot.XYNavigationDirection;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.colors.LinearGradient;
import de.erichseifert.gral.plots.colors.ScaledContinuousColorMapper;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.DataUtils;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.util.Insets2D;


public class SeriesBoxPlot extends Panel
{
	private static final long serialVersionUID = 42L;
	private String title;
	private String YAxisLabel;
	
	
	/**
	 * Create an instance of a box plot.
	 * 
	 * @param title The title of the plot
	 * @param YAxisLabel The label for the Y axis
	 */
	public SeriesBoxPlot(String title, String YAxisLabel)
	{
		this.title = title;
		this.YAxisLabel = YAxisLabel;
	}
	
	/**
	 * Creates a box plot of the data
	 * 
	 * @param data The data to plot as a box plot
	 * @param label The label for the dataset
	 */
	@SuppressWarnings("unchecked")
	public void plot(ArrayList<Double> data, String label)
	{
		/* Add the data provided to the data table series */
		DataTable dataTable = new DataTable(Double.class);
		
		for (Double item : data)
		{
			dataTable.add(item);
		}
		
		
		/* Create a box plot */
		DataSource boxData = BoxPlot.createBoxData(dataTable);
		BoxPlot boxPlot = new BoxPlot(boxData);
		
		/* Format the axes */
		boxPlot.getAxisRenderer(BoxPlot.AXIS_X).setSetting(
                AxisRenderer.TICKS_CUSTOM, DataUtils.map(
                                new Double[] {1.0},
                                new String[] {label}
                )
        );

        /* Format the boxes */
        Stroke stroke = new BasicStroke(2f);
        ScaledContinuousColorMapper colors = new LinearGradient(GraphicsUtils.deriveBrighter(BLUE), Color.WHITE);
        colors.setRange(1.0, 3.0);
        boxPlot.getPointRenderer(boxData).setSetting(BoxWhiskerRenderer.WHISKER_STROKE, stroke);
        boxPlot.getPointRenderer(boxData).setSetting(BoxWhiskerRenderer.BOX_BORDER, stroke);
        boxPlot.getPointRenderer(boxData).setSetting(BoxWhiskerRenderer.BOX_BACKGROUND, colors);
        boxPlot.getPointRenderer(boxData).setSetting(BoxWhiskerRenderer.BOX_COLOR, BLUE);
        boxPlot.getPointRenderer(boxData).setSetting(BoxWhiskerRenderer.WHISKER_COLOR, BLUE);
        boxPlot.getPointRenderer(boxData).setSetting(BoxWhiskerRenderer.BAR_CENTER_COLOR, BLUE);

        boxPlot.getNavigator().setDirection(XYNavigationDirection.VERTICAL);		

        
        boxPlot.setSetting(PointRenderer.SHAPE, null);
		
		/* Specify the spacing for the plot */
        boxPlot.setInsets(new Insets2D.Double(20.0, 60.0, 60.0, 20.0));
		
		/* Set the title, legend, and label the axes */
        boxPlot.setSetting(BoxPlot.TITLE, title);
        boxPlot.getAxisRenderer(BoxPlot.AXIS_Y).setSetting(AxisRenderer.LABEL, YAxisLabel);
		
		
		/* Display the plot */
		InteractivePanel panel = new InteractivePanel(boxPlot);
		add(panel);
		displayPlot();
	}
	
	
	/**
	 * Plots two time series datasets on the same plots
	 * 
	 * @param data1 The first time series dataset to plot on the Y axis
	 * @param label1 The label for the first time series dataset
	 * @param data2 The second time series dataset to plot on the Y axis
	 * @param label2 The label for the second time series dataset
	 */
	@SuppressWarnings("unchecked")
	public void plot(ArrayList<Double> data1, String label1, ArrayList<Double> data2, String label2)
	{
		/* Add the data provided to the data table series */
		DataTable dataTable = new DataTable(Double.class, Double.class);
		
		for (Integer i = 0; i < data1.size(); ++i)
		{
			dataTable.add(data1.get(i), data2.get(i));
		}
		
		
		/* Create a box plot */
		DataSource boxData = BoxPlot.createBoxData(dataTable);
		BoxPlot boxPlot = new BoxPlot(boxData);
		
		/* Format the axes */
		boxPlot.getAxisRenderer(BoxPlot.AXIS_X).setSetting(
                AxisRenderer.TICKS_CUSTOM, DataUtils.map(
                                new Double[] {1.0, 2.0},
                                new String[] {label1, label2}
                )
        );

        /* Format the boxes */
        Stroke stroke = new BasicStroke(2f);
        ScaledContinuousColorMapper colors = new LinearGradient(GraphicsUtils.deriveBrighter(BLUE), Color.WHITE);
        colors.setRange(1.0, 3.0);
        boxPlot.getPointRenderer(boxData).setSetting(BoxWhiskerRenderer.WHISKER_STROKE, stroke);
        boxPlot.getPointRenderer(boxData).setSetting(BoxWhiskerRenderer.BOX_BORDER, stroke);
        boxPlot.getPointRenderer(boxData).setSetting(BoxWhiskerRenderer.BOX_BACKGROUND, colors);
        boxPlot.getPointRenderer(boxData).setSetting(BoxWhiskerRenderer.BOX_COLOR, BLUE);
        boxPlot.getPointRenderer(boxData).setSetting(BoxWhiskerRenderer.WHISKER_COLOR, BLUE);
        boxPlot.getPointRenderer(boxData).setSetting(BoxWhiskerRenderer.BAR_CENTER_COLOR, BLUE);

        boxPlot.getNavigator().setDirection(XYNavigationDirection.VERTICAL);		

        
        boxPlot.setSetting(PointRenderer.SHAPE, null);
		
		/* Specify the spacing for the plot */
        boxPlot.setInsets(new Insets2D.Double(20.0, 60.0, 60.0, 20.0));
		
		/* Set the title, legend, and label the axes */
        boxPlot.setSetting(BoxPlot.TITLE, title);
        boxPlot.getAxisRenderer(BoxPlot.AXIS_Y).setSetting(AxisRenderer.LABEL, YAxisLabel);
		
		
		/* Display the plot */
		InteractivePanel panel = new InteractivePanel(boxPlot);
		add(panel);
		displayPlot();
	}
	
	@Override
	public String getTitle()
	{
		return title;
	}
}