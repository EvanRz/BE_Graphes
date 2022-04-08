package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
    public Node current;
    public boolean  mark;
    public double cost;
    public Arc father;

    public double getCost(){
        return this.cost;
    }
    public Label(Node Current){
        this.current = Current;
        this.father = null;
        this.cost = Double.POSITIVE_INFINITY;
        this.mark = false;
    }

    public int compareTo(Label l){
        if(this.cost > l.cost){
            return 1;
        }else if(this.cost == l.cost){
            return 0;
        }else {
            return -1;
        }
    }
}
