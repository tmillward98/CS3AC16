package mapreduce;

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
	

	public void printSet() {
		System.out.println("pID: " + passengerID);
		System.out.println("FT:" + flightTime);
	}
		
	
	
}
