import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.DatagramPacket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class ClientGUI extends JFrame implements ActionListener, KeyListener {
	
	private Connections model;
	private String ip;
	private String port;
	
	private String line = "";
	
	private JTextArea displayText = new JTextArea(16,50);
	private JScrollPane scroll = new JScrollPane(displayText);
	private DefaultCaret caret  = (DefaultCaret) displayText.getCaret();
	
	
	private JTextField input = new JTextField(1);
	
	// adding Date to the display text
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private Date now = new Date();
	
	private JButton btnSend = new JButton("Send");
	private JButton btnQuit = new JButton("Disconnect");
	
	private JPanel mainJP = new JPanel();//everything goes in here
	private JPanel displayJP = new JPanel();//textarea.. plus whatever 
	private JPanel btnsJP = new JPanel();//put this on the bottom
	
	//private JPanel east = new JPanel();
	private JPanel west = new JPanel();
	private JPanel north = new JPanel();
	private JPanel east = new JPanel();
	
	
	public ClientGUI(Connections brain, String ip, String port){
		this.model = brain;
		this.ip = ip;
		this.port = port;
		
		mainJP.setLayout(new BorderLayout());
		
		this.input.addKeyListener(this);
		
		displayText.setAutoscrolls(true);
		scroll.setAutoscrolls(true);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		displayText.setLineWrap(true);
		
		displayJP.setLayout( new BorderLayout());//new GridLayout(2,1));
		displayJP.add(this.scroll,BorderLayout.CENTER);//displayText); //added textarea to jpanel
		displayJP.add(input, BorderLayout.SOUTH);//added input below textarea to jpanel
		
		
		
		btnsJP.setLayout(new FlowLayout());
		
		
		btnSend.addActionListener(this);
		btnQuit.addActionListener(this);
		

		
		Box box = Box.createHorizontalBox();
		box.add(btnQuit);
		box.add(btnSend);
		
		btnsJP.add(box);
		east.setBackground(Color.ORANGE);
		west.setBackground(Color.ORANGE);
		north.setBackground(Color.ORANGE);
		east.setBackground(Color.ORANGE);
		east.setBackground(Color.ORANGE);
		btnsJP.setBackground(Color.ORANGE);
		
		mainJP.add(displayJP, BorderLayout.CENTER);//add to center
		mainJP.add(btnsJP, BorderLayout.SOUTH);//add to bottom
		mainJP.add(east, BorderLayout.EAST);
		mainJP.add(west, BorderLayout.WEST);
		mainJP.add(north, BorderLayout.NORTH);
		
		
		displayText.setEditable(false);
		
		add(mainJP);
		
		this.addKeyListener(this);
		
		
		//Set the close on exit
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//
		this.pack();
		this.setVisible(true);
		this.setTitle(ip+":"+port);
		
		
	}
	private void setTitle(String InetAddress, String portNumber){
		//TODO make this more efficient and relevant
		this.setTitle(InetAddress+"@"+portNumber);
	}
	
	public void recieveMsg(DatagramPacket inPacket) {
		String message = new String(inPacket.getData());
		this.displayMsg("Sender: "+message);
	}
	
	private void displayMsg(String msg){
		now = new Date();  // generates a new object when method is called, no bueno...
		displayText.append(sdf.format(now)+" "+msg +"\n");
	}
	
	private void disconnect(){
		//TODO find a way to close the application
		this.displayMsg("User has disconnected... Bye Bye");
		this.model.disconectChat(ip, port);
	}
	
	private void send(){
		//TODO write text code
		String message = input.getText();
		if (message.split(" ").length > 0 || message.length() > 0){  // if it's not an empty line or full of spaces...
			this.displayMsg(message);
			// make the ip address into a byte array
			byte[] address = new byte[4];
			String[] ipArray = ip.split("\\.");
			System.out.println("address bytes: "+address);
			System.out.println(ip);
			System.out.println(ipArray);
			for( int i = 0; i < 4; i++){
				address[i] = (byte) Integer.parseInt(ipArray[i]); 
			}
			this.input.setText("");
			this.model.send(message, address, Integer.parseInt(port));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== btnSend)
			this.send();
		
		if(e.getSource() == btnQuit)
			disconnect();
	
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER){
			 this.send();
		 }

	 
	}
	

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode()== KeyEvent.VK_ENTER){
			this.send();
		}
		
	}


}
