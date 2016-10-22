package section2;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;

import gameobjectmodel.GameObject;

public class EventManager {
	
	public ConcurrentHashMap<String, GameObject> registerMap;
	
	//BlockingQueue <Event> eventPriorityQueue;
	public Queue<Event> eventPriorityQueue;

	public EventManager() { 
		registerMap = new ConcurrentHashMap<>();
		eventPriorityQueue = new PriorityBlockingQueue<Event>();
		//eventPriorityQueue = new ConcurrentLinkedQueue<Event>();
	}
	
	public void register(String type, GameObject handler) {
		registerMap.put(type, handler);
	}
	
	public void raise(Event e) {
		eventPriorityQueue.add(e);
	}
	
	public void handle() {
		if (eventPriorityQueue.peek() != null) {
			Event e = eventPriorityQueue.remove();
			GameObject go = registerMap.get(e.type);
			go.onEvent(e);
		}
	}
	
}
