package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import de.tud.cs.st.vespucci.errors.VespucciIllegalArgumentException;

public class VisitableCaster {
	public static IVisitable toVisitable(Object element) {
		String classSimpleName = element.getClass().getSimpleName();
		if (classSimpleName.indexOf('I') == 0) {
			classSimpleName = classSimpleName.substring(1, classSimpleName.length());
		}
		
		String visitableClassName = "Visitable" + classSimpleName;
		
		Class<IVisitable> visitableClass = null;
		
		try {
			visitableClass = (Class<IVisitable>)Class.forName(visitableClassName);
		} catch (ClassNotFoundException e) {
			throw new VespucciIllegalArgumentException(String.format("Given argument [%s] not supported.", element));
		}
		
		Class partypes[] = new Class[] {
				element.getClass()
		};
		
		Constructor constructor = null;
		
		try {
			constructor = visitableClass.getConstructor(partypes);
		} catch (SecurityException e) {
			throw new VespucciIllegalArgumentException(String.format("Given argument [%s] not supported.", element));
		} catch (NoSuchMethodException e) {
			throw new VespucciIllegalArgumentException(String.format("Given argument [%s] not supported.", element));
		}
		
		IVisitable visitable = null;;
		
		try {
			visitable = (IVisitable)constructor.newInstance(element);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return visitable;
	}
}
