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
package de.tud.cs.st.vespucci.diagram.creator;

import java.util.List;

import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Dummy;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.Expected;
import de.tud.cs.st.vespucci.vespucci_model.InAndOut;
import de.tud.cs.st.vespucci.vespucci_model.Incoming;
import de.tud.cs.st.vespucci.vespucci_model.NotAllowed;
import de.tud.cs.st.vespucci.vespucci_model.Outgoing;
import de.tud.cs.st.vespucci.vespucci_model.Shape;

/**
 * This class encapsulates the dependency prolog facts.
 * 
 * @author Patrick Jahnke
 * @author Thomas Schulz
 * @author Alexander Weitzmann
 * @author Theo Kischka
 * 
 */
public class DependencyPrologFacts {

	/**
	 * Name of the current diagram file.
	 */
	private static String diagramFileName;

	/**
	 * Counter for dependencies in given diagram.
	 */
	private static int dependencyCounter;

	/**
	 * A convenience method to retrieve the dependency prolog facts.
	 * 
	 * @param shapeList
	 * @param diagramFileName
	 * @return Returns the formatted dependency facts.
	 */
	static StringBuilder getFacts(final List<Shape> shapeList, final String diagramFileName) {
		DependencyPrologFacts.diagramFileName = diagramFileName;
		
		// reset transaction counter
		dependencyCounter = 1;
		
		return createDependencyFacts(shapeList);
	}

	/**
	 * Search the diagram recursively and create all facts.
	 * 
	 * @param shapeList
	 * @return Returns the formatted dependency facts.
	 * @author Patrick Jahnke
	 */
	static StringBuilder createDependencyFacts(final List<Shape> shapeList) {

		final StringBuilder dependencyFacts = new StringBuilder();
		for (final Shape shape : shapeList) {
			if (shape instanceof Ensemble) {
				final Ensemble ensemble = (Ensemble) shape;
				if (shape != null) {
					for (final Connection connection : ensemble.getTargetConnections()) {
						dependencyFacts.append(createSingleDependencyFact(connection));
					}
				}
				if (ensemble.getShapes() != null) {
					dependencyFacts.append(createDependencyFacts(ensemble.getShapes()));
				}

			}
		}

		return dependencyFacts;
	}

	/**
	 * @param connection
	 * @return Return a fact for a single dependency.
	 */
	private static String createSingleDependencyFact(final Connection connection) {
		final Shape source = getSource(connection);
		final Shape target = getTarget(connection);

		final StringBuilder transactionSB = new StringBuilder();
		// TODO: delete next 2 lines if saved sad file doesn't
		// contains copy/paste artifact references. Problem: if one copy from one sad file
		// an Ensemble with dependency in another sad file than the copy of
		// the Ensemble will contains a reference to original Ensemble in first sad file.
		// This reference has no influence on working process but it has problem here,
		// by converting to prolog facts
		if (connection.getSource().eIsProxy() || connection.getTarget().eIsProxy()) {
			return "";
		}

		final String dependencySuffix = String.format("('%s', %s, %s, [], %s, [], [%s]).\n", diagramFileName, dependencyCounter,
				getEnsembleName(source), getEnsembleName(target), connection.getName());
		if (connection instanceof Outgoing) {
			transactionSB.append("outgoing").append(dependencySuffix);
		} else if (connection instanceof Incoming) {
			transactionSB.append("incoming").append(dependencySuffix);
		} else if (connection instanceof Expected) {
			transactionSB.append("expected").append(dependencySuffix);
		} else if (connection instanceof NotAllowed) {
			transactionSB.append("not_allowed").append(dependencySuffix);
		} else if (connection instanceof InAndOut) {
			transactionSB.append("incoming").append(dependencySuffix);
			transactionSB.append("outgoing").append(dependencySuffix);
		}

		dependencyCounter++;
		return transactionSB.toString();
	}

	/**
	 * Note, that {@link Connection#getSource()} does not return the same. That's because the source
	 * of the connection
	 * will be set to the parent of the semantic source-ensemble, if a red line is used.
	 * 
	 * @param connection
	 * @return Returns the source of given connection.
	 */
	private static Shape getSource(final Connection connection) {
		if ((connection.getOriginalSource() == null) || (connection.getOriginalSource().size() == 0)) {
			return connection.getSource();
		} else {
			return connection.getOriginalSource().get(0);
		}
	}

	/**
	 * Note, that {@link Connection#getTarget()} does not return the same. That's because the target
	 * of the connection
	 * will be set to the parent of the semantic target-ensemble, if a red line is used.
	 * 
	 * @param connection
	 * @return Returns the source of given connection.
	 */
	private static Shape getTarget(final Connection connection) {
		// Get the original target (and not the red line target)
		if ((connection.getOriginalTarget() == null) || (connection.getOriginalTarget().size() == 0)) {
			return connection.getTarget();
		} else {
			return connection.getOriginalTarget().get(0);
		}
	}

	/**
	 * @param shape
	 * @return Returns the name of an ensemble (without the parameter).
	 */
	private static String getEnsembleName(final Shape shape) {
		if (shape instanceof Ensemble) {
			return EnsemblePrologFacts.createEnsembleDescriptor(shape);
		} else if (shape instanceof Dummy) {
			return "empty";
		}
		return "not_defined";
	}

}
