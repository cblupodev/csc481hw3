package secondgame;

import gameobjectmodel.GameObject;
import gameobjectmodel.Movable;
import processing.core.PApplet;

public class MissleClient extends Movable implements GameObject {

	public int id;

	public MissleClient(int id) {
		this.id = id;
	}

	@Override
	public void onEvent(Event e) {
	}
	
	@Override
	public void draw(PApplet p) {
		setParent(p);
		getDrawing().drawFill(new int[] {255,255,255});
		getDrawing().drawRect(this.shape);
	}

}
