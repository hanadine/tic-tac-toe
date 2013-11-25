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
		
		Socket client = new Socket("127.0.0.1", 50060);
		DataOutputStream output = new DataOutputStream(client.getOutputStream());
		
		output.writeInt(port);
		
		client.close();
		startGame();
		
	}

	public void startGame() throws IOException{
		
		Socket connector = listener.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(connector.getInputStream()));
		
		String playerType;
		
		if ((playerType=in.readLine()).equals("SERVER")){
			
			game.setStatus('S');
			peer = listener.accept();
			
		} else {
			
			while((serverPort=in.read())!=-1){}
			while(!(serverAddress=in.readLine()).equals(null)){}
			
			game.setStatus('C');
			listener.close();
			peer = new Socket(serverAddress ,serverPort);
			
		}
			

	}
	
	
}
