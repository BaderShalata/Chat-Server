import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

// Is this a passive or active class?
public class ChatRoom {
	
	private int chatRoomID;
	private int capacity;
	
	private List<User> users = new ArrayList<User>();
	private List<User> users1 = new ArrayList<User>();
	private boolean isOpen;
	private int originalCapacity = capacity;


	public ChatRoom(int chatRoomID, int capacity) {
		// Set the initial value of class variables.
		/** I ADDED THIS */
		this.chatRoomID = chatRoomID;
		this.capacity = capacity;
		// Think carefully about how to protect users from
		// unintended synchronous activity.
	}

	// Consider if this should be run asynchronously.
	public synchronized void open() {
		// Code to open the Chat Room.
		/** I ADDED THIS */
		isOpen = true;
		if(chatRoomID == 0)
		{
			System.out.println("Main Chat Room is Opened");
		}
		else {
			System.out.println("Chat Room " + chatRoomID + " open!");
		}
	}
	// Consider if this should be run asynchronously.
	public synchronized void close() {
		/** I ADDED THIS */
		if(capacity!=originalCapacity)
		{
			synchronized(users1) {
			for (User u : users1) {
				leaveRoom(u);
			}
		}
		}
		if(isOpen == true) {
			System.out.println("Chat Room " + chatRoomID + " is being closed!");
			// Code to close the Chat Room.
			isOpen = false;
			// Think carefully about when you can successfully
			// close the Chat Room.
			System.out.println("Chat Room " + chatRoomID + " closed!");
		}
		else if(!isOpen){
			System.out.println("This Chat Room " + chatRoomID + " is already closed");
		}
	}

	// Consider if this should be run asynchronously.
	public synchronized boolean enterRoom(User user)  throws InterruptedException{
		// Code for a User to enter a Chat Room.
		/** I ADDED THIS */
		if(capacity>0 && isOpen == true && users1.contains(user)== false )
		{
			System.out.println("User " + user.getID() + " joined Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
			capacity--;
			//users.add(user);
			users1.add(user);
			return true;
		}
		else
		{
			System.out.println("User " + user.getID() + " not joined Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
//			wait();
			return false;
		}
		// Consider conditions that need to be true for this 
		// to be successful. 
		// Returns true if joined successfully, false otherwise.
	}

	// Consider if this should be run asynchronously.
	public synchronized void leaveRoom(User user) {
		// Code for a User to leave a Chat Room.
		/** I ADDED THIS */
		if(users1.contains(user) == false )
		{
			System.out.println("User " + user.getID() + "CAN NOT  leave Chat Room  (NOT IN IT) " + chatRoomID + ". (" + user.getWantToChat() + ")");
		} else {
			users1.remove(user);
			capacity++;
			System.out.println("User " + user.getID() + " left Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
			notifyAll();
		}
	}

	public boolean getIsOpen() {
		return isOpen;
	}
}