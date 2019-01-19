package be.pxl.ja.opgave1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityProcessor {

	private static final String ERROR_INVALID_FILENAME = "INVALID FILENAME";
	private CustomerRepository customerRepository;
	private Map<ActivityTracker, ActivityTrackerParser> activityTrackerParserMap = new HashMap<>();
	private ActivityValidator activityValidator;
	
	public ActivityProcessor(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
		activityValidator = new ActivityValidator(customerRepository);
		activityTrackerParserMap.put(ActivityTracker.ENDOMODO, new EndomondoActivityTrackerParserImpl());
		activityTrackerParserMap.put(ActivityTracker.STRAVA, new StravaActivityTrackerParserImpl());
	}
	
	public List<Activity> processActivities(Path activityFile, Path errorFile) {
		Logger logger = new Logger(errorFile, activityFile);
		if (isValidActivityFile(activityFile, logger)) {
			return parseFile(activityFile, logger);
		}
		return Collections.emptyList();
	}

	private List<Activity> parseFile(Path activityFile, Logger logger) {
		ActivityTracker activityTracker = ActivityTracker.fromName(getActivityTrackerPrefix(activityFile));
		List<Activity> activities = activityTrackerParserMap.get(activityTracker).parseFile(activityFile, logger, activityValidator);
		activities.forEach(this::addToCustomer);
		return activities;
	}

	private void addToCustomer(Activity activity) {
		customerRepository.getByCustomerNumber(activity.getCustomerNumber()).addPointsOfActivity(activity);
	}

	private String getActivityTrackerPrefix(Path file) {
		String fileName = file.getFileName().toString();
		int endIndex = fileName.indexOf('_');
		return endIndex > 0 ? fileName.substring(0, endIndex) : fileName;
	}

	private boolean isValidActivityFile(Path activityFile, Logger logger) {
		if (ActivityTracker.isActivityTrackerSupported(getActivityTrackerPrefix(activityFile))) {
			logger.log(ERROR_INVALID_FILENAME);
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
			return super.toString();
	}
}
