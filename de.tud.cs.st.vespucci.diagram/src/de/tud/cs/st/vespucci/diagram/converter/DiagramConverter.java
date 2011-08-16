/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Patrick Jahnke
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
package de.tud.cs.st.vespucci.diagram.converter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * DiagramConverter converts a *.sad into a *.pl File.
 * 
 * @author PatrickJ
 */
public class DiagramConverter {

	/**
	 * Defines a string, that can be used to visually separate text in a file.
	 */
	private static final String VERTICAL_SECTION_SEPARATOR = "%------\n";

	/**
	 * Regular expression to check if argument is in upper case. E.g. a parameter variable.
	 */
	private static final Pattern UPPER_CASE = Pattern.compile("\\p{Upper}.*");

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
	 * an internal member to increase the number of dependencies.
	 */
	private int mDependencyCounter;

	/**
	 * Name of the current diagram file.
	 */
	private String fileName;

	/**
	 * read the given diagram and create a prolog file.
	 * 
	 * @param fullPathFileName
	 *            Name and path of the diagram
	 * @author Patrick Jahnke, MalteV
	 * @throws Exception
	 */
	public void convertDiagramToProlog(final File sadFile) throws Exception {
		this.convertDiagramToProlog(sadFile.getParent(), sadFile.getName());
	}

	/**
	 * read the given diagram and create a prolog file.
	 * 
	 * @param fullPathFileName
	 *            Name and path of the diagram
	 * @author Patrick Jahnke
	 * @throws Exception
	 */
	public void convertDiagramToProlog(final String path, final String fileName) throws Exception {
		this.fileName = fileName;
		final String fullFileName = path + "/" + fileName;
		final ShapesDiagram diagram = loadDiagramFile(fullFileName);

		// create a new Prolog File
		final File prologFile = new File(fullFileName + ".pl");

		// the file will be overwritten
		final FileOutputStream fos = new FileOutputStream(prologFile);
		final BufferedOutputStream bos = new BufferedOutputStream(fos);

		bos.write(getPrologFacts(diagram, fullFileName));

		bos.close();
		fos.close();
	}

	/**
	 * create a dependency fact
	 * 
	 * @param connection
	 * @param fileName
	 * @return
	 * @author Patrick Jahnke
	 * @throws Exception
	 */
	private String createDependencyFact(final Connection connection, final String fileName) throws Exception {
		Shape source = null;
		Shape target = null;

		// Get the original source (and not the red line source)
		if ((connection.getOriginalSource() == null) || (connection.getOriginalSource().size() == 0)) {
			source = connection.getSource();
		} else if ((connection.getOriginalSource() != null) && (connection.getOriginalSource().size() == 1)) {
			source = connection.getOriginalSource().get(0);
		} else {
			throw new Exception(
					"Too many original sources in connection available. Please check the the original sources of connection: \""
							+ connection.getName() + "\"");
		}

		// Get the original target (and not the red line target)
		if ((connection.getOriginalTarget() == null) || (connection.getOriginalTarget().size() == 0)) {
			target = connection.getTarget();
		} else if ((connection.getOriginalTarget() != null) && (connection.getOriginalTarget().size() == 1)) {
			target = connection.getOriginalTarget().get(0);
		} else {
			throw new Exception(
					"Too many original tagets in connection available. Please check the the original targets of connection: \""
							+ connection.getName() + "\"");
		}

		final StringBuilder transactionSB = new StringBuilder();
		// TODO: delete next 2 lines if saved sad file doesn't
		// contains copy/paste artifact references. Problem: if one copy from one sad file
		// an Ensemble with dependency in another sad file than the copy of
		// the Ensemble will contains a reference to original Ensemble in first saf file.
		// This reference has no influence on working process but it has problem here,
		// by converting to prolog facts
		if (connection.getSource().eIsProxy() || connection.getTarget().eIsProxy()) {
			return "";
		}
		if (connection instanceof Outgoing) {
			transactionSB.append("outgoing" + "('" + fileName + "', " + mDependencyCounter + ", "
					+ getDependencyEnsembleName(source) + ", [], " + getDependencyEnsembleName(target) + ", [], "
					+ connection.getName() + ").\n");
		} else if (connection instanceof Incoming) {
			transactionSB.append("incoming" + "('" + fileName + "', " + mDependencyCounter + ", "
					+ getDependencyEnsembleName(source) + ", [], " + getDependencyEnsembleName(target) + ", [], "
					+ connection.getName() + ").\n");
		} else if (connection instanceof Expected) {
			transactionSB.append("expected" + "('" + fileName + "', " + mDependencyCounter + ", "
					+ getDependencyEnsembleName(source) + ", [], " + getDependencyEnsembleName(target) + ", [], "
					+ connection.getName() + ").\n");
		} else if (connection instanceof NotAllowed) {
			transactionSB.append("not_allowed" + "('" + fileName + "', " + mDependencyCounter + ", "
					+ getDependencyEnsembleName(source) + ", [], " + getDependencyEnsembleName(target) + ", [], "
					+ connection.getName() + ").\n");
		} else if (connection instanceof InAndOut) {
			transactionSB.append("incoming" + "('" + fileName + "', " + mDependencyCounter + ", "
					+ getDependencyEnsembleName(source) + ", [], " + getDependencyEnsembleName(target) + ", [], "
					+ connection.getName() + ").\n");
			transactionSB.append("outgoing" + "('" + fileName + "', " + mDependencyCounter + ", "
					+ getDependencyEnsembleName(source) + ", [], " + getDependencyEnsembleName(target) + ", [], "
					+ connection.getName() + ").\n");

		}
		mDependencyCounter++;
		return transactionSB.toString();
	}

