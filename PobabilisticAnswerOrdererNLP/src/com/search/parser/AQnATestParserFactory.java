/**
 * 
 */
package com.search.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author rahulsha
 *         Factory different parsers based on input file
 *         Supported files are .txt
 *         TODO .xml, .json and others
 */
public class AQnATestParserFactory {
	// private static final Logger log = LogManager.getLogger();

	private AQnATestParserFactory() {
		// Nothing to do here
	}

	/**
	 * Get parser for the given file
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static IAQnATestTextParser getParser(final File file) throws IOException {
		String rawText = Files.readString(Paths.get(file.getAbsolutePath()));
		if (file.getName().toLowerCase().endsWith("xml")) {
			// XML Parser
			throw new IllegalArgumentException("XML Parser not implemented yet");
		} else if (file.getName().toLowerCase().endsWith("txt")) {
			return new AQnATestTextParser(rawText);
		} else {
			throw new IllegalArgumentException(String.format("Parser for file %s is not implemented yet", file.getName()));
		}
	}

}
