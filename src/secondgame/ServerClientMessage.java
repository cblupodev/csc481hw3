package secondgame;

import java.util.ArrayList;
import java.util.Arrays;

// This is used because we haven't implemented an event system yet
// I'm passing fields because if I send the whole objects it really slows down the message passing

public class ServerClientMessage {
	
	float[]            floatPlatformShapeMessage;
	ArrayList<float[]> cShapes = new ArrayList<>();
	ArrayList<int[]>   cColor = new ArrayList<>();
	public ArrayList<float[]> missles = new ArrayList<>();
	public ArrayList<ArrayList<float[]>> enemyColumns = new ArrayList<>();
	
	public ServerClientMessage(float[] floatPlatformShapeMessage, ArrayList<float[]> cShapes, ArrayList<int[]> cColor, ArrayList<float[]> missles) {
		this.floatPlatformShapeMessage = floatPlatformShapeMessage;
		this.cShapes = cShapes;
		this.cColor = cColor;
		this.missles = missles;
	}

	public ServerClientMessage() {
	}
}