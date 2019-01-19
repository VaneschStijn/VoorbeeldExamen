package be.pxl.ja.opgave1;

import java.nio.file.Path;
import java.util.List;

// Oefening geeft aan dat er twee implementaties zijn van het parsen van files, daarom een interface
public interface ActivityTrackerParser {

	List<Activity> parseFile(Path file, Logger logger, ActivityValidator activityValidator);

	ActivityTracker getActivityTracker();
}
