package de.tud.cs.st.vespucci.sadclient.controller;

import de.tud.cs.st.vespucci.sadclient.model.SAD;

public interface ISADDialog {
    
    public void updateModel(SAD sad);
    
    public void updateDescription(SAD sad);

    public void updateDocumentation(SAD sad);
    
}
