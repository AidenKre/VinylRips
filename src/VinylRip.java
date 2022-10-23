import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.UnsupportedAudioFileException;

public class VinylRip {
	public static final String SIDE_A = "001-River's Island Side A.wav";
	public static final String SIDE_B = "002-River's Island Side B.wav";
	public static final String COMBINED_NAME = "River's Island Full Album.wav";
	public static final String FILE_PATH = "C:\\Users\\aiden\\OneDrive\\Documents\\Eclipse\\VinylRips\\files\\";
	public static final TimeCode START_A = new TimeCode("0:3");
	public static final TimeCode START_B = new TimeCode("0:4");
	public static final int LAST_SIDE_A = 4;
	//do not change anything after here
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		ArrayList<TimeCode> timeCodes = new ArrayList<>(); //stores time codes for every song
		ArrayList<String> fileNames = new ArrayList<>(); //stores file names
		File file = new File("list.txt");
		getTimeCodeList(file, timeCodes, fileNames);
		System.out.println(timeCodes);
		System.out.println(fileNames);
		splitTracks(SIDE_A, timeCodes, fileNames, 0, LAST_SIDE_A);
		getTimeCodeListSideB(timeCodes);
		splitTracks(SIDE_B, timeCodes, fileNames, LAST_SIDE_A, timeCodes.size() - 1);
		AudioFileCombine.audioFileCombine(SIDE_A, SIDE_B, FILE_PATH + COMBINED_NAME, START_A, START_B);
	}
	//takes file and gets time codes and song name and sorts them into two ArrayLists
	public static void getTimeCodeList(File file, ArrayList<TimeCode> timeCodes, ArrayList<String> fileNames) throws FileNotFoundException {
		Scanner scan = new Scanner(file);
		int count = 0;
		while (scan.hasNextLine()) {
			count++;
			Scanner line = new Scanner(scan.nextLine());
			timeCodes.add(new TimeCode(line.next())); //first thing in every line is a TimeCode
			String name = count + " " + line.next(); //will always be a song name (count for ordering of songs
			while (line.hasNext()) { //if sounds are multiple words long
				name = name + " " +line.next();
			}
			fileNames.add(name + ".wav"); //for file saving
		}
		timeCodes.set(0, timeCodes.get(0).add(START_A)); //adds the offset, only needed for the first song
	}
	public static void getTimeCodeListSideB(ArrayList<TimeCode> timeCodes) {
		for (int i = LAST_SIDE_A + 1; i < timeCodes.size(); i++) { //shifts all the sonds on side b so that the time codes line up
			timeCodes.set(i, timeCodes.get(i).subtract(timeCodes.get(LAST_SIDE_A)));
		}
		timeCodes.set(LAST_SIDE_A, new TimeCode()); //shifts the first song (side b) time code last
		timeCodes.set(LAST_SIDE_A, timeCodes.get(LAST_SIDE_A).add(START_B)); //adds offset for side b
	}
	//takes the time codes and file names to split one audio file
	public static void splitTracks(String filePath, ArrayList<TimeCode> timeCodes, ArrayList<String> fileNames, int start, int end) throws 
	UnsupportedAudioFileException, IOException {
		for (int i = start; i < end; i++) {
			AudioFileCut.audioFileCut(filePath, FILE_PATH + fileNames.get(i), timeCodes.get(i), timeCodes.get(i+1)); //pog
			
		}
	}

}
