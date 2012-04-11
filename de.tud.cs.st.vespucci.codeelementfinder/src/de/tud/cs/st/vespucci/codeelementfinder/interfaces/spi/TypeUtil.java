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

import scala.util.MurmurHash;
import de.tud.cs.st.bat.FieldType;
import de.tud.cs.st.bat.FieldType$;
import de.tud.cs.st.bat.Type;
import de.tud.cs.st.bat.VoidType;

/**
 * 
 * @author Ralf Mitschke
 */
public class TypeUtil {

	
	private static Type getType(String typeQualifier) {
		if (typeQualifier == null || typeQualifier.length() == 0)
			return null;
		if (typeQualifier.charAt(0) == 'V')
			return VoidType.apply();
		FieldType type = FieldType$.MODULE$.apply(typeQualifier);
		return type;
	}
	
	public static int hashCode(String typeQualifier) {
		if (typeQualifier == null)
			return 0;
		return getType(typeQualifier).hashCode();
	}
	
	public static int hashCode(String[] typeQualifierArray) {
		if (typeQualifierArray == null)
			return 0;
		Type[] types = new Type[typeQualifierArray.length];
		for (int i = 0; i < typeQualifierArray.length; i++) {
			String t = typeQualifierArray[i];
			types[i] = getType(t);
		}
	    MurmurHash<Type> hashFunc = new scala.util.MurmurHash<Type>("Seq".hashCode());
	    for (int i = 0; i < types.length; i++) {
			Type type = types[i];
			hashFunc.apply((Object)type);
		}
	    return hashFunc.hash();
	}

}
