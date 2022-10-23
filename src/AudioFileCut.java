/* Aiden Kreeger 1/24/2022
 *takes one audio file and creates a new audio file that is a section of the original
 * adapted from https://stackoverflow.com/questions/37400771/java-audioinputstream-cutting-a-wav-file */
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioFileCut {
	public static void audioFileCut(String filePath, String destinationPath, TimeCode start, TimeCode end) throws UnsupportedAudioFileException, IOException {
		File source = new File(filePath);
		AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(source);
		AudioFormat format = fileFormat.getFormat();
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(source);
		int bytesPS = format.getFrameSize() * (int) format.getFrameRate();
		inputStream.skip(bytesPS * start.getTotalSeconds());
		long duration = end.getTotalSeconds() - start.getTotalSeconds();
		duration = duration * bytesPS / format.getFrameSize();
		AudioInputStream shorterStream = new AudioInputStream(inputStream, format, duration);
		File newFile = new File(destinationPath);
		AudioSystem.write(shorterStream, fileFormat.getType(), newFile);
	}
}
