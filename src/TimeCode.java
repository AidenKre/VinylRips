/* Aiden Kreeger 1/24/2022
 * TimeCode method to read and easily translate MM:SS
 * time codes into S where M = minutes and s = seconds. */

public class TimeCode {
	//FEILDS
	private int minutes;
	private int seconds;
	//CONSTRUCTORS
	
	public TimeCode() {
		this.minutes = 0;
		this.seconds = 0;
	}
	public TimeCode(String s) {
		int count = 0;
		String min = Character.toString(s.charAt(count));
		count++;
		try {
		while (!Character.toString(s.charAt(count)).equals(":")) { //while the count is not :
			min = min +  Character.toString(s.charAt(count));
			count++;
			}
		} catch (IndexOutOfBoundsException ex)
		{ throw new IllegalArgumentException("(TimeCode) " + s + " must contain ':'"); }
			count++;
			this.minutes = Integer.parseInt(min);
			String sec = Character.toString(s.charAt(count));
			count++;
			for (int i = count; i < s.length(); i++) {
				sec = sec + Character.toString(s.charAt(i));
			}
			this.seconds = Integer.parseInt(sec);
	} 
	//METHODS
	public int getTotalSeconds() {
		return this.minutes * 60 + this.seconds;
	}
	public int getMinutes() {
		return this.minutes;
	}
	public int getSeconds() {
		return this.seconds;
	}
	public String toString() {
		return this.minutes + ":" + this.seconds;
	}
	public TimeCode subtract(TimeCode time) {
		this.minutes = this.minutes - time.getMinutes();
		this.seconds = this.seconds - time.getSeconds();
		while (seconds < 0) {
			minutes--;
			seconds += 60;
		}
		return this;
	}
	public TimeCode add(TimeCode time) {
		this.minutes = this.minutes + time.getMinutes();
		this.seconds = this.seconds + time.getSeconds();
		return this;
	}
	//TEST
	public static void main(String[] args) {
		TimeCode timeCode = new TimeCode("26:37");
		TimeCode lessTime = new TimeCode("26:00");
		System.out.println(timeCode.getMinutes());
		System.out.println(timeCode.getSeconds());
		System.out.println(timeCode.getTotalSeconds());
		System.out.println(timeCode);
		timeCode.subtract(lessTime);
		System.out.println(timeCode);
	}
}