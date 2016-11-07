package section2;

public class Event implements Comparable<Object> {

	@Override
	public String toString() {
		return "Event [type=" + type + ", parameters=" + parameters + ", timestamp=" + timestamp + ", age=" + age
				+ ", priority=" + priority + "]";
	}

	public String type;
	public Object parameters;
	public long timestamp;
	public int age;
	public int priority; // most important priorities start at 0
	
	public Event(String type, Object parameters, int age, int priority) {
		this.type = type;
		this.parameters = parameters;
	}

	@Override
	public int compareTo(Object arg0) {
		Event e = (Event) arg0;
		int rtn = 0;
		// compare based on timestamp first
		if (this.timestamp == e.timestamp) {
			rtn = 0;
		} else if (this.timestamp < e.timestamp) {
			rtn = -1;
		} else {
			rtn = 1;
		}
		return rtn;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (timestamp != other.timestamp)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
