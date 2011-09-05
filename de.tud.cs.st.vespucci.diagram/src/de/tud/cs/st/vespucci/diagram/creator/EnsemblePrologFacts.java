package de.tud.cs.st.vespucci.diagram.creator;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;

import de.tud.cs.st.vespucci.vespucci_model.Dummy;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.Shape;

public class EnsemblePrologFacts {

	/**
	 * Regular expression to check if argument starts with an upper case letter. E.g. a parameter
	 * variable.
	 */
	private static final Pattern FIRST_LETTER_IS_UPPER_CASE = Pattern.compile("\\p{Upper}.*");

	/**
	 * Regular expression to match a name of an ensemble that contains parameter.
	 */
	private static final Pattern PARAMETER_LIST = Pattern.compile("^.+?" + // match the descriptor
			"\\(" + // match the first bracket
			"(.*)" + // match anything in between as group
			"\\)$"); // match the last parenthesis by asserting the string ends here
	/**
	 * Regular expression to split parameter names of ensembles.
	 */
	private static final Pattern PARAMETER_NAMES = Pattern.compile("(.*?)=(.*)");

	/**
	 * Name of the current diagram file.
	 */
	private static String diagramFileName;

	/**
	 * A convenience method to retrieve the ensemble prolog facts.
	 * 
	 * @param shapeList
	 * @param diagramFileName
	 * @return Returns the formatted ensemble facts.
	 */
	static StringBuilder getFacts(final List<Shape> shapeList, final String diagramFileName) throws Exception {
		EnsemblePrologFacts.diagramFileName = diagramFileName;
		return createEnsembleFacts(shapeList);
	}

	/**
	 * Search the diagram recursively and create all ensemble facts, except Dummy.
	 * 
	 * @param shapeList
	 *            The list of shapes in the diagram.
	 * @throws Exception
	 * @return Returns the formatted ensemble facts.
	 */
	static StringBuilder createEnsembleFacts(final List<Shape> shapeList) throws Exception {
		final StringBuilder ensembleFacts = new StringBuilder();
		for (final Shape shape : shapeList) {
			// create Ensemble facts:
			if (shape instanceof Ensemble && shape != null) {
				final Ensemble ensemble = (Ensemble) shape;
				if (isAbstractEnsemble(ensemble)) {
					ensembleFacts.append("abstract_ensemble");
				} else {
					ensembleFacts.append("ensemble");
				}

				// TODO: This is a workaround - invent a platform independent solution
				final String query = ensemble.getQuery().replaceAll("\\p{Space}", " ");

				ensembleFacts.append(String.format("('%s', %s, %s, (%s), [%s]).\n", diagramFileName,
						createEnsembleDescriptor(ensemble), createEnsembleParameters(ensemble), query,
						listSubEnsembles(ensemble.getShapes())));

				// do children exist
				if ((ensemble.getShapes() != null) && (ensemble.getShapes().size() > 0)) {
					ensembleFacts.append(createEnsembleFacts(ensemble.getShapes()));
				}

			}

		}
		return ensembleFacts;
	}

	/**
	 * @param ensemble
	 * @return Return true, only if the ensemble is abstract, i.e. the ensemble contains at least
	 *         one parameter
	 *         variable.
	 */
	private static boolean isAbstractEnsemble(final Ensemble ensemble) {
		final String[] parameters = splitEnsembleParameterList(ensemble);
		for (final String parameter : parameters) {
			if (FIRST_LETTER_IS_UPPER_CASE.matcher(parameter).matches()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param shape
	 * @return The name of the ensemble in apostrophes. (Without parameters)
	 */
	static String createEnsembleDescriptor(final Shape shape) {
		String name = "";
		if (shape.getName() != null) {
			name = shape.getName().length() == 0 ? "non-editpart" : shape.getName();
		}
		final StringBuilder s = new StringBuilder("'");
		if (name.indexOf('(') > 0) {
			s.append(name.subSequence(0, name.indexOf('(')));
		} else {
			s.append(name);
		}
		s.append("'");
		return s.toString();
	}

	/**
	 * @param ensemble
	 *            The ensemble whose parameters shall be extracted.
	 * @return A prolog list of the form ['ParamName'=ParamName, ...]
	 */
	private static String createEnsembleParameters(final Ensemble ensemble) {
		final String[] parameters = splitEnsembleParameterList(ensemble);
		if (parameters.length == 0) {
			return "[]";
		}
		final StringBuilder s = new StringBuilder("[");
		s.append(createEncodedParameter(parameters[0]));
		for (int i = 1; i < parameters.length; i++) {
			s.append(", ");
			s.append(createEncodedParameter(parameters[i]));
		}
		s.append("]");
		return s.toString();
	}

	/**
	 * Create a formatted string with all given ensembles.
	 * 
	 * @param ensembles
	 * @return Return formatted string listing the given ensembles.
	 */
	private static String listSubEnsembles(final EList<Shape> ensembles) {
		final StringBuilder strBuilder = new StringBuilder();
		if (ensembles == null) {
			return strBuilder.toString();
		}

		String komma = "";
		for (final Shape shape : ensembles) {
			if (shape instanceof Dummy) {
				strBuilder.append(komma + "'empty'");
			} else if (shape instanceof Ensemble) {
				strBuilder.append(komma + "'" + shape.getName() + "'");
			}
			komma = ", ";
		}
		return strBuilder.toString();
	}

	/**
	 * Returns the parameter definitions of an ensemble. I.e. the parameter list split at ", ".
	 * 
	 * @param ensemble
	 * @return Return an array containing the parameters of given ensemble.
	 */
	private static String[] splitEnsembleParameterList(final Ensemble ensemble) {
		if (ensemble.getName() == null) {
			ensemble.setName("");
		}
		final String name = ensemble.getName().length() == 0 ? "non-editpart" : ensemble.getName();
		final Matcher m = PARAMETER_LIST.matcher(name);
		if (!m.matches()) {
			return new String[0];
		}
		final List<String> parameterDefinitions = new LinkedList<String>();
		final String parameters = m.group(1);
		int start = 0;
		int matchParenthesis = 0;
		for (int i = 0; i < parameters.length(); i++) {
			if (parameters.charAt(i) == '(') {
				matchParenthesis++;
			}
			if (matchParenthesis > 0 && parameters.charAt(i) == ')') {
				matchParenthesis--;
			}
			if (parameters.charAt(i) == ',' && matchParenthesis == 0) {
				parameterDefinitions.add(parameters.substring(start, i).trim());
				start = i + 1;
			}
		}
		parameterDefinitions.add(parameters.substring(start, parameters.length()).trim());
		final String[] result = new String[parameterDefinitions.size()];
		return parameterDefinitions.toArray(result);
	}

	/**
	 * @param parameter
	 * @return Return the encoded parameter.
	 */
	private static String createEncodedParameter(final String parameter) {
		final StringBuilder s = new StringBuilder();
		if (FIRST_LETTER_IS_UPPER_CASE.matcher(parameter).matches()) {
			s.append("'");
			s.append(parameter);
			s.append("'");
			s.append("=");
			s.append(parameter);
			return s.toString();
		}
		final Matcher m = PARAMETER_NAMES.matcher(parameter);
		if (m.matches()) {
			s.append("'");
			s.append(m.group(1));
			s.append("'");
			s.append("=");
			s.append(m.group(2));
			return s.toString();
		}

		s.append("_");
		s.append("=");
		s.append(parameter);
		return s.toString();
	}

}
