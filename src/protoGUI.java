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

	private JFrame frame;
	private JTextField ip_textField;
	private JTextField port_textField;
	
	private Connections model  =  new Connections();
	private JButton btnConnect;
	private JButton btnDisconnect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					protoGUI window = new protoGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public protoGUI() {
		initialize();
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
		
		JLabel ip_label = new JLabel("IP Address:");
		ip_label.setBounds(10, 42, 73, 14);
		connect_panel.add(ip_label);
		
		ip_textField = new JTextField();
		ip_textField.setBounds(110, 39, 174, 20);
		connect_panel.add(ip_textField);
		ip_textField.setColumns(10);
		
		JLabel port_label = new JLabel("Port:");
		port_label.setBounds(10, 82, 73, 14);
		connect_panel.add(port_label);
		
		port_textField = new JTextField();
		port_textField.setBounds(110, 77, 174, 20);
		connect_panel.add(port_textField);
		port_textField.setColumns(10);
		
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
		String address = this.ip_textField.getText();
		String port = this.port_textField.getText();
		this.model.connect(address, port);
		
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
