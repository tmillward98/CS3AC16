package mapreduce;

import java.io.File;
import java.util.ArrayList;

public class Dataset {

	
	public String passengerID;
	public String flightID;
	public String fromAirport;
	public String toAirport;
	public String depTime;
	public int flightTime;
	
	
	
	Dataset(String[] values){
		passengerID = values[0];
		flightID = values[1];
		fromAirport = values[2];
		toAirport = values[3];
		depTime = values[4];
		flightTime = Integer.parseInt(values[5]);
	}
	
	/**
	 * Each field has a unique set of requirements that it must adhere to
	 * Given the values do not adhere to these standards, return false
	 * Else return true
	 * @param values	- Set of values to check
	 * @return
	 */
	public boolean CheeckForErrors(String[] values) {
		return true;
	}
	
	public ArrayList<Dataset> uniqueValues(ArrayList<Dataset> a) {
		
		
		
		ArrayList<Dataset> b = new ArrayList<Dataset>();
		return b;
	}
	
	
}
