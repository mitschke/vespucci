/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
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
package de.tud.cs.st.vespucci.codeelementfinder.interfaces.spi;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;

/**
 * Concrete implementation of ICodeElement
 * 
 * @author
 */
public abstract class CodeElement implements ICodeElement {

	private String packageIdentifier;
	private String simpleClassName;

	public CodeElement(String packageName, String simpleClassName) {
		this.packageIdentifier = packageName;
		this.simpleClassName = simpleClassName;
	}

	@Override
	public String getPackageIdentifier() {
		return packageIdentifier;
	}

	@Override
	public String getSimpleClassName() {
		return simpleClassName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((packageIdentifier == null) ? 0 : packageIdentifier
						.hashCode());
		result = prime * result
				+ ((simpleClassName == null) ? 0 : simpleClassName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ICodeElement))
			return false;
		ICodeElement other = (ICodeElement) obj;
		if (packageIdentifier == null) {
			if (other.getPackageIdentifier() != null)
				return false;
		} else if (!packageIdentifier.equals(other.getPackageIdentifier()))
			return false;
		if (simpleClassName == null) {
			if (other.getSimpleClassName() != null)
				return false;
		} else if (!simpleClassName.equals(other.getSimpleClassName()))
			return false;
		return true;
	}

	
}
