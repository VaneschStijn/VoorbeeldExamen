package be.pxl.ja.opgave1;

import java.util.Arrays;

public enum ActivityType {
	RUNNING(3), RIDING(1), SWIMMING(5);
	
	private int pointsPerKm;
	
	ActivityType(int pointsPerKm) {
		this.pointsPerKm = pointsPerKm;
	}
	
	public int getPointsPerKm() {
		return pointsPerKm;
	}

	public static ActivityType fromName(String name) {
		return Arrays.stream(ActivityType.values()).filter(type -> type.name().equalsIgnoreCase(name)).findFirst().orElseThrow(() -> new ActivityTypeNotFoundException(name));
	}
}
