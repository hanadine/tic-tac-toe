import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	static ServerSocket server;
	static ArrayList<PeerInformation> clients;  
	
	public static void main(String[] args) {
		startServer();
				
	}
	
	public static void startServer() {
		 try {
			server = new ServerSocket(50060);
			clients.clear();			
			listenForClients();
		} catch (IOException e) {
			System.out.println("Could not start server");
			e.printStackTrace();
		} 
	}
	
	public static void listenForClients() throws IOException {
		while (true) {
			//Accept a client connection and add the client in the queue.			
			Socket client = server.accept();
			
			
			
			clients.add(client);
				
			//If two clients are present in the arraylist then esablish a connection between them
			//Send the clients to be connected and remove them from the list 
			
}
