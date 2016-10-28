package section2;

import java.util.ArrayList;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import gameobjectmodel.GameObject;
import section1.Time;

public class Replay implements GameObject {
	
	public ArrayList<Event> log = new ArrayList<>();
	public Multimap<String,GameObject> registerMap = ArrayListMultimap.create();
	public boolean isRecording = false;
	public ServerClientMessage initialReplayState;
	public Time time;
	public int clientid;
	public boolean isInReplayMode = false;

	@Override
	public void onEvent(Event e) {
		if (isRecording == true) {
			log.add(e);
		}
	}
	
	public void startRecording() {
		isRecording = true;
		isInReplayMode = false;
	}
	
	// stop recording and go into replay mode
	public void stopRecording() {
		isRecording = false;
		isInReplayMode = true;
	}

}
