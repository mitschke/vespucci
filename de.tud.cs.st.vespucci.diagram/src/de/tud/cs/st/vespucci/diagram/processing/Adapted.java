/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
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
 */
package de.tud.cs.st.vespucci.diagram.processing;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;

/**
 * @author Patrick Gottschämmer
 * @author Olav Lenz
 */
public class Adapted {

	/**
	 * This function provides a generic adapter client for adapting an input object to a free selectable targetClass.
	 * The type of the returning object will be the same as the passed targetClass (<b>Typesafe!</b>). 
	 * See the linked article for further notice, this abstract generic adapter client replaces 
	 * the specific <code>getImageProvider</code> in this article.  
	 * 
	 * @param adaptable Input object, a fitting adapter is required for correct converting. Otherwise <code>null</code> will be returned.
	 * @param targetClass The desired class of the returned (adapted) object.
	 * @return Adapted input Object with type <code>targetClass</code>.
	 * A fitting adapter is required for correct converting. Otherwise <code>null</code> will be returned.
	 * @see <a href="http://www.eclipse.org/articles/article.php?file=Article-Adapters/index.html">Eclipse Corner Article: Adapters</a>
	 */
	@SuppressWarnings("unchecked")
	public static <A> A getAdapted(Object adaptable, Class<A> targetClass) {

		A target = null;

		// Check if input object is of same type as targetClass
		if (targetClass.isInstance(adaptable)) {
			return (A) adaptable;
		}

		// Check if input object provides an adapter for targetClass itself
		if (adaptable instanceof IAdaptable) {
			target = (A) ((IAdaptable) adaptable).getAdapter(targetClass);
		}

		// Ask platform adapter manager for a correct adapter for targetClass
		if (target == null) {
			IAdapterManager manager = Platform.getAdapterManager();
			target = (A) manager.getAdapter(adaptable, targetClass);
		}

		return target;
	}
	
}
