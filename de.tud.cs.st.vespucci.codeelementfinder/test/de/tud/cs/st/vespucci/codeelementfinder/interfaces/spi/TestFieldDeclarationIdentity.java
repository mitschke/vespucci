package de.tud.cs.st.vespucci.codeelementfinder.interfaces.spi;

import junit.framework.Assert;

import org.junit.Test;

import unisson.query.code_model.FieldDeclarationAdapter;
import de.tud.cs.st.bat.IntegerType;
import de.tud.cs.st.bat.ObjectType;

public class TestFieldDeclarationIdentity {

	@Test
	public void testEqualityVespucciToSAE() {
		FieldDeclaration vespFieldDecl = new FieldDeclaration("test", "Test",
				"test", "I");
		sae.bytecode.model.FieldDeclaration reference = new sae.bytecode.model.FieldDeclaration(
				new ObjectType("test/Test"), "test", IntegerType.apply(), 0,
				false, false);
		FieldDeclarationAdapter saeFielDecl = new FieldDeclarationAdapter(
				reference);

		Assert.assertEquals(vespFieldDecl, saeFielDecl);
	}

	@Test
	public void testEqualitySAEToVespucci() {
		FieldDeclaration vespFieldDecl = new FieldDeclaration("test", "Test",
				"test", "I");
		sae.bytecode.model.FieldDeclaration reference = new sae.bytecode.model.FieldDeclaration(
				new ObjectType("test/Test"), "test", IntegerType.apply(), 0,
				false, false);
		FieldDeclarationAdapter saeFielDecl = new FieldDeclarationAdapter(
				reference);

		Assert.assertEquals(saeFielDecl, vespFieldDecl);
	}

	@Test
	public void testHashEqualityVespucciAndSAE() {
		FieldDeclaration vespFieldDecl = new FieldDeclaration("test", "Test",
				"test", "I");
		sae.bytecode.model.FieldDeclaration reference = new sae.bytecode.model.FieldDeclaration(
				new ObjectType("test/Test"), "test", IntegerType.apply(), 0,
				false, false);
		FieldDeclarationAdapter saeFielDecl = new FieldDeclarationAdapter(
				reference);

		Assert.assertEquals(vespFieldDecl.hashCode(), saeFielDecl.hashCode());
	}

	@Test
	public void testTypeQualifierEqualityVespucciAndSAE() {
		FieldDeclaration vespFieldDecl = new FieldDeclaration("test", "Test",
				"test", "I");
		sae.bytecode.model.FieldDeclaration reference = new sae.bytecode.model.FieldDeclaration(
				new ObjectType("test/Test"), "test", IntegerType.apply(), 0,
				false, false);
		FieldDeclarationAdapter saeFielDecl = new FieldDeclarationAdapter(
				reference);
		Assert.assertEquals(saeFielDecl.getTypeQualifier(),
				vespFieldDecl.getTypeQualifier());
	}

}
