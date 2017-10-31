import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Socket {
	
	public static InetAddress myAddress = null;
	
	public static void recieveMethod(){
		DatagramSocket inSocket = null;
		byte[] inBuffer = new byte[100];
		DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);
		
		
		try {
			inSocket = new DatagramSocket(64000, myAddress);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		do {
			for (int i = 0; i < inBuffer.length; i++) {
				inBuffer[i]=' ';
			}
			try {
				inSocket.receive(inPacket);
			} catch (Exception e) {
				// TODO: handle exception
			}
			String message = new String(inPacket.getData());
			System.out.println("REcieved Message = "+message);
			
		} while (true);
	}

	public static void main(String[] args) {
		
//		InetAddress appleAddress = null;
		
		
		DatagramSocket myOutboundSocket = null;
		
		try{
			myOutboundSocket = new DatagramSocket(63000, myAddress);
			myAddress = InetAddress.getLocalHost();
//			appleAddress = InetAddress.getByName("apple.com");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
//		System.out.println("apple address = "+ appleAddress.getHostAddress());
		System.out.println("my address = "+ myAddress.getHostAddress());
		
		Thread recieveThread = new Thread(new Runnable() {
			@Override
			public void run() {
				recieveMethod();
			}
		});
		
		recieveThread.setName("My Datagram Recieve Thread");
		recieveThread.start();
		
		
		Scanner sncanner = new Scanner(System.in);
		System.out.println("Start Sending? Press enter...");
		sncanner.nextLine();

		byte[] buffer = new byte[1000];
		
		String prefix =" / /\n"+
" L_L_\n"+
"/    \\\n"+
"|00  |       _______\n"+
"|_/  |      /  ___  \' \n"+
"|    |     /  /   \\  \\ \n"+
"|    |_____\\  \\_  /  / \n"+
"\\          \\____/  /_____\n"+
"  \\ _______________/______\\...Sneaky Snail....";
		for (int i = 0; i <= 1000 ; i++) {
			String message = prefix + i;
			buffer = message.getBytes();
		
			try{
				// for an ip address 192.168.1.118
				byte[] otherHost = new byte[4];
				otherHost[0] = (byte)192;
				otherHost[1] = (byte)168;
				otherHost[2] = (byte)1;
				otherHost[3] = (byte)118;
				
				InetAddress otherAddress = InetAddress.getByAddress(otherHost);
				
				DatagramPacket packet =  new DatagramPacket(buffer, message.length(),otherAddress,64000);
				
				System.out.println("Sending Message = "+message);
				myOutboundSocket.send(packet);
				TimeUnit.SECONDS.sleep(5);
			}catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		
		}
		
//		try {
//			TimeUnit.MINUTES.sleep(1);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			System.exit(-1);
//		}
//		
		System.out.println("Main Method exiting... Bye bye...");
	}
	
}
