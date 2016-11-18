package secondgame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import com.google.common.collect.*;
import gameobjectmodel.GameObject;

public class EventManager {
	
	// store handlers that are registered
	public ConcurrentHashMap<String, ArrayList<GameObject>> registerMap = new ConcurrentHashMap<>();
	// priority queue to store unhandled events
	private Queue<Event> eventPriorityQueue = new PriorityBlockingQueue<Event>();

	public void register(String type, GameObject handler) {
		ArrayList<GameObject> handlers = registerMap.get(type);
		if (handlers == null) {
			registerMap.put(type, new ArrayList<GameObject>()); // initialize the array list for each new key
			handlers = registerMap.get(type);
		}
		handlers.add(handler);
	}
	
	private void raise(Event e) {
		eventPriorityQueue.add(e);
	}
	
	public void handle() {
		while (eventPriorityQueue.peek() != null) { // handle all the events in one game loop, unles you run this in a new thread
			Event e = eventPriorityQueue.remove();
			ArrayList<GameObject> goList = registerMap.get(e.type);
			for (GameObject go : goList) {
				go.onEvent(e);
			}
		}
	}
	
	public void addEvent(Event e) {
		e.timestamp = Server.gametime.getTime();
		boolean duplicate = false;
		for (Event event : eventPriorityQueue) {
			if (e.equals(event)) {
				duplicate = true;
				break;
			}
		}
		if (duplicate == false) {
			eventPriorityQueue.add(e);
		}
	}

	public boolean isEmpty() {
		return eventPriorityQueue.isEmpty();
	}
	
}
