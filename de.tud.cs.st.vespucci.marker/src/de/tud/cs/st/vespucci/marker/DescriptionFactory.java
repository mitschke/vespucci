package de.tud.cs.st.vespucci.marker;

import java.util.HashMap;

import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.model.IExpected;
import de.tud.cs.st.vespucci.model.IGlobalIncoming;
import de.tud.cs.st.vespucci.model.IGlobalOutgoing;
import de.tud.cs.st.vespucci.model.IInAndOut;
import de.tud.cs.st.vespucci.model.IIncoming;
import de.tud.cs.st.vespucci.model.INotAllowed;
import de.tud.cs.st.vespucci.model.IOutgoing;

public class DescriptionFactory {

	HashMap<String, DescriptionGenerator> descMap;

	public DescriptionFactory() {

		this.descMap = new HashMap<String, DescriptionGenerator>();
		fillDescMapWithViolationKinds();

	}

	public String getDescription(IViolation violation) {

		DescriptionGenerator descGen = this.descMap.get(violation
				.getViolatingKind());
		return descGen.buildDescription(violation);

	}

	public static String getDescription(IViolationSummary violationSummmary){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(getQualifiedName(violationSummmary.getSourceEnsemble()));
		sb.append(" to ");
		sb.append(getQualifiedName(violationSummmary.getTargetEnsemble()));
		sb.append(" has ");
		sb.append(violationSummmary.numberOfViolations());
		sb.append(" violation(s)");
		
		// Constraint:
		sb.append(" of ");
		sb.append(getConstraintType(violationSummmary.getConstraint()));
		sb.append("'");
		sb.append(violationSummmary.getConstraint().getDependencyKind());
		sb.append("'");
		sb.append(" from "); 
		sb.append(getQualifiedName(violationSummmary.getConstraint().getSource()));
		sb.append(" to ");
		sb.append(getQualifiedName(violationSummmary.getConstraint().getTarget()));
		
		return sb.toString();
	}

	public static String getQualifiedName(IEnsemble ensemble) {
		if (ensemble.getParent() == null)
			return ensemble.getName();
		return getQualifiedName(ensemble.getParent()) + "."
				+ ensemble.getName();
	}

	public static String getConstraintType(IConstraint constraint) {
		if (constraint instanceof IIncoming)
			return "incoming";
		if (constraint instanceof IOutgoing)
			return "incoming";
		if (constraint instanceof IInAndOut)
			return "in/out";
		if (constraint instanceof IGlobalIncoming)
			return "global incoming";
		if (constraint instanceof IGlobalOutgoing)
			return "global outgoing";
		if (constraint instanceof IExpected)
			return "expected";
		if (constraint instanceof INotAllowed)
			return "not allowed";
		return "unknown";
	}

	private void fillDescMapWithViolationKinds() {

		this.descMap.put("extends", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(sourcePrefix(false, violation));
				sb.append("extends class: ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("implements", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(sourcePrefix(false, violation));
				sb.append("implements interface: ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("invoke_special", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(sourcePrefix(true, violation));
				sb.append("calls a constructor on class: ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("invoke_virtual", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(sourcePrefix(true, violation));
				sb.append("calls a method on class: ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("invoke_interface", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(sourcePrefix(true, violation));
				sb.append("calls a method on interface: ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});
		
		this.descMap.put("invoke_static", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(sourcePrefix(true, violation));
				sb.append("calls a static method on : ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("write_field", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(sourcePrefix(true, violation));
				sb.append("assigns a new Value to a field in ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("read_field", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(sourcePrefix(true, violation));
				sb.append("reads the Value of a field in ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("parameter", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(sourcePrefix(true, violation));
				sb.append("declares a parameter of type ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("return_type", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(sourcePrefix(true, violation));
				sb.append("has the return type of");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("class_cast", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(sourcePrefix(true, violation));
				sb.append("casts an object to ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("create", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(sourcePrefix(true, violation));
				sb.append("instantiates class: ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("field_type", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append("A field in ");
				sb.append(sourcePrefix(false, violation));
				sb.append("is declared as type: ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("throws", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append("A exception in ");
				sb.append(sourcePrefix(false, violation));
				sb.append("is thrown of type: ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});

		this.descMap.put("instanceof", new DescriptionGenerator() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append("An instanceof check in ");
				sb.append(sourcePrefix(false, violation));
				sb.append("is performed on type: ");
				sb.append(targetSuffix(violation));

				return sb.toString();
			}
		});
	}

}
