/**
 * 
 */
package com.search.classifier;

/**
 * @author rahulsha
 */
public interface IScoreClassifier extends IParagraphTopicGetter {

	/*
	 * A 2 dimensional matrix with all question in y-axis and answers in x-axis
	 * Matrix contains probability of answer matching to question.
	 */
	double[][] generateScore();

	/**
	 * Text description of classifier for audit
	 * 
	 * @return
	 */
	String getDescription();

}
