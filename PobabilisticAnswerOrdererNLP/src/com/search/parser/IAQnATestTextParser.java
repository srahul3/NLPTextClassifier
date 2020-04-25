/**
 * 
 */
package com.search.parser;

import java.io.IOException;
import com.search.datamodel.Questionnaire;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

/**
 * @author rahulsha
 */
public interface IAQnATestTextParser {

	/**
	 * Questioner with un-ordered answers
	 * 
	 * @return
	 * @throws IOException
	 */
	Questionnaire parse() throws IOException;

}
