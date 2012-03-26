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

/**
 * Representation of a method
 * 
 * @author 
 */
public interface IMethodDeclaration extends ICodeElement{

	/**
	 * Returns the name of the method
	 * 
	 * for example: <br>
	 * 	<code>doSome</code> for <code>void doSome(){..}</code><br>
	 * 	<code>calcFaculty</code> for <code>int calcFaculty(int n){...}</code>
	 * 
	 * @return The name of the method
	 */
	String getMethodName();

	/**
	 * Returns the return value type qualifier of method
	 * 
	 * for example: <br>
	 * 	<code>V;</code> for <code>void doSome(int a, String[] b){..}</code><br>
	 * 	<code>I;</code> for <code>int calcFaculty(int n){...}</code>
	 *
	 * @return The return value type qualifier of the class
	 */
	String getReturnTypeQualifier();

	/**
	 * Returns the type qualifier of the parameter
	 * 
	 * for example: <br>
	 * 	<code>{V;, [Ljava/lang/String;}</code> for <code>void doSome(int a, String[] b){..}</code><br>
	 * 	<code>{I;}</code> for <code>int calcFaculty(int n){...}</code>
	 *
	 * @return The type qualifier of the paramter
	 */
	String[] getParameterTypeQualifiers();
}
