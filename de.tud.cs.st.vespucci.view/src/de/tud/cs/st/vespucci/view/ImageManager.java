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
package de.tud.cs.st.vespucci.view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * Class which provides images (cached) for other Plug-in projects
 * 
 * @author Olav Lenz 
 */
public class ImageManager {

	public static final String VESPUCCI_DIAGRAM_FILE = "icons/Vespucci_modelDiagramFile.gif";
	public static final String ICON_PROJECT = "icons/icon_project.gif";
	public static final String ICON_PACKAGE = "icons/icon_package.gif";
	public static final String ICON_CLASS = "icons/icon_class.gif";
	public static final String ICON_METHOD_UNCOLORED = "icons/icon_method_uncolored.gif";
	public static final String ICON_FIELD_UNCOLORED = "icons/icon_field_uncolored.gif";
	public static final String ENSEMBLE = "icons/ensemble.gif";
	public static final String EXPAND_ALL = "icons/expand_all.gif";
	public static final String COLLAPSE_ALL = "icons/collapse_all.gif";
	
	private static Map<String, Image> imageCache = new HashMap<String, Image>();
	
	/**
	 * Return image for given name, if ImageManager know this name and is able to load
	 * the image. Otherwise null is returned.
	 * (Names and corresponding path for Images are stored as static variables in ImageManager)
	 * 
	 * @param name Name of the wanted image
	 * @return Image for given name, null if there is no image for this name
	 */
	public static Image getImage(String name){
		if (imageCache.containsKey(name)){
			return imageCache.get(name);
		}
		
		ImageDescriptor imageDescriptor = Activator.getImageDescriptor(name);
		if (imageDescriptor != null){
			Image image = imageDescriptor.createImage();
			imageCache.put(name, image);
			return image;
		}
		return null;
	}
}
