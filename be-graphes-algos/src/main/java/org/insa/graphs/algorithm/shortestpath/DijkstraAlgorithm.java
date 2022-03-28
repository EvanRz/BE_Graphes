package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

import java.util.ArrayList;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        //Start Init
        BinaryHeap<Label> bin = new BinaryHeap<>();
        Label[] listLabel = new Label[graph.size()];
        for(int i=0; i<graph.size();i++){
            listLabel[i]=(new Label(i,null,false,Double.POSITIVE_INFINITY));
        }
        Node origin = data.getOrigin();
        listLabel[origin.getId()].cost = 0.0 ;
        bin.insert(listLabel[origin.getId()]);
        // Init Done
        // Start Traitement
        // A implémenter entièrement ALED
        // Traitement Done
        return solution;
    }

}
