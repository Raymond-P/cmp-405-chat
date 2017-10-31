import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ConnectionsGUI extends JFrame {

		private JButton connect = new JButton("Connect");
		
		private JLabel ip = new JLabel("IP Address:");
		private JTextArea address= new JTextArea();
		
		private JLabel port = new JLabel("Port:");
		private JTextArea portTA = new JTextArea();
		
		private JPanel portPanel = new JPanel();
		
		private JPanel ipPanel = new JPanel();
		
		private JPanel mainJP = new JPanel();
		
		private JPanel box = new JPanel();
		
		public ConnectionsGUI() {
			
			this.setLayout(new BorderLayout());
			box.setLayout(new GridLayout(2, 2));
			
			
			box.add(ip);
			box.add(address);
			
			box.add(port);
			box.add(portTA);
			
			mainJP.add(box);
			mainJP.add(connect);
						
			
			this.add(mainJP, BorderLayout.CENTER);
			
			this.setSize(300, 500);
			
			this.pack();
			this.setVisible(true);
			
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
		
		}
		
		public static void main(String[] args) {
			ConnectionsGUI n = new ConnectionsGUI();
		}
}
