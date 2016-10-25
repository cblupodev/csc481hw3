package section2;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import gameobjectmodel.GameObject;

public class EventManager {
	
	public ConcurrentHashMap<String, GameObject> registerMap;
	private Replay replays = new Replay();
	private Queue<Event> eventPriorityQueue;

	public EventManager() {
		registerMap = new ConcurrentHashMap<>();
		eventPriorityQueue = new PriorityBlockingQueue<Event>();
	}
	
	public void register(String type, GameObject handler) {
		registerMap.put(type, handler);
	}
	
	public void raise(Event e) {
		eventPriorityQueue.add(e);
	}
	
	public void handle() {
		while (eventPriorityQueue.peek() != null) { // handle all the events in one game loop, unles you run this in a new thread
			Event e = eventPriorityQueue.remove();
			GameObject go = registerMap.get(e.type);
			go.onEvent(e);
		}
	}
	
	public void addEvent(Event e) {
		e.timestamp = Server.gametime.getTime();
		eventPriorityQueue.add(e);
		replays.log.add(e);
	}

	public boolean isEmpty() {
		return eventPriorityQueue.isEmpty();
	}
	
}
