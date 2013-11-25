package server;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.omg.CORBA.portable.InputStream;


public class Server {
	static ServerSocket server;
	static ArrayList<PeerInformation> clients = new ArrayList<PeerInformation>();  
	
	public static void main(String[] args) {
		Connector connector = new Connector();
		Thread connThread = new Thread(connector);
		connThread.start();
		startServer();
		
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
			DataInputStream in = new DataInputStream(client.getInputStream());
			
			int port;
			port = in.readInt();
					
			PeerInformation peerInfo = new PeerInformation();
			
			//Need to fill the PeerInformation pojo with correct info and add it to the array list
			peerInfo.setClient(client);
			peerInfo.setPort(port);
			
			System.out.println("Client:" + client.getInetAddress());
			System.out.println("Port:" + port);
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
		clients.remove(0);
	}	
}
