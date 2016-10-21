package section1;

public class Time {
	
	public Time anchor;
	public int ticSize;
	public int origin;
	public int tic;

	public Time(Time anchor, int ticSize, int origin) {
		this.anchor = anchor;
		this.ticSize = ticSize;
		this.origin = origin;
	}

	public int getTime() {
		return tic;
	}

}
