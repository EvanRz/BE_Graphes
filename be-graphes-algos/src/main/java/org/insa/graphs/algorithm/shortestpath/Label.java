package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
    public int current;
    public boolean  mark;
    public double cost;
    public Arc father;

    public double getCost(){
        return this.cost;
    }
    public Label(int Current,Arc father,boolean mark, double cost){
        this.current = Current;
        this.father = father;
        this.cost = cost;
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
