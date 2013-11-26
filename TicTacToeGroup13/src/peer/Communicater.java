package peer;
import java.io.*;
import java.net.*;

public class Communicater{
	
	int port = 50040, serverPort;
	String serverAddress, ipAddress="142.157.114.147";
	ServerSocket listener;
	Socket peer;
	Game game;
	
	public Communicater(Game game) throws IOException{
		
		this.game = game;
		listener = new ServerSocket(port);
		//port = listener.getLocalPort();
		
		Socket client = new Socket(ipAddress, 50060);
		DataOutputStream output = new DataOutputStream(client.getOutputStream());
		System.out.println("port : "+ port);
		output.writeInt(port);
		
		client.close();
		
		System.out.println("closed client successfully");
		startGame();
		
	}

	public void startGame() throws IOException{
		
		System.out.println("test 1");
		Socket connector = listener.accept();
		System.out.println("test 2");
		BufferedReader in = new BufferedReader(new InputStreamReader(connector.getInputStream()));
		//DataInputStream inStream = new DataInputStream(connector.getInputStream());
		String type = in.readLine();
		System.out.println(type);
		if (type.equals("SERVER")){
	
			peer = listener.accept();
			game.setStatus('S');
			
		} else {
			
			serverPort = Integer.parseInt(in.readLine());
			//System.out.println("serverPort: "+ in.readLine());
			
			
			serverAddress=in.readLine();
			System.out.println("serverAddress: "+ serverAddress);
			
			listener.close();
			
			peer = new Socket(serverAddress, serverPort);
			//peer = new Socket(serverAddress, serverPort);
			game.setStatus('C');

		}
		
		Receiver receiver = new Receiver(game, peer);
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
		
	}	
	
	public void sendPosition(int position) throws IOException{
		
		//BufferedReader peerInput = new BufferedReader(new InputStreamReader(peer.getInputStream()));
		DataOutputStream outputPeer = new DataOutputStream(peer.getOutputStream());
		System.out.println("position sent : "+ position);
		outputPeer.writeInt(position);
		//System.out.println("position sent : "+ position);
	}
}
