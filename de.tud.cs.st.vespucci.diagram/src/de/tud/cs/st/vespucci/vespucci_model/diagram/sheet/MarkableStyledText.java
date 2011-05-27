package de.tud.cs.st.vespucci.vespucci_model.diagram.sheet;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;

/**
 * Additional to StyledText this class offers methods to mark positions of the text.
 * 
 * @see org.eclipse.swt.custom.StyledText
 * @author Alexander Weitzmann
 * @version 1.0
 */
public class MarkableStyledText extends StyledText{

	// Set containing all marked positions.
	private Set<Integer> markedPositions;
	
	// Initial set-size for marked positions.
	private static final int MARKED_SET_INIT_SIZE = 100;
	
	/**
	 * Constructor <br>
	 * @see org.eclipse.swt.custom.StyledText#StyledText(Composite, int)
	 */
	public MarkableStyledText(Composite parent, int style) {
		super(parent, style);
		markedPositions = new HashSet<Integer>(MARKED_SET_INIT_SIZE);
	}
	
	/**
	 * Marks given position.
	 * @param position Position to be marked
	 */
	public void markPosition(int position){
		markedPositions.add(Integer.valueOf(position));
	}
	
	/**
	 * Unmarks all text-positions.
	 */
	public void unmarkAllPositions(){
		markedPositions = new HashSet<Integer>(MARKED_SET_INIT_SIZE);
	}
	
	/**
	 * Unmarks given position.
	 * @param position Position to be unmarked
	 */
	public void unmarkPosition(int position){
		markedPositions.remove(Integer.valueOf(position));
	}
	
	/**
	 * Checks if given position is marked.
	 * @param position Position of interest.
	 * @return True, if given position is marked, false otherwise.
	 */
	public boolean isPositionMarked(int position){
		return markedPositions.contains(Integer.valueOf(position));
	}
}
