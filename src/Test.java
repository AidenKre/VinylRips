import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

public class Test {
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		TimeCode start = new TimeCode("0:10");
		TimeCode end = new TimeCode("0:20");
		AudioFileCut.audioFileCut("Ride on Time Full Album.wav", "test2.wav", start, end);
		
	}
}
