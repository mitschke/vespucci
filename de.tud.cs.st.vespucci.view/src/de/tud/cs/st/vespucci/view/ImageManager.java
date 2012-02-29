package de.tud.cs.st.vespucci.view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

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
