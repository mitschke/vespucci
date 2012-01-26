package de.tud.cs.st.vespucci.interfaces;



public interface IViolationView extends IDataView<IViolation>
{

	IDataView<IViolationSummary> getSummaryView();
	
}
