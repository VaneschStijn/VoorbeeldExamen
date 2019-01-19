package be.pxl.ja.opgave2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Opgave2 {

	/*
	* Dit is een mogelijke oplossing, niet de enige!
	*/
	public static void main(String[] args) {
		Path passPhrasesFile = Paths.get("./resources/opgave2/passphrases.txt");

		List<PassPhraseValidator<String>> passPhraseValidators = parsePassPhraseFile(passPhrasesFile);

		// per thread wachten op resultaat, filter de valid threads eruit en tel ze op
		long numberValidPassPhrases = passPhraseValidators.stream().map(Opgave2::waitForThreadToFinishAndReturnResult).filter(p -> p).count();

		System.out.println("Aantal geldige wachtwoordzinnen: " + numberValidPassPhrases);
	}

	// Threads zijn aan het draaien, je moet dan ook wachten tot ze allen gedaan zijn via join(), nadien kan je resultaat bekijken
	// Merk op dat de PassPhraseValidatorTester eigenlijk fout is omdat deze niet wacht op de threads, toevallig loopt alles goed
	// synchronized block had ook mogelijk geweest voor het optellen, maar dat is complexer en wordt meestal gebruikt voor langlopende threads die uitwisseling doen van data
	private static Boolean waitForThreadToFinishAndReturnResult(PassPhraseValidator<String> p) {
		try {
			p.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return p.isValid();
	}

	// opstarten van validator per lijn
	private static List<PassPhraseValidator<String>> parsePassPhraseFile(Path passPhrasesFile) {
		List<PassPhraseValidator<String>> passPhraseValidators = new ArrayList<>();
		try (BufferedReader bufferedReader = Files.newBufferedReader(passPhrasesFile)) {
			String passPhraseLine;
			while ((passPhraseLine = bufferedReader.readLine()) != null){
				PassPhraseValidator<String> passPhraseValidator = new PassPhraseValidator<>(Arrays.asList(passPhraseLine.split(" ")));
				passPhraseValidator.start();
				passPhraseValidators.add(passPhraseValidator);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return passPhraseValidators;
	}
}
