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
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.sadclient.controller;

import java.io.File;

import org.eclipse.jface.viewers.Viewer;

import de.tud.cs.st.vespucci.sadclient.model.SAD;

/**
 * This bean represents instructions needed to perform an update to an existing
 * SAD or to store new one.
 * 
 * @author Mateusz Parzonka
 * 
 */
public class SADUpdate {

    final Viewer viewer;

    boolean descriptionChanged;
    SAD sad;
    boolean deleteModel;
    File modelFile;
    boolean deleteDocumentation;
    File documentationFile;

    public SADUpdate(Viewer viewer) {
	super();
	this.viewer = viewer;
    }

    public boolean isDescriptionChanged() {
	return descriptionChanged;
    }

    public void setDescriptionChanged(boolean descriptionChanged) {
	this.descriptionChanged = descriptionChanged;
    }

    public SAD getSAD() {
	return sad;
    }

    public void setSAD(SAD sad) {
	this.sad = sad;
    }

    public boolean isDeleteModel() {
	return deleteModel;
    }

    public void setDeleteModel(boolean deleteModel) {
	this.deleteModel = deleteModel;
    }

    public File getModelFile() {
	return modelFile;
    }

    public void setModelFile(File modelFile) {
	this.modelFile = modelFile;
    }

    public boolean isDeleteDocumentation() {
	return deleteDocumentation;
    }

    public void setDeleteDocumentation(boolean deleteDocumentation) {
	this.deleteDocumentation = deleteDocumentation;
    }

    public File getDocumentationFile() {
	return documentationFile;
    }

    public void setDocumentation(File documentationFile) {
	this.documentationFile = documentationFile;
    }

    public Viewer getViewer() {
	return viewer;
    }

}
