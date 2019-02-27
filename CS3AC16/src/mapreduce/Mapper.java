package mapreduce;

import javafx.util.Pair;

public class Mapper {

	/**
	 * Maps an object based on key, returning a Key-Value pair of (Key, Object)
	 * @param key	- Chosen key, specified parameter within the class (eg. FlightID)
	 * @param a		- The object
	 * @return
	 */
	public Pair<String, Dataset> map(String key, Dataset a){
		Pair<String, Dataset> result = new Pair<>(key, a);
		return result;
	}

}
