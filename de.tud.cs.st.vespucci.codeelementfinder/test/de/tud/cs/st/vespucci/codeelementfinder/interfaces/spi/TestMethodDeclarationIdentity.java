package de.tud.cs.st.vespucci.codeelementfinder.interfaces.spi;

import java.util.Arrays;

import org.junit.Test;

import sae.bytecode.model.MethodReference;
import scala.collection.immutable.Vector;
import unisson.query.code_model.MethodDeclarationAdapter;

import junit.framework.Assert;
import de.tud.cs.st.bat.ObjectType;
import de.tud.cs.st.bat.Type;
import de.tud.cs.st.bat.VoidType;

public class TestMethodDeclarationIdentity {

	@Test
	public void testEqualityVespucciToSAE_NoParams() {
		MethodDeclaration vespMethodDecl = new MethodDeclaration("test",
				"Test", "test", "V", new String[] {});
		MethodReference reference = new MethodReference(new ObjectType(
				"test/Test"), "test",
				new Vector<Type>(0, 0, 0), VoidType.apply());

		MethodDeclarationAdapter saeMethodDecl = new MethodDeclarationAdapter(
				reference);
		Assert.assertEquals(vespMethodDecl, saeMethodDecl);
	}

	@Test
	public void testEqualitySAEToVespucci_NoParams() {
		MethodDeclaration vespMethodDecl = new MethodDeclaration("test",
				"Test", "test", "V", new String[] {});
		MethodReference reference = new MethodReference(new ObjectType(
				"test/Test"), "test",
				new Vector<Type>(0, 0, 0), VoidType.apply());

		MethodDeclarationAdapter saeMethodDecl = new MethodDeclarationAdapter(
				reference);
		Assert.assertEquals(saeMethodDecl, vespMethodDecl);
	}

	@Test
	public void testHashEqualityVespucciAndSAE_NoParams() {
		MethodDeclaration vespMethodDecl = new MethodDeclaration("test",
				"Test", "test", "V", new String[] {});
		MethodReference reference = new MethodReference(new ObjectType(
				"test/Test"), "test",
				new Vector<Type>(0, 0, 0), VoidType.apply());

		MethodDeclarationAdapter saeMethodDecl = new MethodDeclarationAdapter(
				reference);

		Assert.assertEquals(saeMethodDecl.hashCode(), vespMethodDecl.hashCode());
	}

	@Test
	public void testTypeQualifierEqualityVespucciAndSAE_NoParams() {
		MethodDeclaration vespMethodDecl = new MethodDeclaration("test",
				"Test", "test", "V", new String[] {});
		MethodReference reference = new MethodReference(new ObjectType(
				"test/Test"), "test",
				new Vector<Type>(0, 0, 0), VoidType.apply());

		MethodDeclarationAdapter saeMethodDecl = new MethodDeclarationAdapter(
				reference);

		Assert.assertEquals(saeMethodDecl.getReturnTypeQualifier(),
				vespMethodDecl.getReturnTypeQualifier());

		Assert.assertTrue(Arrays.equals(
				saeMethodDecl.getParameterTypeQualifiers(),
				vespMethodDecl.getParameterTypeQualifiers()));
	}
	
}
