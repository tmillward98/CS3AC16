package mapreduce;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ErrorReport {

	String errorReport;
	int counter = 0;
	
	ArrayList<String> finalReport;
	
	ErrorReport(){
		finalReport = new ArrayList<String>();
		finalReport.add("*____ERROR LOG - Starting from line 0____*");
	}
	
	void addToReport(String toAdd) {
		finalReport.add("[" + counter + "]" + toAdd);
		counter++;
	}
	
	void generateReport() {
		try {
			PrintWriter writer = new PrintWriter("ErrorReport.txt");
			for(int i = 0; i < finalReport.size(); i++) {
				writer.println(finalReport.get(i));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}
