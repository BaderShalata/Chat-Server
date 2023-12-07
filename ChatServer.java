import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

// Is this a passive or active class?
public class ChatServer {

	private ArrayList<ChatRoom> rooms = new ArrayList<ChatRoom>();
	private static List<User> users = new ArrayList<User>();
	private Admin admin;

	private int capacity;
	private boolean isOpen;
	/** I ADDED THIS */
	private int originalCapacity = capacity;

	public ChatServer(int capacity, int numOfRooms, Admin admin) {
		// Set the initial value of class variables.
		/** I ADDED THIS */
		this.capacity = capacity;
		this.originalCapacity = capacity;
		// Think carefully about how to protect users from
		// unintended synchronous activity.
		/** I ADDED THIS */
		for(int i = 1; i<=numOfRooms;i++) {
			ChatRoom cr = new ChatRoom(i,5);
			rooms.add(cr);
		}
		// We initalise the admin attribute and call the
		// assignServer method of the admin with this object
		// as the parameter.
		this.admin = admin;
		admin.assignServer(this);
	}

	// Consider if this should be run asynchronously.
	public synchronized void open() {
		// Code to open the Chat Room.
		/** I ADDED THIS */
		isOpen = true;
		System.out.println("Chat Server is Opened.");
	}

	// Consider if this should be run asynchronously.
	public synchronized void close() {
		System.out.println("Chat Server is being Closed.");
		// Code to close the Chat Server.
		/** I ADDED THIS */
		// Think carefully about when you can successfully
		// close the Chat Server.
		if(capacity == originalCapacity) {
			System.out.println("Chat Server is Closed.");
			isOpen = false;
		}
		else {
			for (User u : users) {
				leave(u);
			}
			isOpen = false;
		}
	}

	// Consider if this should be run asynchronously.
	public synchronized boolean join(User user) throws InterruptedException{
		// Code for a User to enter the Chat Server.
		/** I ADDED THIS */
		if(capacity>0)
		{
			users.add(user);
			System.out.println("User " + user.getID() + " admitted to Chat Server (" + user.getWantToChat() + ").");
			capacity--;
			return true;
		}
		else
		{
			System.out.println("User " + user.getID() + " failed to join Chat Server (" + user.getWantToChat() + ").");
			wait();
			return false;
		}
		// Consider conditions that need to be true for this
		// to be successful.
		// Returns true if joined successfully, false otherwise.
	}

	// Consider if this should be run asynchronously.
	public synchronized void leave(User user) {
		// Code for a User to leave the Chat Server.
		/** I ADDED THIS */
		users.remove(user);
		System.out.println("User " + user.getID() + " left the Chat Server.");
		if(users.contains(user) == false) {
			System.out.println("Could not remove User " + user.getID() + " as is not in the Chat Server.");
			capacity++;
			notifyAll();
		}
	}

	/** OPEN METHOD OF CHAT ROOM NOT SERVER */
	public synchronized void openChatRoom(int chatRoomID) {
		// Code to open Chat Room.
		/** I ADDDED THIS */
		if(isRoomOpen(chatRoomID) == false)
		{
			rooms.get(chatRoomID).open();
		}

		/** YOU CAN ADD IF TRUE */
	}

	public synchronized void closeChatRoom(int chatRoomID) {
		// Code to close Chat Room.
		/** I ADDDED THIS */
		if(isRoomOpen(chatRoomID) == true)
		{
			rooms.get(chatRoomID).close();
		} else
		{
			System.out.println("Sorry, this chat room" + chatRoomID + "is not opened to be closed");
		}

	}

	public synchronized boolean enterRoom(User user, int chatRoomID) throws InterruptedException {
		// Code to allow user to enter Chat Room.
		/** I ADDED THIS */
		ChatRoom R = rooms.get(chatRoomID);
		if(R.enterRoom(user))
		{
			R.enterRoom(user);
			return true;
		}
		else
		{
			return false;
		}
	}
	public synchronized void leaveRoom(User user, int chatRoomID) {
		// Code to allow user to leave Chat Room.
		/** I ADDED THIS */
		ChatRoom L = rooms.get(chatRoomID);
		L.leaveRoom(user);
	}

	public int getNumberOfRooms() {
		return rooms.size();
	}

	public static void addUsers(User user)
	{
		users.add(user);
	}

	public boolean isRoomOpen(int chatRoomID) {

		return rooms.get(chatRoomID).getIsOpen();
	}

	public int getNumberOfUsers() {
		return users.size();
	}

}