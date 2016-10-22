package section2;

public class Event implements Comparable {
	
	public String type;
	public Object parameters;
	public long timestamp;
	public int age;
	public int order; // priority subdivision within the game loop
	
	public Event(String type, Object parameters, long timestamp) {
		this.type = type;
		this.parameters = parameters;
		this.timestamp = timestamp;
	}

	@Override
	public int compareTo(Object arg0) {
		Event e = (Event) arg0;
		if (this.timestamp == e.timestamp) {
			return 0;
		} else if(this.timestamp < e.timestamp) {
			return -1;
		} else {
			return 1;
		}
	}
	
}
