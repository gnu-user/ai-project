package algorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class OutputWriter
{
	private String outputDir;
	
	public OutputWriter(String outputDir)
	{
		this.outputDir = outputDir;
	}

	/**
	 * @return the outputDir
	 */
	public String getOutputDir()
	{
		return outputDir;
	}

	/**
	 * @param outputDir the outputDir to set
	 */
	public void setOutputDir(String outputDir)
	{
		this.outputDir = outputDir;
	}
	
	/**
	 * Save the results to the to the given file. When the output is only 2 columns.
	 * @param data
	 * @param columns
	 * @param filename
	 * @throws IOException 
	 */
	public <K, V> void saveResults(Map<K, V> data, ArrayList<String> columns, String filename) 
        throws IOException
	{
	    File file = new File(outputDir + filename);
	    FileWriter fileWriter;
	    
	    if (! file.exists())
        {
	        fileWriter = new FileWriter(outputDir + filename);
	        writeRow(fileWriter, columns);
        }
	    else
	    {
	        fileWriter = new FileWriter(outputDir + filename, true);
	    }

	    		    
	    /* Get all of the keys */ 
		for (Object key : data.keySet())
		{
		    ArrayList<String> list = new ArrayList<String>();
		    list.add(String.valueOf(key));
            list.add(String.valueOf(data.get(key)));
            
            writeRow(fileWriter, list);
		}
		
		fileWriter.flush();
		fileWriter.close();
	}	
	
	private void writeRow(FileWriter writer, ArrayList<String> columns)
        throws IOException
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
	 * More abstract version of saveResults which should work for files containing 2 or more columns. 
	 * @param data <Integer, ArrayList<Integer>> run number -> list of columns involved
	 * @param columns
	 * @param filename
	 * @throws IOException 
	 */
	public <K, E> void saveResultsMul(Map<K, ArrayList<E>> data, ArrayList<String> columns, String filename)
        throws IOException
	{
        File file = new File(outputDir + filename);
        FileWriter fileWriter;
        
        if (! file.exists())
        {
            fileWriter = new FileWriter(outputDir + filename);
            writeRow(fileWriter, columns);
        }
        else
        {
            fileWriter = new FileWriter(outputDir + filename, true);
        }
		
        
		/* Get all of the keys */ 
        for (Object key : data.keySet())
        {
            ArrayList<String> list = new ArrayList<String>();
            list.add(String.valueOf(key));
            
            /* Get the list of all the columns */ 
            for (Object value : data.get(key))
            {
                list.add(String.valueOf(value));
            }
            
            /* Write the columns to the file */ 
            writeRow(fileWriter, list);
        }
		
		fileWriter.flush();
		fileWriter.close();
	}
}
