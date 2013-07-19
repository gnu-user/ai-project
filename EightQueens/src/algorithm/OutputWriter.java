package algorithm;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class OutputWriter {

	private String outputDir;
	
	public OutputWriter(String outputDir)
	{
		this.outputDir = outputDir;
	}

	/**
	 * @return the outputDir
	 */
	public String getOutputDir() {
		return outputDir;
	}

	/**
	 * @param outputDir the outputDir to set
	 */
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
	
	/**
	 * Save the solutions to the to the given file. When the output is only 2 columns.
	 * @param ht
	 * @param columns
	 * @param filename
	 * @return
	 */
	public boolean save_solutions(Hashtable<Integer, Integer> ht, ArrayList<String> columns, String filename)
	{
		try {
			FileWriter fileWriter = new FileWriter(outputDir + filename);
			
			writeRow(fileWriter, columns);
			
			Enumeration<Integer> iterator = ht.keys();
			while(iterator.hasMoreElements())
			{
				Integer element = iterator.nextElement();
				ArrayList<String> list = new ArrayList<String>();
				list.add(String.valueOf(element));
				list.add(String.valueOf(ht.get(element)));
				
				writeRow(fileWriter, list);
			}
			
			fileWriter.flush();
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}	
	
	public void writeRow(FileWriter writer, ArrayList<String> columns) throws IOException
	{
		for (int i = 0; i < columns.size(); i++)
		{
			writer.append(columns.get(i));
			
			/* Add a comma after each element and a new line at the end of the row */
			if (i < (columns.size() - 1))
			{
				writer.append(",");
			}
			else
			{
				writer.append("\n");
			}
		}
		
	}
	
	/**
	 * More abstract version of save_solution which should work for files containing 2 or more columns. 
	 * @param ht <Integer, ArrayList<Integer>> run number -> list of columns involved
	 * @param columns
	 * @param filename
	 * @return
	 */
	public boolean saveSolutions(Hashtable<Integer, ArrayList<Integer>> ht, ArrayList<String> columns, String filename)
	{
		try {
			FileWriter fileWriter = new FileWriter(outputDir + filename);
			
			writeRow(fileWriter, columns);
			
			/* Get all of the keys */ 
			Enumeration<Integer> iterator = ht.keys();
			while(iterator.hasMoreElements())
			{
				/* Create the list of the elements in the array list */
				Integer element = iterator.nextElement();
				ArrayList<String> list = new ArrayList<String>();
				list.add(String.valueOf(element));
				
				/* Get the list of all the columns */ 
				Iterator<Integer> values = ht.get(element).iterator();
				while(values.hasNext())
				{
					list.add(String.valueOf(values.next()));
				}
				
				/* Write the columns to the file */ 
				writeRow(fileWriter, list);
			}
			
			fileWriter.flush();
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
