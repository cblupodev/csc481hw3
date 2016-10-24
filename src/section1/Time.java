package section1;

public class Time {
	
	public Time anchor;
	public long ticSize;
	public long origin;
	public long tic = 0;
	private long lastTime = 0;

	public Time(Time anchor, int ticSize, int origin) {
		this.anchor = anchor;
		this.ticSize = ticSize;
		this.origin = origin;
	}

	public long getTime() {
		if (anchor == null) { // if this is real time
			return System.nanoTime();
		}
		long currentTime = anchor.getTime();
		//if (currentTime - lastTime >= ticSize) {
			tic += Math.floor((currentTime - lastTime) / ticSize); // if return zero then no harm done
		//}
		lastTime = currentTime;
		return tic;
	}
	
	public long getRealTime() {
		return System.nanoTime();
	}
	
}