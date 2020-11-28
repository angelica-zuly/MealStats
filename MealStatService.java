import java.util.ArrayList;
import java.util.List;


public class MealStatService {

	private static List<UserData> userDataList = new ArrayList<>();
	private static String currentUserEmail;	//current user
	private static int orderID = 0;

	public static int getCurrentOrderId(){
		return orderID;
	}
	
	public static int getNextOrderId(){
		orderID = (int)System.currentTimeMillis()/1000;
		return orderID;
	}

	public static void addUserData(UserData data) {
		//adding user to ArrayList of customers
		if (data != null) {
			userDataList.add(data);
		}
	}

	public static void setCurrentUser(String email) {
		//setting current user via their sign-in email
		currentUserEmail = email;
	}

	public static UserData getCurrentUserData() {
		UserData result = null;

		if (currentUserEmail != null) {
			System.out.println("Current email is: " + currentUserEmail);
			for (UserData data : userDataList) {
				String email = data.getEmail();

				if (currentUserEmail.equals(email)) {
					// User data found in the userDataList.
					result = data;
				}
			}
		} 
		else {
			System.out.println("Error 1: No current email was found");
		}

		if (result == null) {
			System.out.println("Error 2: Current email is NULL");
		}

		return result;
	}
}
