package send_read_data;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTextField;

public class send_read_data {

	private JFrame frame;
	private JTextField tb1;
	private JTextField tb2;
	private JTable table;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					send_read_data window = new send_read_data();
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
	public send_read_data() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.activeCaption);
		frame.setBounds(100, 100, 1285, 813);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NAME :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(77, 258, 115, 29);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("MARKS :");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(87, 298, 107, 40);
		frame.getContentPane().add(lblNewLabel_1);
		
		tb1 = new JTextField();
		tb1.setBounds(202, 265, 156, 20);
		frame.getContentPane().add(tb1);
		tb1.setColumns(10);
		
		tb2 = new JTextField();
		tb2.setBounds(204, 311, 156, 20);
		frame.getContentPane().add(tb2);
		tb2.setColumns(10);
		
		JButton btnNewButton = new JButton("SUBMIT");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String n=tb1.getText();
			    String m=tb2.getText();
			    int ma=Integer.parseInt(m);
			    try {
					Connection con=DriverManager.
					getConnection
					("jdbc:mysql://localhost:3306/eee","root","mrec");
					String q="insert into mrec values('"+n+"','"+ma+"')";
					Statement sta=con.createStatement();
					sta.execute(q);
					con.close();
					JOptionPane.showMessageDialog(btnNewButton, "DONE");
				} catch (SQLException el) {
					el.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(202, 373, 107, 23);
		frame.getContentPane().add(btnNewButton);
		
		table = new JTable();
		table.setBounds(584, 69, 391, 482);
		frame.getContentPane().add(table);
		
		btnNewButton_1 = new JButton("LOAD");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		         try {
		        	 Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/eee","root","mrec");
						Statement sta=con.createStatement();
						String q="select * from mrec";
						ResultSet rs=sta.executeQuery(q);
						ResultSetMetaData rsmd=rs.getMetaData();
						DefaultTableModel model=(DefaultTableModel)table.getModel();
						int cols=rsmd.getColumnCount();
						String[] colname=new String[cols];
						for(int i=0;i<cols;i++)
						{
							colname[i]=rsmd.getColumnName(i+1);
							        model.setColumnIdentifiers(colname);
							String n1,m1;
							while(rs.next())
							{
								n1=rs.getString(1);
								m1=rs.getString(2);
								String[] row= {n1,m1};
								model.addRow(row);
							}
							sta.close();
							con.close();
						}
		         }catch (ClassNotFoundException | SQLException el) {
		        	 el.printStackTrace();
		         }
				
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1.setBounds(594, 562, 150, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("CLEAR");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(new DefaultTableModel());
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_2.setBounds(860, 562, 115, 23);
		frame.getContentPane().add(btnNewButton_2);
	}
}
