import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

public class ClientGUI extends JFrame implements ActionListener, KeyListener {

	JPanel mainJP = new JPanel();
	JPanel inputJP = new JPanel();
	
	JTextArea displayTA = new JTextArea();
	DefaultCaret caret  = (DefaultCaret)displayTA.getCaret();
	
	JTextArea inputTA = new JTextArea();
	
	JButton sendButton = new JButton("Send");
	
	
	public ClientGUI() throws HeadlessException {
		// setting layout of the JPanels
		mainJP.setLayout(new BorderLayout());
		inputJP.setLayout(new FlowLayout());
		
		//setting the display text
		displayTA.setLineWrap(true);
		displayTA.setAutoscrolls(true);
		displayTA.setEditable(false);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		//setting the input text area
		inputTA.setLineWrap(true);
		inputTA.setAutoscrolls(true);
		
		
		/*adding components to JPanels*/
		
		// filling mainJP
		mainJP.add(new JPanel(),BorderLayout.EAST);	
		mainJP.add(new JPanel(),BorderLayout.NORTH);	
		mainJP.add(new JPanel(),BorderLayout.WEST);	

		mainJP.add(inputTA, BorderLayout.CENTER);
		
		// filling up inputJP
		inputJP.add(inputTA);
		inputJP.add(sendButton);
		
		//adding content to JPanel
		this.add(mainJP);
		
		
		//setting JFrame 
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //makes sure to kill the thread when exiting
		this.setVisible(true);
		this.setSize(500, 500);
//		this.pack();
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args) {
		ClientGUI app = new ClientGUI();
	}

}
