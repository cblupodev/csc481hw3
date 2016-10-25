package gameobjectmodel;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import processing.core.PApplet;
import section1.Time;
import section2.Event;
import section2.EventManager;
import section2.FloatingPlatform;
import section2.Server;

public class Physics extends PApplet implements Component {
	
	// reference the floating platform
	public FloatingPlatform floatingPlatform;
	private Type EventType = new TypeToken<Event>() {}.getType();
	private EventManager events;
	
	public Physics(EventManager events) {
		this.events = events;
	}

	public Physics() { }

	public float sinWrap(float f) {
		return sin(f);
	}
	
	public float radiansWrap(float f) {
		return radians(f);
	}
	
	// detect collisions for all the characters
	public void collision() {
		ArrayList<Event> eventList = new ArrayList<>();
		try {
			for (int j = 0; j < Server.characters.size(); j++) {
				section2.CharacterServer c = Server.characters.get(j);
				// check immovables
				for (int i = 0; i < Server.immovables.size(); i++) {
					if (c.type.equals("rect")) { // if its a rectangle
						if (Server.immovables.get(i).type.equals("rect")) {
							if (rectRectWrap(Server.immovables.get(i).shape, c.shape)) {
								events.addEvent(new Event("collision,"+j, c.color, 0, 0)); // TODO do i need this color paramter
							}
						} else if (Server.immovables.get(i).type.equals("line")) {
							if (lineRectWrap(Server.immovables.get(i).shape, c.shape)) {
								events.addEvent(new Event("collision,"+j, c.color, 0, 0));
							}
						}
					}
				}
				if (rectRectWrap(c.shape, floatingPlatform.shape)) {
					events.addEvent(new Event("collision,"+j, c.color, 0, 0));
				} 
			}
		} catch (Exception e) {
			// this will probably catch the exceptions thrown from methods with mismatched shapes 
			System.out.println(e.getMessage());
		}
	}
	
	// collision detection between two lines
	// copied from https://github.com/jeffThompson/CollisionDetection
	// LINE/LINE
	boolean lineLine(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {

		  // calculate the distance to intersection point
		  float uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));
		  float uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

		  // if uA and uB are between 0-1, lines are colliding
		  if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
		    
		    // optionally, draw a circle where the lines meet
		    float intersectionX = x1 + (uA * (x2-x1));
		    float intersectionY = y1 + (uA * (y2-y1));
		    
		    return true;
		  }
		  return false;
		}
	
	// collision detection between a rectangle and rectangle
	// copied from https://github.com/jeffThompson/CollisionDetection
	// RECTANGLE/RECTANGLE
	boolean rectRect(float r1x, float r1y, float r1w, float r1h, float r2x, float r2y, float r2w, float r2h) {
	  
	  // are the sides of one rectangle touching the other?
	  
	  if (r1x + r1w >= r2x &&    // r1 right edge past r2 left
	      r1x <= r2x + r2w &&    // r1 left edge past r2 right
	      r1y + r1h >= r2y &&    // r1 top edge past r2 bottom
	      r1y <= r2y + r2h) {    // r1 bottom edge past r2 top
	        return true;
	  }
	  return false;
	}
	
	// collision detection between a line and rectangle
	// copied from https://github.com/jeffThompson/CollisionDetection
	// LINE/RECTANGLE
	boolean lineRect(float x1, float y1, float x2, float y2, float rx, float ry, float rw, float rh) {

	  // check if the line has hit any of the rectangle's sides
	  // uses the Line/Line function below
	  boolean left =   lineLine(x1,y1,x2,y2, rx,ry,rx, ry+rh);
	  boolean right =  lineLine(x1,y1,x2,y2, rx+rw,ry, rx+rw,ry+rh);
	  boolean top =    lineLine(x1,y1,x2,y2, rx,ry, rx+rw,ry);
	  boolean bottom = lineLine(x1,y1,x2,y2, rx,ry+rh, rx+rw,ry+rh);

	  // if ANY of the above are true, the line 
	  // has hit the rectangle
	  if (left || right || top || bottom) {
	    return true;
	  }
	  return false;
	}
	
	boolean lineRectWrap(float[] r1, float[] r2) {
		return lineRect(r1[0], r1[1], r1[2], r1[3], r2[0], r2[1], r2[2], r2[3]);
	}
	
	boolean rectRectWrap(float[] r1, float[] r2) {
		return rectRect(r1[0], r1[1], r1[2], r1[3], r2[0], r2[1], r2[2], r2[3]);
	}
}