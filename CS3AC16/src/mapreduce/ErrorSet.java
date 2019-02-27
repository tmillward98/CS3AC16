package mapreduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorSet {
	
	private ArrayList<String> PassengerID;
	private ArrayList<String> FlightID;
	private ArrayList<String> fromAirport;
	private ArrayList<String> toAirport;
	private ArrayList<String> departureTime;
	private ArrayList<String> totalTime;
	
	Pattern f1 = Pattern.compile("[A-Z][A-Z][A-Z]\\d\\d\\d\\d[A-Z][A-Z]\\d");
	Pattern f2 = Pattern.compile("[A-Z][A-Z][A-Z]\\d\\d\\d\\d[A-Z]");
	Pattern f3 = Pattern.compile("[A-Z][A-Z][A-Z]");
	

	ErrorSet(File file) {
		
		PassengerID = new ArrayList<String>();
		FlightID = new ArrayList<String>();
		fromAirport = new ArrayList<String>();
		toAirport = new ArrayList<String>();
		departureTime = new ArrayList<String>();
		totalTime = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			String[] values;

			while ((line = reader.readLine()) != null) {
				values = line.split(",");
				if (PassengerID == null) {
					PassengerID.add(values[0]);
					FlightID.add(values[1]);
					fromAirport.add(values[2]);
					toAirport.add(values[3]);
					departureTime.add(values[4]);
					totalTime.add(values[5]);
				} else {
					generateUniques(values);

				}

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Long function Check to see if value is unique to all over values If unique,
	 * add value
	 * 
	 * @param values
	 */
	void generateUniques(String[] values) {
		
		  //If the function reaches the end without breaking, add the value 
		for (int i= 0; i < PassengerID.size(); i++) { 
			if(values[0] == PassengerID.get(i)) {
				break; 
				} 
			else if (i == PassengerID.size() - 1) { 
				PassengerID.add(values[0]);
				} 
			}
		  
		  //Rinse and repeat for each fields 
		for (int i = 0; i < FlightID.size(); i++) { 
			if(values[1] == FlightID.get(i)) { 
				break; 
				} 
			else if(i == FlightID.size() -1) { 
				FlightID.add(values[1]); 
				} 
			}
		  
		  for (int i = 0; i < fromAirport.size(); i++) { 
			  if(values[2] ==fromAirport.get(i)) { 
				  break; 
			  } 
			  else if(i == fromAirport.size() - 1) {
				  fromAirport.add(values[2]); 
				  } 
			  }
		  
		  for(int i = 0; i < toAirport.size(); i++) {
			  if(values[3] == toAirport.get(i)) {
				  break;
			  }
			  else if (i == toAirport.size() - 1) {
				  toAirport.add(values[3]);
			  }
		  }
		  
		  for(int i = 0; i < departureTime.size(); i++) {
			  if(values[4] == departureTime.get(i)) {
				  break;
			  }
			  else if(i == departureTime.size() - 1) {
				  departureTime.add(values[4]);
			  }
		  }
		  
		  for(int i = 0; i < totalTime.size(); i++) {
			  if(values[5] == totalTime.get(i)) {
				  break;
			  }
			  else if (i == totalTime.size() - 1) {
				  totalTime.add(values[5]);
			  }
		  }
	}
	
	public boolean compareUniques(String[] values) {
		
		for(int i = 0; i < PassengerID.size(); i++) {
			
		}
		
		return true;
	}
	
	public boolean regexCompare(String[] values) {
		
		//Fits format XXXnnnnXXn
		Matcher m = f1.matcher(values[0]);
		if(!m.matches()) {
			System.out.println("Incorrect syntax: " + values[0]);
			return false;
		}
		
		//Fits format XXXnnnnX
		m = f2.matcher(values[1]);
		if(!m.matches()) {
			System.out.println("Incorrect syntax: " + values[1]);
			return false;
		}
		
		//Reusable for 2 and 3
		m = f3.matcher(values[2]);
		if(!m.matches()) {
			System.out.println("Incorrect syntax: " + values[2]);
			return false;
		}
		
		m = f3.matcher(values[3]);
		if(!m.matches()) {
			System.out.println("Incorrect syntax: " + values[3]);
			return false;
		}
		
		return true;
	}
}

