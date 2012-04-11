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
package de.tud.cs.st.vespucci.generateprologfacts.creator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.eclipse.core.resources.IFile;

import de.tud.cs.st.vespucci.vespucci_model.Dummy;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;

/**
 * PrologFileCreator creates a *.pl file from a *.sad file .
 * 
 * @author Patrick Jahnke
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 * @author Theo Kischka
 * @author Patrick Gottschämmer
 */
public class PrologFileCreator {

	/**
	 * Name of the current diagram file.
	 */
	private String diagramFileName;
	
	/**
	 * Read the given diagram and create a prolog file.
	 * 
	 * @param diagramIFile
	 *            IFile of the diagram.
	 * @author Patrick Gottschämmer
	 * @param shapesdiagram 
	 * @throws Exception
	 */
	public void createPrologFileFromDiagram(IFile diagramIFile, ShapesDiagram shapesdiagram) throws Exception {
		
		diagramFileName = diagramIFile.getName();
		
		final String fullFileName = diagramIFile.getLocation().toString();
		
		// create a new Prolog File
		final File prologFile = new File(fullFileName + ".pl");

		// the file will be overwritten
		final FileOutputStream fos = new FileOutputStream(prologFile);
		final BufferedOutputStream bos = new BufferedOutputStream(fos);

		bos.write(createPrologFacts(shapesdiagram, fullFileName));

		bos.close();
		fos.close();
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

		final StringBuilder ensembleFacts = EnsemblePrologFacts.getFacts(diagram.getShapes(), diagramFileName);

		if (hasDummy(diagram.getShapes())) {
			ensembleFacts.append("ensemble('" + diagramFileName + "',(empty),empty,[]).\n");
		}

		// insert ensembles
		strBuilder.append(ensembleFacts);

		// insert dependency header
		strBuilder.append(InvariantPrologFacts.createDependencyHeader());

		// insert dependencies
		strBuilder.append(DependencyPrologFacts.getFacts(diagram.getShapes(), diagramFileName));

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

}
