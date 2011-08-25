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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Dummy;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.Expected;
import de.tud.cs.st.vespucci.vespucci_model.InAndOut;
import de.tud.cs.st.vespucci.vespucci_model.Incoming;
import de.tud.cs.st.vespucci.vespucci_model.NotAllowed;
import de.tud.cs.st.vespucci.vespucci_model.Outgoing;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;

/**
 * PrologFileCreator creates a *.pl file from a *.sad file .
 * 
 * @author Patrick Jahnke
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 */
public class PrologFileCreator {

	/**
	 * Regular expression to check if argument starts with an upper case letter. E.g. a parameter
	 * variable.
	 */
	private static final Pattern FIRST_LETTER_IS_UPPER_CASE = Pattern.compile("\\p{Upper}.*");

	/**
	 * Regular expression to split parameter names of ensembles.
	 */
	private static final Pattern PARAMETER_NAMES = Pattern.compile("(.*?)=(.*)");

	/**
	 * Regular expression to match a name of an ensemble that contains parameter.
	 */
	private static final Pattern PARAMETER_LIST = Pattern.compile("^.+?" + // match the descriptor
			"\\(" + // match the first bracket
			"(.*)" + // match anything in between as group
			"\\)$"); // match the last parenthesis by asserting the string ends here

	/**
	 * Read the given diagram and create a prolog file.
	 * 
	 * @param sadFile
	 *            File of the diagram.
	 * @author Malte Viering
	 * @throws Exception
	 */
	public void createPrologFileFromDiagram(final File sadFile) throws Exception {
		this.createPrologFileFromDiagram(sadFile.getParent(), sadFile.getName());
	}

	/**
	 * Read the given diagram and create a prolog file.
	 * 
	 * @param location
	 * @param fileName
	 * @throws Exception
	 */
	public void createPrologFileFromDiagram(final String location, final String fileName) throws Exception {
		diagramFileName = fileName;
		final String fullFileName = location + "/" + fileName;
		final ShapesDiagram diagram = loadDiagramFile(fullFileName);

		// create a new Prolog File
		final File prologFile = new File(fullFileName + ".pl");

		// the file will be overwritten
		final FileOutputStream fos = new FileOutputStream(prologFile);
		final BufferedOutputStream bos = new BufferedOutputStream(fos);

		bos.write(createPrologFacts(diagram, fullFileName));

		bos.close();
		fos.close();
	}

	/**
	 * Loads a diagram file.
	 * 
	 * @param fullPath
	 * @return Returns the loaded diagram.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author Dominic Scheurer
	 */
	private static ShapesDiagram loadDiagramFile(final String fullPath) throws FileNotFoundException, IOException {
		final XMIResourceImpl diagramResource = new XMIResourceImpl();
		final FileInputStream diagramStream = new FileInputStream(new File(fullPath));

		diagramResource.load(diagramStream, new HashMap<Object, Object>());

		// Find the ShapesDiagram-EObject
		for (int i = 0; i < diagramResource.getContents().size(); i++) {
			if (diagramResource.getContents().get(i) instanceof ShapesDiagram) {
				final EObject eObject = diagramResource.getContents().get(i);
				return (ShapesDiagram) eObject;
			}
		}

		throw new FileNotFoundException("ShapesDiagram could not be found in Document.");
	}

	/**
	 * Read the diagram and create the prolog facts of ensembles and dependencies.
	 * 
	 * @param diagram
	 *            The diagram where the ensembles and dependencies are defined.
	 * @param fullFileName
	 *            The path to the diagram including its filename.
	 * @return Returns a string with the prolog facts.
	 * @throws Exception
	 */
	private byte[] createPrologFacts(final ShapesDiagram diagram, final String fullFileName) throws Exception {
		final StringBuilder strBuilder = new StringBuilder();

		strBuilder.append(InvariantPrologFacts.createFileHeader(fullFileName));

		// insert ensemble Header
		strBuilder.append(InvariantPrologFacts.createEnsembleHeader());

		// reset transaction counter
		dependencyCounter = 1;

		final boolean containsDummy = hasDummy(diagram.getShapes());
		final StringBuilder ensembleFacts = createEnsembleFacts(diagram.getShapes());

		if (containsDummy) {
			ensembleFacts.append("ensemble('" + diagramFileName + "',(empty),empty,[]).\n");
		}

		// insert ensembles
		strBuilder.append(ensembleFacts);

		// insert dependency header
		strBuilder.append(InvariantPrologFacts.createDependencyHeader());

		// insert dependencies
		strBuilder.append(createDependencyFacts(diagram.getShapes()));

		return strBuilder.toString().getBytes();
	}

