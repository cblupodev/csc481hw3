package section2;

import java.util.ArrayList;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import gameobjectmodel.GameObject;
import section1.Time;

public class Replay implements GameObject {
	
	public ArrayList<Event> log = new ArrayList<>();
	public boolean isRecording = false;
	public ServerClientMessage initialReplayState;
	public Time time;
	public int clientid;
	public boolean isInReplayMode = false;
	private long timeWhenHitRecord = 0;

	@Override
	public void onEvent(Event e) {
		if (isRecording == true) {
			log.add(e);
		}
	}
	
	public void startRecording() {
		isRecording = true;
		isInReplayMode = false;
		timeWhenHitRecord = Server.gametime.getTime();
	}
	
	// stop recording and go into replay mode
	public void stopRecording() {
		isRecording = false;
		isInReplayMode = true;
		if (time == null) { // only do this once
			long firstEventTimestamp = log.get(0).timestamp;
			long diffBetweenHitRecordAndFirstEvent = firstEventTimestamp - timeWhenHitRecord;
			time = new Time(Server.gametime, 1f, log.get(0).timestamp - diffBetweenHitRecordAndFirstEvent);
		}
	}
	
	public void endReplay() {
		isInReplayMode = false;
	}

}
