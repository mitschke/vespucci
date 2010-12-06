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
 *     Universitï¿½t Darmstadt nor the names of its contributors may be used to 
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
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.impl.DummyImpl;
import de.tud.cs.st.vespucci.vespucci_model.impl.EnsembleImpl;
import de.tud.cs.st.vespucci.vespucci_model.impl.ExpectedImpl;
import de.tud.cs.st.vespucci.vespucci_model.impl.InAndOutImpl;
import de.tud.cs.st.vespucci.vespucci_model.impl.IncomingImpl;
import de.tud.cs.st.vespucci.vespucci_model.impl.NotAllowedImpl;
import de.tud.cs.st.vespucci.vespucci_model.impl.OutgoingImpl;
import de.tud.cs.st.vespucci.vespucci_model.impl.ShapesDiagramImpl;

public class DiagramConverter {
	
	
	/**
	 * Regular expression to check parameter of Ensembles.
	 */
	private static Pattern mParameterVariable = Pattern.compile("\\p{Upper}.*");

	/**
	 * Regular expression to split parameter names of Ensembles.
	 */
	private static Pattern mParameterNames = Pattern.compile("(.*?)=(.*)");
	
	/**
	 * Regular expression to match a parameter.
	 */
	private static Pattern mParameterList = Pattern
	.compile(	"^.+?" + // match the descriptor
				"\\(" +  // match the first bracket
				"(.*)" + // match anything in between as group
				"\\)$"); // match the last parenthesis by asserting the string ends here

	/**
	 * an internal member to increase the number of dependencies.
	 */
	private int mDependencyCounter;
	
	/**
	 * an internal member to create an empty ensemble only once.
	 */
	private boolean mCreateEmptyEnsemble = false;

	/**
	 * load a Diagram File
	 * @param fullPathFileName the full path filename. 
	 * @return ShapesDiagramImpl Object
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author Patrick Jahnke
	 */
	public ShapesDiagramImpl loadDiagramFile(String fullPathFileName) throws FileNotFoundException, IOException
	{
		//TODO löschen?
       	XMIResourceImpl resource = new XMIResourceImpl();
       	File source = new File(fullPathFileName);
        
   		resource.load( new FileInputStream(source), new HashMap<Object,Object>());

   		EObject eObject = resource.getContents().get(0);
      	return (ShapesDiagramImpl) eObject;
	}
	
	/**
	 * load a Diagram File
	 * @param fullPathFileName the full path filename. 
	 * @return ShapesDiagramImpl Object
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author Patrick Jahnke, MalteV
	 */
	public ShapesDiagramImpl loadDiagramFile(File sadFile) throws FileNotFoundException, IOException
	{
       	XMIResourceImpl resource = new XMIResourceImpl();
       	File source = sadFile; 
   		resource.load( new FileInputStream(source), new HashMap<Object,Object>());
   		EObject eObject = resource.getContents().get(0);
      	return (ShapesDiagramImpl) eObject;
	}
	
