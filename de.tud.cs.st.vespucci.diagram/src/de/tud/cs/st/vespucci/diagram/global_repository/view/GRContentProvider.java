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

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.tud.cs.st.vespucci.vespucci_model.Ensemble;

/**
 * 
 * @author Tabea Born, Christian Knapp
 *
 */
public class GRContentProvider implements ITreeContentProvider {

	private Init init;
	
	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.init = (Init) newInput;

	}

	/**
	 * gets the children of an Ensemble an put it in the right place in the tree
	 */
	@Override
	public Object[] getChildren(Object ensemble) {
		if(hasChildren(ensemble) == true){
			return ((Ensemble) ensemble).getShapes().toArray();
			
		}
		return null;
	}

	@SuppressWarnings("static-access")
	@Override
	public Object[] getElements(Object inputElement) {
		return init.getEnsembleList().toArray();
	}

	@Override
	public Object getParent(Object parent) {
		return null;
	}

	/**
	 * 
	 * check if the choosen Ensemble has children
	 */
	@Override
	public boolean hasChildren(Object ensemble) {
		if(ensemble instanceof Ensemble){
			if(((Ensemble) ensemble).getShapes() != null && ((Ensemble) ensemble).getShapes().size() != 0);{
				return true;
			}
		}
		return false;
	}

}