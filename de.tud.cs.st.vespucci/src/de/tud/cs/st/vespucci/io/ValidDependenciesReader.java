/**
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Author Tam-Minh Nguyen
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universität Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 * 
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.io;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * This class reads in the keywords specified in a file (see {@link #PATH}) and provides
 * methods to retrieve these keywords. The specified file must only contain one keyword
 * per line; empty lines may be added.
 * 
 * Basically {@link de.tud.cs.st.vespucci.diagram.io.QueryKeywordReader}
 * 
 * @author Alexander Weitzmann
 * @version 1.1
 */
public class ValidDependenciesReader {

	/**
	 * Path to file, that contains the keywords
	 */
	private static final String PATH = "/resources/validDependencies.txt";

	/**
	 * Keywords read in from given file (see {@link #PATH})
	 */
	private String[] keywords;

	/**
	 * Returns keywords from {@value #PATH}.
	 * 
	 * @return Returns an array containing all keywords listed in the given text-file (see
	 *         {@link #PATH}).
	 */
	public String[] getKeywords() {
		return keywords.clone();
	}

	/**
	 * Constructor.
	 */
	public ValidDependenciesReader() {
		readKeywordFile();
	}

	/**
	 * Reads in the keyword-file.
	 */
	private void readKeywordFile() {
		final Scanner scanner = new Scanner(this.getClass().getResourceAsStream(PATH));

		final List<String> keywordList = new LinkedList<String>();

		while (scanner.hasNextLine()) {
			keywordList.add(scanner.nextLine());
		}
		scanner.close();

		keywords = keywordList.toArray(new String[0]);
	}

}
