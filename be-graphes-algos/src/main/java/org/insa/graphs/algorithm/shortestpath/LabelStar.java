package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class LabelStar extends Label{
    public double distance;

    public LabelStar(Node Current) {
        super(Current);
    }

    public double getTotalCost(Node destination){
        distance = this.current.getPoint().distanceTo(destination.getPoint());
        return super.getCost()+ distance;
    }
    public int compareTo(LabelStar l,Node dest){
        if(this.getTotalCost(dest) > l.getTotalCost(dest)){
            return 1;
        }else if(this.getTotalCost(dest) == l.getTotalCost(dest)){
            return 0;
        }else {
            return -1;
        }
    }

}
