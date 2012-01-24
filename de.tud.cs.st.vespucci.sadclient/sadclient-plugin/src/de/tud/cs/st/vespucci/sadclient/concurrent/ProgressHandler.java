package de.tud.cs.st.vespucci.sadclient.concurrent;

/**
 * 
 * @author Mateusz Parzonka
 *
 */
public interface ProgressHandler {
 
    /**
     * @param progress is expected to be in the range [0, 1]
     */
    public void setProgress(double progress);
    
}
