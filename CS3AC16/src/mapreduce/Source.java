package mapreduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import javafx.util.*;

public class Source {

	
	/**
	 * Bulk of the program
	 * All operations are done within this class
	 * And oh lord, is it messy.
	 */
	public static void main() {
		
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
			
			while ((line = reader.readLine()) != null) {
				values = line.split(",");
				if(set.regexCompare(values, report)) {
					if(set.compareValues(values)) {
						copy = new Dataset(values);
						dataset1.add(copy);
					}
				}
			}
			reader.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
		
		//Task 1 - No. of flights from each airport
		ArrayList<Pair<String, Dataset>> Mapped = new ArrayList<Pair<String, Dataset>>();
		
		//Map data based on key fromAiport
		for (int i = 0; i < dataset1.size(); i++) {
			Mapped.add(mapper.map(dataset1.get(i).fromAirport, dataset1.get(i)));
		}
		
		//Reduce data
		ArrayList<Pair<String, ArrayList<Dataset>>> results = new ArrayList<Pair<String, ArrayList<Dataset>>>();
		results = reducer.reduce(Mapped);
		
		//Calculate execution time, generate report
		long endTime = System.currentTimeMillis();
		Long totalTime = (endTime - startTime);
		report.addToReport("Executed in: " + Long.toString(totalTime) + "ms.");
		report.generateReport();	
		
		//Write result to file
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
		
		//Task 2 - Map by FlightID, reuse already fixed data 
		Mapped = new ArrayList<Pair<String, Dataset>>();
		
		//Map by FlightID
		for (int i = 0; i < dataset1.size(); i++) {
			Mapped.add(mapper.map(dataset1.get(i).flightID, dataset1.get(i)));
		}
		
		results = new ArrayList<Pair<String, ArrayList<Dataset>>>();
		results = reducer.reduce(Mapped);		
		//Write result to file
		try {
			PrintWriter writer = new PrintWriter("Result2.txt");
			for (int i = 0; i < results.size(); i++) {
				writer.println("*________________________Flight ID: " + results.get(i).getKey() + "________________________*");
				for(Dataset n : results.get(i).getValue()) {
					writer.println(n.passengerID + "\t" + n.fromAirport + "\t" + n.toAirport + "\t" + Date.from(Instant.ofEpochSecond(Integer.parseInt(n.depTime))) + "\t" + 
							Date.from(Instant.ofEpochSecond((Integer.parseInt(n.depTime) + (n.flightTime * 60)))) + "\t" + n.flightTime);
				}
			}
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		//Task 3 - Map by FlightID, calculate number of passengers on each flight (reuse previous results)
		try {
			PrintWriter writer = new PrintWriter("Result3.txt");
			writer.println("Flight ID - Number of Passengers");
			for(int i = 0; i < results.size(); i++) {
				writer.println(results.get(i).getKey() + " - " + results.get(i).getValue().size());
			}
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		//Task 4 - Map by passenger ID, find the corresponding To -> From in the second dataset, determine total dist
		Mapped = new ArrayList<Pair<String, Dataset>>();
		
		//Map by FlightID
		for (int i = 0; i < dataset1.size(); i++) {
			Mapped.add(mapper.map(dataset1.get(i).passengerID, dataset1.get(i)));
		}
		
		results = new ArrayList<Pair<String, ArrayList<Dataset>>>();
		results = reducer.reduce(Mapped);
		
		ArrayList<Dataset2> dataset2 = new ArrayList<Dataset2>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("Top30_airports_LatLong.csv"));
			String line;
			String[] values;
			Dataset2 copy;
			
			while ((line = reader.readLine()) != null) {
				values = line.split(",");
				copy = new Dataset2(values);
				dataset2.add(copy);
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
		//For each passenger, we must calculate the nauticals gained from each flight
		//This should be appended to a running total 
		//The final total should be used to generate a key-value pair, which is then stored
		//Lord help us all
		
		double lat1 = 0, lon1 = 0, lat2 = 0, lon2 = 0, nMiles;
		ArrayList<Pair<String, Double>> nResults = new ArrayList<Pair<String, Double>>();

		//For each passenger
		for(int i = 0; i < results.size(); i++) {
			
			nMiles = 0;
			
			//For each flight
			for (int j = 0; j < results.get(i).getValue().size(); j++) {
				
				//Loop to find dataset2's corresponding lat/long
				for(int x = 0; x < dataset2.size(); x++) {
					if(results.get(i).getValue().get(j).fromAirport.equals(dataset2.get(x).airportCode)) {
						lat1 = dataset2.get(x).getLat();
						lon1 = dataset2.get(x).getLon();
						break;
					}
				}
				for(int x = 0; x < dataset2.size(); x++) {
					if(results.get(i).getValue().get(j).toAirport.equals(dataset2.get(x).airportCode)) {
						lat2 = dataset2.get(x).getLat();
						lon2 = dataset2.get(x).getLon();
						break;
					}
				}
				nMiles += calculateDistance(lon1, lat1, lon2, lat2);

			}
			nResults.add(new Pair<>(results.get(i).getKey(), nMiles));
				
		}
		
		//Now that the miles for each passenger has been determined, the highest must be found
		//Then we can finally print the results and put this abomination to rest
		
		double hValue = 0;
		int hIndex = 0;
		
		for(int i = 0; i < nResults.size(); i++) {		
			if(nResults.get(i).getValue() > hValue) {
				hValue = nResults.get(i).getValue();
				hIndex = i;
			}
		}
		
		try {
			PrintWriter writer = new PrintWriter("Result4.txt");
			writer.println("Passenger ID - " + nResults.get(hIndex).getKey() + " had the highest number of miles, with " + hValue + ".");
			
			writer.println("___REMAINING RESULTS___");
			for(int i = 0; i < nResults.size(); i++) {
				if(i != hIndex) {
					writer.println("ID: " + nResults.get(i).getKey() + " Miles: " + nResults.get(i).getValue());
				}
			}
			
			
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}//End of main
	
	/**
	 * 
	 * @param lon1 	-Start lon
	 * @param lat1	-Start lat
	 * @param lon2	-End lon
	 * @param lat2	-End lat
	 * @return		-Distance between two points
	 */
	public static double calculateDistance(double lon1, double lat1, double lon2, double lat2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
		dist = Math.acos(dist);
		dist = Math.toDegrees(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 0.8684;
		return (dist);	
	}
	
}
