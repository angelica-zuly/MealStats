
public enum StatusEnum {
	NO_STATUS("No Status"),
	RECEIVED_ORDER("Received Order"),
	PREPARING_ORDER("Preparing Order"),
	ORDER_DONE("Order Done"),
	PICK_UP("Picked Up");	
	
	private String displayName;
	
	private StatusEnum (String displayName){
		this.displayName = displayName;
		
	}
	
	public String displayName(){
		return displayName;
	}
}
 