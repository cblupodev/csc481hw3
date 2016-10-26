package section2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import com.google.common.collect.*;
import gameobjectmodel.GameObject;

public class EventManager {
	
	//ConcurrentHashMap<String, GameObject> registerMap;
	public Multimap<String,GameObject> registerMap = ArrayListMultimap.create();
	private Replay replays = new Replay();
	private Queue<Event> eventPriorityQueue = new PriorityBlockingQueue<Event>();;
	public boolean isRecording = false;

	public void register(String type, GameObject handler) {
		registerMap.put(type, handler);
	}
	
	public void raise(Event e) {
		eventPriorityQueue.add(e);
	}
	
	public void handle() {
		while (eventPriorityQueue.peek() != null) { // handle all the events in one game loop, unles you run this in a new thread
			Event e = eventPriorityQueue.remove();
			//Collection<GameObject> goList = registerMap.get(e.type);
			for (GameObject go : registerMap.values()) {
				go.onEvent(e);
			}
		}
	}
	
	public void addEvent(Event e) {
		e.timestamp = Server.gametime.getTime();
		eventPriorityQueue.add(e);
		if (isRecording = true) {
			replays.log.add(e);
		}
	}

	public boolean isEmpty() {
		return eventPriorityQueue.isEmpty();
	}
	
}
