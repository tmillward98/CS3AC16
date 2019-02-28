package mapreduce;

import java.io.File;
import java.util.ArrayList;

/**
 * Used to house object for key-value pairs
 * @author Tom
 *
 */
public class Dataset {

	
	public String passengerID;
	public String flightID;
	public String fromAirport;
	public String toAirport;
	public String depTime;
	public int flightTime;
	
	
	/**
	 * Constructor
	 * @param values - Values to be placed inside the housed variables
	 */
	Dataset(String[] values){
		passengerID = values[0];
		flightID = values[1];
		fromAirport = values[2];
		toAirport = values[3];
		depTime = values[4];
		flightTime = Integer.parseInt(values[5]);
	}
	
	
}
