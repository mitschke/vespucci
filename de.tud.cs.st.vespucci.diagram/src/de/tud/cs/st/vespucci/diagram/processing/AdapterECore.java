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

import java.util.LinkedList;

import org.eclipse.core.runtime.IAdapterFactory;

import de.tud.cs.st.vespucci.diagram.outputIModel.*;

/**
 * 
 * @author Patrick Gottschämmer
 * @author Olav Lenz
 */
public class AdapterECore implements IAdapterFactory {

	private static Class<?>[] adapterList = { IShape.class, IDummy.class, IEnsemble.class, 
		IConnection.class, IExpected.class, IGlobalIncoming.class, IGlobalOutgoing.class, 
		IInAndOut.class, IIncoming.class, INotAllowed.class, IOutgoing.class, IViolation.class};

	@Override
	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		
		if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.Connection){
			IConnection connection = null;
			if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.Expected){
				connection = new Expected();
			}
			if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming){
				connection = new GlobalIncoming();
			}
			if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing){
				connection = new GlobalOutgoing();
			}
			if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.InAndOut){
				connection = new InAndOut();
			}
			if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.Incoming){
				connection = new Incoming();
			}
			if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.NotAllowed){
				connection = new NotAllowed();
			}
			if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.Outgoing){
				connection = new Outgoing();
			}
			if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.Violation){
				connection = new Violation();
			}		
			if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.Connection){
				connection = new Connection();
			}
			if (connection != null){
				return createIConnectionSubtype((de.tud.cs.st.vespucci.vespucci_model.Connection) adaptableObject, connection);
			}
		}
		if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.Shape){
			if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.Dummy){
				return createDummyInstace((de.tud.cs.st.vespucci.vespucci_model.Dummy)adaptableObject);
			}
			if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.Ensemble){
				return createEnsembleInstace((de.tud.cs.st.vespucci.vespucci_model.Ensemble)adaptableObject);
			}
			if (adaptableObject instanceof de.tud.cs.st.vespucci.vespucci_model.Shape){
				return createShapeInstace((de.tud.cs.st.vespucci.vespucci_model.Shape)adaptableObject);
			}
		}


		return null;
	}

	private static IShape createShapeInstace(de.tud.cs.st.vespucci.vespucci_model.Shape adaptableObject) {
		IShape shape = new Shape();
		shape.setDescription(adaptableObject.getDescription());
		shape.setName(adaptableObject.getName());
		shape.setQuery(adaptableObject.getQuery());
		return shape;
	}

	private static IEnsemble createEnsembleInstace(de.tud.cs.st.vespucci.vespucci_model.Ensemble adaptableObject) {
		IEnsemble ensemble = new Ensemble();
		ensemble.setDescription(adaptableObject.getDescription());
		ensemble.setName(adaptableObject.getName());
		ensemble.setQuery(adaptableObject.getQuery());

		LinkedList<IShape> shapes = new LinkedList<IShape>();
		for (de.tud.cs.st.vespucci.vespucci_model.Shape sha : adaptableObject.getShapes()){
			shapes.add(createShapeInstace(sha));
		}		
		ensemble.setShapes(shapes);

		return ensemble;
	}

	private static IDummy createDummyInstace(de.tud.cs.st.vespucci.vespucci_model.Dummy aptableObject) {
		IDummy dummy = new Dummy();
		dummy.setDescription(aptableObject.getDescription());
		dummy.setName(aptableObject.getName());
		dummy.setQuery(aptableObject.getQuery());
		return dummy;
	}

	private static IConnection createIConnectionSubtype(de.tud.cs.st.vespucci.vespucci_model.Connection adaptableObject, IConnection adapterType) {
		adapterType.setName(adaptableObject.getName());
		adapterType.setTemp(adaptableObject.isTemp());
		adapterType.setSource(createShapeInstace(adaptableObject.getSource()));
		adapterType.setTarget(createShapeInstace(adaptableObject.getTarget()));

		return adapterType;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return AdapterECore.adapterList;
	}

}
