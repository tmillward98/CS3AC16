package mapreduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.util.Pair;

/**
 * Second dataset, contains the long/lat information for each airport
 * @author Tom
 *
 */
public class Dataset2 {

	
	String airportName;
	String airportCode;
	double latitude;
	double longitude;
	
	Dataset2(String[] values){
		airportName = values[0];
		airportCode = values[1];
		latitude = Double.parseDouble(values[2]);
		longitude = Double.parseDouble(values[3]);
		
	}
	
	public double getLat() {
		return latitude;
	}
	public double getLon() {
		return longitude;
	}
	public String getCode() {
		return airportCode;
	}
	
	
}
