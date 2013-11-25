package server;

import java.io.IOException;
import java.net.Socket;
import java.io.DataOutputStream;

public class Connector extends Thread {
	
	final private int NUMBER_OF_CLIENTS = 2;
	PeerInformation client1 = new PeerInformation();
	PeerInformation client2 = new PeerInformation();
	
	public Connector() {}
	
	@Override
	public void run() {
		while (true) {
			if (Server.getArrayListSize() >= NUMBER_OF_CLIENTS) {
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
		
		Socket c1 = new Socket(client1.getClient().getInetAddress(), client1.getPort());
		Socket c2 = new Socket(client2.getClient().getInetAddress(), client2.getPort());
		
		setServerAndClient(c1, c2);
	}
	
	public void setServerAndClient(Socket c1, Socket c2) throws IOException {
		String server = "SERVER";
		String client = "CLIENT";
		int port = client1.getPort();
		String addr = client1.getClient().getInetAddress().getHostAddress();
		
		//Create the objects to send information to clients.		
		DataOutputStream outToClient1 = new DataOutputStream(c1.getOutputStream());
		DataOutputStream outToClient2 = new DataOutputStream(c2.getOutputStream());
		
		//Tell the clients if they are the client or the server
		outToClient1.writeBytes(server + "\n");
		outToClient2.writeBytes(client + "\n");
		
		//Get the port number that the server has chosen.		
		outToClient2.writeBytes(port + "\n");
		outToClient2.writeBytes(addr);
		
		c1.close();
		c2.close();
	}
}
