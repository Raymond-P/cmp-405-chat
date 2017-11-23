
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

/**
 * TODO: find a way to broadcast in connect
 * TODO: store usernames as keys in connected_users, we'll deal with repeated usernames later 
 * TODO: modify recieve thread to add an user when we recive their "ARP" reply
 * "##### name-of-user #### AA.BB.CC.DD"
 * 
 * TODO: Add more documentation.
 * 
 * 
 * @author Raymond Perez
 * @email raymondperez158@gmail.com 
 *<br> <br>
 * <b>Notes:</b> Now that we are connecting using usernames instead of addresses
 * there is a much greater chance that multiple users have the same username.
 * In order to avoid a problem with multiple congruent usernames we will 
 * simply assume that every username has a unique name.
 */
public class Connections {
	private Hashtable<String, ClientGUI> connected_users = new Hashtable<String, ClientGUI>();
	private Hashtable<String, String> users_list = new Hashtable<String, String>();
	
	private DatagramSocket socket;
	private int socketNumber = 64000;
	private InetAddress myAddress;
	private String myUserName;
	private String arpReply;
	
	public Connections() {

		try { 
			myAddress = InetAddress.getLocalHost();
			socket = new DatagramSocket(socketNumber, myAddress);	
			myUserName = "Raymond";
			arpReply = "##### "+myUserName+" ##### "+myAddress.getHostAddress();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		Thread recieveThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				recieve();
			}
		});
		recieveThread.start();
	}
	
	public String getAddressString(){
		return (myAddress.toString()+":"+socketNumber);
	}
	
	/**
	 * The receive method that listens in to incoming messages 
	 */
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
			String hostAddress = inPacket.getAddress().getHostAddress();
			String port = ""+inPacket.getPort();
			String key = hostAddress+":"+port;
			String message = new String(inPacket.getData());

			
			// address request
			if (message.startsWith("????? ")) {  // "yo, where you at?"
				if(message.split(" ")[1].equals(myUserName)) {  // who? me?
					this.send(this.arpReply, hostAddress);
				}
			}
			else if (message.startsWith("##### ")) {  // I am 
				String[] info = message.split(" ");
				String theirName = info[1];
				String theirAddress = info[3];
				
				this.users_list.put(theirName, theirAddress);
			}
			
			
			if (!this.connected_users.containsKey(key)){
				//create GUI instance and push it into the table
				connected_users.put( key , new ClientGUI(this,hostAddress,port));
			}
			this.connected_users.get(key).recieveMsg(inPacket);
			System.out.println("Received message = " + message);
			
		} while(true);


	}
	
	/**
	 * Parses an IPv4 network address and parses it 
	 * into a byte array. This is meant to be used 
	 * when using the InetAddress.getByAddress() method
	 * @param ipAddress A string representation of the IPv4 address
	 * @return a byte array (of length 4) of representation of the ipAddress
	 */
	public byte[] parseIpToBytes(String ipAddress) {
		// make the ip address into a byte array
		byte[] address = new byte[4];
		String[] ipArray = ipAddress.split("\\.");
		System.out.println("address bytes: "+address);
		System.out.println(ipAddress);
		System.out.println(ipArray);
		for( int i = 0; i < 4; i++){
			address[i] = (byte) Integer.parseInt(ipArray[i]); 
		}
		return address;
	}
	
	
	/**
	 * Sends a datagram packet to a host at the specified address
	 * and at the specified port
	 * @param message The string message to be sent
	 * @param address the destination address in the form of a byte array
	 * @param port	the port where the host will recieve the message
	 * @deprecated
	 */
	public void send(String message, byte[] address, int port ) {
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
	
	/**
	 * Sends a datagram packet to a host at the specified address
	 * and at the specified port
	 * @param message The string message to be sent
	 * @param address the destination address in the form of a String
	 * @param port	the port where the host will recieve the message
	 */
	public void send(String message, String address, int port) {
		// TODO Auto-generated method stub
		byte[] parsedAddress = this.parseIpToBytes(address);
		byte[] buffer = message.getBytes();
		InetAddress destination_address;
		try {
			destination_address = InetAddress.getByAddress(parsedAddress);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, destination_address, port);
			socket.send(packet);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***
	 * Sends a message inside a datagram packet to a
	 * host at the specifed address
	 * @param message the string message to be sent
	 * @param address the destination addess in teh form of a String
	 */
	public void send(String message, String address) {
		this.send(message, address, socketNumber); // the agreed upon socket was 64000
	}
	
	/**
	 * Connects to another client by specifying the client's username.
	 * It does this by making an ARP-like request where it 
	 * broadcasts the connect to username and it gets a response from that user.
	 * the second part of this process, the message received, is handled in the 
	 * receive method
	 * @param ipAddress The IP address of the user that you are requesting to connect to
	 * @param port the specified port that the host will recieve the message in.
	 */
	protected void connect(String ipAddress, String port){
		System.out.println("Model connect was called... with username: "+ipAddress);
		if (!connected_users.containsKey(ipAddress)){
			connected_users.put(ipAddress+":"+port, new ClientGUI(this,ipAddress,port));
			connected_users.get(ipAddress+":"+port).requestFocus();
		}
		
	}
	
	/**
	 * TODO: prompt ERROR: Could not connect to user
	 * 
	 * Connects to another client by specifying the client's username.
	 * It does this by making an ARP-like request where it 
	 * broadcasts an ARP like request asking the username to respond with it's address.
	 * the second part of this process, the message received, is handled in the 
	 * receive method
	 * @param username The name of the user that you are requesting to connect to.
	 */
	protected void connectTo(String username) {

		if(!users_list.containsKey(username)) {
			String arpRequest = "????? "+username+" ##### "+this.myUserName; // where is username this is me
			String broadcastAddress = "255.255.255.255";
			for (int TTL = 20;!users_list.containsKey(username) && TTL > 1 ;TTL-- ) {
				
				this.send(arpRequest, broadcastAddress );

				try {
					TimeUnit.MILLISECONDS.sleep(10); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		if (users_list.containsKey(username)) {
			//TODO: get the user address from the dictionary
			this.connect(users_list.get(username), ""+socketNumber);
		}
	}


	/**
	 * Checks if we are already connected to someone with the specified
	 * username
	 * @param username the username we are trying to find
	 * @return True if its connected False if it is not
	 */
	protected boolean isConectedTo(String username) {
		return connected_users.containsKey(username);
	}
	
	protected void disconectChat(String address, String port) {
		String key = address+":"+port;
		ClientGUI chat = connected_users.remove(key);
//		chat.dispatchEvent(new WindowEvent(chat, WindowEvent.WINDOW_CLOSING));
	}
	
	protected void close(){
		this.socket.disconnect();
		this.socket.close();
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Connections model = new Connections();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					protoGUI window = new protoGUI(model);
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
