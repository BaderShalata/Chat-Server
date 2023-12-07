import java.lang.reflect.Array;
import java.util.ArrayList;

public class Application implements Runnable {
	
	public static void main(String[] args) throws InterruptedException {

		// This provides an example of the classes you are required to 
		// code for this coursework running. 

		// If implemented correctly this code will run without exception
		// nor error (test multiple times). //However this does NOT mean
		// your implementation is correct and there may still be concurrency 
		// issues.

		Admin admin = new Admin("Liam");
		Thread at = new Thread(admin);
		ChatServer cs = new ChatServer(10, 2, admin);

		ArrayList<Thread> userThreads = new ArrayList<Thread>();
		at.start();

		/** THE PROBLEM IS WE ARE CREATING OBJECTS BUT WE ARE NOT ADDING THEM TO THE ARRAYLIST THATS WHY ITS NULL */
		/** HOW DO I CREATE AN ARRAYLIST AND USE IT IN JOIN() in USER CLASS */
		// Create 20 users with random UserIDs (1-100) and start their threads
		/** CREATED 20 THREADS */
		for (int i = 0; i < 20; i++) {
			int userID = (int)(Math.random() * (100-1+1) + 1);
			User u1 = new User(userID, cs);
			u1.User1(u1);
			cs.addUsers(u1);
			Thread u1t = new Thread(u1);
			/** I ADDED THIS */
			userThreads.add(u1t);
			u1t.start();			
		}
		// Make sure this waits for all user threads to end
		for (Thread t : userThreads) {
			t.join();
		}
		at.join();
	}

	@Override
	public void run() {

	}
}