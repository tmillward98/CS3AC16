package mapreduce;

import java.util.ArrayList;

import javafx.util.Pair;

public class Reducer {

	/**
	 * Stores the key and remainder of the object
	 * Count can be determined by the size of the ArrayList
	 * @param toReduce
	 * @return
	 */
	public ArrayList<Pair<String, ArrayList<Dataset>>> reduce(ArrayList<Pair<String, Dataset>> toReduce){
		
		//Array to store results
		ArrayList<Pair<String, ArrayList<Dataset>>> results = new ArrayList<Pair<String, ArrayList<Dataset>>>();	

		//Add initial value to the ArrayList
		ArrayList<Dataset> a = new ArrayList<Dataset>();
		a.add(toReduce.get(0).getValue());
		Pair<String, ArrayList<Dataset>> b = new Pair<>(toReduce.get(0).getKey(), a);
		results.add(b);
		
		//Loop for each mapped value
		for(int i = 1; i < toReduce.size(); i++) {	
			//Check to see if key has already been mapped
			//If it has, add object to the ArrayList
			//Otherwise create new pair
			for(int j = 0; j <= results.size(); j++) {
					
				if (j == results.size()) {
					ArrayList<Dataset> x = new ArrayList<Dataset>();
					x.add(toReduce.get(i).getValue());
					Pair<String, ArrayList<Dataset>> y = new Pair<>(toReduce.get(i).getKey(), x);
					results.add(y);	
					break;
				}	
				else if(toReduce.get(i).getKey().equals(results.get(j).getKey())) {
					//System.out.println("Matching key: " + toReduce.get(i).getKey() + " - " + results.get(j).getKey());
					results.get(j).getValue().add(toReduce.get(i).getValue());
					break;
				}


			}	
		}
		return results;
	}
	
	
}
