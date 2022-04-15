package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import java.util.ArrayList;
import java.util.Collections;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        Arc[] predecessorArcs = new Arc[graph.size()];
        
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
        notifyOriginProcessed(origin);
        
        while (!bin.isEmpty()){
            Label currentLabel = bin.deleteMin();
            if(currentLabel.current == data.getDestination()) {
            	break;
            }
            int pere = currentLabel.current.getId();
            listLabel[pere].mark=true;
            notifyNodeMarked(currentLabel.current);
            
            for(Arc a:currentLabel.current.getSuccessors()){
            	 
            	if(!data.isAllowed(a)) {
                 	continue;
                 }
            	 
                int fils = a.getDestination().getId();
                
                if(!listLabel[fils].mark){
                	if(listLabel[fils].cost == Double.POSITIVE_INFINITY) {
                		
                		listLabel[fils].cost = listLabel[pere].cost + data.getCost(a);
                        predecessorArcs[fils] = a;
                        listLabel[fils].father = a;
                        notifyNodeReached(a.getDestination());
                        bin.insert(listLabel[fils]);
                        
                        
                    }else if(listLabel[fils].cost > listLabel[pere].cost + data.getCost(a) ){
                    	       
                		bin.remove(listLabel[fils]);
                        listLabel[fils].cost = listLabel[pere].cost + data.getCost(a);
                        predecessorArcs[fils] = a;
                        listLabel[fils].father = a;
                        bin.insert(listLabel[fils]);

                    }
                	
                }

            }
        }
        
     // Destination has no predecessor, the solution is infeasible...
        if (predecessorArcs[data.getDestination().getId()] == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = predecessorArcs[data.getDestination().getId()];
            while (arc != null) {
                arcs.add(arc);
                arc = predecessorArcs[arc.getOrigin().getId()];
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        
        // Traitement Done
        return solution;
    }

}
