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
package de.tud.cs.st.vespucci.sadclient.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.List;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.internal.resources.Resource;
import org.eclipse.ui.part.IDropActionDelegate;

import de.tud.cs.st.vespucci.sadclient.controller.Controller;
import de.tud.cs.st.vespucci.sadclient.model.SAD;

/**
 * This {@link IDropActionDelegate} is responsible for drops of (multiple) SADs
 * into the package explorer.
 * 
 * @author Mateusz Parzonka
 * 
 */
@SuppressWarnings("restriction")
public class ExplorerDropDelegate implements IDropActionDelegate {

    @Override
    public boolean run(Object source, Object target) {

	if (target instanceof Project || target instanceof Folder) {
	    Resource resource = (Resource) target;
	    File file = resource.getLocation().toFile();

	    if (source instanceof byte[]) {
		try {
		    Controller.getInstance().downloadModelsAndDocumentation(toSADs((byte[]) source), file.getAbsolutePath(), resource);
		    return true;
		} catch (Exception e) {
		    e.printStackTrace();
		    return false;
		}
	    }
	}
	return false;
    }

    @SuppressWarnings("unchecked")
    private static List<SAD> toSADs(byte[] bytes) throws Exception {
	ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
	ObjectInput in = new ObjectInputStream(bis);
	Object o = in.readObject();
	bis.close();
	in.close();
	return (List<SAD>) o;
    }

}
