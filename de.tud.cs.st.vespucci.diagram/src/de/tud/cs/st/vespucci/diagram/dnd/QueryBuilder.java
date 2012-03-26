/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
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
package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IJavaElement;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;

/**
 * Static helper methods for building Vespucci queries from Elipse {@link IJavaElement}s
 * or SAE {@link ICodeElement}s
 * 
 * @author Ralf Mitschke
 * @author Malte Viering
 * @author Benjamin Lück
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 */
public class QueryBuilder implements IQueryBuilderConstants {

	/**
	 * 
	 * @param data
	 * @return Returns the Query created from the data of a drop event.
	 * @author BenjaminL
	 */
	public static String createQueryFromRequestData(
			final Map<String, Object> data) {
		return createQueryFromRequestData(data, "");
	}

	/**
	 * Creates a new Query from the data of the drop event by extending the old
	 * Query. A derived query is never extended, but returned as is.
	 * 
	 * @param data
	 * @param oldQuery
	 *            Old Query of the model element.
	 * @return Returns the created query.
	 * @author Ralf Mitschke
	 * @author BenjaminL
	 */
	public static String createQueryFromRequestData(
			@SuppressWarnings("rawtypes") final Map data, final String oldQuery) {

		@SuppressWarnings("unchecked")
		List<ICodeElement> codeElements = (List<ICodeElement>) data
				.get(IJavaElementDropConstants.DROP_DATA_ICODEELEMENT);

		@SuppressWarnings("unchecked")
		List<IJavaElement> javaElements = (List<IJavaElement>) data
				.get(IJavaElementDropConstants.DROP_DATA_IJAVAELEMENT);

		if (oldQuery != null && oldQuery.trim().equals(DERIVED_QUERY))
			return oldQuery;

		// shortcut sanity check, this would happen anyway using
		// concatenateQueries(..)
		if (codeElements.isEmpty() && javaElements.isEmpty())
			return oldQuery;

		String codeElementExtension = CodeElementQueryBuilder.createQueryFromICodeElements(codeElements);
		String javaElementExtension = JavaElementQueryBuilder.createQueryFromIJavaElements(javaElements);

		return concatenateQueries(oldQuery,
				concatenateQueries(codeElementExtension, javaElementExtension));
	}


	/**
	 * Concatenates two queries using "or". If either query is empty the other
	 * is returned.
	 * 
	 * @param q1
	 * @param q2
	 * @return
	 */
	protected static String concatenateQueries(String q1, String q2) {
		if (isEmpty(q1))
			return q2;
		if (isEmpty(q2))
			return q1;
		return String.format("%s\n%s\n%s", q1, QUERY_DELIMITER, q2);
	}

	/**
	 * Returns true if the query is empty, which means:<br>
	 * - the query is null<br>
	 * - the query is the empty String<br>
	 * - the query conforms to "empty"
	 * 
	 * @param query
	 * @return
	 */
	private static boolean isEmpty(String query) {
		return query == null || query.isEmpty()
				|| query.trim().equals(EMPTY_QUERY);
	}

	/**
	 * Concatenates two queries using "or". If either query is empty the other
	 * is returned.
	 * 
	 * @param q1
	 * @param q2
	 * @return
	 */
	protected static String concatenateParameters(String s1, String s2) {
		if (s1 == null || s1.isEmpty())
			return s2;
		if (s2 == null || s2.isEmpty())
			return s1;
		return s1 + PARAMETER_DELIMITER + s2;
	}
}
