package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
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
        boolean maj ;
        //Start Init
        BinaryHeap<Label> bin = new BinaryHeap<>();
        Label[] listLabel = new Label[graph.size()];
        for(Node n:graph.getNodes()){
            listLabel[n.getId()]=(new Label(n));
        }
        Node origin = data.getOrigin();
        listLabel[origin.getId()].cost = 0.0 ;
        bin.insert(listLabel[origin.getId()]);
        // Init Done
        // Start Traitement
        while (!bin.isEmpty()){
            maj = false;
            Label currentLabel = bin.deleteMin();
            int pere = currentLabel.current.getId();
            listLabel[pere].mark=true;
            for(Arc a:currentLabel.current.getSuccessors()){
                int fils = a.getDestination().getId();
                if(!listLabel[fils].mark){
                    if(listLabel[fils].cost < listLabel[pere].cost + a.getLength() ){
                        listLabel[fils].cost = listLabel[pere].cost + a.getLength();
                        maj = true;
                    }
                    if(maj){
                        bin.insert(listLabel[fils]);
                        listLabel[fils].father = a;
                    }
                }

            }
        }
        // Traitement Done
        return solution;
    }

}
