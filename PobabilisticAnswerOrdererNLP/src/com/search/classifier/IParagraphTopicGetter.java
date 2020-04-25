/**
 * 
 */
package com.search.classifier;

import java.util.Set;

/**
 * @author rahulsha
 */
public interface IParagraphTopicGetter {

	/**
	 * Given a paragraph, the result is the topic/topics of paragraph. e.g Zebra, Flower, Pen, Car
	 * 
	 * @return
	 */
	Set<String> getTopic();

}
