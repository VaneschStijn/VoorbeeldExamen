package be.pxl.ja.opgave1;

import java.util.Arrays;

public enum ActivityTracker {
	ENDOMODO, STRAVA;

	public static ActivityTracker fromName(String name) {
		return Arrays.stream(ActivityTracker.values()).filter(tracker -> tracker.name().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	public static boolean isActivityTrackerSupported(String name) {
		return fromName(name) == null;
	}
}
