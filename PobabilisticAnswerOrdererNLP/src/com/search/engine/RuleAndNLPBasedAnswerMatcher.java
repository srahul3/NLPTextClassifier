/**
 * 
 */
package com.search.engine;

import java.util.List;
import java.util.Map;
import com.search.classifier.ClassifierFactory;
import com.search.classifier.IScoreClassifier;
import com.search.datamodel.Questionnaire;

/**
 * @author rahulsha
 *         A Facade.
 *         Applies different rules and classifiers to create a probabilistic result
 */
public class RuleAndNLPBasedAnswerMatcher {
	// private static final Logger log = LogManager.getLogger();

	/**
	 * @param input un-solved Questionnaire
	 * @return
	 */
	public static Questionnaire solve(final Questionnaire input) {

		double[][] scoreMatrix = new double[input.getQestions().size()][input.getAnswers().size()];

		List<IScoreClassifier> classifiers = ClassifierFactory.createClassifierList(input);

		// Running all classifiers on given input data and combing the probabilistic-result in matrix
		for (IScoreClassifier classifier : classifiers) {
			double[][] classifiersscoreMatrix = classifier.generateScore();

			System.out.println("");
			System.out.println(classifier.getDescription());
			printMatrix(classifiersscoreMatrix);

			mergeScore(classifiersscoreMatrix, scoreMatrix);
		}

		System.out.println("Final score matrix");
		printMatrix(scoreMatrix);

		// Based on probability matrix, ordering the answer
		QnAMatcherUsingScore qnaMatcher = new QnAMatcherUsingScore(input.getQestions(), input.getAnswers(), scoreMatrix);

		Map<String, String> matchedAnswers = qnaMatcher.matchBestQuestionAndAnswer();
		return new Questionnaire(input, matchedAnswers, false);
	}

	private static void mergeScore(final double[][] from, final double[][] to) {
		for (int i = 0; i < to.length; i++) {
			double[] col = to[i];
			for (int j = 0; j < col.length; j++) {
				to[i][j] = to[i][j] + from[i][j];
			}
		}
	}

	public static void printMatrix(final double[][] matrix) {
		// Printing score matrix
		for (double[] rows : matrix) {
			for (double col : rows) {
				System.out.print(col);
				System.out.print(", ");
			}
			System.out.println("");
		}
		System.out.println();
	}
}
