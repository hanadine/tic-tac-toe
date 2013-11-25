package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
	static ServerSocket server;
	static ArrayList<PeerInformation> clients = new ArrayList<PeerInformation>();  
	
	public static void main(String[] args) {
		startServer();
		Connector connector = new Connector();
		connector.start();
	}
	
	public static void startServer() {
		 try {
			server = new ServerSocket(50060);			
			listenForClients();
			clients.clear();
		} catch (IOException e) {
			System.out.println("Could not start server");
			e.printStackTrace();
		} 
	}
	
	public static void listenForClients() throws IOException {
		while (true) {
			//Accept a client connection and add the client in the queue.			
			Socket client = server.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			int port;
			port = in.read();
					
			PeerInformation peerInfo = new PeerInformation();
			
			//Need to fill the PeerInformation pojo with correct info and add it to the array list
			peerInfo.setClient(client);
			peerInfo.setPort(port);
			
			System.out.println("Client:" + client.getInetAddress());
			System.out.print("Port:" + port);
			clients.add(peerInfo);				
		}
	}
	
	public static int getArrayListSize() {
		return clients.size();
	}
	
	public static PeerInformation getFirstClient() {
		return clients.get(0);
	}
	
	public static PeerInformation getSecondClient() {
		return clients.get(1);
	}
	
	public static void deleteClients() {
		clients.remove(0);
		clients.remove(1);
	}	
}
