//Aiden Kreeger
//Jan. 25th 2021
/* takes two audio files and combines them into one
 * used for combing two sides of a vinly record
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioFileCombine {
	public static void audioFileCombine(String filePath1, String filePath2, String destination, TimeCode offSet1, TimeCode offSet2) throws UnsupportedAudioFileException, IOException {
		File file1 = new File(filePath1);
		File file2 = new File(filePath2);
		AudioFileFormat fileFormat1 = AudioSystem.getAudioFileFormat(file1);
		AudioFileFormat fileFormat2 = AudioSystem.getAudioFileFormat(file2);
		AudioFormat format1 = fileFormat1.getFormat();
		AudioFormat format2 = fileFormat2.getFormat();
		AudioInputStream inputStream1 = AudioSystem.getAudioInputStream(file1);
		AudioInputStream inputStream2 = AudioSystem.getAudioInputStream(file2);
		float bytesPS = format1.getFrameSize() * format1.getFrameRate();
		if (!(bytesPS == format2.getFrameRate() * format2.getFrameSize())) {
			throw new IllegalArgumentException("Audio Files must have identical bit rate and sample rate");
		}
		inputStream1.skip((long) (offSet1.getTotalSeconds() * bytesPS));
		inputStream2.skip((long) (offSet2.getTotalSeconds() * bytesPS));
		byte[] byteArray1 = new byte[fileFormat1.getByteLength()];
		byte[] byteArray2 = new byte[fileFormat2.getByteLength()];
		byte[] combinedByteArray = new byte[byteArray1.length + byteArray2.length];
		inputStream1.read(byteArray1);
		inputStream2.read(byteArray2);
		for (int i = 0; i < byteArray1.length; i++) {
			combinedByteArray[i] = byteArray1[i];
		}
		for(int i = 0; i < byteArray2.length; i++) {
			combinedByteArray[i+byteArray1.length] = byteArray2[i];
		}
		InputStream combinedStream = new ByteArrayInputStream(combinedByteArray);
		AudioInputStream combinedAudioStream = new AudioInputStream(combinedStream, format1, combinedByteArray.length);
		File newFile = new File(destination);
		AudioSystem.write(combinedAudioStream, fileFormat1.getType(), newFile);
	}
}
