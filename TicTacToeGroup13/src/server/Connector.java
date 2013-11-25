package server;


public class Connector extends Thread {
	
	final private int NUMBER_OF_CLIENTS = 2;   
	
	
	public Connector() {}
	
	@Override
	public void run() {
		while (true) {
			if (Server.getArrayListSize() >= NUMBER_OF_CLIENTS) {
				
			}
		}		
	}
	
	
	
}
