package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.AbstractInputData;

public class LabelStar extends Label{
	private AbstractInputData.Mode mode;
	
	
    public LabelStar(Node Current, Node Dest, AbstractInputData.Mode Mode ,double Speed) {
        super(Current);
        this.effectiveCost = this.current.getPoint().distanceTo(Dest.getPoint());
        if(Mode == AbstractInputData.Mode.TIME) {
        	this.effectiveCost = 3.6*this.effectiveCost/Speed;
        }
        
    }
    
    @Override
    public double getTotalCost(){
        return this.cost + this.effectiveCost;
    }
  
}
