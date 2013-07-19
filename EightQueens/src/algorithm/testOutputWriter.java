package algorithm;

import java.util.ArrayList;
import java.util.Hashtable;

public class testOutputWriter {
	
	public static void main (String[] args)
	{
		OutputWriter ow = new OutputWriter("/home/joseph/test/");
		
		Hashtable<Integer, Integer> ht = new Hashtable<Integer, Integer>();
		ht.put(1, 1324);
		ArrayList<String> al = new ArrayList<String>();
		al.add("solution");
		al.add("generation");
		
		
		System.out.println(ow.save_solutions(ht, al, "test.csv"));
		
		Hashtable<Integer, ArrayList<Integer>> ot = new Hashtable<Integer, ArrayList<Integer>>();
		
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
		
		
		
		System.out.println(ow.saveSolutions(ot, co, "test2.csv"));
	}

}
