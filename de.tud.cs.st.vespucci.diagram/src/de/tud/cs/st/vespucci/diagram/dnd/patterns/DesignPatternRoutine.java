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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart;
import de.tud.cs.st.vespucci.vespucci_model.impl.EnsembleImpl;

/**
 * This class controls the mechanisms that are needed to <br>
 * compute queries for ensembles in a diagram which was <br>
 * recognized as a pattern template.
 * 
 * @author Thomas Schulz
 * @version 0.9
 */
public class DesignPatternRoutine {

    // ====================================
    // ====================================
    // ====================================
    
    // ####################################
    // ####################################
    // ####################################
    
    // TODO Connect class with QueryBuilder.
    // TODO How to create Commands/Requests for editing other, non-selected
    // ensembles.
    // TODO Update license text in all new classes.
    // TODO Complete comments in new classes regarding their responsibilities.
    // TODO Optional: Apply Visitor Pattern for each new Design Pattern.
    
    // ####################################
    // ####################################
    // ####################################
    
    // ====================================
    // ====================================
    // ====================================

    private static List<String> abstractFactoryKeywords = new ArrayList<String>();

    private static ShapesDiagramEditPart currentDiagram = null;

    private static List<EnsembleImpl> ensembles = new ArrayList<EnsembleImpl>();

    private static boolean isDiagramPatternTemplate = false;

    private static boolean isPatternRoutineComplete = false;

    /**
     * For now, it is not necessary to instantiate this class <br>
     * as it contains only static methods and just fulfills <br>
     * pure computation tasks.
     */
    private DesignPatternRoutine() {

    }

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

    /**
     * Stores the current diagram and its ensembles <u> <b>once</b> </u> <br>
     * s.t. the same diagram will not be cached every time.
     * 
     * @param diagram
     *            The diagram to cache.
     */
    public static void lazyStoreDiagramAndEnsembles(
	    final ShapesDiagramEditPart diagram) {
	if (currentDiagram == null || !currentDiagram.equals(diagram)) {
	    // the user has opened a new unknown diagram file
	    buildAbstractFactoryKeywords();
	    storeDiagramAndEnsembles(diagram);
	    // ESCA-JAVA0266:
	    System.out.println("You just opened a new diagram.");
	} else {
	    // the given diagram is already the one that was cached before
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
    private static void storeDiagramAndEnsembles(
	    final ShapesDiagramEditPart diagram) {
	currentDiagram = diagram;
	resolveSemanticEnsemblesFromList(currentDiagram.getChildren());
    }

    /**
     * Resolve the semantic ensembles from a List<EditPart>. <br>
     * The names will be compared against the pattern keywords. <br>
     * <br>
     * This method implicitly determines whether the current diagram is a
     * pattern template.
     * 
     * @param childrenOfDiagram
     *            The ShapesDiagramEditPart which contains the ensembles to
     *            resolve.
     */
    private static void resolveSemanticEnsemblesFromList(
	    final List<Object> childrenOfDiagram) {
	isDiagramPatternTemplate = true;
	for (final Object o : childrenOfDiagram) {
	    if (o instanceof EnsembleEditPart) {
		// recovery cast
		final EnsembleEditPart eep = (EnsembleEditPart) o;
		final EObject eo = eep.resolveSemanticElement();
		if (eo instanceof EnsembleImpl) {
		    // recovery cast
		    final EnsembleImpl ensemble = (EnsembleImpl) eo;
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
//	return false;
	return isDiagramPatternTemplate;
    }

    /**
     * @return Returns true iff the pattern routine is finished.
     */
    public static boolean isPatternRoutineFinished() {
	return isPatternRoutineComplete;
    }

    public static List<EnsembleImpl> getEnsembles() {
	return ensembles;
    }

}
