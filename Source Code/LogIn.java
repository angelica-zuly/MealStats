import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LogIn extends JFrame {

	private JPanel contentPane;
	private JTextField email;
	private JPasswordField password;
	private static final String WORKER_EMAIL = "worker@mealstats.com";
	private static final String WORKER_PASSWORD = "worker123";

	private static final String SQL_CLASS = "com.mysql.jdbc.Driver";
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LogIn frame = new LogIn();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}


	/**
	 * Create the frame.
	 */
	public LogIn() {
		setTitle("Log-In Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 362, 267);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		email = new JTextField();
		email.setBounds(118, 54, 161, 20);
		contentPane.add(email);
		email.setColumns(10);

		password = new JPasswordField();
		password.setBounds(118, 85, 161, 20);
		contentPane.add(password);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(74, 56, 34, 17);
		contentPane.add(emailLabel);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(50, 87, 69, 17);
		contentPane.add(passwordLabel);


		JButton loginButton = new JButton("Log-In");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Class.forName(SQL_CLASS);
					Connection con;
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customers","root","");
					Statement stmt =  con.createStatement();
					String query = "SELECT user_name, user_email, user_password FROM users";
					ResultSet rs = stmt.executeQuery(query);

					boolean validated = false;

					//retrieving user 
					String inputEmail = email.getText();
					String inputPassword = password.getText().toString();
					while(rs.next()){
						String dbEmail = rs.getString("user_email");
						String dbPassword = rs.getString("user_password");
						System.out.println("[" +  dbEmail + "]");
						System.out.println("[" +  dbPassword + "]");

						if(dbEmail.equals(inputEmail) && dbPassword.equals(inputPassword)){
							validated = true;
							break;	
						}		
					}

					if(validated){
						if(inputEmail.equals(WORKER_EMAIL) && inputPassword.equals(WORKER_PASSWORD)){
							//user who logged in is a worker 
							JOptionPane.showMessageDialog(null, "Welcome Worker!");
							//setting the global current user variable via worker email
							MealStatService.setCurrentUser(inputEmail);
							//opening customers order page for worker
							CustomersOrderPage cop = new CustomersOrderPage();						
							cop.setVisible(true);
							setVisible(false);	

						} else {
							//user who logged in is a customer
							JOptionPane.showMessageDialog(null, "Login Successful!");
							//setting the global current user variable via log-in email						
							MealStatService.setCurrentUser(inputEmail);
													
							//opening customer home page for customer
							CustomerHomePage chp = new CustomerHomePage();						
							chp.setVisible(true);
							setVisible(false);					
						}

					} else{
						JOptionPane.showMessageDialog(null, "Invalid Login...");
					}

				} catch(ClassNotFoundException e){
					System.out.println("Cound not find " + SQL_CLASS);
					e.printStackTrace();
				} catch (SQLException e) {
					System.out.println("SQL Error");
					e.printStackTrace();
				}
			}
		});
		loginButton.setBounds(150, 124, 86, 23);
		contentPane.add(loginButton);

		JButton btnNewButton = new JButton("Create an Account");
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				SignUp signinpage = new SignUp();
				signinpage.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton.setForeground(Color.BLUE);
		btnNewButton.setBounds(105, 158, 161, 23);
		contentPane.add(btnNewButton);

	}
}
