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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Base panel class used by the plotting classes to plot various data.
 */
public abstract class Panel extends JPanel
{
	private static final long serialVersionUID = 42L;
	
	protected static final Color BLUE = new Color(55, 170, 200);
	protected static final Color RED = new Color(200, 80, 75);

	/**
	 * Sets the plot dimensions and background colour
	 */
	public Panel()
	{
		super(new BorderLayout());
		setPreferredSize(new Dimension(1000, 600));
		setBackground(Color.WHITE);
	}

	/**
	 * Gets the title of the plot set when the plot was created.
	 * 
	 * @return The title of the plot
	 */
	public abstract String getTitle();

	/**
	 * Display the plot within a JFrame
	 * 
	 * @return JFrame to display the plot in
	 */
	protected JFrame displayPlot()
	{
		JFrame frame = new JFrame(getTitle());
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(getPreferredSize());
		frame.setVisible(true);
		return frame;
	}
}