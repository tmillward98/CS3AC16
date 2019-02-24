package mapreduce;

import javafx.util.Pair;

public class Mapper {

	public Pair<String, Dataset> map(String key, Dataset a){
		Pair<String, Dataset> result = new Pair<>(key, a);
		return result;
	}

}
