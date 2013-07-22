package algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class testOutputWriter {
	
	public static void main (String[] args)
	{
		OutputWriter ow = new OutputWriter("/home/jon/test/");
		
		LinkedHashMap<Integer, Integer> ht = new LinkedHashMap<Integer, Integer>();
		ht.put(1, 1324);
		ArrayList<String> al = new ArrayList<String>();
		al.add("solution");
		al.add("generation");
		
      LinkedHashMap<Integer, Integer> ht2 = new LinkedHashMap<Integer, Integer>();
        ht2.put(999, 1);
		
		
		try
        {
            ow.saveResults(ht, al, "test.csv");
            ow.saveResults(ht2, al, "test.csv");
        }
        catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
		
		LinkedHashMap<Integer, ArrayList<Integer>> ot = new LinkedHashMap<Integer, ArrayList<Integer>>();
		
		ArrayList<Integer> ai1 = new ArrayList<Integer>();
		ai1.add(1);
		ai1.add(3242);
		ai1.add(109);
		
		ArrayList<Integer> ai2 = new ArrayList<Integer>();
		ai2.add(23);
		ai2.add(124);
		ai2.add(923);
		
		ot.put(1, ai1);
		ot.put(2, ai2);
		
		ArrayList<String> co = new ArrayList<String>();
		co.add("solution");
		co.add("second");
		co.add("third");
		co.add("fourth");
		
		
		
		try
        {
            ow.saveResultsMul(ot, co, "test2.csv");
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

}
