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
package de.tud.cs.st.vespucci.generateprologfacts;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.resources.IFile;

import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.diagram.processing.IModelSaveAction;
import de.tud.cs.st.vespucci.diagram.processing.Util;
import de.tud.cs.st.vespucci.exceptions.VespucciIOException;
import de.tud.cs.st.vespucci.generateprologfacts.creator.PrologFileCreator;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;
/**
 * A processor/handler for saving a *.sad file to a *.pl Prolog file
 * 
 * @author Patrick Gottschämmer
 * @author Olav Lenz
 */
public class GenerateProlog implements IModelProcessor, IModelSaveAction {
	
	public GenerateProlog() {
	}

	@Override
	public Object processModel(Object diagramModel) {
		
		final PrologFileCreator prologFileCreator = new PrologFileCreator();
		
		IFile diagramFile = Util.adapt(diagramModel, IFile.class);
		ShapesDiagram shapesdiagram = Util.adapt(diagramModel, ShapesDiagram.class);
				
		try {
			prologFileCreator.createPrologFileFromDiagram(diagramFile, shapesdiagram);
		} catch (final FileNotFoundException e) {
			throw new VespucciIOException(String.format("File [%s] not found.",diagramFile), e);
		} catch (final IOException e) {
			throw new VespucciIOException(String.format("Failed to save Prolog file from [%s].",diagramFile), e);
		} catch (final Exception e) {
			throw new VespucciIOException(String.format("File [%s] not found.",diagramFile), e);
		}

		return null;
	}
		
	@Override
	public void doSave(Object diagramModel) {
		processModel(diagramModel);
	}

	@Override
	public Class<?> resultClass() {
		return null;
	}

}
