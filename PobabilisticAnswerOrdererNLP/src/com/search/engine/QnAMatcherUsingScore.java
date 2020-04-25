package com.search.engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class takes probability matrix as input and orders the answer.
 * y-axis - questions
 * x- axis - answers
 * 
 * @author rahulsha
 */
public class QnAMatcherUsingScore {
	// private static final Logger log = LogManager.getLogger();

	private List<String>	qestions;
	private List<String>	answers;
	private double[][]	scoreMatrix;

	public QnAMatcherUsingScore(	final List<String> qestions,
						final List<String> answers,
						final double[][] scoreMatrix) {
		this.qestions = qestions;
		this.answers = answers;
		this.scoreMatrix = scoreMatrix;
	}

	/**
	 * A ordering logic
	 * 
	 * @return
	 */
	public Map<String, String> matchBestQuestionAndAnswer() {
		Map<String, String> result = new LinkedHashMap<>();

		Map<String, double[]> quesVsAnsScore = new LinkedHashMap<>();
		int qIndex = 0;
		for (String q : qestions) {
			quesVsAnsScore.put(q, scoreMatrix[qIndex]);
			qIndex++;
		}

		List<String> answersClone = new ArrayList<>(answers);
		String skippedQestion = null;

		for (String q : quesVsAnsScore.keySet()) {
			double[] ansScore = quesVsAnsScore.get(q);
			int ansAnchor = resolveAnswer(ansScore);
			if (ansAnchor != -1) {
				String answer = answers.get(ansAnchor);
				answersClone.remove(ansAnchor);
				result.put(q, answer);
			} else {
				skippedQestion = q;
				result.put(q, null);
			}
		}

		if (answersClone.size() == 1) {
			// Only one answer unmapped. Mapping to last remaining question
			result.put(skippedQestion, answersClone.get(0));
		}

		return result;
	}

	private int resolveAnswer(final double[] ascrore) {
		double maxScore = 0;
		for (double score : ascrore) {
			maxScore = Math.max(maxScore, score);
		}

		int ansAnchor = -1;
		int loopIndex = 0;
		for (double score : ascrore) {
			if ((ansAnchor != -1) && (score == maxScore)) {
				// Duplicate max val
				return -1;
			} else if (score == maxScore) {
				ansAnchor = loopIndex;
			}

			loopIndex++;
		}
		return ansAnchor;
	}

}
