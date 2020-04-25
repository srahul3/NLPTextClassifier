/**
 * 
 */
package com.search.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author rahulsha
 */
public class Questionnaire implements IStudyMaterial, IQuestions, IAnswers {

	private String		paragraph;
	private List<String>	questionList;
	private List<String>	answersList;
	private boolean		shuffled;

	public Questionnaire(	final String paragraph,
					final List<String> questionList,
					final List<String> answersList,
					final boolean shuffled) {
		this.paragraph = paragraph;
		this.questionList = questionList;
		this.answersList = answersList;
		this.shuffled = shuffled;
	}

	public Questionnaire(	final Questionnaire input,
					final Map<String, String> matchedAnswers,
					final boolean shuffled) {
		paragraph = input.paragraph;
		questionList = new ArrayList<>(input.questionList);
		answersList = new ArrayList<>();
		this.shuffled = shuffled;

		for (String q : questionList) {
			String a = matchedAnswers.get(q);
			answersList.add(a);

			System.out.println(q + ": " + a);
		}
	}

	@Override
	public List<String> getAnswers() {
		return answersList;
	}

	@Override
	public boolean isShuffled() {
		return shuffled;
	}

	@Override
	public List<String> getQestions() {
		return questionList;
	}

	@Override
	public String getTextToStudy() {
		return paragraph;
	}
	// private static final Logger log = LogManager.getLogger();

}
