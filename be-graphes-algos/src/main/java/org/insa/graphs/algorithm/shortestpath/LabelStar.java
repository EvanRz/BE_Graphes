package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class LabelStar extends Label{
	public double distance;
	public Node dest;
	
    public LabelStar(Node Current, Node Dest) {
        super(Current);
        this.dest = Dest;
    }
    
    @Override
    public double getTotalCost(){
        this.distance = this.current.getPoint().distanceTo(this.dest.getPoint());
        return this.cost + this.distance;
    }
  
}
