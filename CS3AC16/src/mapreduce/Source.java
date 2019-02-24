package mapreduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.util.*;

public class Source {

	public static void main(String[] args) {
		
		File file = new File("E:\\University\\Year 3\\Final Year Project\\Workspace\\CS3AC16\\src\\mapreduce\\AComp_Passenger_data.csv");
		ArrayList<Dataset> dataset1 = new ArrayList<Dataset>();
		Mapper mapper = new Mapper();
		
		
		try {
			/**
			 * Read file in line by line
			 * If line entry contains null, or 0, skip
			 */
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line; String[] values;
			Dataset copy;
			
			while ((line = reader.readLine()) != null) {
				values = line.split(",");
				
				for(int i = 0; i < values.length; i++) {
					if(values[i] == "0" || values[i] == null) {
						System.out.println("Invalid Entry, passing");
					}
					else {
						copy = new Dataset(values);
						dataset1.add(copy);
						copy.printSet();
					}
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
		ArrayList<Pair<String, Dataset>> Mapped = new ArrayList<Pair<String, Dataset>>();
		
		for (int i = 0; i < dataset1.size(); i++) {
			Mapped.add(mapper.map(dataset1.get(i).flightID, dataset1.get(i)));
		}
		
		
	}

}
