package de.tud.cs.st.vespucci.codeelementfinder.interfaces.spi;

import org.junit.Test;

import junit.framework.Assert;
import de.tud.cs.st.bat.ObjectType;

public class TestClassDeclarationIdentity {

	@Test
	public void testEqualityVespucciToSAE() {
		ClassDeclaration vespClassDecl = new ClassDeclaration("test", "Test");
		unisson.query.code_model.ClassDeclaration saeClassDecl = new unisson.query.code_model.ClassDeclaration(
				new ObjectType("test/Test"));
		Assert.assertEquals(vespClassDecl, saeClassDecl);
	}

	@Test
	public void testEqualitySAEToVespucci() {
		ClassDeclaration vespClassDecl = new ClassDeclaration("test", "Test");
		unisson.query.code_model.ClassDeclaration saeClassDecl = new unisson.query.code_model.ClassDeclaration(
				new ObjectType("test/Test"));
		Assert.assertEquals(saeClassDecl, vespClassDecl);
	}

	@Test
	public void testHashEqualityVespucciAndSAE() {
		ClassDeclaration vespClassDecl = new ClassDeclaration("test", "Test");
		unisson.query.code_model.ClassDeclaration saeClassDecl = new unisson.query.code_model.ClassDeclaration(
				new ObjectType("test/Test"));
		Assert.assertEquals(vespClassDecl.hashCode(), saeClassDecl.hashCode());
	}
	
	@Test
	public void testTypeQualifierEqualityVespucciAndSAE() {
		ClassDeclaration vespClassDecl = new ClassDeclaration("test", "Test");
		unisson.query.code_model.ClassDeclaration saeClassDecl = new unisson.query.code_model.ClassDeclaration(
				new ObjectType("test/Test"));
		Assert.assertEquals(vespClassDecl.getTypeQualifier(), saeClassDecl.getTypeQualifier());
	}


}
