/**
 * Artificial Intelligence Project -- Differential Evolution
 *
 * Copyright (C) 2013, Joseph Heron, Jonathan Gillett, and Daniel Smullen
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
public abstract class ControlVariables
{
	public static final Integer DIMENSIONS = 30;
	public static final Integer POPULATION_SIZE = 2*DIMENSIONS;
	public static final Double MUTATION_RATE = 0.5;
	public static final Double CROSSOVER_RATE = 0.9;
	public static final Integer MAX_FUNCTION_CALLS = 1000*DIMENSIONS;
}
