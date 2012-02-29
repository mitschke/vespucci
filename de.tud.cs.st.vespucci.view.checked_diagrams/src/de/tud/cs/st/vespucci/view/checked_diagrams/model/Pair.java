/*
 License (BSD Style License):
  Copyright (c) 2011
  Software Technology Group
  Department of Computer Science
  Technische Universität Darmstadt
  All rights reserved.

  Redistribution and use in source and binary forms, with or without
  modification, are permitted provided that the following conditions are met:

  - Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.
  - Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.
  - Neither the name of the Software Technology Group Group or Technische 
    Universität Darmstadt nor the names of its contributors may be used to 
    endorse or promote products derived from this software without specific 
    prior written permission.

  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
  POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.view.checked_diagrams.model;


import de.tud.cs.st.vespucci.interfaces.IPair;

/**
 * Generic implementation of IPair<A,B>
 * 
 * @author 
 */
public class Pair<A,B> implements IPair<A, B> {

	private A first;
	private B seconde;

	public Pair(A first, B seconde){
		this.first = first;
		this.seconde = seconde;
	}

	@Override
	public A getFirst() {
		return first;
	}

	@Override
	public B getSecond() {
		return seconde;
	}

	public static <A,B> IPair<A, B> transfer(Object obj, Class<A> first, Class<B> secode){
		if (obj instanceof IPair){
			IPair<?,?> temp = (IPair<?,?>) obj;
			if (first.isInstance(temp.getFirst())){
				if (secode.isInstance(temp.getSecond())){
					@SuppressWarnings("unchecked")
					IPair<A, B> result = (IPair<A, B>) temp;
					return result;
				}
			}
		}
		return null;
	}
}
