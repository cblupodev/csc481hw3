package section2;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import gameobjectmodel.GameObject;

public class EventManager {
	
	ConcurrentHashMap<String, GameObject> registerMap;
	
	PriorityBlockingQueue<Event> eventPriorityQueue;

	public EventManager() { }
	
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
			Set<String> s = registerMap.keySet();
			foreach (String )
		}
	}
	
}
