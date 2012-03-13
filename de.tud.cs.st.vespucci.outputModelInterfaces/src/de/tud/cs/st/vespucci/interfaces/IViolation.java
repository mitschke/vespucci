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
package de.tud.cs.st.vespucci.interfaces;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;

/**
 * Stands for an violation of the regulations make in the diagram file.
 * Provide information about the violation.
 * 
 * @author 
 */
public interface IViolation {

	/**
	 * Returns the violating kind of the violation.
	 * (also known as "Dependency Kind")
	 * <br><br>
	 * For example:<br>
	 * <code>extends</code><br>
	 * <code>implements</code><br>
	 * <code>invoke_virtual</code><br>
	 * <code>...</code><br>
	 * 
	 * @return The violating kind
	 */
	String getViolatingKind();

	/**
	 * Returns the related diagram file as path + filename as string
	 * 
	 * @return Diagram file as string
	 */
	String getDiagramFile();

	/**
	 * Returns the source element of the violation
	 * <br><br>
	 * Could be null!
	 * 
	 * @return Source element
	 */
	ICodeElement getSourceElement();

	/**
	 * Returns the target element of the violation
	 * <br><br>
	 * Could be null!
	 * 
	 * @return Target element
	 */
	ICodeElement getTargetElement();

	/**
	 * Returns the source ensemble of the violation
	 * 
	 * @return Source ensemble
	 */
	IEnsemble getSourceEnsemble();

	/**
	 * Returns the target ensemble of the violation
	 * 
	 * @return Target ensemble
	 */
	IEnsemble getTargetEnsemble();
	
	/**
	 * Returns the causality constraint of the violation
	 * 
	 * @return Constraint
	 */
	IConstraint getConstraint();
}
