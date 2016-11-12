package scripting;

public class Time {
	
	public Time anchor;
	public float ticSize;
	public long origin;
	public long tic = 0;
	private long lastTime = 0;
	private long lastTic = 0;
	private float originalTicSize = 0;

	public Time(Time anchor, float ticSize, long origin) {
		this.anchor = anchor;
		this.ticSize = ticSize;
		this.origin = origin;
		this.originalTicSize = ticSize;
	}

	public long getTime() {
		if (anchor == null) { // if this is real time
			return System.nanoTime();
		}
		long currentTime = anchor.getTime();
		tic += Math.floor((currentTime - lastTime) / ticSize); // if return zero then no harm done
		lastTime = currentTime;
		return tic;
	}
	
	public long getRealTime() {
		return System.nanoTime();
	}

	public void pause() {
		ticSize = Integer.MAX_VALUE;
	}
	
	public void resume() {
		ticSize = originalTicSize;
	}
	
	// did the tic advance the last time you checked?
	public boolean advanced() {
		lastTic = tic;
		tic = getTime();
		if (lastTic < tic) {
			return true;
		}
		return false;
	}
	
}
