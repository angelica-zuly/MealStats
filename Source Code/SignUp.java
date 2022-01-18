import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.PreparedStatement;


public class SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField email;
	private JTextField password;
	private JTextField name;
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SignUp frame = new SignUp();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @return 
	 */

	public void initializeFrame(){

		setTitle("Sign-Up Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 329, 308);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		email = new JTextField();
		email.setBounds(81, 57, 148, 20);
		contentPane.add(email);
		email.setColumns(10);

		password = new JTextField();
		password.setColumns(10);
		password.setBounds(81, 103, 148, 20);
		contentPane.add(password);

		name = new JTextField();
		name.setColumns(10);
		name.setBounds(81, 150, 148, 20);
		contentPane.add(name);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(82, 37, 46, 14);
		contentPane.add(lblEmail);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(81, 85, 89, 14);
		contentPane.add(lblPassword);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(81, 134, 46, 14);
		contentPane.add(lblName);
		
		JButton backButton = new JButton("Back");
		backButton.setBounds(105, 215, 89, 23);
		contentPane.add(backButton);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				LogIn login = new LogIn();
				login.setVisible(true);
				setVisible(false);
			}
		});

	}

	//Constructor
	public SignUp() {
		
		initializeFrame();

		JButton btnSignup = new JButton("Sign-Up");
		btnSignup.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customers","root","");
					PreparedStatement ps = (PreparedStatement) con.prepareStatement("insert into users(user_name,user_email,user_password) values(?,?,?);");
					ps.setString(1,name.getText());
					ps.setString(2,email.getText());
					ps.setString(3,password.getText());
					int x = ps.executeUpdate();
					if(x>0 && !email.getText().isEmpty()){
						//TODO: no duplicates
						JOptionPane.showMessageDialog(null, "sign up successful");				
						setVisible(false);
						LogIn login = new LogIn();
						login.setVisible(true);				
					}else{
						JOptionPane.showMessageDialog(null, "sign up unsuccessful");
					}
				}catch(Exception e){System.out.print(e);}
			}
		});
		btnSignup.setBounds(105, 181, 89, 23);
		contentPane.add(btnSignup);


	}
}