	/**
	 * returns the right name of an ensemble (empty or x)
	 * 
	 * @param Shape
	 * @return name of the ensemble
	 * @author Patrick Jahnke
	 */
	private String getDependencyEnsembleName(final Shape shape) {
		if (shape instanceof Ensemble) {
			return this.getEnsembleDescriptor(shape);
		} else if (shape instanceof Dummy) {
			return "empty";
		}
		return "not_defined";
	}

	/**
	 * Search the diagram recursive and create all facts.
	 * 
	 * @author Patrick Jahnke
	 * @throws Exception
	 */
	private StringBuilder getDependencyFacts(final List<Shape> shapeList) throws Exception {
		final StringBuilder dependencyFacts = new StringBuilder();
		for (final Shape shape : shapeList) {
			// create Ensemble facts:
			if (shape == null) {
				continue;
			}
			// create transaction facts:
			for (final Connection connection : shape.getTargetConnections()) {
				dependencyFacts.append(createDependencyFact(connection, fileName));
			}

		}
		return dependencyFacts;
	}

	/**
	 * returns a static string for the begin of the dependency facts
	 * 
	 * @return
	 */
	private String getDependencyHeader() {
		final StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("\n");
		strBuilder.append(VERTICAL_SECTION_SEPARATOR);
		// insert common information
		strBuilder.append("%DEPENDENCY(File, ID, SourceE, TargetE, Type) :- Definition of a dependency between two ensembles.\n");
		strBuilder
				.append("%\tDEPENDENCY - The type of the dependency. Possible values: outgoing, incoming, expected, not_allowed\n");
		strBuilder.append("%\tFile - The simple file name in which the dependency is defined. (e.g., 'Flashcards.sad')\n");
		strBuilder.append("%\tID - An ID identifying the dependency\n");
		strBuilder.append("%\tSourceE - The source ensemble\n");
		strBuilder.append("%\tTargetE - The target ensemble\n");
		strBuilder
				.append("%\tRelation classifier - Kinds of uses-relation between source and target ensemble (all, field_access, method_call,...)\n");
		strBuilder.append(VERTICAL_SECTION_SEPARATOR);
		return strBuilder.toString();
	}

