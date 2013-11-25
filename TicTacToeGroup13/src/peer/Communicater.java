package peer;
import java.io.*;
import java.net.*;

public class Communicater extends Thread{
	
	int port, serverPort;
	ServerSocket listener;
	Game game;
	
	public Communicater(Game game) throws IOException{
		
		this.game = game;
		listener = new ServerSocket(0);
		port = listener.getLocalPort();
		
		Socket client = new Socket("127.0.0.1", 50060);
		DataOutputStream output = new DataOutputStream(client.getOutputStream());
		
		output.writeInt(port);
		
		client.close();
		
	}
	
	@Override
	public void run(){
		
		Socket connector = listener.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(connector.getInputStream()));
		
		String playerType;
		
		if ((playerType=in.readLine()).equals("SERVER")){
			
			Socket peer = listener.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(connector.getInputStream()));
			game.setStatus('S');	
			
			
		} else {
			
			while((serverPort=in.read())!=-1){
				
			}
			game.setStatus('C');
			
		}
			

	}
	
	
}
