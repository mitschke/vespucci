package de.tud.cs.st.vespucci.marker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;

public class DescriptionFactory {
	
	HashMap<String, DescriptionGenerator> descMap;
	
	public DescriptionFactory(){
		
		this.descMap = new HashMap<String, DescriptionGenerator>();
		fillDescMapWithViolationKinds();
		
	}
	
	public String getDescription(IViolation violation){
		
		DescriptionGenerator descGen = this.descMap.get(violation.getViolatingKind());	
		return descGen.buildDescription(violation);
		
		}
	
	public String getDescription(IViolationSummary violationSummmary){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("There are ");
		sb.append(violationSummmary.numberOfViolations());
		sb.append("from Ensemble ");
		sb.append(violationSummmary.getSourceEnsemble());
		sb.append(" to Ensemble ");
		sb.append(violationSummmary.getTargetEnsemble());
		// Optional:
		sb.append(" (Contraint Type: )");
		sb.append(violationSummmary.getConstraint().getDependencyKind());
		sb.append(")");
		
		return sb.toString();
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

				return violation.getViolatingKind();
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

		this.descMap.put("cast", new DescriptionGenerator() {

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
	}

}
