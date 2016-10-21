package section1;

public class Time {
	
	public Time anchor;
	public long ticSize;
	public long origin;
	public long tic;

	public Time(Time anchor, int ticSize, int origin) {
		this.anchor = anchor;
		this.ticSize = ticSize;
		this.origin = origin;
	}

	public long getTime() {
		return tic;
	}
	
	private long getRealTime() {
		return System.nanoTime();
	}

}
