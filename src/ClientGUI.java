import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

public class ClientGUI extends JFrame implements ActionListener, KeyListener {
	
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
	
	//nice looking ;P//
	ImageIcon orangeIcon = new ImageIcon("src/icon-orange.png");
	
	
	public ClientGUI(){
		
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
		/*btnsJP.add(btnPrivate);
		btnsJP.add(btnConnect);
		btnsJP.add(btnSend);
		btnsJP.add(btnQuit); */
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
		
//		input.setEditable(false);
		
		displayText.setEditable(false);
		//input.setPreferredSize(new Dimension(500,500));
		
		add(mainJP);
//		btnQuit.setEnabled(false);
//		btnSend.setEnabled(false);
		
		
		this.addKeyListener(this);
		
		//fancy icon//
		this.setIconImage(this.orangeIcon.getImage());
		
		
		//Set the close on exit
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	private void setTitle(String InetAddress, String portNumber){
		//TODO make this more efficient and relevant
		this.setTitle(InetAddress+"@"+portNumber);
	}
	
	private void displayMsg(String msg){
		now = new Date();  // generates a new object when method is called, no bueno...
		displayText.append(sdf.format(now)+" "+msg +"\n");
	}
	
	private void disconnect(){
		//TODO find a way to close the application
		this.displayMsg("User has disconnected... Bye Bye");
	}
	
	private void send(){
		//TODO write text code
		if (input.getText().split("").length > 0){  // if it's not an empty line or full of spaces...
			this.displayMsg(input.getText());
			this.input.setText("");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//do something when connect
		//connect(serverName, serverPortNumber);
//		if(e.getSource() == btnConnect)
		
		//do something when send
		if(e.getSource()== btnSend)
			this.send();
		
		if(e.getSource() == btnQuit)
			disconnect();
//		if(e.getSource() == btnPrivate)
//			
		
		//send()
		//do something when private
		//send... private...
		//do something when quit
		
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		 if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isShiftDown()){ 
//		    	System.out.println("private text");
//		    	this.privatemsg();
		 }
		 else if (e.getKeyCode() == KeyEvent.VK_ENTER){
//			 System.out.println("public text");
			 this.send();
		 }
		 else if(e.isControlDown()){
//			 this.chkboxEncrypt.doClick();
		 }
	 
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater( new Runnable(){
			public void run() {
				ClientGUI chatclient = new ClientGUI();
				chatclient.pack();
				chatclient.setVisible(true);
				//chatclient.setSize(500,500);
				chatclient.setTitle("Orange Chat");
				
			}
			
			
		}
				
				);
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
