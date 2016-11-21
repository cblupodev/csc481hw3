package secondgame;

import java.util.ArrayList;
import java.util.Arrays;

// This is used because we haven't implemented an event system yet
// I'm passing fields because if I send the whole objects it really slows down the message passing

public class ServerClientMessage {
	
	public float[]            floatPlatformShapeMessage;
	public ArrayList<float[]> cShapes = new ArrayList<>();
	public ArrayList<int[]>   cColor = new ArrayList<>();
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

	@Override
	public String toString() {
		String rtn = "";
		rtn += "   floatPlatformShapeMessage" + Arrays.toString(floatPlatformShapeMessage);
		rtn += "   cShapes ";
		for (float[] fa : cShapes) {
			rtn += Arrays.toString(fa);
		}
		rtn += "   cColor";
		for (int[] ia : cColor) {
			rtn += Arrays.toString(ia);
		}
		rtn += "   missles";
		for (float[] fa : missles) {
			rtn += Arrays.toString(fa);
		}
		rtn += "   enemyColumns";
		for (ArrayList<float[]> afa: enemyColumns) {
			for (float[] fa : afa) {
				rtn += Arrays.toString(fa);
			}
		}
		return rtn+"\n";
	}
}