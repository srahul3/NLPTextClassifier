/**
 * 
 */
package com.search.classifier;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import com.search.datamodel.Questionnaire;
import com.search.invertedIndex.InvertedIndexAlgorithm;
import com.search.invertedIndex.InvertedIndexAlgorithm.Tuple;

/**
 * @author rahulsha
 */
public class InvertedIndexClassfier implements IScoreClassifier {

	private Questionnaire			questionare;
	private InvertedIndexAlgorithm	indexer;
	private HashSet<String>			topicList;

	public InvertedIndexClassfier( final Questionnaire input) {
		questionare = input;
		indexer = new InvertedIndexAlgorithm();
		indexer.indexFile("para", input.getTextToStudy());
	}
	// private static final Logger log = LogManager.getLogger();

	@Override
	public double[][] generateScore() {

		topicList = new HashSet<>(indexer.getParagraphTopic());

		LinkedHashMap<String, List<Tuple>> paragraphTupplesForQuestion = new LinkedHashMap<>();
		List<String> questions = questionare.getQestions();
		for (String question : questions) {
			List<String> questionFiltered = InvertedIndexAlgorithm.removeStopWords(question, topicList);
			List<Tuple> searchResult = indexer.search(questionFiltered);
			paragraphTupplesForQuestion.put(question, searchResult);

			// log.debug
		}

		LinkedHashMap<String, List<Tuple>> paragraphTupplesForAnswer = new LinkedHashMap<>();
		List<String> answers = questionare.getAnswers();
		for (String answer : answers) {
			List<String> answerFiltered = InvertedIndexAlgorithm.removeStopWords(answer, topicList);
			List<Tuple> searchResult = indexer.search(answerFiltered);
			paragraphTupplesForAnswer.put(answer, searchResult);

			// log.debug
		}

		// Now match score by creating Questions x Answers matrix
		return generateScore(paragraphTupplesForQuestion, paragraphTupplesForAnswer);
	}

	public double[][] generateScore(	final LinkedHashMap<String, List<Tuple>> paragraphTupplesForQuestion,
							final LinkedHashMap<String, List<Tuple>> paragraphTupplesForAnswer) {
		double[][] score = new double[paragraphTupplesForQuestion.size()][paragraphTupplesForAnswer.size()];

		Set<Entry<String, List<Tuple>>> questionsWraped = paragraphTupplesForQuestion.entrySet();
		Set<Entry<String, List<Tuple>>> answersWraped = paragraphTupplesForAnswer.entrySet();

		int rowIndex = 0;
		for (Entry<String, List<Tuple>> qEntry : questionsWraped) {
			int colIndex = 0;
			for (Entry<String, List<Tuple>> aEntry : answersWraped) {
				score[rowIndex][colIndex] = score(qEntry.getValue(), aEntry.getValue());
				colIndex++;
			}
			rowIndex++;
		}

		return score;
	}

	private double score(final List<Tuple> question, final List<Tuple> answer) {
		Set<Integer> qParagraphNumbers = new HashSet<>();
		for (Tuple q : question) {
			qParagraphNumbers.add(q.getParagraphNumber());
		}

		Set<Integer> aParagraphNumbers = new HashSet<>();
		for (Tuple a : answer) {
			aParagraphNumbers.add(a.getParagraphNumber());
		}

		double score = 0;
		double step = 1.0 / aParagraphNumbers.size();

		for (Integer aParaNumber : aParagraphNumbers) {
			if (qParagraphNumbers.contains(aParaNumber)) {
				score += step;
			}
		}
		return score;
	}

	@Override
	public Set<String> getTopic() {
		return topicList;
	}

	@Override
	public String getDescription() {
		return "Inverted index Question and Answer matcher based on common paragraph lines";
	}

}