	/**
	 * 
	 * @param shapeList
	 * @return Return true only if given shape list contains a dummy.
	 */
	private static boolean hasDummy(final List<Shape> shapeList) {
		for (final Shape shape : shapeList) {
			if (shape instanceof Dummy) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Search the diagram recursively and create all ensemble facts, except Dummy.
	 * 
	 * @param shapeList
	 *            The list of shapes in the diagram.
	 * @throws Exception
	 * @return Returns the formatted ensemble facts.
	 */
	private StringBuilder createEnsembleFacts(final List<Shape> shapeList) throws Exception {
		final StringBuilder ensembleFacts = new StringBuilder();
		for (final Shape shape : shapeList) {
			// create Ensemble facts:
			if (shape instanceof Ensemble && shape != null) {
				final Ensemble ensemble = (Ensemble) shape;
				if (isAbstractEnsemble(ensemble)) {
					ensembleFacts.append("abstract_ensemble");
				} else {
					ensembleFacts.append("ensemble");
				}

				//TODO: This is a workaround - invent a platform independent solution
				final String query = ensemble.getQuery().replaceAll("\\p{Space}", " ");

				ensembleFacts.append(String.format("('%s', %s, %s, (%s), [%s]).\n", diagramFileName,
						createEnsembleDescriptor(ensemble), createEnsembleParameters(ensemble), query,
						listSubEnsembles(ensemble.getShapes())));

				// do children exist
				if ((ensemble.getShapes() != null) && (ensemble.getShapes().size() > 0)) {
					ensembleFacts.append(createEnsembleFacts(ensemble.getShapes()));
				}

			}

		}
		return ensembleFacts;
	}

	/**
	 * Search the diagram recursive and create all facts.
	 * 
	 * @param shapeList
	 * @return Returns the formatted dependency facts.
	 * @author Patrick Jahnke
	 */
	private StringBuilder createDependencyFacts(final List<Shape> shapeList) {
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
	 * @param parameter
	 * @return Return the encoded parameter.
	 */
	private static String createEncodedParameter(final String parameter) {
		final StringBuilder s = new StringBuilder();
		if (FIRST_LETTER_IS_UPPER_CASE.matcher(parameter).matches()) {
			s.append("'");
			s.append(parameter);
			s.append("'");
			s.append("=");
			s.append(parameter);
			return s.toString();
		}
		final Matcher m = PARAMETER_NAMES.matcher(parameter);
		if (m.matches()) {
			s.append("'");
			s.append(m.group(1));
			s.append("'");
			s.append("=");
			s.append(m.group(2));
			return s.toString();
		}

		s.append("_");
		s.append("=");
		s.append(parameter);
		return s.toString();
	}

	/**
	 * 
	 * @param shape
	 * @return The name of the ensemble in apostrophes. (Without parameters)
	 */
	private static String createEnsembleDescriptor(final Shape shape) {
		String name = "";
		if (shape.getName() != null) {
			name = shape.getName().length() == 0 ? "non-editpart" : shape.getName();
		}
		final StringBuilder s = new StringBuilder("'");
		if (name.indexOf('(') > 0) {
			s.append(name.subSequence(0, name.indexOf('(')));
		} else {
			s.append(name);
		}
		s.append("'");
		return s.toString();
	}

	/**
	 * @param ensemble
	 *            The ensemble whose parameters shall be extracted.
	 * @return A prolog list of the form ['ParamName'=ParamName, ...]
	 */
	private static String createEnsembleParameters(final Ensemble ensemble) {
		final String[] parameters = splitEnsembleParameterList(ensemble);
		if (parameters.length == 0) {
			return "[]";
		}
		final StringBuilder s = new StringBuilder("[");
		s.append(createEncodedParameter(parameters[0]));
		for (int i = 1; i < parameters.length; i++) {
			s.append(", ");
			s.append(createEncodedParameter(parameters[i]));
		}
		s.append("]");
		return s.toString();
	}

	/**
	 * @param shape
	 * @return Returns the name of an ensemble (without the parameter).
	 */
	private static String getEnsembleName(final Shape shape) {
		if (shape instanceof Ensemble) {
			return PrologFileCreator.createEnsembleDescriptor(shape);
		} else if (shape instanceof Dummy) {
			return "empty";
		}
		return "not_defined";
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
	 * @param ensemble
	 * @return Return true, only if the ensemble is abstract, i.e. the ensemble contains at least
	 *         one parameter
	 *         variable.
	 */
	private static boolean isAbstractEnsemble(final Ensemble ensemble) {
		final String[] parameters = splitEnsembleParameterList(ensemble);
		for (final String parameter : parameters) {
			if (FIRST_LETTER_IS_UPPER_CASE.matcher(parameter).matches()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param file
	 * @return Return true only if the given file object is a diagram file.
	 */
	public static boolean isDiagramFile(final File file) {
		final String extension = file.getName().substring((file.getName().length() - 3), file.getName().length());
		return (extension.equals("sad"));
	}

	/**
	 * Create a formatted string with all given ensembles.
	 * 
	 * @param ensembles
	 * @return Return formatted string listing the given ensembles.
	 */
	private static String listSubEnsembles(final EList<Shape> ensembles) {
		final StringBuilder strBuilder = new StringBuilder();
		if (ensembles == null) {
			return strBuilder.toString();
		}

		String komma = "";
		for (final Shape shape : ensembles) {
			if (shape instanceof Dummy) {
				strBuilder.append(komma + "'empty'");
			} else if (shape instanceof Ensemble) {
				strBuilder.append(komma + "'" + shape.getName() + "'");
			}
			komma = ", ";
		}
		return strBuilder.toString();
	}

	/**
	 * Returns the parameter definitions of an ensemble. I.e. the parameter list split at ", ".
	 * 
	 * @param ensemble
	 * @return Return an array containing the parameters of given ensemble.
	 */
	private static String[] splitEnsembleParameterList(final Ensemble ensemble) {
		if (ensemble.getName() == null) {
			ensemble.setName("");
		}
		final String name = ensemble.getName().length() == 0 ? "non-editpart" : ensemble.getName();
		final Matcher m = PARAMETER_LIST.matcher(name);
		if (!m.matches()) {
			return new String[0];
		}
		final List<String> parameterDefinitions = new LinkedList<String>();
		final String parameters = m.group(1);
		int start = 0;
		int matchParenthesis = 0;
		for (int i = 0; i < parameters.length(); i++) {
			if (parameters.charAt(i) == '(') {
				matchParenthesis++;
			}
			if (matchParenthesis > 0 && parameters.charAt(i) == ')') {
				matchParenthesis--;
			}
			if (parameters.charAt(i) == ',' && matchParenthesis == 0) {
				parameterDefinitions.add(parameters.substring(start, i).trim());
				start = i + 1;
			}
		}
		parameterDefinitions.add(parameters.substring(start, parameters.length()).trim());
		final String[] result = new String[parameterDefinitions.size()];
		return parameterDefinitions.toArray(result);
	}

	/**
	 * Counter for dependencies in given diagram.
	 */
	private int dependencyCounter;

	/**
	 * Name of the current diagram file.
	 */
	private String diagramFileName;

	/**
	 * @param connection
	 * @return Return a fact for a single dependency.
	 */
	private String createSingleDependencyFact(final Connection connection) {
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

		final String dependencySuffix = String.format("('%s', %s, %s, [], %s, [], %s).\n", diagramFileName, dependencyCounter,
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

}
