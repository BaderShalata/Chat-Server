import java.util.ArrayList;

// Note: This is an active class and must implemnet runnable -
public class Admin implements Runnable {

	private static int sleepScale = 100;

	private String name;
	private ChatServer chatServer;
	private int numOfActions = 15;  //Could be either openning or closing a chatroom

	public Admin(String name) {
		// Set the initial value of class variables
		this.name = name;
	}

	public void assignServer(ChatServer chatServer) {
		// Store given Chat Server in Class Attribute
		this.chatServer = chatServer;
	}

	// Does this class require a run() method? If so consider how to ensure
	// when the thread is run that it performs all required actions.

	public void run() {
		//int randomRoomID = (int)(Math.random() * (4-2+1) + 2);
		// you need to open the chat server and the chat rooms
		/** I ADDED THIS */
		try {
			chatServer.open();
			chatServer.openChatRoom(0);
			chatServer.openChatRoom(1);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		// run actions randomly (HINT: you may use a randomised sleep time before doing the action)
		//close the chat server and the chat rooms
		chatServer.closeChatRoom(1);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		chatServer.closeChatRoom(0);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		chatServer.close();
	}
}
