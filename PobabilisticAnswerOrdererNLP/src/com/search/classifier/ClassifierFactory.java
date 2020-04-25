/**
 * 
 */
package com.search.classifier;

import java.util.ArrayList;
import java.util.List;
import com.search.datamodel.Questionnaire;

/**
 * @author rahulsha
 */
public class ClassifierFactory {
	// private static final Logger log = LogManager.getLogger();

	private ClassifierFactory() {
		// Nothing to do here
	}

	public static List<IScoreClassifier> createClassifierList(final Questionnaire input) {
		List<IScoreClassifier> classifiers = new ArrayList<>();
		IScoreClassifier importantClassifier = new InvertedIndexClassfier(input);
		classifiers.add(importantClassifier);
		classifiers.add(new NumberAndListOfItemsClassifier(input, importantClassifier));

		return classifiers;
	}

}
