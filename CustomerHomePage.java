import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class CustomerHomePage extends JFrame {

	private JPanel contentPane;
	private static final String SQL_CLASS = "com.mysql.jdbc.Driver";

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CustomerHomePage frame = new CustomerHomePage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public void populateListOfCustomers(){	
		try{
			Class.forName(SQL_CLASS);
			Connection con;
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customers","root","");
			Statement stmt = con.createStatement();

			String query = "SELECT user_name, user_email, user_password FROM users";
			ResultSet rs = stmt.executeQuery(query);

			//retrieving customers 		
			while(rs.next()){
				String userName = rs.getString("user_name");
				String userEmail = rs.getString("user_email");
				String userPassword = rs.getString("user_password");

				System.out.println("[" +  userName + "]");
				System.out.println("[" +  userEmail + "]");
				System.out.println("[" +  userPassword + "]");

				//initializing a new user in the system
				UserData userData = new UserData(userName, userEmail, userPassword);
				//adding new user's data to ArrayList of customers
				MealStatService.addUserData(userData);
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
	public CustomerHomePage() {

		//populate a list of customers from db
		populateListOfCustomers();
		//grabbing the current user's information
		UserData currentUserData = MealStatService.getCurrentUserData();	

		setTitle("Customer Home Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		

		//displaying the user's name at the top of the page
		JLabel lblWelcome = 	new JLabel("Welcome " + currentUserData.getName() + "!");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblWelcome.setBounds(10, 43, 229, 24);
		contentPane.add(lblWelcome);

		JButton btnPlaceAnOrder = new JButton("Place an Order");
		btnPlaceAnOrder.setBackground(Color.WHITE);
		btnPlaceAnOrder.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnPlaceAnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//opening menu page for signed-in customer
				MenuPage mp = new MenuPage();				
				mp.setVisible(true);
				setVisible(false);
			}
		});
		btnPlaceAnOrder.setBounds(10, 78, 190, 79);
		contentPane.add(btnPlaceAnOrder);

		JButton btnBackToLogin = new JButton("Log-Out");
		btnBackToLogin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LogIn login = new LogIn();
				login.setVisible(true);
				setVisible(false);
			}
		});
		btnBackToLogin.setBounds(172, 177, 85, 30);
		contentPane.add(btnBackToLogin);

		JButton btnSeeYourMeal = new JButton("See Your Meal Status");
		btnSeeYourMeal.setBackground(Color.WHITE);
		btnSeeYourMeal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSeeYourMeal.setBounds(234, 78, 190, 79);
		contentPane.add(btnSeeYourMeal);
		btnSeeYourMeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//opening menu page for signed-in customer
				MyMealsPage mmp = new MyMealsPage();				
				mmp.setVisible(true);
				setVisible(false);
			}
		});




	}
}