	/**
	 * read the given diagram and create a prolog file. 
	 * @param fullPathFileName Name and path of the diagram
	 * @throws FileNotFoundException 
	 * @throws IOException
	 * @author Patrick Jahnke
	 */
	public void ConvertDiagramToProlog (String path, String fileName) throws FileNotFoundException, IOException
	{
		String fullFileName = path + "/" + fileName;
		ShapesDiagramImpl diagram = loadDiagramFile(fullFileName);
		
		// create a new Prolog File
		File prologFile = new File(fullFileName + ".pl");
		
		// the file will be overwritten
		FileOutputStream fos;
		fos = new FileOutputStream(prologFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		// write header string
		bos.write(this.getFileHeaderString(fullFileName).getBytes());
		
		// translate ensemble facts
		bos.write(this.getFacts(diagram, fileName).getBytes());

		bos.close();
		fos.close();
		
		return;
	}
	
	/**
	 * read the given diagram and create a prolog file. 
	 * @param fullPathFileName Name and path of the diagram
	 * @throws FileNotFoundException 
	 * @throws IOException
	 * @author Patrick Jahnke, MalteV
	 */
	public void ConvertDiagramToProlog (File sadFile) throws FileNotFoundException, IOException
	{
		String fullFileName = sadFile.getParent() + "/" + sadFile.getName();
		ShapesDiagramImpl diagram = loadDiagramFile(sadFile);
		
		// create a new Prolog File
		File prologFile = new File(sadFile.getAbsoluteFile()  + ".pl");
		
		// the file will be overwritten
		FileOutputStream fos;
		fos = new FileOutputStream(prologFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		// write header string
		bos.write(this.getFileHeaderString(fullFileName).getBytes());
		
		// translate ensemble facts
		bos.write(this.getFacts(diagram, sadFile.getName()).getBytes());

		bos.close();
		fos.close();
		
		return;
	}

	public boolean isDiagramFile(File file){
		//TODO need to be fixed
		return true;
	}

	
	/**
	 * returns a separator like %------ 
	 * @author Patrick Jahnke
	 */
	private String getStringSeperator() {
		return "%------\n";
	}
	
	/**
	 * Build and returns a PrologFile Header.
	 * @author Patrick Jahnke
	 */
	private String getFileHeaderString(String fileName)
	{
		StringBuilder strBuilder = new StringBuilder();
		// insert separator
		strBuilder.append(getStringSeperator());
		// insert common information
		strBuilder.append("% Prolog based representation of the Vespucci architecture diagram: ");
		strBuilder.append(fileName+"\n");
		strBuilder.append("% Created by Vespucci, Technische Universitiät Darmstadt, Department of Computer Science\n");
		strBuilder.append("% www.opal-project.de\n\n");
		strBuilder.append(":- multifile ensemble/5.\n");
		strBuilder.append(":- multifile outgoing/7.\n");
		strBuilder.append(":- multifile incoming/7.\n");
		strBuilder.append(":- multifile not_allowed/7.\n");
		strBuilder.append(":- multifile expected/7.\n");
		strBuilder.append(":- discontiguous ensemble/5.\n");
		strBuilder.append(":- discontiguous outgoing/7.\n");
		strBuilder.append(":- discontiguous incoming/7.\n");
		strBuilder.append(":- discontiguous not_allowed/7.\n");
		strBuilder.append(":- discontiguous expected/7.\n\n");
		// insert Date
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		strBuilder.append("% Date <" + dateFormat.format(date) + ">.\n");
		// insert separator
		strBuilder.append(getStringSeperator());
		// insert new line
		strBuilder.append("\n");
		
		return strBuilder.toString();
	}
	
	/**
	 * Read the diagram and create the prolog facts of ensembles and dependencies
	 * @param diagram the diagram model where are the ensembles and dependencies are defined.
	 * @param fileName the filenam of the diagram model
	 * @return a string with the facts
	 * @author Patrick Jahnke
	 */
	private String getFacts(ShapesDiagramImpl diagram, String fileName)
	{
		StringBuilder strBuilder = new StringBuilder();
		// insert ensemble Header
		strBuilder.append(getEnsembleHeader());

		// create ensembles and dependencies from diagram
		StringBuilder ensembleFacts = new StringBuilder();
		StringBuilder dependencyFacts = new StringBuilder();
		// reset Transaction Counter
		mDependencyCounter = 1;
		mCreateEmptyEnsemble = false;
		createFacts(diagram.getShapes(), fileName, ensembleFacts, dependencyFacts);
		if (mCreateEmptyEnsemble)
			ensembleFacts.append("ensemble('" + fileName + "',(empty),empty,[]).\n");

		// insert ensembles
		strBuilder.append(ensembleFacts);
		
		// insert dependency Header
		strBuilder.append(getDependencyHeader());
		
		// insert dependencies
		strBuilder.append(dependencyFacts);

		return strBuilder.toString();
	}
	
	/**
	 * Search the diagram recursive and create all facts.
	 * @author Patrick Jahnke
	 */
	private void createFacts(EList<Shape> shapeList, String fileName, StringBuilder ensembleFacts, StringBuilder dependencyFacts)
	{
        for (Shape shape : shapeList) {
        	// create Ensemble Facts:
        	if (shape == null)
        		continue;
       		if (shape instanceof DummyImpl)
       			mCreateEmptyEnsemble = true;
        	else if (shape instanceof EnsembleImpl)
        	{
        		EnsembleImpl ensemble = (EnsembleImpl) shape;
        		if (this.isAbstractEnsemble(ensemble))
        			ensembleFacts.append("abstract_ensemble");
        		else
        			ensembleFacts.append("ensemble");
        		
        		ensembleFacts.append("('" + fileName + "', '" + this.getEnsembleDescriptor(ensemble) + "', "+ getEnsembleParameters (ensemble) +", (" + ensemble.getQuery() + "), [" + listSubEnsembles(ensemble.getShapes()) + "]).\n");
           		// does children exist
	        	if ((ensemble.getShapes() != null) && (ensemble.getShapes().size()>0))
	        		createFacts(ensemble.getShapes(), fileName, ensembleFacts, dependencyFacts);
        			
        	}
       		// create Transaction Facts:
        	for (Connection connection : shape.getTargetConnections())
        		dependencyFacts.append(createDependencyFact(connection, fileName));

        }
	}
	
	/**
	 * Check if an ensemble an abstract one.
	 * @param ensemble
	 * @return
	 * @author Patrick Jahnke
	 */
	private boolean isAbstractEnsemble(Ensemble ensemble) {
		String[] parameters = getEnsembleParameterDefinitions(ensemble);
		for (int i = 0; i < parameters.length; i++) {
			if (mParameterVariable.matcher(parameters[i]).matches())
				return true;
		}
		return false;
	}
	
	/**
	 * @return a prolog list of the form ['ParamName'=ParamName, ...]
	 * @author Patrick Jahnke
	 */
	private String getEnsembleParameters(Ensemble ensemble) {
		String[] parameters = this.getEnsembleParameterDefinitions(ensemble);
		if (parameters.length == 0)
			return "[]";
		StringBuilder s = new StringBuilder("[");
		s.append(getEncodedParameter(parameters[0]));
		for (int i = 1; i < parameters.length; i++) {
			s.append(", ");
			s.append(getEncodedParameter(parameters[i]));
		}
		s.append("]");
		return s.toString();
	}

	/**
	 * the name of the ensemble. (Without parameters)
	 * @param shape
	 * @return
	 * @author Patrick Jahnke
	 */
	private String getEnsembleDescriptor(Shape shape) {
		String name = shape.getName().length()== 0 ? "non-editpart" : shape.getName();
		StringBuilder s = new StringBuilder("'");
		if (name.indexOf('(') > 0) {
			s.append(name.subSequence(0, name.indexOf('(')));
		} else {
			s.append(name);
		}
		s.append("'");
		return s.toString();
	}

	/**
	 * Encode and returns the pure parameter.
	 * @param name
	 * @return
	 * @author Patrick Jahnke
	 */
	private String getEncodedParameter(String name) {
		StringBuilder s = new StringBuilder();
		if (mParameterVariable.matcher(name).matches()) {
			s.append("'");
			s.append(name);
			s.append("'");
			s.append("=");
			s.append(name);
			return s.toString();
		}
		Matcher m = mParameterNames.matcher(name);
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
	 * returns the parameter definitions of an ensemble
	 * @param ensemble
	 * @return
	 * @author Patrick Jahnke
	 */
	private String[] getEnsembleParameterDefinitions(Ensemble ensemble) {
		String name = ensemble.getName().length()== 0 ? "non-editpart" : ensemble.getName();
		Matcher m = mParameterList.matcher(name);
		if (!m.matches())
			return new String[0];
		List<String> parameterDefinitions = new LinkedList<String>();
		String parameters = m.group(1);
		int start = 0;
		int matchParenthesis = 0;
		for (int i = 0; i < parameters.length(); i++) {
			if(parameters.charAt(i) == '(')
				matchParenthesis++;
			if(matchParenthesis > 0 && parameters.charAt(i) == ')')
				matchParenthesis--;
			if(parameters.charAt(i) == ',' && matchParenthesis == 0) {
				parameterDefinitions.add(parameters.substring(start, i).trim());
				start = i + 1;
			}
		}
		parameterDefinitions.add(parameters.substring(start, parameters.length()).trim());
		String[] result = new String[parameterDefinitions.size()];
		return parameterDefinitions.toArray(result);
	}
	
	/**
	 * returns a static string for the begin of the ensemble facts. 
	 * @author Patrick Jahnke
	 */
	private String getEnsembleHeader()
	{
		StringBuilder strBuilder = new StringBuilder();
		// insert new line
		strBuilder.append(getStringSeperator());
		// insert common information
		strBuilder.append("%ensemble(File, Name, Query, SubEnsembles) :- Definition of an ensemble.\n");
		strBuilder.append("%\tFile - The simple file name in which the ensemble is defined. (e.g., 'Flashcards.sad')\n");
		strBuilder.append("%\tName - Name of the ensemble\n");
		strBuilder.append("%\tQuery - Query that determines which source elements belong to the ensemble\n");
		strBuilder.append("%\tSubEnsembles - List of all sub ensembles of this ensemble.\n");
		// insert new line
		strBuilder.append(getStringSeperator());
		return strBuilder.toString();
	}

	/**
	 * returns a static string for the begin of the dependency facts
	 * @return
	 */
	private String getDependencyHeader()
	{
		StringBuilder strBuilder = new StringBuilder();
		// insert new line
		strBuilder.append("\n");
		// insert separator
		strBuilder.append(getStringSeperator());
		// insert common information
		strBuilder.append("%DEPENDENCY(File, ID, SourceE, TargetE, Type) :- Definition of a dependency between two ensembles.\n");
		strBuilder.append("%\tDEPENDENCY - The type of the dependency. Possible values: outgoing, incoming, expected, not_allowed\n");
		strBuilder.append("%\tFile - The simple file name in which the dependency is defined. (e.g., 'Flashcards.sad')\n");
		strBuilder.append("%\tID - An ID identifying the dependency\n");
		strBuilder.append("%\tSourceE - The source ensemble\n");
		strBuilder.append("%\tTargetE - The target ensemble\n");
		strBuilder.append("%\tRelation classifier - Kinds of uses-relation between source and target ensemble (all, field_access, method_call,...)\n");
		// insert new line
		strBuilder.append(getStringSeperator());
		return strBuilder.toString();
	}

	/**
	 * create a dependency fact
	 * @author Patrick Jahnke
	 */
	private String createDependencyFact(Connection connection, String fileName)
	{
		StringBuilder transactionSB = new StringBuilder();

		if (connection instanceof OutgoingImpl)
			transactionSB.append("outgoing"
					+ "('" + fileName + "', " + mDependencyCounter + ", "
					+ "'" + getDependencyEnsembleName(connection.getSource()) + "', [], "
					+ "'" + getDependencyEnsembleName(connection.getTarget()) + "', [], " + connection.getName() + ").\n");
		else if (connection instanceof IncomingImpl)
			transactionSB.append("incoming"
					+ "('" + fileName + "', " + mDependencyCounter + ", "
					+ "'" + getDependencyEnsembleName(connection.getSource()) + "', [], "
					+ "'" + getDependencyEnsembleName(connection.getTarget()) + "', [], " + connection.getName() + ").\n");
		else if (connection instanceof ExpectedImpl)
			transactionSB.append("expected"
					+ "('" + fileName + "', " + mDependencyCounter + ", "
					+ "'" + getDependencyEnsembleName(connection.getSource()) + "', [], "
					+ "'" + getDependencyEnsembleName(connection.getTarget()) + "', [], " + connection.getName() + ").\n");
		else if (connection instanceof NotAllowedImpl)
			transactionSB.append("not_allowed"
					+ "('" + fileName + "', " + mDependencyCounter + ", "
					+ "'" + getDependencyEnsembleName(connection.getSource()) + "', [], "
					+ "'" + getDependencyEnsembleName(connection.getTarget()) + "', [], " + connection.getName() + ").\n");
		else if (connection instanceof InAndOutImpl)
		{
			transactionSB.append("incoming"
					+ "('" + fileName + "', " + mDependencyCounter + ", "
					+ "'" + getDependencyEnsembleName(connection.getSource()) + "', [], "
					+ "'" + getDependencyEnsembleName(connection.getTarget()) + "', [], " + connection.getName() + ").\n");
			transactionSB.append("outgoing"
					+ "('" + fileName + "', " + mDependencyCounter + ", "
					+ "'" + getDependencyEnsembleName(connection.getSource()) + "', [], "
					+ "'" + getDependencyEnsembleName(connection.getTarget()) + "', [], " + connection.getName() + ").\n");

		}
		mDependencyCounter++;
		return transactionSB.toString();
	}
	
	/**
	 * returns the right name of an ensemble (empty or x)
	 * @author Patrick Jahnke 
	 */
	private String getDependencyEnsembleName(Shape shape)
	{
		if (shape instanceof EnsembleImpl)
			return this.getEnsembleDescriptor(shape);
		else if (shape instanceof DummyImpl)
			return "empty";
		return "not_defined";
	}

	/**
	 * create a string with all subensembles of a parten.
	 * @author Patrick Jahnke
	 */
	private String listSubEnsembles(EList<Shape> shapeList)
	{
		StringBuilder strBuilder = new StringBuilder();
		if (shapeList == null)
			return strBuilder.toString();

		String komma = "";
		for (Shape shape : shapeList)
		{
       		if (shape instanceof DummyImpl)
       			strBuilder.append(komma + "'empty'");
        	else if (shape instanceof EnsembleImpl)
       			strBuilder.append(komma + "'" + shape.getName() + "'");
			komma = ", ";			
		}
		return strBuilder.toString();
	}

}
