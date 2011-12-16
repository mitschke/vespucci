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
package de.tud.cs.st.vespucci.information.interfaces.spi;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IViolation;

public class Violation implements IViolation{

	String description;
	ICodeElement sourceElement;
	ICodeElement targetElement;
	IEnsemble sourceEnsemble;
	IEnsemble targetEnsemble;
	IConstraint constraint;
	
	public Violation(String description, ICodeElement sourceElement,
			ICodeElement targetElement, IEnsemble sourceEnsemble,
			IEnsemble targetEnsemble, IConstraint constraint) {
		super();
		this.description = description;
		this.sourceElement = sourceElement;
		this.targetElement = targetElement;
		this.sourceEnsemble = sourceEnsemble;
		this.targetEnsemble = targetEnsemble;
		this.constraint = constraint;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public ICodeElement getSourceElement() {
		return sourceElement;
	}

	@Override
	public ICodeElement getTargetElement() {
		return targetElement;
	}

	@Override
	public IEnsemble getSourceEnsemble() {
		return sourceEnsemble;
	}

	@Override
	public IEnsemble getTargetEnsemble() {
		return targetEnsemble;
	}

	@Override
	public IConstraint getConstraint() {
		return constraint;
	}
	
}
