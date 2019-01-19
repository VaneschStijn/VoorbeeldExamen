package be.pxl.ja.opgave1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StravaActivityTrackerParserImpl extends AbstractActivityTrackerParser {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	protected Activity extractActivity(String line) {
		String[] sections = line.split(";");
		ActivityType activityType = ActivityType.fromName(sections[2]);
		return new Activity(
				extractCustomerNumber(sections[0]),
				LocalDate.from(DATE_TIME_FORMATTER.parse(sections[1])),
				activityType,
				getDistanceInKm(sections[3], activityType),
				getActivityTracker()
				);
	}

	private String extractCustomerNumber(String section) {
		return section.substring(section.lastIndexOf(' ') + 1);
	}

	@Override
	public ActivityTracker getActivityTracker() {
		return ActivityTracker.STRAVA;
	}
}
