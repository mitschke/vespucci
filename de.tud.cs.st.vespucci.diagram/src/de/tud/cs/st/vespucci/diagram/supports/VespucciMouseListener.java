/** License (BSD Style License):
 *  Copyright (c) 2010
 *  Software Engineering
 *  Department of Computer Science
 *  Technische Universität Darmstadt
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  - Neither the name of the Software Engineering Group or Technische 
 *    Universität Darmstadt nor the names of its contributors may be used to 
 *    endorse or promote products derived from this software without specific 
 *    prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.diagram.supports;

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;

/**
 * Responsibility: Store mouse position on each right click.
 * 
 * @author Tam-Minh Nguyen
 * 
 */
public class VespucciMouseListener implements MouseListener {
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.MouseListener#mouseDoubleClicked(org.eclipse.draw2d.MouseEvent)
     */
    public void mouseDoubleClicked(MouseEvent me) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.MouseListener#mousePressed(org.eclipse.draw2d.MouseEvent)
     */
    public void mousePressed(MouseEvent me) {
	if (me.button == 1) {
	    // left click
	}

	if (me.button == 2) {
	    // middle click
	}

	if (me.button == 3) {
	    // right click
	    EPService.RECENT_MOUSE_RIGHT_CLICK_POSITION = me.getLocation();
	    System.err.println("3:" + EPService.RECENT_MOUSE_RIGHT_CLICK_POSITION.x + ","
		    + EPService.RECENT_MOUSE_RIGHT_CLICK_POSITION.y);
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.MouseListener#mouseReleased(org.eclipse.draw2d.MouseEvent)
     */
    public void mouseReleased(MouseEvent me) {
    }
}
