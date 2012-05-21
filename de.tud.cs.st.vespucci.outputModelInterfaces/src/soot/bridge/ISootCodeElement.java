package soot.bridge;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;

/**
 * 
 * @author Ralf Mitschke
 *
 */
public interface ISootCodeElement extends ICodeElement {

	/**
	 * Returns soot compatible identifier of the element. <br>
	 * for classes:<br>
	 * a fully qualified class name<br>
	 * e.g. com.ctc.wstx.compat.Jdk14Impl$Foo<br>
	 * <br>
	 * for methods:<br>
	 * The signature is wrapped in angle brackets and starts with the declaring type, then a colon, then return
	 * type followed by space and the methods name, then parenthesis and a comma
	 * separated list of parameter types.<br>
	 * e.g. &lt;org.apache.geronimo.connector.deployment.AdminObjectRefBuilder:void
	 * &lt;init&gt;(org.apache.geronimo.kernel.repository.Environment,java.lang.String
	 * [][])&gt;<br>
	 * <br>
	 * for fields:<br>
	 * The signature is wrapped in angle brackets and starts with the declaring type, then a colon, then field
	 * type followed by space and the field name<br>
	 * e.g. &lt;soot.jimple.interproc.ifds.solver.IDESolver:com.google.common.collect.Table endSummary&gt;
	 */
	String getSootIdentifier();

}
