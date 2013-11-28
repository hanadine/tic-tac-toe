package peer;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class Communicater{
	
	int port = 50040, serverPort;
	String serverAddress;
	ServerSocket listener;
	Socket peer;
	Game game;
	Receiver receiver;
	
	public Communicater(Game game) {
		
		this.game = game;
		
		WaitForPeer waitForPeer = new WaitForPeer(game, this);
		waitForPeer.start();
		
	}
	
	public void connectToServer() throws IOException{
	
		try {
			listener = new ServerSocket(port);
			Socket client = new Socket("142.157.166.19", 50060);
			DataOutputStream output = new DataOutputStream(client.getOutputStream());
			System.out.println("port : "+ port);
			output.writeInt(port);
			
			client.close();
			
			System.out.println("closed client successfully");
			startGame();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "server not available", "ERROR", JOptionPane.ERROR_MESSAGE);
			game.btn1v1.setEnabled(true);
			e.printStackTrace();
		}
		
	}
	
	

	public void startGame() throws IOException{
		
		System.out.println("test 1");
		Socket connector = listener.accept();
		System.out.println("test 2");
		BufferedReader in = new BufferedReader(new InputStreamReader(connector.getInputStream()));
		String type = in.readLine();
		System.out.println(type);
		if (type.equals("SERVER")){
			
			listener.close();
			peer = listener.accept();
			game.setStatus('S');
			
		} else {
			
			serverPort = Integer.parseInt(in.readLine());
						
			serverAddress = in.readLine();
			System.out.println("serverAddress: "+ serverAddress);
			
			listener.close();
			
			peer = new Socket(serverAddress, serverPort);
			game.setStatus('C');

		}
		
		receiver = new Receiver(game, peer);
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
		
	}	
	
	public void sendPosition(int position) throws IOException{
		
		DataOutputStream outputPeer = new DataOutputStream(peer.getOutputStream());
		System.out.println("position sent : "+ position);
		outputPeer.writeInt(position);
	}
	
	public void sendNewGame() throws IOException{		
		DataOutputStream outputPeer = new DataOutputStream(peer.getOutputStream());
		System.out.println("New Game Request sent");
		outputPeer.writeInt(10);		
	}
	
	public void sendConfirm() throws IOException{		
		DataOutputStream outputPeer = new DataOutputStream(peer.getOutputStream());
		System.out.println("New Game Confirm sent");
		outputPeer.writeInt(11);		
	}
	
	public void sendExit() throws IOException{
		DataOutputStream outputPeer = new DataOutputStream(peer.getOutputStream());
		System.out.println("Exit sent");
		outputPeer.writeInt(12);
	}
	
	public void close() throws IOException{
		listener.close();
		peer.close();
	}
	
	
	public void stopReceiver(){
		receiver.stopThread();
	}

}
