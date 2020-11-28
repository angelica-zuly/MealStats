import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.mysql.jdbc.PreparedStatement;


public class CustomersOrderPage extends JFrame {

	private JPanel contentPane;
	private static final String SQL_CLASS = "com.mysql.jdbc.Driver";

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CustomersOrderPage frame = new CustomersOrderPage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public void createTable(DefaultTableModel model){
		//creating table from model
		try{
			Class.forName(SQL_CLASS);
			Connection con;
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customers","root","");
			Statement stmt = con.createStatement();

			String query = "SELECT order_number, order_items, order_name, order_email, order_status FROM orders";
			ResultSet rs = stmt.executeQuery(query);

			//retrieving orders 
			while(rs.next()){
				int orderNum = rs.getInt("order_number");
				String orderItems = rs.getString("order_items");
				String orderName = rs.getString("order_name");
				String orderEmail = rs.getString("order_email");
				String orderStatus = rs.getString("order_status");

				System.out.println("[" +  orderNum + "]");
				System.out.println("[" +  orderItems + "]");
				System.out.println("[" +  orderName + "]");
				System.out.println("[" +  orderEmail + "]");
				System.out.println("[" +  orderStatus + "]");

				//populate data onto table
				model.addRow(new Object[]{
						Integer.toString(orderNum),
						orderItems,
						orderName,
						orderStatus});
			}
		} catch(ClassNotFoundException e){
			System.out.println("Cound not find " + SQL_CLASS);
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Error");
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public CustomersOrderPage() {
		setTitle("Customer's Order Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblFoodOrders = new JLabel("Customer Orders:");
		lblFoodOrders.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFoodOrders.setBounds(10, 11, 142, 22);
		contentPane.add(lblFoodOrders);

		JButton logOutBtn = new JButton("Log-Out");
		logOutBtn.setBounds(302, 11, 98, 22);
		contentPane.add(logOutBtn);
		logOutBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LogIn login = new LogIn();
				login.setVisible(true);
				setVisible(false);
			}
		});

		/////////////CREATING ORDER TABLE/////////////

		final JTable table = new JTable();

		//adding scrollPane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 43, 407, 206);
		scrollPane.setPreferredSize(new Dimension(300,300));
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);


		final DefaultTableModel model = new DefaultTableModel();
		//column names:
		model.addColumn("#");
		model.addColumn("Customer");
		model.addColumn("Order");
		model.addColumn("Status");	
		table.setModel(model);

		//display table from db	
		createTable(model);

		//formatting table model columns sizes
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(40);
		columnModel.getColumn(3).setPreferredWidth(100);


		/////////////COMBOBOX////////////			
		//adding comboBox to column
		TableColumn column = columnModel.getColumn(3);		
		final JComboBox<String> comboBox = new JComboBox<>();
		comboBox.addItem(StatusEnum.NO_STATUS.displayName());
		comboBox.addItem(StatusEnum.RECEIVED_ORDER.displayName());
		comboBox.addItem(StatusEnum.PREPARING_ORDER.displayName());
		comboBox.addItem(StatusEnum.ORDER_DONE.displayName());
		comboBox.addItem(StatusEnum.PICK_UP.displayName());	
		column.setCellEditor(new DefaultCellEditor(comboBox));


		//add action listener to comboBox to update any changes in order's status
		final ItemListener itemListener = new ItemListener(){
			public void itemStateChanged(ItemEvent event) {

				if(event.getStateChange() == ItemEvent.SELECTED){

					//get row of selected item
					int row = table.getEditingRow();	
					//row = table.convertRowIndexToModel(row);
					System.out.println("Row: " + "[" + row + "]");	


					if(row != -1){		

						//get the selected item's status:
						String selectedStatus = comboBox.getSelectedItem().toString();
						System.out.println("\nStatus: " + "[" + selectedStatus + "]");
						System.out.println("-->Row: " + "[" + row + "]");

						// get order number from selected row
						int orderNumCell = Integer.parseInt((String) table.getModel().getValueAt(row, 0));
						System.out.println("testing order#: " + "[" + orderNumCell + "]");	

						//updating status of that order# in db:
						try{
							Class.forName("com.mysql.jdbc.Driver");
							Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customers","root","");
							PreparedStatement ps1 = (PreparedStatement) con.prepareStatement("UPDATE orders SET order_status=? WHERE order_number=?");
							ps1.setString(1,selectedStatus);
							ps1.setInt(2,orderNumCell);
							ps1.executeUpdate();

							if(selectedStatus.equals("Picked Up")){
								//delete order from db if status is marked as "picked up"
								PreparedStatement ps2 = (PreparedStatement)con.prepareStatement("DELETE FROM orders WHERE order_number=?");
								ps2.setInt(1,orderNumCell);
								//check for deletion
								if(ps2.executeUpdate() > 0){
									
									//the following two lines were a pain in the ass
									DefaultCellEditor dce = (DefaultCellEditor)table.getCellEditor(); 
									if (dce != null) {
										dce.stopCellEditing();
									}
									model.removeRow(row);
									System.out.println("Order was deleted successfully!");
								}
							}
						} catch(ClassNotFoundException e){
							System.out.print(e);
						}catch(SQLException e){
							System.out.print(e);
						}
					}


				}
			}
		};

		comboBox.addItemListener(itemListener);
	}
}
