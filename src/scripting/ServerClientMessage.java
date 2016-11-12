package scripting;

import java.util.ArrayList;
import java.util.Arrays;

// This is used because we haven't implemented an event system yet
// I'm passing fields because if I send the whole objects it really slows down the message passing

public class ServerClientMessage {
	@Override
	public String toString() {
		return "ServerClientMessage [floatPlatformShapeMessage=" + Arrays.toString(floatPlatformShapeMessage)
				+ ", cShapes=" + cShapes + ", cJumping=" + cJumping + ", cjumpingAngle=" + cjumpingAngle + ", cColor="
				+ cColor + "]";
	}

	float[]            floatPlatformShapeMessage;
	ArrayList<float[]> cShapes = new ArrayList<>();
	ArrayList<Boolean> cJumping = new ArrayList<>();
	ArrayList<Float>   cjumpingAngle = new ArrayList<>();
	ArrayList<int[]>   cColor = new ArrayList<>();
	
	public ServerClientMessage(float[] floatPlatformShapeMessage, ArrayList<float[]> cShapes,
			ArrayList<Boolean> cJumping, ArrayList<Float> cjumpingAngle, ArrayList<int[]> cColor) {
		this.floatPlatformShapeMessage = floatPlatformShapeMessage;
		this.cShapes = cShapes;
		this.cJumping = cJumping;
		this.cjumpingAngle = cjumpingAngle;
		this.cColor = cColor;
	}

	public ServerClientMessage() {
	}
}