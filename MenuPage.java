import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.PreparedStatement;



public class MenuPage extends JFrame {

	private JPanel contentPane;
	private JTextField paymentField;

	final DefaultListModel<String> DLM = new DefaultListModel<String>();
	String taxPayment;
	String taxDisplay;
	int totalPayment = 0;
	int pizzaPrice = 1199;
	int saladPrice = 350;
	int sandwichPrice = 799;
	int soupPrice = 499;
	int sodaPrice = 179;
	int waterPrice = 100;
	int juicePrice = 150;
	int teaPrice = 175;
	int cookiePrice = 199;
	int piePrice = 499;
	int icecreamPrice = 250;
	int cakePrice = 499;	

	//grabbing the current user's information
	final UserData currentUserData = MealStatService.getCurrentUserData();

	//setting an order# to a new empty order
	final Order userOrder = new Order(MealStatService.getNextOrderId());

	List<String> tempOrder = new ArrayList<>();

	void updatePrice(){
		//subtotal price
		double price =  (double)totalPayment/100;
		String payment = Double.toString(price);					
		paymentField.setText("$" + payment);

		//taxed price
		double tax = price*.065;
		Double truncatedDouble1 = BigDecimal.valueOf(tax)
				.setScale(2, RoundingMode.HALF_UP)
				.doubleValue();			
		taxDisplay = Double.toString(truncatedDouble1);	

		//total price with tax		
		double totalTax = tax + price;
		Double truncatedDouble2 = BigDecimal.valueOf(totalTax)
				.setScale(2, RoundingMode.HALF_UP)
				.doubleValue();			
		taxPayment = Double.toString(truncatedDouble2);	
	}


//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MenuPage frame = new MenuPage();
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
	public MenuPage() {

		System.out.println("User Data: " + currentUserData);		

		setTitle("Menu Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 336);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBounds(0, 93, 119, 121);
		contentPane.add(panel);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_1.setBounds(115, 93, 103, 121);
		contentPane.add(panel_1);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_2.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel_2.setBounds(223, 93, 119, 121);
		contentPane.add(panel_2);

		paymentField = new JTextField();
		paymentField.setText("$0.00");
		paymentField.setBounds(105, 244, 56, 20);
		contentPane.add(paymentField);
		paymentField.setColumns(10);

		JLabel totalLabel = new JLabel("Total (no tax):");
		totalLabel.setBounds(23, 247, 78, 14);
		contentPane.add(totalLabel);
		String payment = Float.toString(totalPayment);

		final JList listArea = new JList();
		listArea.setBackground(Color.LIGHT_GRAY);
		listArea.setBounds(352, 34, 101, 230);
		contentPane.add(listArea);
		listArea.setModel(DLM);


		////////////////////////////////////////////////

		final JRadioButton rdbtnPizza = new JRadioButton("Pizza");	
		panel.add(rdbtnPizza);
		rdbtnPizza.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnPizza.isSelected()){
					DLM.addElement(rdbtnPizza.getText());
					//adding item to temp order list
					tempOrder.add(rdbtnPizza.getText());
					totalPayment = totalPayment + pizzaPrice;
				}
				else{
					DLM.removeElement(rdbtnPizza.getText());
					//removing item from temp order list
					tempOrder.remove(rdbtnPizza.getText());
					totalPayment = totalPayment - pizzaPrice;	
				}
				updatePrice();
				System.out.println(totalPayment);
			}			
		});

		JLabel label = new JLabel("$11.99");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label.setForeground(Color.GRAY);
		panel.add(label);

		final JRadioButton rdbtnSalad = new JRadioButton("Salad");
		panel.add(rdbtnSalad);
		rdbtnSalad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnSalad.isSelected()){
					DLM.addElement(rdbtnSalad.getText());
					//adding item to temp order list
					tempOrder.add(rdbtnSalad.getText());
					totalPayment = totalPayment + saladPrice;
				}
				else{
					DLM.removeElement(rdbtnSalad.getText());
					//removing item from temp order list
					tempOrder.remove(rdbtnSalad.getText());
					totalPayment = totalPayment - saladPrice;
				}
				updatePrice();
				System.out.println(totalPayment);
			}			
		});

		JLabel label_1 = new JLabel("$3.50");
		label_1.setForeground(Color.GRAY);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel.add(label_1);

		final JRadioButton rdbtnSandwich = new JRadioButton("Sandwich");
		panel.add(rdbtnSandwich);
		rdbtnSandwich.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnSandwich.isSelected()){
					DLM.addElement(rdbtnSandwich.getText());
					//adding item to temp order list
					tempOrder.add(rdbtnSandwich.getText());
					totalPayment = totalPayment + sandwichPrice;
				}
				else{
					DLM.removeElement(rdbtnSandwich.getText());
					//removing item from temp order list
					tempOrder.remove(rdbtnSandwich.getText());
					totalPayment = totalPayment - sandwichPrice;	
				}
				updatePrice();
				System.out.println(totalPayment);
			}			
		});

		JLabel label_2 = new JLabel("$7.99");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_2.setForeground(Color.GRAY);
		panel.add(label_2);

		final JRadioButton rdbtnSoup = new JRadioButton("Soup");
		panel.add(rdbtnSoup);
		rdbtnSoup.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnSoup.isSelected()){
					DLM.addElement(rdbtnSoup.getText());
					//adding item to temp order list
					tempOrder.add(rdbtnSoup.getText());
					totalPayment = totalPayment + soupPrice;
				}
				else{
					DLM.removeElement(rdbtnSoup.getText());
					//removing item from temp order list
					tempOrder.remove(rdbtnSoup.getText());
					totalPayment = totalPayment - soupPrice;	
				}
				updatePrice();
				System.out.println(totalPayment);
			}			
		});

		final JRadioButton rdbtnSoda = new JRadioButton("Soda");
		panel_1.add(rdbtnSoda);
		rdbtnSoda.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnSoda.isSelected()){
					DLM.addElement(rdbtnSoda.getText());
					//adding item to temp order list
					tempOrder.add(rdbtnSoda.getText());
					totalPayment = totalPayment + sodaPrice;
				}
				else{
					DLM.removeElement(rdbtnSoda.getText());
					//removing item from temp order list
					tempOrder.remove(rdbtnSoda.getText());
					totalPayment = totalPayment - sodaPrice;	
				}
				updatePrice();
				System.out.println(totalPayment);
			}			
		});

		JLabel label_4 = new JLabel("$1.79");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_4.setForeground(Color.GRAY);
		panel_1.add(label_4);

		final JRadioButton rdbtnWater = new JRadioButton("Water");
		panel_1.add(rdbtnWater);
		rdbtnWater.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnWater.isSelected()){
					DLM.addElement(rdbtnWater.getText());
					//adding item to temp order list
					tempOrder.add(rdbtnWater.getText());
					totalPayment = totalPayment + waterPrice;
				}
				else{
					DLM.removeElement(rdbtnWater.getText());
					//removing item from temp order list
					tempOrder.remove(rdbtnWater.getText());
					totalPayment = totalPayment - waterPrice;	
				}
				updatePrice();
				System.out.println(totalPayment);
			}			
		});

		JLabel label_5 = new JLabel("$1.00");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_5.setForeground(Color.GRAY);
		panel_1.add(label_5);

		final JRadioButton rdbtnJuice = new JRadioButton("Juice");
		panel_1.add(rdbtnJuice);
		rdbtnJuice.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnJuice.isSelected()){
					DLM.addElement(rdbtnJuice.getText());
					//adding item to temp order list
					tempOrder.add(rdbtnJuice.getText());
					totalPayment = totalPayment + juicePrice;
				}
				else{
					DLM.removeElement(rdbtnJuice.getText());
					//removing item from temp order list
					tempOrder.remove(rdbtnJuice.getText());
					totalPayment = totalPayment - juicePrice;	
				}
				updatePrice();
				System.out.println(totalPayment);
			}			
		});

		JLabel label_6 = new JLabel("$1.50");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_6.setForeground(Color.GRAY);
		panel_1.add(label_6);

		final JRadioButton rdbtnTea = new JRadioButton("Tea");
		panel_1.add(rdbtnTea);
		rdbtnTea.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnTea.isSelected()){
					DLM.addElement(rdbtnTea.getText());
					//adding item to temp order list
					tempOrder.add(rdbtnTea.getText());
					totalPayment = totalPayment + teaPrice;
				}
				else{
					DLM.removeElement(rdbtnTea.getText());
					//removing item from temp order list
					tempOrder.remove(rdbtnTea.getText());
					totalPayment = totalPayment - teaPrice;	
				}
				updatePrice();
				System.out.println(totalPayment);
			}			
		});

		final JRadioButton rdbtnCookie = new JRadioButton("Cookie");
		panel_2.add(rdbtnCookie);
		rdbtnCookie.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnCookie.isSelected()){
					DLM.addElement(rdbtnCookie.getText());
					//adding item to temp order list
					tempOrder.add(rdbtnCookie.getText());
					totalPayment = totalPayment + cookiePrice;
				}
				else{
					DLM.removeElement(rdbtnCookie.getText());
					//removing item from temp order list
					tempOrder.remove(rdbtnCookie.getText());
					totalPayment = totalPayment - cookiePrice;	
				}
				updatePrice();
				System.out.println(totalPayment);
			}			
		});

		JLabel label_8 = new JLabel("$1.99");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_8.setForeground(Color.GRAY);
		panel_2.add(label_8);

		final JRadioButton rdbtnPie = new JRadioButton("Pie");
		panel_2.add(rdbtnPie);
		rdbtnPie.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnPie.isSelected()){
					DLM.addElement(rdbtnPie.getText());
					//adding item to temp order list
					tempOrder.add(rdbtnPie.getText());
					totalPayment = totalPayment + piePrice;
				}
				else{
					DLM.removeElement(rdbtnPie.getText());
					//removing item from temp order list
					tempOrder.remove(rdbtnPie.getText());
					totalPayment = totalPayment - piePrice;	
				}
				updatePrice();
				System.out.println(totalPayment);
			}			
		});

		JLabel label_9 = new JLabel("$4.99");
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_9.setForeground(Color.GRAY);
		panel_2.add(label_9);

		final JRadioButton rdbtnIcecream = new JRadioButton("Icecream");
		panel_2.add(rdbtnIcecream);
		rdbtnIcecream.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnIcecream.isSelected()){
					DLM.addElement(rdbtnIcecream.getText());
					//adding item to temp order list
					tempOrder.add(rdbtnIcecream.getText());
					totalPayment = totalPayment + icecreamPrice;
				}
				else{
					DLM.removeElement(rdbtnIcecream.getText());
					//removing item from temp order list
					tempOrder.remove(rdbtnIcecream.getText());
					totalPayment = totalPayment - icecreamPrice;	
				}
				updatePrice();
				System.out.println(totalPayment);
			}			
		});

		JLabel label_10 = new JLabel("$2.50");
		label_10.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_10.setForeground(Color.GRAY);
		panel_2.add(label_10);

		final JRadioButton rdbtnCake = new JRadioButton("Cake");
		panel_2.add(rdbtnCake);
		rdbtnCake.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnCake.isSelected()){
					DLM.addElement(rdbtnCake.getText());
					//adding item to temp order list
					tempOrder.add(rdbtnCake.getText());
					totalPayment = totalPayment + cakePrice;
				}
				else{
					DLM.removeElement(rdbtnCake.getText());
					//removing item from temp order list
					tempOrder.remove(rdbtnCake.getText());
					totalPayment = totalPayment - cakePrice;	
				}
				updatePrice();
				System.out.println(totalPayment);
			}			
		});

		JLabel label_3 = new JLabel("$4.99");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_3.setForeground(Color.GRAY);
		panel.add(label_3);

		JLabel label_7 = new JLabel("$1.75");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_7.setForeground(Color.GRAY);
		panel_1.add(label_7);

		JLabel label_11 = new JLabel("$4.99");
		label_11.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_11.setForeground(Color.GRAY);
		panel_2.add(label_11);

		JLabel lblYourFoodOrder = new JLabel("Order Selection(s):");
		lblYourFoodOrder.setBounds(347, 17, 119, 14);
		contentPane.add(lblYourFoodOrder);

		JLabel lblDishes = new JLabel("Dishes");
		lblDishes.setForeground(new Color(0, 0, 0));
		lblDishes.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDishes.setBounds(23, 68, 56, 14);
		contentPane.add(lblDishes);

		JLabel lblDrinks = new JLabel("Drinks");
		lblDrinks.setForeground(new Color(0, 0, 0));
		lblDrinks.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDrinks.setBounds(129, 68, 56, 14);
		contentPane.add(lblDrinks);

		JLabel lblDesserts = new JLabel("Desserts");
		lblDesserts.setForeground(new Color(0, 0, 0));
		lblDesserts.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDesserts.setBounds(225, 68, 76, 14);
		contentPane.add(lblDesserts);

		JLabel lblDishCategories = new JLabel("Meal Categories");
		lblDishCategories.setBounds(98, 27, 150, 23);
		contentPane.add(lblDishCategories);
		lblDishCategories.setFont(new Font("Tahoma", Font.PLAIN, 20));
		

		//////////////////////////MAKING A PAYMENT//////////////////////////
		
		JButton btnMakePayment = new JButton("Make Payment");
		btnMakePayment.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnMakePayment.setBounds(193, 241, 130, 23);
		contentPane.add(btnMakePayment);
		btnMakePayment.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				makePayment();
			}

		});

		JButton btnNewButton = new JButton("Back");
		btnNewButton.setBounds(15, 13, 64, 23);
		contentPane.add(btnNewButton);		
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CustomerHomePage chp = new CustomerHomePage();
				chp.setVisible(true);
				setVisible(false);
			}
		});
	}


	public void makePayment(){
		
		if(totalPayment != 0){		

			//add new empty order to list of orders
			currentUserData.addOrder(userOrder);
			//adding items to newly created order
			userOrder.addItemsFromList(tempOrder);

			JOptionPane.showMessageDialog(contentPane,"Order # " + MealStatService.getCurrentOrderId()
					+ "\nSubtotal: $" + (double)totalPayment/100
					+ "\nTax: $" + taxDisplay
					+ "\nTotal: $" + taxPayment);

			//redirect customer to customer home page
			CustomerHomePage chp = new CustomerHomePage();						
			chp.setVisible(true);
			setVisible(false);	

			//display to console
			System.out.println();
			System.out.println("-----------Order List-----------");
			System.out.println(userOrder.getOrder());

			System.out.println(MealStatService.getCurrentOrderId());
			System.out.println(userOrder.getOrder().toString());
			System.out.println(userOrder.getOrderStatus());
		}
		else{
			JOptionPane.showMessageDialog(contentPane,"No order selections have been made");
		}

		//adding order to database
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customers","root","");
			PreparedStatement ps = (PreparedStatement) con.prepareStatement("insert into orders(order_number, order_items, order_name, order_email, order_status) values(?,?,?,?,?)");
			ps.setInt(1,MealStatService.getCurrentOrderId());
			ps.setString(2,userOrder.getOrder().toString());
			ps.setString(3,currentUserData.getName());
			ps.setString(4,currentUserData.getEmail());
			ps.setString(5,userOrder.getOrderStatus());
			ps.execute();
		}catch(Exception e){System.out.print(e);}

	}




}
