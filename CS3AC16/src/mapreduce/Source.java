package mapreduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.*;

public class Source {

	public static void main(String[] args) {
		
		//Timing how long the program takes to execute
		long startTime = System.currentTimeMillis();
		
		//MapReduce Objects declaration
		Mapper mapper = new Mapper();
		Reducer reducer = new Reducer();
		ErrorReport report = new ErrorReport();
		ErrorSet set = new ErrorSet(new File("AComp_Passenger_data_no_error.csv"));
		
		//Create file variables
		File file = new File("AComp_Passenger_data.csv");	
		ArrayList<Dataset> dataset1 = new ArrayList<Dataset>();

		//Read in the file
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line; String[] values;
			Dataset copy;
			
			//Line tracker
			int counter = 0;
			
			while ((line = reader.readLine()) != null) {
				values = line.split(",");
				
				for(int i = 0; i < values.length; i++) {	
					//If the file contains a 0, null, non-word character or contains a lower case character, omit the value
					if(values[i].equals("0")|| values[i].equals(null) || !values[i].matches("[A-Za-z0-9 ]*") || !values[i].equals(values[i].toUpperCase())) {
						report.addToReport("Inital Read: Invalid entry, passing line: " + counter);
						break;
					}
					if(!set.regexCompare(values)) {
						break;
					}
					else if (i == values.length - 1) {
						copy = new Dataset(values);
						dataset1.add(copy);
					}
				}
				counter++;
			}
			reader.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
		
		//Task 1 - No. of flights from each airport
		ArrayList<Pair<String, Dataset>> Mapped = new ArrayList<Pair<String, Dataset>>();
		
		for (int i = 0; i < dataset1.size(); i++) {
			Mapped.add(mapper.map(dataset1.get(i).fromAirport, dataset1.get(i)));
		}
		
		ArrayList<Pair<String, ArrayList<Dataset>>> results = new ArrayList<Pair<String, ArrayList<Dataset>>>();
		results = reducer.reduce(Mapped);
		
		//Calculate execution time
		long endTime = System.currentTimeMillis();
		Long totalTime = (endTime - startTime);
		report.addToReport("Executed in: " + Long.toString(totalTime) + "ms.");
		report.generateReport();	
		
		try {
			PrintWriter writer = new PrintWriter("Result1.txt");
			for (int i = 0; i < results.size(); i++) {
				writer.println(results.get(i).getKey() + ": " + results.get(i).getValue().size());
			}
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
