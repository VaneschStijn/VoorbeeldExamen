package be.pxl.ja.opgave1;

public class ActivityTypeNotFoundException extends RuntimeException{
	public ActivityTypeNotFoundException(String name) {
		super("Activity type not found: " + name);
	}
}
