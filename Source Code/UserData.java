import java.util.ArrayList;
import java.util.List;

public class UserData {
	private String name;
	private String email;
	private String password;
	private List<Order> listOfOrders;
	
	public UserData(String name, String email, String password){
		this.name = name;
		this.email = email;
		this.password = password;
		listOfOrders = new ArrayList<Order>();
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getName() {
		return name;
	}
	
	public void addOrder(Order order) {
		listOfOrders.add(order);
	}
	
	public void removeOrder(Order order) {
		listOfOrders.remove(order);		
	}
		
	public List<Order> getListOfOrders() {
		return listOfOrders;
	}
	
	public String toString(){
		return name + ", " + email + ", " + password + ", " + listOfOrders.toString();
	}
}
