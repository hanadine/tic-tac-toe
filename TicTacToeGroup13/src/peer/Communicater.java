package peer;
import java.io.*;
import java.net.*;

public class Communicater{
	
	int port, serverPort;
	String serverAddress;
	ServerSocket listener;
	Socket peer;
	Game game;
	
	public Communicater(Game game) throws IOException{
		
		this.game = game;
		listener = new ServerSocket(0);
		port = listener.getLocalPort();
		
		Socket client = new Socket("142.157.112.62", 50060);
		DataOutputStream output = new DataOutputStream(client.getOutputStream());
		
		output.writeInt(port);
		
		client.close();
		startGame();
		
	}

	public void startGame() throws IOException{
		
		Socket connector = listener.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(connector.getInputStream()));
		
		if (!in.readLine().equals("SERVER")){
	
			peer = listener.accept();
			game.setStatus('S');
			
		} else {
			
			while((serverPort=in.read())!=-1){}
			while(!(serverAddress=in.readLine()).equals(null)){}
		
			listener.close();
			
			peer = new Socket(serverAddress ,serverPort);
			peer = new Socket(serverAddress, serverPort);
			game.setStatus('C');

		}
	}

	
	
	
	public void receivePosition() throws IOException{
		
		BufferedReader peerInput = new BufferedReader(new InputStreamReader(peer.getInputStream()));
		int position;
		while((position=peerInput.read())!=-1){}
		
		game.setGrid(position);
		
	}
	
	public void sendPosition(int position) throws IOException{
		
		//BufferedReader peerInput = new BufferedReader(new InputStreamReader(peer.getInputStream()));
		DataOutputStream outputPeer = new DataOutputStream(peer.getOutputStream());
		outputPeer.writeInt(position);
		
	}

}
