package de.tud.cs.st.vespucci.codeelementfinder.interfaces.spi;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import scala.collection.JavaConversions;
import scala.collection.Seq;
import de.tud.cs.st.bat.ArrayType;
import de.tud.cs.st.bat.BooleanType;
import de.tud.cs.st.bat.ByteType;
import de.tud.cs.st.bat.CharType;
import de.tud.cs.st.bat.DoubleType;
import de.tud.cs.st.bat.FloatType;
import de.tud.cs.st.bat.IntegerType;
import de.tud.cs.st.bat.LongType;
import de.tud.cs.st.bat.ObjectType;
import de.tud.cs.st.bat.ShortType;
import de.tud.cs.st.bat.Type;
import de.tud.cs.st.bat.VoidType;

public class TestTypeUtil {

	@Test
	public void testVoidType() {
		VoidType type = VoidType.apply();
		assertEquals(type.hashCode(), TypeUtil.hashCode(type.signature()));
	}

	@Test
	public void testByteType() {
		ByteType type = ByteType.apply();
		assertEquals(type.hashCode(), TypeUtil.hashCode(type.signature()));
	}

	@Test
	public void testCharType() {
		CharType type = CharType.apply();
		assertEquals(type.hashCode(), TypeUtil.hashCode(type.signature()));
	}

	@Test
	public void testDoubleType() {
		DoubleType type = DoubleType.apply();
		assertEquals(type.hashCode(), TypeUtil.hashCode(type.signature()));
	}

	@Test
	public void testFloatType() {
		FloatType type = FloatType.apply();
		assertEquals(type.hashCode(), TypeUtil.hashCode(type.signature()));
	}

	@Test
	public void testIntegerType() {
		IntegerType type = IntegerType.apply();
		assertEquals(type.hashCode(), TypeUtil.hashCode(type.signature()));
	}

	@Test
	public void testLongType() {
		LongType type = LongType.apply();
		assertEquals(type.hashCode(), TypeUtil.hashCode(type.signature()));
	}

	@Test
	public void testShortType() {
		ShortType type = ShortType.apply();
		assertEquals(type.hashCode(), TypeUtil.hashCode(type.signature()));
	}

	@Test
	public void testBooleanType() {
		BooleanType type = BooleanType.apply();
		assertEquals(type.hashCode(), TypeUtil.hashCode(type.signature()));
	}

	@Test
	public void testObjectType() {
		ObjectType type = new ObjectType("test/Test");
		assertEquals(type.hashCode(), TypeUtil.hashCode("Ltest/Test;"));
	}

	@Test
	public void testArrayType() {
		ArrayType type = new ArrayType(1, new ObjectType("test/Test"));
		assertEquals(type.hashCode(), TypeUtil.hashCode(type.signature()));
	}

	@Test
	public void testEmptyArrayTypes() {
		String[] primitveTypes = new String[] {};
		Seq<Type> scalaTypes = JavaConversions.asScalaBuffer(
				new ArrayList<Type>(0)).toSeq();
		assertEquals(scalaTypes.hashCode(), TypeUtil.hashCode(primitveTypes));
	}

	@Test
	public void testArrayOfOnePrimitiveType() {
		String[] primitveTypes = new String[] { "Z" };
		List<Type> javaList = new ArrayList<Type>();
		javaList.add(BooleanType.apply());
		Seq<Type> scalaTypes = JavaConversions.asScalaBuffer(javaList).toSeq();
		assertEquals(scalaTypes.hashCode(), TypeUtil.hashCode(primitveTypes));
	}

	@Test
	public void testArrayOfMorePrimitiveTypes() {
		String[] primitveTypes = new String[] { "Z", "I", "F" };
		List<Type> javaList = new ArrayList<Type>();
		javaList.add(BooleanType.apply());
		javaList.add(IntegerType.apply());
		javaList.add(FloatType.apply());
		Seq<Type> scalaTypes = JavaConversions.asScalaBuffer(javaList).toSeq();
		assertEquals(scalaTypes.hashCode(), TypeUtil.hashCode(primitveTypes));
	}

	@Test
	public void testArrayOfObjectTypes() {
		String[] primitveTypes = new String[] { "[Ltest/Test;", "[[I" };
		List<Type> javaList = new ArrayList<Type>();
		javaList.add(new ArrayType(1, new ObjectType("test/Test")));
		javaList.add(new ArrayType(1, new ArrayType(1,IntegerType.apply())));
		// javaList.add(new ArrayType(2, IntegerType.apply()));
		Seq<Type> scalaTypes = JavaConversions.asScalaBuffer(javaList).toSeq();
		assertEquals(scalaTypes.hashCode(), TypeUtil.hashCode(primitveTypes));
	}

}
