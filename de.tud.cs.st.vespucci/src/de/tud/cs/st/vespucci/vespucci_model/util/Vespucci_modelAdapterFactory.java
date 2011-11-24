/**
 *  License (BSD Style License):
 *   Copyright (c) 2011
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
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.vespucci_model.util;

import de.tud.cs.st.vespucci.vespucci_model.*;
import de.tud.cs.st.vespucci.vespucci_model.Connection;

import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.Expected;
import de.tud.cs.st.vespucci.vespucci_model.InAndOut;
import de.tud.cs.st.vespucci.vespucci_model.Incoming;
import de.tud.cs.st.vespucci.vespucci_model.NotAllowed;
import de.tud.cs.st.vespucci.vespucci_model.Outgoing;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage
 * @generated
 */
public class Vespucci_modelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static Vespucci_modelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vespucci_modelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = Vespucci_modelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Vespucci_modelSwitch<Adapter> modelSwitch =
		new Vespucci_modelSwitch<Adapter>() {
			@Override
			public Adapter caseShapesDiagram(ShapesDiagram object) {
				return createShapesDiagramAdapter();
			}
			@Override
			public Adapter caseShape(Shape object) {
				return createShapeAdapter();
			}
			@Override
			public Adapter caseEmpty(Empty object) {
				return createEmptyAdapter();
			}
			@Override
			public Adapter caseEnsemble(Ensemble object) {
				return createEnsembleAdapter();
			}
			@Override
			public Adapter caseConnection(Connection object) {
				return createConnectionAdapter();
			}
			@Override
			public Adapter caseNotAllowed(NotAllowed object) {
				return createNotAllowedAdapter();
			}
			@Override
			public Adapter caseOutgoing(Outgoing object) {
				return createOutgoingAdapter();
			}
			@Override
			public Adapter caseIncoming(Incoming object) {
				return createIncomingAdapter();
			}
			@Override
			public Adapter caseInAndOut(InAndOut object) {
				return createInAndOutAdapter();
			}
			@Override
			public Adapter caseExpected(Expected object) {
				return createExpectedAdapter();
			}
			@Override
			public Adapter caseGlobalOutgoing(GlobalOutgoing object) {
				return createGlobalOutgoingAdapter();
			}
			@Override
			public Adapter caseGlobalIncoming(GlobalIncoming object) {
				return createGlobalIncomingAdapter();
			}
			@Override
			public Adapter caseViolation(Violation object) {
				return createViolationAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram <em>Shapes Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram
	 * @generated
	 */
	public Adapter createShapesDiagramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.Shape <em>Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Shape
	 * @generated
	 */
	public Adapter createShapeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.Empty <em>Empty</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Empty
	 * @generated
	 */
	public Adapter createEmptyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.Ensemble <em>Ensemble</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Ensemble
	 * @generated
	 */
	public Adapter createEnsembleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Connection
	 * @generated
	 */
	public Adapter createConnectionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.NotAllowed <em>Not Allowed</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.NotAllowed
	 * @generated
	 */
	public Adapter createNotAllowedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.Outgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Outgoing
	 * @generated
	 */
	public Adapter createOutgoingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.Incoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Incoming
	 * @generated
	 */
	public Adapter createIncomingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.InAndOut <em>In And Out</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.InAndOut
	 * @generated
	 */
	public Adapter createInAndOutAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.Expected <em>Expected</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Expected
	 * @generated
	 */
	public Adapter createExpectedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing <em>Global Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing
	 * @generated
	 */
	public Adapter createGlobalOutgoingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming <em>Global Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming
	 * @generated
	 */
	public Adapter createGlobalIncomingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tud.cs.st.vespucci.vespucci_model.Violation <em>Violation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tud.cs.st.vespucci.vespucci_model.Violation
	 * @generated
	 */
	public Adapter createViolationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //Vespucci_modelAdapterFactory
