/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Author Tam-Minh Nguyen
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
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
 *   - Neither the name of the Software Engineering Group or Technische 
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
	 * @return Returns true only if given position is marked.
	 */
	public boolean isPositionMarked(int position){
		return markedPositions.contains(Integer.valueOf(position));
	}
}
