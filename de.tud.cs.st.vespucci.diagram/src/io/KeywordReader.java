package io;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * This class reads in the keywords specified in a file (see {@link #PATH}) and
 * provides methods to retrieve these keywords.
 * 
 * @author Alexander Weitzmann
 * @version 1.0
 */
public class KeywordReader {

	/**
	 * Path to file, that contains the keywords
	 */
	private static final String PATH = "queryKeywords.txt";
	
	/**
	 * Keywords read in from given file (see {@link #PATH})
	 */
	private String[] _keywords;

	/**
	 * Returns keywords from {@value #PATH}.
	 * 
	 * @return Returns an array containing all keywords listed in the given
	 *         text-file (see {@link #PATH}).
	 */
	public String[] getKeywords() {
		return _keywords.clone();
	}

	/**
	 * Private constructor.
	 */
	public KeywordReader() {
		readKeywordFile();
	}

	/**
	 * Reads in the keyword-file.
	 */
	private void readKeywordFile() {
		Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(PATH));
		
		final List<String> keywordList = new LinkedList<String>();

		// TODO add support for comments (or even XML) in queryKeywords.txt
		while (scanner.hasNextLine()) {
			keywordList.add(scanner.nextLine());
		}
		
		_keywords = keywordList.toArray(new String[0]);
	}
	
}
