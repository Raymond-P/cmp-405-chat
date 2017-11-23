import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class protoGUI implements ActionListener{

	private static String port = "64000";
	private JFrame frame;
	private JTextField username_textField;
	
	private Connections model;
	private JButton btnConnect;
	private JButton btnDisconnect;

	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Create the application.
	 */
	public protoGUI(Connections model) {
		initialize();
		
		this.model = model;
		this.btnConnect.addActionListener(this);
		this.frame.setTitle(this.model.getAddressString());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JPanel connect_panel = new JPanel();
		connect_panel.setBounds(132, 32, 302, 173);
		connect_panel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		connect_panel.setLayout(null);
		
		JLabel username_label = new JLabel("Username:");
		username_label.setBounds(10, 42, 73, 14);
		connect_panel.add(username_label);
		
		username_textField = new JTextField();
		username_textField.setText("name-of-person");
		username_textField.setBounds(110, 39, 174, 20);
		connect_panel.add(username_textField);
		username_textField.setColumns(10);
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(10, 116, 90, 23);
		connect_panel.add(btnConnect);
		frame.getContentPane().add(connect_panel);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDisconnect.setBounds(319, 237, 115, 23);
		frame.getContentPane().add(btnDisconnect);
	}
	
	
	private void connect(){
		System.out.println("connect() was called...");
		String username = this.username_textField.getText();
		this.model.connectTo(username);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnConnect){
			System.out.println("Button was pressed...");
			this.connect();
		}
		else if (e.getSource() == this.btnDisconnect){
			System.out.println("Disconnect Buttons was pressed");
			this.model.close();
		}
	}
}
