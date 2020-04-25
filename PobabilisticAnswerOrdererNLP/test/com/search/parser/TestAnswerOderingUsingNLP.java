package com.search.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import com.search.datamodel.Questionnaire;
import com.search.engine.RuleAndNLPBasedAnswerMatcher;

/**
 * @author rahulsha
 */
public class TestAnswerOderingUsingNLP {

	@Test
	public void test() throws IOException {
		File rawTextFile = new File("test.txt");

		IAQnATestTextParser parserFactory = AQnATestParserFactory.getParser(rawTextFile);
		Questionnaire unsolvedQuestionare = parserFactory.parse();

		Assert.assertTrue("Unsolved questinare must have shuffles answers", unsolvedQuestionare.isShuffled());
		Questionnaire solvedQuestionare = RuleAndNLPBasedAnswerMatcher.solve(unsolvedQuestionare);
		Assert.assertFalse("Solved questionare must have ordered answers", solvedQuestionare.isShuffled());

		List<String> expectedAnswers = new ArrayList<>();
		expectedAnswers.add("Grévy's zebra and the mountain zebra");
		expectedAnswers.add("aims to breed zebras that are phenotypically similar to the quagga");
		expectedAnswers.add("horses and donkeys");
		expectedAnswers.add("the plains zebra, the Grévy's zebra and the mountain zebra");
		expectedAnswers.add("subgenus Hippotigris");

		Assert.assertEquals("Solved questionare must have ordered answers", expectedAnswers, solvedQuestionare.getAnswers());
	}

}
