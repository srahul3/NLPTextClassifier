package com.search.invertedIndex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InvertedIndexAlgorithm {

	private String			mostUsedWord;
	private int				mostUsedWordCount	= 0;
	private static Set<String>	stopwords		= new HashSet<>(Arrays.asList("a",
														"able",
														"about",
														"across",
														"after",
														"all",
														"almost",
														"also",
														"am",
														"among",
														"an",
														"and",
														"any",
														"are",
														"as",
														"at",
														"be",
														"because",
														"been",
														"but",
														"by",
														"can",
														"cannot",
														"could",
														"dear",
														"did",
														"do",
														"does",
														"either",
														"else",
														"ever",
														"every",
														"for",
														"from",
														"get",
														"got",
														"had",
														"has",
														"have",
														"he",
														"her",
														"hers",
														"him",
														"his",
														"how",
														"however",
														"i",
														"if",
														"in",
														"into",
														"is",
														"it",
														"its",
														"just",
														"least",
														"let",
														"like",
														"likely",
														"may",
														"me",
														"might",
														"most",
														"must",
														"my",
														"neither",
														"no",
														"nor",
														"not",
														"of",
														"off",
														"often",
														"on",
														"only",
														"or",
														"other",
														"our",
														"own",
														"rather",
														"said",
														"say",
														"says",
														"she",
														"should",
														"since",
														"so",
														"some",
														"than",
														"that",
														"the",
														"their",
														"them",
														"then",
														"there",
														"these",
														"they",
														"this",
														"tis",
														"to",
														"too",
														"twas",
														"us",
														"wants",
														"was",
														"we",
														"were",
														"what",
														"when",
														"where",
														"which",
														"while",
														"who",
														"whom",
														"why",
														"will",
														"with",
														"would",
														"yet",
														"you",
														"your"));

	Map<String, List<Tuple>>	index			= new HashMap<>();

	public void indexFile(final String indexName, final String rawText) {
		assertNotNull("Index name must not be null or empty", indexName);
		assertNotNull("Raw text must not be null or empty", rawText);

		int pos = 0;
		int paraIdx = 0;
		for (String line : rawText.split("\\.")) {
			for (String _word : line.split("\\W+")) {
				String word = _word.toLowerCase();
				pos++;
				if (word.isBlank() || word.isEmpty()) {
					continue;
				}
				if (stopwords.contains(word)) {
					continue;
				}
				List<Tuple> idx = index.get(word);
				if (idx == null) {
					idx = new LinkedList<>();
					index.put(word, idx);
				}
				idx.add(new Tuple(line, indexName, paraIdx, pos));

				if (idx.size() > mostUsedWordCount) {
					mostUsedWord = word;
					mostUsedWordCount = idx.size();
				}
			}
			paraIdx++;
		}

		System.out.println("inverted indexed " + indexName + " " + pos + " words");
		aboutTopic();
	}

	private void aboutTopic() {
		instanceCountOf(mostUsedWord);
		instanceCountOf(mostUsedWord + "s");
		instanceCountOf(mostUsedWord + "n");
	}

	private void instanceCountOf(final String word) {
		List<Tuple> lines = index.get(word);
		int count = lines == null ? 0 : lines.size();
		System.out.println("This Paragraph appears to be about " + word + " count: " + count);
	}

	public List<String> getParagraphTopic() {
		return Arrays.asList(mostUsedWord, mostUsedWord + "s");
	}

	public List<Tuple> search(final List<String> words) {
		List<Tuple> result = new ArrayList<>();
		for (String _word : words) {
			String word = _word.toLowerCase();
			List<Tuple> idx = index.get(word);

			// if (log.isDebugEnabled()) {
			// System.out.print(word);
			// System.out.print(" found in ");
			// }

			if (idx != null) {
				result.addAll(idx);

				// Printing for debugging
				// if (log.isDebugEnabled()) {
				// for (Tuple tuple : idx) {
				// System.out.print(tuple);
				// System.out.println("");
				// }
				// }
			}

			// if (log.isDebugEnabled()) {
			// System.out.println("");
			// }

		}

		return result;
	}

	public static List<String> removeStopWords(final String line, final Set<String> additionalStopList) {
		List<String> result = new ArrayList<>();
		for (String _word : line.split("\\W+")) {
			String word = _word.toLowerCase();

			if (word.length() < 3) {
				continue;
			}
			if (stopwords.contains(word)) {
				continue;
			}

			if ((additionalStopList != null) && additionalStopList.contains(word)) {
				continue;
			}
			result.add(word);
		}
		return result;
	}

	/**
	 * @param indexName
	 * @param message
	 */
	private void assertNotNull(final String indexName, final String message) {
		if ((indexName == null) || indexName.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void main(final String[] args) {
		try {
			StringBuilder rawText = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new FileReader("paragraph.txt"))) {
				for (String para = reader.readLine(); para != null; para = reader.readLine()) {
					rawText.append(para);
				}
			}

			InvertedIndexAlgorithm idx = new InvertedIndexAlgorithm();
			idx.indexFile("RawText1", rawText.toString());
			idx.search(Arrays.asList("Zebra", "endangered", "Quagga"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class Tuple {
		private String	indexName;
		private int		paragraphNo;
		private int		colPosition;
		private String	paraText;

		public Tuple( final String paraText, final String indexName, final int para, final int colPosition) {
			this.paraText = paraText;
			this.indexName = indexName;
			this.colPosition = colPosition;
			paragraphNo = para;
		}

		@Override
		public String toString() {
			return String.format("file: %s, Paragraph#: %s, col: %s, Text: %s", indexName, paragraphNo, colPosition, paraText);
		}

		public Integer getParagraphNumber() {
			return paragraphNo;
		}
	}
}
