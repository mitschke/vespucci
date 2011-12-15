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
    // TODO How to create Commands/Requests for editing other, non-selected
    // ensembles.

    private static List<String> abstractFactoryKeywords = new ArrayList<String>();

    private static ShapesDiagramEditPart currentDiagram = null;

    private static List<EnsembleImpl> ensembles = new ArrayList<EnsembleImpl>();

    private static boolean isDiagramPatternTemplate = false;

    private static boolean isPatternRoutineComplete = false;

    /**
     * Sets the characteristic keywords of the Abstract Factory <br>
     * Vespucci Diagram Template.
     */
    private static void buildAbstractFactoryKeywords() {
	abstractFactoryKeywords.add("Client");
	abstractFactoryKeywords.add("Abstract Factory");
	abstractFactoryKeywords.add("Concrete Factories");
	abstractFactoryKeywords.add("Abstract Products");
	abstractFactoryKeywords.add("Concrete Products");
    }

    public static void cacheCurrentDiagramAndEnsembles(
	    ShapesDiagramEditPart diagram) {
	if (currentDiagram == null) {
	    buildAbstractFactoryKeywords();
	    cache(diagram);
	} else if (currentDiagram.equals(diagram)) {
	    System.out.println("Already got it.");
	} else {
	    buildAbstractFactoryKeywords();
	    cache(diagram);
	    System.out.println("You just opened a new diagram.");
	}

    }

    /**
     * Caches the (semantic) ensembles of the given diagram file <br>
     * as well as the diagram itself.
     * 
     * @param diagram
     *            The diagram from which the ensembles shall be extracted.
     */
    @SuppressWarnings("unchecked")
    private static void cache(ShapesDiagramEditPart diagram) {
	currentDiagram = diagram;
	resolveSemanticEnsemblesFromList((List<Object>) currentDiagram
		.getChildren());
    }

    /**
     * Resolve the semantic ensembles from a List<EditPart>. <br>
     * The names will be compared against the pattern keywords. <br>
     * <br>
     * This method implicitly determines whether this diagram is a pattern
     * template.
     * 
     * @param childrenOfDiagram
     *            The ShapesDiagramEditPart which contains the ensembles to
     *            resolve.
     */
    private static void resolveSemanticEnsemblesFromList(
	    List<Object> childrenOfDiagram) {
	isDiagramPatternTemplate = true;
	for (Object o : childrenOfDiagram) {
	    if (o instanceof EnsembleEditPart) {
		EnsembleEditPart eep = (EnsembleEditPart) o;
		EObject eo = eep.resolveSemanticElement();
		if (eo instanceof EnsembleImpl) {
		    EnsembleImpl ensemble = (EnsembleImpl) eo;
		    ensembles.add(ensemble);
		    abstractFactoryKeywords.remove(ensemble.getName());
		}
	    }
	}
	isDiagramPatternTemplate = abstractFactoryKeywords.isEmpty();
    }

    /**
     * @return Returns true iff the current diagram is a pattern template.
     */
    public static boolean isDiagramPatternTemplate() {
	return isDiagramPatternTemplate;
    }

    /**
     * @return Returns true iff the pattern routine is complete.
     */
    public static boolean isPatternRoutineComplete() {
	return isPatternRoutineComplete;
    }

}
