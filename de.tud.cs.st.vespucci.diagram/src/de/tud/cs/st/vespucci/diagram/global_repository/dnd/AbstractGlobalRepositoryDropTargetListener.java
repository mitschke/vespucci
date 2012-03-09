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

package de.tud.cs.st.vespucci.diagram.global_repository.dnd;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DropTargetEvent;

import de.tud.cs.st.vespucci.vespucci_model.Ensemble;

/**
 * 
 * @author Tabea Born
 * @author Christian Knapp
 * TODO: Autorenfrage klären!
 *
 */

public abstract class AbstractGlobalRepositoryDropTargetListener extends AbstractTransferDropTargetListener {

	public AbstractGlobalRepositoryDropTargetListener(final EditPartViewer viewer) {
		super(viewer, LocalSelectionTransfer.getTransfer());
	}
	
	protected static void addTrensferredSelectionToRequest(Request request){
		ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
		if(selection ==null)
			return;
		if(selection.isEmpty())
			return;
		if(!(selection instanceof IStructuredSelection))
			return;
		
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		
		List<Ensemble> ensembleList = new ArrayList<Ensemble>();
		
		for(@SuppressWarnings("rawtypes")
		Iterator iterator = structuredSelection.iterator(); iterator.hasNext();){
			Object e = iterator.next();
			if(e instanceof Ensemble){
				ensembleList.add((Ensemble) e);
			}
		}
		
		//TODO: eigene HashMap?
	}
	
	@Override
	public boolean isEnabled(DropTargetEvent event){
		if (!super.isEnabled(event))
			return false; // we did not receive a LocalSelectionTransfer
			ISelection selection = LocalSelectionTransfer.getTransfer()
			.getSelection();
			if (selection == null)
			return false;
			if (selection.isEmpty())
			return false;
			if (!(selection instanceof IStructuredSelection))
			return false;
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			for (@SuppressWarnings("rawtypes")
			Iterator iterator = structuredSelection.iterator(); iterator.hasNext();) {
			Object next = iterator.next();
			if (!(next instanceof Ensemble)){
				return false;
			}
		}
		return true;
	}


}
