/**
 * 
 */
package com.search.classifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.search.datamodel.Questionnaire;
import com.search.invertedIndex.InvertedIndexAlgorithm;

/**
 * @author rahulsha
 */
public class NumberAndListOfItemsClassifier implements IScoreClassifier {

	private Questionnaire		questionare;
	private IParagraphTopicGetter	topicGetter;

	public NumberAndListOfItemsClassifier( final Questionnaire input, final IParagraphTopicGetter topicGetter) {
		questionare = input;
		this.topicGetter = topicGetter;
	}

	@Override
	public double[][] generateScore() {
		List<String> questions = questionare.getQestions();
		List<String> answerss = questionare.getAnswers();

		LinkedHashMap<String, Number> qmap = new LinkedHashMap<>();
		for (String text : questions) {
			List<String> filteredWords = InvertedIndexAlgorithm.removeStopWords(text, null);
			Number count = ListFinder.isList(text.toLowerCase(), filteredWords, false);
			qmap.put(text, count);

			System.out.println(text + ": " + count);
		}

		LinkedHashMap<String, Number> amap = new LinkedHashMap<>();
		for (String text : answerss) {
			List<String> filteredWords = InvertedIndexAlgorithm.removeStopWords(text, null);
			Number count = ListFinder.isList(text.toLowerCase(), filteredWords, true);
			amap.put(text, count);

			System.out.println(text + ": " + count);
		}

		double[][] score = new double[questions.size()][answerss.size()];
		int rowIndex = 0;
		for (Entry<String, Number> qEntry : qmap.entrySet()) {
			Number qVal = qEntry.getValue();

			int colIndex = 0;
			for (Entry<String, Number> aEntry : amap.entrySet()) {
				double scoreVal = 0;

				Number aVal = aEntry.getValue();
				if (!qVal.equals(Integer.valueOf(-1)) && !aVal.equals(Integer.valueOf(-1)) && qVal.equals(aVal)) {
					scoreVal = 5.0;
				}

				score[rowIndex][colIndex] = scoreVal;
				colIndex++;
			}
			rowIndex++;
		}

		return score;
	}

	@Override
	public Set<String> getTopic() {
		return topicGetter.getTopic();
	}

	private static class ListFinder {

		private static final Map<String, Number> word2NumMap = new HashMap<>();
		static {
			// Special words for numbers
			word2NumMap.put("dozen", 12);
			word2NumMap.put("score", 20);
			word2NumMap.put("gross", 144);
			word2NumMap.put("quarter", 0.25);
			word2NumMap.put("half", 0.5);
			word2NumMap.put("oh", 0);
			word2NumMap.put("a", 1);
			word2NumMap.put("an", 1);

			// Standard words for numbers
			word2NumMap.put("zero", 0);
			word2NumMap.put("one", 1);
			word2NumMap.put("two", 2);
			word2NumMap.put("three", 3);
			word2NumMap.put("four", 4);
			word2NumMap.put("five", 5);
			word2NumMap.put("six", 6);
			word2NumMap.put("seven", 7);
			word2NumMap.put("eight", 8);
			word2NumMap.put("nine", 9);
			word2NumMap.put("ten", 10);
			word2NumMap.put("eleven", 11);
			word2NumMap.put("twelve", 12);
			word2NumMap.put("thirteen", 13);
			word2NumMap.put("fourteen", 14);
			word2NumMap.put("fifteen", 15);
			word2NumMap.put("sixteen", 16);
			word2NumMap.put("seventeen", 17);
			word2NumMap.put("eighteen", 18);
			word2NumMap.put("nineteen", 19);
			word2NumMap.put("twenty", 20);
			word2NumMap.put("thirty", 30);
			word2NumMap.put("forty", 40);
			word2NumMap.put("fifty", 50);
			word2NumMap.put("sixty", 60);
			word2NumMap.put("seventy", 70);
			word2NumMap.put("eighty", 80);
			word2NumMap.put("ninety", 90);
			word2NumMap.put("hundred", 100);
			word2NumMap.put("thousand", 1000);
			word2NumMap.put("million", 1000000);
			word2NumMap.put("billion", 1000000000);
			word2NumMap.put("trillion", 1000000000000L);
		}

		private static Set<String> textAsList = new HashSet<>(Arrays.asList("how many"));

		public static Number isList(final String text, final List<String> filteredWords, final boolean isAnswer) {
			for (String word : filteredWords) {
				if (word2NumMap.containsKey(word.toLowerCase())) {
					return word2NumMap.get(word.toLowerCase());
				}
			}

			for (String keyWord : textAsList) {
				if (text.contains(keyWord)) {
					return 1;
				}
			}

			int count = 0;
			if (isAnswer) {
				String newText = text.replace("and", ",");
				count = newText.split(",").length;
				if (count < 2) {
					count = 0;
				}
			}

			if (count == 0) {
				count = -1;
			}
			return count;
		}
	}

	@Override
	public String getDescription() {
		return "A list and number classifier, if a given string has description of a number or contains a list of items forming a list";
	}

}
