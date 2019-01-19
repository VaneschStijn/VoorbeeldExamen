package be.pxl.ja.opgave1;

import java.nio.file.Path;
import java.util.List;

public interface ActivityTrackerParser {

	List<Activity> parseFile(Path file, Logger logger, ActivityValidator activityValidator);

	ActivityTracker getActivityTracker();
}
