/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Technology Group Group or Technische
 *     Universität Darmstadt nor the names of its contributors may be used to
 *     endorse or promote products derived from this software without specific
 *     prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.marker;

import java.util.HashMap;

import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.utilities.ModelUtils;

/**
 * Generator for building specific Descriptions for different kinds of violations.
 * Use of strategy pattern and hashmap for building the different descriptions 
 * for each kind of violation.
 * 
 * @author Patrick Gottschaemmer
 * @author Olav Lenz
 */
public class DescriptionGenerator {
	
	// Unicode for "->" : \u2192 
	// or just use "to"
	public static final String TOCHAR = "\u2192";
	
	// Hashmap for ViolationString --> DescriptionBuilder
	private HashMap<String, DescriptionBuilder> descMap;
	
	// Static Singleton Instance
	private static DescriptionGenerator staticGenerator;

	private DescriptionGenerator() {

		this.descMap = new HashMap<String, DescriptionBuilder>();
		fillDescMapWithViolationKinds();

	}
	
	public static DescriptionGenerator getInstance() {
		
		if (staticGenerator == null){
			staticGenerator = new DescriptionGenerator();
		}
		
		return staticGenerator;
	}

	public String getDescription(IViolation violation) {

		DescriptionBuilder descGen = this.descMap.get(violation
				.getViolatingKind());
		
		//Check whether we recognized the ViolatingKind, deliver a generic DescriptionGenerator if not
		if (descGen == null){
			descGen = new DescriptionBuilder() {
				
				@Override
				public String buildDescription(IViolation violation) {
					
					StringBuffer sb = new StringBuffer();
					sb.append(prefix(violation));
					sb.append("Unrecognized violation of kind '");
					sb.append(violation.getViolatingKind());
					sb.append("'");
					return sb.toString();
				}
			};
		}
		
		return descGen.buildDescription(violation);

	}

	public String getDescription(IViolationSummary violationSummmary){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(getQualifiedName(violationSummmary.getSourceEnsemble()));
		sb.append(" to ");
		sb.append(getQualifiedName(violationSummmary.getTargetEnsemble()));
		sb.append(" has ");
		sb.append(violationSummmary.numberOfViolations());
		sb.append(" violation(s)");
		
		// Constraint:
		sb.append(" of ");
		sb.append(ModelUtils.getConstraintType(violationSummmary.getConstraint()));
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



	private void fillDescMapWithViolationKinds() {

		this.descMap.put("extends", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();
				sb.append(prefix(violation));
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" extends ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();
			}
		});

		this.descMap.put("implements", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();
				sb.append(prefix(violation));
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" implements ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();

			}
		});

		this.descMap.put("invoke_special", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();
				
				sb.append(prefix(violation));
				sb.append("A method in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" calls a constructor on ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();
			}
		});

		this.descMap.put("invoke_virtual", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(prefix(violation));
				sb.append("A method in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" calls a method on ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();
			}
		});

		this.descMap.put("invoke_interface", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(prefix(violation));
				sb.append("A method in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" calls a method on ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();
			}
		});
		
		this.descMap.put("invoke_static", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(prefix(violation));
				sb.append("A method in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" calls a static method on ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();
			}
		});

		this.descMap.put("write_field", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(prefix(violation));
				sb.append("A method in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" assigns a new value to a field in ");
				sb.append(violation.getTargetElement().getSimpleClassName());
				
				return sb.toString();
			}
		});

		this.descMap.put("read_field", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(prefix(violation));
				sb.append("A method in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" reads the value of a field in ");
				sb.append(violation.getTargetElement().getSimpleClassName());
				
				return sb.toString();
			}
		});

		this.descMap.put("parameter", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(prefix(violation));
				sb.append("A method in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" declares a parameter of type ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();
			}
		});

		this.descMap.put("return_type", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();
				
				sb.append(prefix(violation));
				sb.append("A method in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" has the return type of ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();
			}
		});

		this.descMap.put("class_cast", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(prefix(violation));
				sb.append("A method in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" casts an object to ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();
			}
		});

		this.descMap.put("create", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(prefix(violation));
				sb.append("A method in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" creates an instance of ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();
			}
		});

		this.descMap.put("field_type", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(prefix(violation));
				sb.append("A field in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" is declared as type ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();
			}
		});

		this.descMap.put("throws", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(prefix(violation));
				sb.append("An exception in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" is thrown of type ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();
			}
		});

		this.descMap.put("instanceof", new DescriptionBuilder() {

			public String buildDescription(IViolation violation) {

				StringBuffer sb = new StringBuffer();

				sb.append(prefix(violation));
				sb.append("An instanceof check in ");
				sb.append(violation.getSourceElement().getSimpleClassName());
				sb.append(" is performed on type: ");
				sb.append(violation.getTargetElement().getSimpleClassName());

				return sb.toString();
			}
		});
	}

	private String prefix(IViolation violation){
		
		StringBuffer sb = new StringBuffer();
		sb.append(violation.getSourceEnsemble().getName());
		sb.append(" " + TOCHAR + " ");
		sb.append(violation.getTargetEnsemble().getName());
		sb.append(": ");
		
		return sb.toString();
	}
}
