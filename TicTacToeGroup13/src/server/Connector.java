package server;

import java.io.IOException;
import java.net.Socket;
import java.io.DataOutputStream;

public class Connector extends Thread {
	
	final private int NUMBER_OF_CLIENTS = 2;
	PeerInformation client1 = new PeerInformation();
	PeerInformation client2 = new PeerInformation();
	
	public Connector() {
		System.out.println("In Connector");
	}
	
	@Override
	public void run() {
		while (true) {
			try {Thread.sleep(200);} catch (InterruptedException e1) {}
			
			if (Server.getArrayListSize() >= NUMBER_OF_CLIENTS) {
				System.out.println("Got two clients");
				try {
					connectToClients();
				} catch (IOException e) {
					System.out.println("Failed to connect clients");
					e.printStackTrace();
				}
			}
		}		
	}
	
	public void connectToClients() throws IOException {
		client1 = Server.getFirstClient();
		client2 = Server.getSecondClient();
		Server.deleteClients();
		
		System.out.println("c1:" + client1.getClient().getInetAddress().getHostAddress() + "   " + client1.getPort());
		System.out.println("c2:" + client2.getClient().getInetAddress().getHostAddress() + "   " + client2.getPort());
		
		Socket c1 = new Socket(client1.getClient().getInetAddress().getHostAddress(), client1.getPort());
		Socket c2 = new Socket(client2.getClient().getInetAddress().getHostAddress(), client2.getPort());
		
		setServerAndClient(c1, c2);
	}
	
	public void setServerAndClient(Socket c1, Socket c2) throws IOException {
		String server = "SERVER";
		String client = "CLIENT";
		String port = Integer.toString(client1.getPort());
		String addr = client1.getClient().getInetAddress().getHostAddress();
		
		//Create the objects to send information to clients.		
		DataOutputStream outToClient1 = new DataOutputStream(c1.getOutputStream());		
		DataOutputStream outToClient2 = new DataOutputStream(c2.getOutputStream());
		
		
		//Tell the clients if they are the client or the server
		outToClient1.writeBytes(server);
		outToClient2.writeBytes(client + "\n" + port + "\n" + addr);
		outToClient2.flush();
			
		c1.close();
		c2.close();
	}
}
