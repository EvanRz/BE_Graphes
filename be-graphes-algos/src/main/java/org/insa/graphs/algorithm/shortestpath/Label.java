package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
    public Node current;
    public boolean  mark;
    public double cost;
    public Arc father;
    public double effectiveCost;
    
    
    public double getTotalCost() {
    	return this.cost;
    }
    
    public Label(Node Current) {
        this.current = Current;
        this.father = null;
        this.cost = Double.POSITIVE_INFINITY;
        this.mark = false;
        this.effectiveCost = 0.0;
    }

    public int compareTo(Label l){
    	
        /*if(this.getTotalCost() > l.getTotalCost()){
            return 1;
            
        }else if(this.getTotalCost() < l.getTotalCost()){
        	return -1;
        }else {
        	if(this.distance > l.distance) {
        		return 1;
        	}else if(this.distance < l.distance) {
        		return -1;
        	}else {
        		return 0;
        	}
        }*/
    	if(Double.compare(this.getTotalCost(), l.getTotalCost()) == 0) {
    		if(Double.compare(this.effectiveCost, l.effectiveCost) == 0) {
    			return 0;
    		}else {
    			return (Double.compare(this.effectiveCost, l.effectiveCost));
    		}
    	}else {
    		return Double.compare(this.getTotalCost(), l.getTotalCost());
    	}
    }
}
