package mapreduce;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

	private ExecutorService jobQueue;
	
	ThreadPool(int threads) {
		jobQueue = Executors.newFixedThreadPool(threads);
	}
	
	ThreadPool() {
		jobQueue = Executors.newFixedThreadPool(4);
	}
	
	
}
