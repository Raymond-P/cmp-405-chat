
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Hashtable;



public class Connections {
	private Hashtable<SocketAddress, ClientGUI> list_of_messengers = new Hashtable();
	
	private DatagramSocket socket;
	private int socketNumber = 64000;
	private InetAddress myAddress;
	
	public Connections() {

		try { 
			myAddress = InetAddress.getLocalHost();
			socket = new DatagramSocket(socketNumber, myAddress);	
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	private void recieve() {
		
		byte[] inBuffer = new byte[100];
		DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);
		
		do {
			for ( int i = 0 ; i < inBuffer.length ; i++ ) {
				inBuffer[i] = ' ';
			}
			
			try {
				// this thread will block in the receive call
				// until a message is received
				System.out.println("Waiting for input...");
				socket.receive(inPacket);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			// TODO: find the corresponding gui and send the message to it
			
			if (!this.list_of_messengers.containsKey(inPacket.getSocketAddress())){
				//TODO: create gui instance and push it into the table
				list_of_messengers.put( inPacket.getSocketAddress() , new ClientGUI(this));
			}
			this.list_of_messengers.get(inPacket.getSocketAddress()).recieveMsg(inPacket);
			String message = new String(inPacket.getData());
			System.out.println("Received message = " + message);
			
		} while(true);


	}
	
	private void send(String message, byte[] address, int port ) {
		// TODO Auto-generated method stub
		byte[] buffer = message.getBytes();
		InetAddress destination_address;
		try {
			destination_address = InetAddress.getByAddress(address);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, destination_address, port);
			socket.send(packet);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
}
