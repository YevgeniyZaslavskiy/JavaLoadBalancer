import java.util.ArrayList;

/*
 * Author: Andrew Lytle
 * May 15, 2013
 * 
 * Disclaimer: This application was created as an academic exercise, and is
 * 		not intended to be production stable or scalable past a few thousand connections.
 * 
 */

public class LoadBalance {
	
	public static ArrayList<ServerObject> servers = new ArrayList<ServerObject>();

	public static void main(String[] args) {
		// Read Config file, construct listener with given options
		// Static data includes server addresses
		
		// Hard code the servers in for now. TODO: Read serverlist file
		servers.add(new ServerObject(20081, "93.74.110.188", 4000, 1.00));

		
		
		
		// Construct listener and select port here
		LoadBalanceListener listener = new LoadBalanceListener(15100);
		
		// Pass in configuration options here
		listener.configure();
		
		Thread listenerThread = new Thread(listener);
		listenerThread.run();
		
		
		
		
			

	}

}
