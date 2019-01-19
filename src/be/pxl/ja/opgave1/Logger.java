package be.pxl.ja.opgave1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

class Logger {

	private Path errorFile;
	private String sourceFile;

	Logger(Path errorFile, Path sourceFile) {
		this.errorFile = errorFile;
		this.sourceFile = sourceFile.getFileName().toString();
		createErrorFileIfNeeded(errorFile);
	}

	private void createErrorFileIfNeeded(Path errorFile) {
		if (!Files.exists(errorFile)) {
			try {
				Files.createDirectories(errorFile.getParent());
				Files.createFile(errorFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	void log(String error) {
		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(errorFile, StandardOpenOption.APPEND)) {
			bufferedWriter.write(String.join(" - " , LocalDateTime.now().toString(), sourceFile, error));
			bufferedWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
