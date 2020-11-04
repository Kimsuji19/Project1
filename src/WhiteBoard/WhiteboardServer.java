package WhiteBoard;

import java.net.*;
import java.util.*;

import chat.ChatHandler;
import chat.PrintDebugMessage;

public class WhiteboardServer {
	private Vector<WhiteboardHandler> handlers;
	
	public WhiteboardServer (int port) {
		try {
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket (port);
			handlers = new Vector<WhiteboardHandler>();
			System.out.println("WhiteboardServer is ready.");
			while (true) {
				Socket s = server.accept ();
				WhiteboardHandler c = new WhiteboardHandler(this, s);
				c.start ();
			}
		} catch(Exception e) {
			PrintDebugMessage.print(e);
		}
	}

	public Object getHandler(int index) {
		return handlers.elementAt(index);
	}

	public void register(WhiteboardHandler c) {
		handlers.addElement(c);
	}

	public void unregister(Object o) {
		handlers.removeElement(o);
	}

	public void broadcast (String msg) {
		synchronized (handlers) {
			int n = handlers.size();
			for(int i=0; i < n; i++) {
				WhiteboardHandler c = (WhiteboardHandler) handlers.elementAt(i);
				try {
					c.println(msg);
				} catch (Exception ex) {
					PrintDebugMessage.print(ex);
				}
			}
		}
	}

	public static void main (String args[]) {
		WhiteboardServer server = new WhiteboardServer(9850);
	}
 }
