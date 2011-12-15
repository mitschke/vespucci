/** License (BSD Style License):
 *  Copyright (c) 2011
 *  Thomas Schulz
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  - The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.diagram.dnd.patterns;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import de.tud.cs.st.vespucci.diagram.creator.PrologFileCreator;
import de.tud.cs.st.vespucci.diagram.handler.GeneratePrologFacts;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;

public abstract class PatternRoutine {

    // TODO Get rid of file parameter.
    // TODO Retrieve diagram location by workspace path.
    
    
    private List<String> abstractFactory = new ArrayList<String>();

    /**
     * Sets the characteristic keywords of the Abstract Factory <br>
     * Vespucci Diagram Template.
     */
    private void buildKeywords() {
	abstractFactory.add("Abstract Factory");
	abstractFactory.add("Concrete Factories");
	abstractFactory.add("Client");
	abstractFactory.add("Abstract Products");
	abstractFactory.add("Concrete Products");
    }
    
    /**
     * Returns true iff the diagram file is a pattern template.
     * 
     * @param diagramFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    @AbsFac("AF")
    public boolean diagramIsPatternTemplate(File diagramFile)
	    throws FileNotFoundException, IOException {
	
	if (abstractFactory.isEmpty())
	    buildKeywords();
	
	// Retrieve all ensembles
	String location = diagramFile.getParent();
	String diagramFileName = diagramFile.getName();
	final String fullFileName = location + "/" + diagramFileName;
	final ShapesDiagram diagram = loadDiagramFile(fullFileName);
	List<Shape> shapes = diagram.getShapes();
	
	// Check if the names of the ensembles match at least one keyword.
	for (Shape shape : shapes) {
	    if (shape instanceof Ensemble && shape != null) {
		Ensemble ensemble = (Ensemble) shape;
		for (String s : abstractFactory) {
		    if (ensemble.getName().equals(s)) {
			return true;
		    }
		}
		}
	}

	return false;
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
    private static ShapesDiagram loadDiagramFile(final String fullPath)
	    throws FileNotFoundException, IOException {
	final XMIResourceImpl diagramResource = new XMIResourceImpl();
	final FileInputStream diagramStream = new FileInputStream(new File(
		fullPath));

	diagramResource.load(diagramStream, new HashMap<Object, Object>());

	// Find the ShapesDiagram-EObject
	for (int i = 0; i < diagramResource.getContents().size(); i++) {
	    if (diagramResource.getContents().get(i) instanceof ShapesDiagram) {
		final EObject eObject = diagramResource.getContents().get(i);
		return (ShapesDiagram) eObject;
	    }
	}

	throw new FileNotFoundException(
		"ShapesDiagram could not be found in Document.");
    }

}
