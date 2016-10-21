package gameobjectmodel;

// just a marker interface to tag something as a game object

import section2.Event;

public interface GameObject {
	
	public void onEvent(Event e);
	
}