package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import java.util.ArrayList;
import java.util.Collections;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);

    }

    public void initLabel(ShortestPathData data, Graph graph) {
    	this.listLabel = new Label[graph.size()];
        for(Node n:graph.getNodes()){
            listLabel[n.getId()]=(new LabelStar(n,data.getDestination()));
        }
    }
    

}
