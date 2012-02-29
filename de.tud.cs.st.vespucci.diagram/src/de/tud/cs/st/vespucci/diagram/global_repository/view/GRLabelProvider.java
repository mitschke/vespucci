/**
 *  License (BSD Style License):
 *   Copyright (c) 2012
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
 */

package de.tud.cs.st.vespucci.diagram.global_repository.view;



import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

import de.tud.cs.st.vespucci.vespucci_model.Ensemble;

/**
 * 
 * @author Tabea Born, Christian Knapp
 * 
 */
public class GRLabelProvider extends StyledCellLabelProvider/*LabelProvider*/ {

	Image img = new Image(null, getClass().getResourceAsStream("/icons/obj16/Ensemble.gif"));
	Image emptyImage = new Image(null, getClass().getResourceAsStream("/icons/obj16/Empty.gif"));

/*	/**
	 * returns the name of the specific ensemble
	 */
/*	@Override
	public String getText(Object ensemble) {
		if (ensemble instanceof Ensemble) {
			return ((Ensemble) ensemble).getName();
		}
		return null;
	}

	/**
	 * the Image for the Ensembles in the list
	 */
/*	@Override
	public Image getImage(Object element) {
		return img;
	}*/

	
	/**
	 * updates the Ensemble List in View
	 * Adds a counter for the direct children of one Ensemble
	 */
	@Override
	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		StyledString text = new StyledString();
		
		if(element instanceof Ensemble){
			Ensemble ensemble = (Ensemble) element;
			text.append(ensemble.getName());
			cell.setImage(img);
			text.append(" ( " +ensemble.getShapes().size() + " ) ", StyledString.COUNTER_STYLER);
		} 
		
		cell.setText(text.toString());
		cell.setStyleRanges(text.getStyleRanges());
		super.update(cell);
	}
}
