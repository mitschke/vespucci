package de.tud.cs.st.vespucci.codeelementfinder.interfaces.spi;

import scala.util.MurmurHash;
import de.tud.cs.st.bat.FieldType;
import de.tud.cs.st.bat.FieldType$;
import de.tud.cs.st.bat.Type;
import de.tud.cs.st.bat.VoidType;

public class TypeUtil {

	
	private static Type getType(String typeQualifier) {
		if (typeQualifier == null || typeQualifier.length() == 0)
			return null;
		if (typeQualifier.charAt(0) == 'V')
			return VoidType.apply();
		FieldType type = FieldType$.MODULE$.apply(typeQualifier);
		return type;
	}
	
	public static int hashCode(String typeQualifier) {
		if (typeQualifier == null)
			return 0;
		return getType(typeQualifier).hashCode();
	}
	
	public static int hashCode(String[] typeQualifierArray) {
		if (typeQualifierArray == null)
			return 0;
		Type[] types = new Type[typeQualifierArray.length];
		for (int i = 0; i < typeQualifierArray.length; i++) {
			String t = typeQualifierArray[i];
			types[i] = getType(t);
		}
	    MurmurHash<Type> hashFunc = new scala.util.MurmurHash<Type>("Seq".hashCode());
	    for (int i = 0; i < types.length; i++) {
			Type type = types[i];
			hashFunc.apply((Object)type);
		}
	    return hashFunc.hash();
	}

}
