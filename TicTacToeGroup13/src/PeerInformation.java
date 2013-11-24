import java.net.Socket;

public class PeerInformation {
	private Socket client;
	private int port;
	
	public PeerInformation() {
		this.client = null;
		this.port = 0;
	}
	
	public Socket getClient() {
		return client;
	}
	public void setClient(Socket client) {
		this.client = client;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}	
}
