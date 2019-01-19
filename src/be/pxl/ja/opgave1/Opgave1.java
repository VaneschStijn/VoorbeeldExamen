package be.pxl.ja.opgave1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Eerst de makkelijke streams oplossen / opgave2, daarna de grotere oefening doen met de activity tracker implementaties
public class Opgave1 {
	public static void main(String[] args) {
		CustomerRepository customerRepository = new CustomerRepository();
		System.out.println("*** Klanten uit Louisville:");
		// TODO: 1
		long louisvilleCustomers = customerRepository.findAll().stream().filter(c -> c.getCity().equalsIgnoreCase("Louisville")).count();
		System.out.println(louisvilleCustomers);

		System.out.println("*** Jarige klanten: ");
		// TODO: 2
		LocalDate now = LocalDate.now();
		customerRepository
				.findAll()
				.stream()
				.filter(c -> c.getDateOfBirth().getMonth() == now.getMonth() && c.getDateOfBirth().getDayOfMonth() == now.getDayOfMonth())
				.forEach(c -> System.out.println(c.getName() + " " + c.getFirstName() + " " + c.getDateOfBirth()));

		System.out.println("*** 10 jongste klanten:");
		// TODO: 3
		customerRepository
				.findAll()
				.stream()
				.sorted((c1, c2) -> c2.getDateOfBirth().compareTo(c1.getDateOfBirth()))
				.limit(10)
				.forEach(c -> System.out.println(c.getName() + " " + c.getFirstName() + " " + c.getDateOfBirth()));
	
		ActivityProcessor activityFileProcessor = new ActivityProcessor(customerRepository);
		// TODO: 4
		List<Activity> allActivities = processFilesInDirectory("./resources/opgave1", activityFileProcessor);

		System.out.println("*** Top 10 klanten");
		// TODO: 5
		customerRepository
				.findAll()
				.stream()
				.sorted((c1, c2) -> c2.getPoints() - c1.getPoints())
				.limit(10)
				.forEach(c -> System.out.println(c.getName() + " " + c.getFirstName() + " " + c.getPoints()));
		
		System.out.println("** Alle activiteiten meest actieve klant (gesorteerd op datum):");
		// TODO: 6
		customerRepository
				.findAll()
				.stream()
				// iets andere manier, maar kan ook via sortering en dan eerste te nemen
				.min((c1, c2) -> c2.getPoints() - c1.getPoints())
				.ifPresent(c -> printActivities(c, allActivities));
	}

	private static List<Activity> processFilesInDirectory(String directory, ActivityProcessor activityFileProcessor) {
		Path directoryPath = Paths.get(directory);
		Path errorFile = Paths.get("resources/opgave1/log/errors.log");
		try {
			// alle files 1 voor 1 behandelen
			return Files
					.list(directoryPath)
					// enkel files geen directories
					.filter(p -> Files.isRegularFile(p))
					// process file
					.map(file -> activityFileProcessor.processActivities(file, errorFile))
					// resultaat is lijst, flatmap om van meerdere lijsten van activiteiten 1 lijst te maken
					.flatMap(activities -> activities.stream())
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private static void printActivities(Customer c, List<Activity> allActivities) {
		allActivities
				.stream()
				.filter(a -> a.getCustomerNumber().equals(c.getCustomerNumber()))
				.sorted((a1, a2) -> a2.getActivityDate().compareTo(a1.getActivityDate()))
				.forEach(a -> System.out.println(a.getActivityDate() + " " + a.getActivityType() + " " + getPoints(a)));
	}

	// cast to int, maakt dat de komma getallen wegvallen, net zoals in de customer points
	private static int getPoints(Activity a) {
		return (int) (a.getDistance() * a.getActivityType().getPointsPerKm());
	}
}
