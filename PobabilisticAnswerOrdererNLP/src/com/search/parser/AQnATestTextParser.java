package com.search.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.search.datamodel.Questionnaire;

public class AQnATestTextParser implements IAQnATestTextParser {
	private String		rawText;

	private String		paragraph;
	private List<String>	questionList;
	private String		answersRaw;
	private List<String>	answersList;

	// private static final Logger log = LogManager.getLogger();

	public AQnATestTextParser( final String rawText) {
		this.rawText = rawText;
	}

	@Override
	public Questionnaire parse() throws IOException {
		List<String> data = new ArrayList<>();
		try (StringReader stringReader = new StringReader(rawText)) {
			try (BufferedReader reader = new BufferedReader(stringReader)) {
				for (String para = reader.readLine(); para != null; para = reader.readLine()) {
					data.add(para);
				}
			}
		}

		if (data.size() != 7) {
			throw new IllegalArgumentException("Expecting 7 lines files only");
		}

		paragraph = data.get(0);
		questionList = data.subList(1, 6);

		answersRaw = data.get(6);
		answersList = Arrays.asList(answersRaw.split(";"));

		answersList = answersList.stream().map(String::trim).collect(Collectors.toList());

		return new Questionnaire(paragraph, questionList, answersList, true);
	}

}