	/**
	 * Encode and returns the pure parameter.
	 * 
	 * @param name
	 * @return
	 * @author Patrick Jahnke
	 */
	private String getEncodedParameter(final String name) {
		final StringBuilder s = new StringBuilder();
		if (UPPER_CASE.matcher(name).matches()) {
			s.append("'");
			s.append(name);
			s.append("'");
			s.append("=");
			s.append(name);
			return s.toString();
		}
		final Matcher m = PARAMETER_NAMES.matcher(name);
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
		s.append(name);
		return s.toString();
	}

	/**
	 * The name of the ensemble in apostrophes. (Without parameters)
	 * 
	 * @param shape
	 * @return
	 * @author Patrick Jahnke
	 */
	private static String getEnsembleDescriptor(final Shape shape) {
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
	 * Search the diagram recursively and create all ensemble facts, except Dummy.
	 * 
	 * @param shapeList
	 *            The list of shapes in the diagram.
	 * @author Patrick Jahnke
	 * @throws Exception
	 * @return Returns the formatted ensemble facts.
	 */
	private StringBuilder getEnsembleFacts(final List<Shape> shapeList) throws Exception {
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

				// fix: inconsistent newline encodings
				final String query = ensemble.getQuery().replaceAll("\n", " ");
				
				ensembleFacts.append(String.format("('%s', %s, %s, (%s), [%s]).\n", fileName, getEnsembleDescriptor(ensemble),
						getEnsembleParameters(ensemble), query, listSubEnsembles(ensemble.getShapes())));
				
				// do children exist
				if ((ensemble.getShapes() != null) && (ensemble.getShapes().size() > 0)) {
					getEnsembleFacts(ensemble.getShapes());
				}

			}

		}
		return ensembleFacts;
	}

	/**
	 * returns a static string for the begin of the ensemble facts.
	 * 
	 * @author Patrick Jahnke
	 */
	private String getEnsembleHeader() {
		final StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(VERTICAL_SECTION_SEPARATOR);
		// insert common information
		strBuilder.append("%ensemble(File, Name, Query, SubEnsembles) :- Definition of an ensemble.\n");
		strBuilder.append("%\tFile - The simple file name in which the ensemble is defined. (e.g., 'Flashcards.sad')\n");
		strBuilder.append("%\tName - Name of the ensemble\n");
		strBuilder.append("%\tQuery - Query that determines which source elements belong to the ensemble\n");
		strBuilder.append("%\tSubEnsembles - List of all sub ensembles of this ensemble.\n");
		strBuilder.append(VERTICAL_SECTION_SEPARATOR);
		return strBuilder.toString();
	}

	/**
	 * returns the parameter definitions of an ensemble
	 * 
	 * @param ensemble
	 * @return
	 * @author Patrick Jahnke
	 */
	private String[] getEnsembleParameterDefinitions(final Ensemble ensemble) {
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
	 * @return a prolog list of the form ['ParamName'=ParamName, ...]
	 * @author Patrick Jahnke
	 */
	private String getEnsembleParameters(final Ensemble ensemble) {
		final String[] parameters = getEnsembleParameterDefinitions(ensemble);
		if (parameters.length == 0) {
			return "[]";
		}
		final StringBuilder s = new StringBuilder("[");
		s.append(getEncodedParameter(parameters[0]));
		for (int i = 1; i < parameters.length; i++) {
			s.append(", ");
			s.append(getEncodedParameter(parameters[i]));
		}
		s.append("]");
		return s.toString();
	}

	/**
	 * Build and returns a prolog file header.
	 * 
	 * @author Patrick Jahnke
	 */
	private String getFileHeader(final String fileName) {
		final StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(VERTICAL_SECTION_SEPARATOR);
		// insert common information
		strBuilder.append("% Prolog based representation of the Vespucci architecture diagram: ");
		strBuilder.append(fileName + "\n");
		strBuilder.append("% Created by Vespucci, Technische Universitiät Darmstadt, Department of Computer Science\n");
		strBuilder.append("% www.opal-project.de\n\n");
		strBuilder.append(":- multifile ensemble/5.\n");
		strBuilder.append(":- multifile abstract_ensemble/5.\n");
		strBuilder.append(":- multifile outgoing/7.\n");
		strBuilder.append(":- multifile incoming/7.\n");
		strBuilder.append(":- multifile not_allowed/7.\n");
		strBuilder.append(":- multifile expected/7.\n");
		strBuilder.append(":- discontiguous ensemble/5.\n");
		strBuilder.append(":- discontiguous abstract_ensemble/5.\n");
		strBuilder.append(":- discontiguous outgoing/7.\n");
		strBuilder.append(":- discontiguous incoming/7.\n");
		strBuilder.append(":- discontiguous not_allowed/7.\n");
		strBuilder.append(":- discontiguous expected/7.\n\n");
		// insert Date
		final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		final Date date = new Date();
		strBuilder.append("% Date <" + dateFormat.format(date) + ">.\n");
		strBuilder.append(VERTICAL_SECTION_SEPARATOR);
		strBuilder.append("\n");

		return strBuilder.toString();
	}

	/**
	 * Read the diagram and create the prolog facts of ensembles and dependencies
	 * 
	 * @param diagram
	 *            the diagram model where are the ensembles and dependencies are defined.
	 * @param fileName
	 *            the filename of the diagram model
	 * @return a string with the facts
	 * @author Patrick Jahnke
	 * @param fullFileName
	 * @throws Exception
	 */
	private byte[] getPrologFacts(final ShapesDiagram diagram, final String fullFileName) throws Exception {
		final StringBuilder strBuilder = new StringBuilder();

		strBuilder.append(getFileHeader(fullFileName));

		// insert ensemble Header
		strBuilder.append(getEnsembleHeader());

		// reset transaction counter
		mDependencyCounter = 1;

		final boolean containsDummy = hasDummy(diagram.getShapes());
		final StringBuilder ensembleFacts = getEnsembleFacts(diagram.getShapes());

		if (containsDummy) {
			ensembleFacts.append("ensemble('" + fileName + "',(empty),empty,[]).\n");
		}

		// insert ensembles
		strBuilder.append(ensembleFacts);

		// insert dependency header
		strBuilder.append(getDependencyHeader());

		// insert dependencies
		strBuilder.append(getDependencyFacts(diagram.getShapes()));

		return strBuilder.toString().getBytes();
	}

	private boolean hasDummy(final List<Shape> shapeList) {
		for (final Shape shape : shapeList) {
			if (shape instanceof Dummy) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if an ensemble an abstract one.
	 * 
	 * @param ensemble
	 * @return Return true, if the ensemble is abstract, i.e. the ensemble contains at least one parameter, that is
	 * @author Patrick Jahnke
	 */
	private boolean isAbstractEnsemble(final Ensemble ensemble) {
		final String[] parameters = getEnsembleParameterDefinitions(ensemble);
		for (final String parameter : parameters) {
			if (UPPER_CASE.matcher(parameter).matches()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return true when the given file object is a diagram file.
	 * 
	 * @author Patrick Jahnke
	 */
	public boolean isDiagramFile(final File file) {
		final String extension = file.getName().substring((file.getName().length() - 3), file.getName().length());
		return (extension.equals("sad"));
	}

	/**
	 * create a string with all subensembles of a parent.
	 * 
	 * @param EList
	 *            <Shape>
	 * @author Patrick Jahnke
	 */
	private String listSubEnsembles(final EList<Shape> shapeList) {
		final StringBuilder strBuilder = new StringBuilder();
		if (shapeList == null) {
			return strBuilder.toString();
		}

		String komma = "";
		for (final Shape shape : shapeList) {
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
	 * Loads a diagram file.
	 * 
	 * @param fullPath
	 * @return ShapesDiagramImpl Object
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author Patrick Jahnke
	 * @author DominicS
	 */
	public ShapesDiagram loadDiagramFile(final String fullPath) throws FileNotFoundException, IOException {
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

}
