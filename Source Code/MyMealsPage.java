import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


public class MyMealsPage extends JFrame {
	private JPanel contentPane;

	private static final String SQL_CLASS = "com.mysql.jdbc.Driver";


	/**
	 * Create the frame.
	 */
	public MyMealsPage() {

		setTitle("My Meals Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 470, 326);
		setResizable(false);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblYourOrders = new JLabel("Your Orders:");
		lblYourOrders.setBounds(10, 11, 85, 19);
		lblYourOrders.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblYourOrders);

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(367, 11, 69, 23);
		contentPane.add(btnBack);
		contentPane.setVisible(true);
		btnBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CustomerHomePage chp = new CustomerHomePage();
				chp.setVisible(true);
				setVisible(false);
			}
		});


		//grabbing the current user's information
		UserData currentUserData = MealStatService.getCurrentUserData();

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("#");
		model.addColumn("Items");
		model.addColumn("Status");	

		try{
			Class.forName(SQL_CLASS);
			Connection con;
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customers","root","");
			Statement stmt = con.createStatement();

			String query = "SELECT order_number, order_items, order_name, order_email, order_status FROM orders";
			ResultSet rs = stmt.executeQuery(query);

			//retrieving orders 
			int orderNum = 0;
			String orderItems = null;
			String orderStatus = null;	
			String orderEmail = null;

			while(rs.next()){
				orderNum = rs.getInt("order_number");
				orderItems = rs.getString("order_items");
				orderEmail = rs.getString("order_email");
				orderStatus = rs.getString("order_status");

				System.out.println("[" +  orderNum + "]");
				System.out.println("[" +  orderItems + "]");
				System.out.println("[" +  orderEmail + "]");
				System.out.println("[" +  orderStatus + "]");

				if(orderEmail.equals(currentUserData.getEmail())){
					//populate data onto table
					model.addRow(new Object[]{
							Integer.toString(orderNum),
							orderItems,
							orderStatus});

				}
			}
			
		} catch(ClassNotFoundException e){
			System.out.println("Cound not find " + SQL_CLASS);
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Error");
			e.printStackTrace();
		}

		////////////////////////////////////////



		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 425, 218);
		scrollPane.setPreferredSize(new Dimension(300,300));
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
		contentPane.add(scrollPane);

		JTable table = new JTable(model);
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		table.setBackground(Color.WHITE);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setFillsViewportHeight(true);

		//table formatting
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(40);
		columnModel.getColumn(1).setPreferredWidth(245);
		columnModel.getColumn(2).setPreferredWidth(115);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		for(int x=0;x<3;x++){
			table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyMealsPage frame = new MyMealsPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
