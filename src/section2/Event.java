package section2;

public class Event {
	
	public String type;
	public Object parameters;
	public long timestamp;
	
	public Event(String type, Object parameters, int timestamp) {
		this.type = type;
		this.parameters = parameters;
		this.timestamp = timestamp;
	}
	
}
