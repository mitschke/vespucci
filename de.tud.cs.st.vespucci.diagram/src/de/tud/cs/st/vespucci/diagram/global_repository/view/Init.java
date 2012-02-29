/**
 *  License (BSD Style License):
 *   Copyright (c) 2012
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
 */

package de.tud.cs.st.vespucci.diagram.global_repository.view;

import java.util.ArrayList;
import java.util.List;


import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.impl.EnsembleImpl;

/**
 * 
 * @author Tanya Harizanova
 * @author Christian Knapp
 * A dummy class for creating an Array with Ensembles
 */
 public final  class Init {
	
	public static  List<Ensemble> getEnsembleList(){
		
		Ensemble a = new EnsembleImpl();
		a.setName("a");
		a.setDescription("description");
		a.setQuery("empty");
		
		Ensemble b = new EnsembleImpl();
		b.setName("b");
		b.setDescription("description");
		b.setQuery("empty");
		
		Ensemble outer = new EnsembleImpl();
		outer.setName("outer");
		outer.setDescription("description");
		outer.setQuery("derived");
		
		Ensemble inner = new EnsembleImpl();
		inner.setName("inner");
		inner.setDescription("description");
		inner.setQuery("derived");
		
		Ensemble c = new EnsembleImpl();
		c.setName("c");
		c.setDescription("description");
		c.setQuery("empty");
		
		Ensemble e = new EnsembleImpl();
		e.setName("e");
		e.setDescription("description");
		e.setQuery("empty");
	
		Ensemble f = new EnsembleImpl();
		f.setName("f");
		f.setDescription("description");
		f.setQuery("empty");
		
		Ensemble inner2 = new EnsembleImpl();
		inner2.setName("inner2");
		inner2.setDescription("description");
		inner2.setQuery("derived");
	 
		Ensemble outer2 = new EnsembleImpl();
		outer2.setName("outer2");
		outer2.setDescription("description");
		outer2.setQuery("empty");
		
		Ensemble in2 = new EnsembleImpl();
		in2.setName("in2");
		in2.setDescription("description");
		in2.setQuery("empty");
	
		Ensemble in3 = new EnsembleImpl();
		in3.setName("in3");
		in3.setDescription("description");
		in3.setQuery("empty");
		
		Ensemble in4 = new EnsembleImpl();
		in4.setName("in4");
		in4.setDescription("description");
		in4.setQuery("empty");
		
		Ensemble in5 = new EnsembleImpl();
		in5.setName("in5");
		in5.setDescription("description");
		in5.setQuery("empty");
		
		
		inner.getShapes().add(c);
		outer.getShapes().add(inner);
		outer.getShapes().add(inner2);
		inner2.getShapes().add(e);
		inner2.getShapes().add(f);
		outer2.getShapes().add(in2);
		outer2.getShapes().add(in3);
		outer2.getShapes().add(in4);
		outer2.getShapes().add(in5);
		
		Ensemble d = new EnsembleImpl();
		d.setName("d");
		d.setDescription("description");
		d.setQuery("empty");
		
		List<Ensemble> resultList = new ArrayList<Ensemble>();
		resultList.add(a);
		resultList.add(b);
		resultList.add(outer);
		resultList.add(d);
		resultList.add(outer2);
		
		return resultList;
		
		
	}

}
