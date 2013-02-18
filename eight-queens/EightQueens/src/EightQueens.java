/*
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

import java.util.ArrayList;
import java.util.Random;

public class EightQueens
{
	private static final Integer POPULATION = 64;
	
	public static void main(String[] args)
	{
		/* Create an array of uniformly random chromosomes for initial population */
		ArrayList<Chromosome> initPopulation =  new ArrayList<Chromosome>(POPULATION);
		
		while (initPopulation.size() < POPULATION)
		{
			initPopulation.add(new Chromosome(new Random()));
		}
		
		for (Chromosome chromosome : initPopulation)
		{
			System.out.println(chromosome.get().toString());
		}
	}
}
