/*
 *  License (BSD Style License):
 *   Software Technology Group
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
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
 *   - Neither the name of the Software Technology Group Group or Technische 
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
 */
package de.tud.cs.st.vespucci.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import de.tud.cs.st.vespucci.exceptions.VespucciIOException;

/**
 * This class provides a method to read in and parse a Vespucci resource
 * file. The specified file must only contain one keyword
 * per line; empty lines may be added.
 * 
 * @author Alexander Weitzmann
 * @author Theo Kischka
 * @author Dominic Scheurer
 * @version 1.3
 */
public class KeywordReader {
	private KeywordReader() {}
	
	/**
	 * Reads a Vespucci resource file and returns the parsed data.
	 * The specified file must only contain one keyword per line;
	 * empty lines may be added.
	 * 
	 * @param bundleSymbolicName Symbolic name of the bundle containing the desired file.
	 * @param filePath The path to the relative to the specified bundle.
	 * @return The keywords contained in the given file.
	 */
	public static String[] readAndParseResourceFile(String bundleSymbolicName, String filePath) {
		InputStream fileInputStream = createFileInputStream(bundleSymbolicName, filePath);
		
		final Scanner scanner = new Scanner(fileInputStream);

		final List<String> keywordList = new LinkedList<String>();

		while (scanner.hasNextLine()) {
			keywordList.add(scanner.nextLine());
		}
		scanner.close();

		return keywordList.toArray(new String[0]); // Argument is added for type safety reasons
	}

	/**
	 * @param bundleSymbolicName Symbolic name of the bundle containing the desired file.
	 * @param filePath The path to the relative to the specified bundle.
	 * @return An input stream for the given file.
	 */
	private static InputStream createFileInputStream(String bundleSymbolicName, String filePath) {
		try {
			Bundle bundle = Platform.getBundle(bundleSymbolicName);
			IPath path = new Path(filePath);
			return FileLocator.openStream(bundle, path, false);
		} catch (IOException e) {
			throw new VespucciIOException("Error reading Vespucci resource file", e);
		}		
	}

}
