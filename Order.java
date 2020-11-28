import java.util.ArrayList;
import java.util.List;

public class Order {
	private int orderID;
	private List<String> orderItems;
	private String status;

	public Order(int orderID){		
		this.orderID = orderID;
		orderItems = new ArrayList<>();
		status = StatusEnum.NO_STATUS.displayName();
	}	

	public int getOrderID(){
		return orderID;
	}

	public String getOrderStatus(){
		return status;
	}

	public void addItem(String item) {
		//adding item to customer's order (itemsList)
		orderItems.add(item);
	}

	public void addItemsFromList(List<String> items) {
		//adding a temp list of items to customer's order (itemsList)
		orderItems.addAll(items);
	}

	public void removeItem(String item) {
		//removing item from customer's order (itemsList)
		orderItems.remove(item);	
	}

	public List<String> getOrder() {
		return orderItems;
	}

	public String toString(){
		return orderID + ", " + orderItems + ", " + status;
	}

}
