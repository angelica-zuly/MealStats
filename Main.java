import java.awt.EventQueue;


public class Main {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				LogIn frame = new LogIn();
				frame.setVisible(true);
			
			}
		});
	}

}
