package be.pxl.ja.opgave1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EndomondoActivityTrackerParserImpl extends AbstractActivityTrackerParser {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

	protected Activity extractActivity(String line) {
		String[] sections = line.split(";");

		ActivityType activityType = ActivityType.fromName(sections[2]);
		return new Activity(
				sections[1],
				LocalDate.from(DATE_TIME_FORMATTER.parse(sections[0])),
				activityType,
				getDistanceInKm(sections[3], activityType),
				getActivityTracker());
	}

	@Override
	public ActivityTracker getActivityTracker() {
		return ActivityTracker.ENDOMODO;
	}
}
