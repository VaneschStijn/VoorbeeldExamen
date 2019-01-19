package be.pxl.ja.opgave2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PassPhraseValidator<T> extends Thread {

	private List<T> passPhrases;
	private boolean valid;

	PassPhraseValidator(List<T> passPhrases) {
		this.passPhrases = passPhrases;
	}

	@Override
	public void run() {
		Set<T> uniquePhrases = new HashSet<>(passPhrases);
		valid = uniquePhrases.size() == passPhrases.size();
	}

	boolean isValid() {
		return valid;
	}

	String getPassPhrase() {
		return "[" + passPhrases.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
	}
}
