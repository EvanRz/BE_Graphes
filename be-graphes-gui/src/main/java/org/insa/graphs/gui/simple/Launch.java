

package org.insa.graphs.gui.simple;




import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.lang.Double;
import java.lang.Math;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.insa.graphs.model.io.BinaryReader;
import org.insa.graphs.model.io.BinaryPathReader;
public class Launch {

    /**
     * Create a new Drawing inside a JFrame an return it.
     *
     * @return The created drawing.
     *
     * @throws Exception if something wrong happens when creating the graph.
     */
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }
    public static Path ShortestTest(Graph graph,int idorigin,int iddest,String algo,boolean isTimed){
        Node origin = graph.get(idorigin);
        Node destination = graph.get(iddest);
        ArcInspector arcInspector;

        if(isTimed) {
            arcInspector = ArcInspectorFactory.getAllFilters().get(2);
        }else {
            arcInspector = ArcInspectorFactory.getAllFilters().get(0);
        }

        ShortestPathSolution sol;
        Path chemin = null;
        ShortestPathData data= new ShortestPathData(graph,origin,destination,arcInspector);


        // Lancement avec l'algo
        switch (algo){
            case "DJ":
                DijkstraAlgorithm algorithmD = new DijkstraAlgorithm(data);
                sol =  algorithmD.doRun();
                chemin = sol.getPath();
                System.out.print("Dijkstra : ");
                if(chemin != null) {
	                if(isTimed){
	                    if(Math.abs((double)chemin.getMinimumTravelTime() - algorithmD.listLabel[chemin.getDestination().getId()].cost) <= 0.01){
	                        System.out.println("Cout temporel conforme");
	                    }else{
	                        System.out.println("Cout temporel incorrect");
	                        System.out.println("cout chemin : " + (double)chemin.getLength() + " cout label : " + algorithmD.listLabel[chemin.getDestination().getId()].cost);
	                    }
	                }
	                else{
	                    if( Math.abs((double)chemin.getLength() - algorithmD.listLabel[chemin.getDestination().getId()].cost) <= 0.01){
	                        System.out.println("Cout spatial conforme");
	                    }else{
	                        System.out.println("Cout spatial incorrect");
	                        System.out.println("cout chemin : " + (double)chemin.getLength() + " cout label : " + algorithmD.listLabel[chemin.getDestination().getId()].cost);
	                    }
	                }
                }
                break;
            case "A*":
            	AStarAlgorithm algorithmA = new AStarAlgorithm(data);
                sol =  algorithmA.doRun();
                chemin = sol.getPath();
                System.out.print("Astar : ");
                if(chemin != null) {
	            	if(isTimed){
	                    if(Math.abs((double)chemin.getMinimumTravelTime() - algorithmA.listLabel[chemin.getDestination().getId()].cost) <= 0.01){
	                        System.out.println("Cout temporel conforme");
	                    }else{
	                        System.out.println("Cout temporel incorrect");
	                        System.out.println("cout chemin : " + (double)chemin.getLength() + " cout label : " + algorithmA.listLabel[chemin.getDestination().getId()].cost);
	                    }
	                }
	                else{
	                    if( Math.abs((double)chemin.getLength() - algorithmA.listLabel[chemin.getDestination().getId()].cost) <= 0.01){
	                        System.out.println("Cout spatial conforme");
	                    }else{
	                        System.out.println("Cout spatial incorrect");
	                        System.out.println("cout chemin : " + (double)chemin.getLength() + " cout label : " + algorithmA.listLabel[chemin.getDestination().getId()].cost);
	                    }
	                }
                }
                break;
            case "BF":
                BellmanFordAlgorithm BF = new BellmanFordAlgorithm(data);
                sol =  BF.doRun();
                chemin = sol.getPath();
                break;
            default:
                System.out.println("Algorithme incorrect");
                break;
        }

        return chemin;

    }
    public static boolean comparePath(Path p1, Path p2,boolean isTimed){
        if(isTimed)
            return Double.compare(p1.getMinimumTravelTime(),p2.getMinimumTravelTime())==0;
        else
            return Float.compare(p1.getLength(),p2.getLength())==0;
    }
    
    public static void test(String algo,boolean isTimed, final String mapName) throws Exception {
    	
    	final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName)))); 	
        final Graph graph = reader.read();
        final Drawing drawing = createDrawing();
        drawing.drawGraph(graph);
        
        int nbTest = 20;
    	int idorigin;
    	int iddest;
    	Path path1;
    	Path path2;
    	String refAlgo = "BF";
    	for(int i=1; i<=nbTest;i++) {
    		idorigin = (int) (Math.random()*(graph.size())) ;
    		iddest = (int) (Math.random()*(graph.size())) ;
    		System.out.println(idorigin+" to "+iddest);
    		path1 = ShortestTest(graph,idorigin,iddest,algo,isTimed);
        	path2 = ShortestTest(graph,idorigin,iddest,refAlgo,isTimed);
        	
        	if(path1 != null && path2 != null) {        		
            	
            	if(comparePath(path1,path2, isTimed)) {
            		drawing.drawPath(path1,Color.GREEN);
            		System.out.println("Test numéro "+i+"/"+nbTest+" valide");
            	}else {
            		System.out.println("Test numéro "+i+"/"+nbTest+" incorrect avec origine "+idorigin+" et destination "+iddest);
            		drawing.drawPath(path1, Color.BLUE);
            		drawing.drawPath(path2, Color.RED);
            	}
        	}else	{
        		System.out.println("pas de chemin possible");
        		System.out.println("Test numéro "+i+"/"+nbTest+" valide");
        	}
        	System.out.println();
    	}
    	
    	
    }

    public static void main(String[] args) throws Exception {

        // Visit these directory to see the list of available files on Commetud.
        final String mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/paris.mapgr";
		/*
		 * final String pathName =
		 * "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path"
		 * ;
		 * 
		 * // Create a graph reader. final GraphReader reader = new BinaryGraphReader(
		 * new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		 * 
		 * // TODO: Read the graph. final Graph graph = reader.read();
		 * 
		 * // Create the drawing: final Drawing drawing = createDrawing();
		 * 
		 * // TODO: Draw the graph on the drawing. drawing.drawGraph(graph);
		 * 
		 * // TODO: Create a PathReader. final PathReader pathReader = new
		 * BinaryPathReader( new DataInputStream(new BufferedInputStream(new
		 * FileInputStream(pathName)))); ;
		 */

        // TODO: Read the path.
        //final Path path = pathReader.readPath(graph);

        // TODO: Draw the path.
        Path solutionBF;
        Path solutionDJ;
        Path solutionA;
        boolean isTime = true;
        
        //100167 et 116585
        test("A*",isTime,mapName);
        //test("DJ",isTime,mapName);
        // Lancement des tests auto
		/*
		 * solutionBF = ShortestTest(graph,13880,32864,"BF",isTime); solutionDJ =
		 * ShortestTest(graph,13880,32864,"DJ",isTime);
		 * System.out.println(comparePath(solutionBF,solutionDJ,isTime));
		 * System.out.println(graph.size());
		 * 
		 * drawing.drawPath(solutionDJ); drawing.drawPath(solutionBF);
		 * 
		 * isTime = true; solutionBF = ShortestTest(graph,13880,32864,"BF",isTime);
		 * solutionDJ = ShortestTest(graph,13880,32864,"DJ",isTime);
		 * System.out.println(comparePath(solutionBF,solutionDJ,isTime));
		 * drawing.drawPath(solutionDJ); drawing.drawPath(solutionBF);
		 */
        
        /*solutionA =  ShortestTest(graph,13880,32864,"A*",isTime);
        solutionDJ =  ShortestTest(graph,13880,32864,"DJ",isTime);
        System.out.println(comparePath(solutionA,solutionDJ,isTime));
        
        drawing.drawPath(solutionA);
        drawing.drawPath(solutionDJ);
        
        solutionA =  ShortestTest(graph,7000,14000,"A*",isTime);
        solutionBF =  ShortestTest(graph,7000,14000,"BF",isTime);
        System.out.println(comparePath(solutionA,solutionBF,isTime));

        drawing.drawPath(solutionA);
        drawing.drawPath(solutionBF);*/

    }

}