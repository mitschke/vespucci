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
import java.util.ResourceBundle;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;

import de.tud.cs.st.vespucci.diagram.dnd.QueryBuilder;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart;
import de.tud.cs.st.vespucci.vespucci_model.impl.EnsembleImpl;

public abstract class PatternRoutine {

    // TODO Connect class with QueryBuilder.
    // TODO How to create Commands/Requests for editing other, non-selected ensembles.

    private List<String> abstractFactory = new ArrayList<String>();

    private static ShapesDiagramEditPart currentDiagram = null;

    /**
     * Sets the characteristic keywords of the Abstract Factory <br>
     * Vespucci Diagram Template.
     */
    private void buildKeywords() {
	abstractFactory.add("Client");
	abstractFactory.add("Abstract Factory");
	abstractFactory.add("Concrete Factories");
	abstractFactory.add("Abstract Products");
	abstractFactory.add("Concrete Products");
    }

    public static void cacheCurrentDiagramAndEnsembles(
	    ShapesDiagramEditPart diagram) {
	if (currentDiagram == null) {
	    cache(diagram);
	} else if (currentDiagram.equals(diagram)) {
	    System.out.println("Already got it.");
	} else {
	    cache(diagram);
	    System.out.println("You just opened a new diagram.");
	}

    }

    private static void cache(ShapesDiagramEditPart diagram) {
	currentDiagram = diagram;
	List<EnsembleImpl> ensembles = resolveSemanticEnsembles(currentDiagram
		.getChildren());
	for (EnsembleImpl e : ensembles) {
	    System.out.println(e.getName());
	}
    }

    /**
     * Resolve the semantic ensembles from a List<EditPart>. <br>
     * The names and queries of the semantic elements can be edited.
     * 
     * @param childrenOfDiagram
     *            The ShapesDiagramEditPart which contains the ensembles to
     *            resolve.
     * @return
     */
    private static List<EnsembleImpl> resolveSemanticEnsembles(
	    List childrenOfDiagram) {
	List<EnsembleImpl> ensembles = new ArrayList<EnsembleImpl>();
	for (Object o : childrenOfDiagram) {
	    if (o instanceof EnsembleEditPart) {
		EnsembleEditPart eep = (EnsembleEditPart) o;
		EObject eo = eep.resolveSemanticElement();
		if (eo instanceof EnsembleImpl) {
		    ensembles.add((EnsembleImpl) eo);
		}
	    }
	}
	return ensembles;
    }

    /**
     * Resolve the semantic ensembles from a List<EditPart>. <br>
     * The names and queries of the semantic elements can be edited.
     * 
     * @param Object The Object where the
     * @return
     */
    private static EnsembleImpl resolveSemanticEnsemble(Object o) {
	EnsembleImpl ensemble = null;

	if (o instanceof EnsembleEditPart) {
	    EnsembleEditPart eep = (EnsembleEditPart) o;
	    EObject eo = eep.resolveSemanticElement();
	    if (eo instanceof EnsembleImpl) {
		ensemble = ((EnsembleImpl) eo);
	    }
	}

	return ensemble;
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
