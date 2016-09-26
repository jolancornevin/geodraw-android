
package ot3.insa.fr.geodraw.models;

import java.util.LinkedList;
import java.util.List;

/**
 * Stocks a list of drawable segments
 * @author Max 
 */
public class Drawing {
    private final List<Segment> trace;

    public Drawing()
    {
        this.trace = new LinkedList<>();
    }
    
    public void addSegment(Segment segment)
    {
        trace.add(segment);
    }

    public List<Segment> getSegments()
    {
        return trace;
    }
    
    
    
}
