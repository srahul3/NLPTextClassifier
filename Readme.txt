Project Properties:
	This project is eclipse project.

Project Dependencies:
	Open JDK 11
	Junit 4

Problem Statement: You are provided with short paragraphs (no more than 5,000 characters) of text from Wikipedia, about well known places, persons, animals, flowers etc. For each paragraph, there is a set of five questions, which can be answered by reading the paragraph. Answers are of entirely factual nature, e.g. date, number, name, etc. and will be exact substrings of the paragraph. Answers could either be one word, group of words, or a complete sentence. You are also provided with the answers to each of these questions, though they are jumbled up, separated by semicolon, and provided in no specific order. Your task is to identify, which answer corresponds to which question.

Approach: Probabilistic matching and answers with given question. Using different text classifier and Inverted index search, a match matrix is created.

1) Inverted index search: This paragraph is passed to a inverted serch algorithm, which evaluates to things. About topic of the paragraph. Now all questions and answers are passed to classifier to find the probability of questions and answer keywords are available on common line.
2) Number and List classifier: This classifier generates the score of a question is asking for numbe and answer has list of same number of items in list.

		
TODO: I have avoided adding log4j depoendency to make the project easy to import. I am planning to use gradle to solve this problem.
TODO: Logging and refined documentation. I have added a sufficient amount5 of documentation as of now.
TODO: Classifier needs to more mature for more generalized use.

Start with Junit test: TestAnswerOderingUsingNLP


Console output:
inverted indexed para 248 words
This Paragraph appears to be about zebra count: 9
This Paragraph appears to be about zebras count: 6
This Paragraph appears to be about zebran count: 0

Inverted index Question and Answer matcher based on common paragraph lines
0.0, 0.25, 0.0, 0.0, 0.3333333333333333, 
0.0, 0.25, 0.0, 1.0, 0.0, 
0.0, 0.0, 1.0, 0.0, 0.0, 
1.0, 0.5, 0.0, 0.0, 0.6666666666666666, 
1.0, 1.0, 0.0, 0.5, 1.0, 

Which Zebras are endangered?: -1
What is the aim of the Quagga Project?: -1
Which animals are some of their closest relatives?: -1
Which are the three species of zebras?: 3
Which subgenus do the plains zebra and the mountain zebra belong to?: -1
subgenus Hippotigris: -1
the plains zebra, the Grévy's zebra and the mountain zebra: 3
horses and donkeys: 2
aims to breed zebras that are phenotypically similar to the quagga: -1
Grévy's zebra and the mountain zebra: 2

A list and number classifier, if a given string has description of a number or contains a list of items forming a list
0.0, 0.0, 0.0, 0.0, 0.0, 
0.0, 0.0, 0.0, 0.0, 0.0, 
0.0, 0.0, 0.0, 0.0, 0.0, 
0.0, 5.0, 0.0, 0.0, 0.0, 
0.0, 0.0, 0.0, 0.0, 0.0, 

Final score matrix
0.0, 0.25, 0.0, 0.0, 0.3333333333333333, 
0.0, 0.25, 0.0, 1.0, 0.0, 
0.0, 0.0, 1.0, 0.0, 0.0, 
1.0, 5.5, 0.0, 0.0, 0.6666666666666666, 
1.0, 1.0, 0.0, 0.5, 1.0, 

Which Zebras are endangered?: Grévy's zebra and the mountain zebra
What is the aim of the Quagga Project?: aims to breed zebras that are phenotypically similar to the quagga
Which animals are some of their closest relatives?: horses and donkeys
Which are the three species of zebras?: the plains zebra, the Grévy's zebra and the mountain zebra
Which subgenus do the plains zebra and the mountain zebra belong to?: subgenus Hippotigris
