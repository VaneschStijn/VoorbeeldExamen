package be.pxl.ja.opgave1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// het process van een file parsen schilt enkel in de lijn, daarom een abstracte class
public abstract class AbstractActivityTrackerParser implements ActivityTrackerParser {

	@Override
	public List<Activity> parseFile(Path file, Logger logger, ActivityValidator activityValidator) {
		List<Activity> activities = new ArrayList<>();
		try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				// try catch rond elke lijn om eventuele fouten per lijn te kunnen loggen en verder te gaan
				try {
					Activity activity = extractActivity(line);
					addValidActivityToList(logger, activityValidator, activities, line, activity);
				} catch (Exception e) {
					logger.log(e.getMessage());
				}
			}
		} catch (IOException e) {
			logger.log(e.getMessage());
		}
		return activities;
	}

	protected abstract Activity extractActivity(String line);

	// de oefening stelt dat je een log moet maken met de specifieke lijn van een unknown customer
	// je kon hier al de customer points eentueel toekennen, maar verantwoordelijkheid van deze class is
	// het parsen, validatie kan nog eventueel bij deze verantwoordelijkheid passen, daarom hier valdiatie voor de logging
	private void addValidActivityToList(Logger logger, ActivityValidator activityValidator, List<Activity> activities, String line, Activity activity) {
		if (activityValidator.isCustomerValid(activity)) {
			activities.add(activity);
		} else {
			logger.log("UNKNOWN CUSTOMER: " + line);
		}
	}

	double getDistanceInKm(String section, ActivityType activityType) {
		double distance = Double.parseDouble(section);
		return activityType == ActivityType.SWIMMING ? distance / 1000 : distance;
	}


}
