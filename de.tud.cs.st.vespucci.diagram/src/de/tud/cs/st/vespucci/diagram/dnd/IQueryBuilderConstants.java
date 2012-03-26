package de.tud.cs.st.vespucci.diagram.dnd;

public interface IQueryBuilderConstants {
	// constants for the querybuilder
	static final String PACKAGE_QUERY = "package";
	static final String CLASS_WITH_MEMBERS_QUERY = "class_with_members";
	static final String CLASS_QUERY = "class";
	static final String METHOD_QUERY = "method";
	static final String FIELD_QUERY = "field";
	static final String DERIVED_QUERY = "derived";
	static final Object EMPTY_QUERY = "empty";
	
	static final String QUERY_DELIMITER = "or";
	static final String PARAMETER_DELIMITER = ",";
}