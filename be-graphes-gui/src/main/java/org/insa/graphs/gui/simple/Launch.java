package org.insa.graphs.gui.simple;




import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.lang.Double;
import java.lang.Math;

import org.insa.graphs.algorithm.ArcInspector;
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
    public static Path ShortestTest(Graph graph,Path path,int idorigin,int iddest,String algo,boolean isTimed){
        Node origin = graph.get(idorigin);
        Node destination = graph.get(iddest);
        ArcInspector arcInspector = null;
        ShortestPathSolution sol;
        boolean isBF = false;
        Path chemin = null;
        ShortestPathData data= new ShortestPathData(graph,origin,destination,arcInspector);


        // Lancement avec l'algo
        switch (algo){
            case "DJ":
                DijkstraAlgorithm algorithm = new DijkstraAlgorithm(data);
                sol =  algorithm.doRun();
                chemin = sol.getPath();
                if(isTimed){
                    if(Double.compare(chemin.getMinimumTravelTime(),algorithm.listLabel[chemin.getDestination().getId()].cost)==0){
                        System.out.println("Cout temporel conforme");
                    }else{
                        System.out.println("Cout temporel incorrect");
                    }
                }
                else{
                    if(Double.compare((double)chemin.getLength(),algorithm.listLabel[chemin.getDestination().getId()].cost)==0){
                        System.out.println("Cout spatial conforme");
                    }else{
                        System.out.println("Cout spatial incorrect");
                    }
                }
                break;
            case "Astar":
                break;
            case "BF":
                isBF = true;
                BellmanFordAlgorithm BF = new BellmanFordAlgorithm(data);
                sol =  BF.doRun();
                chemin = sol.getPath();
                System.out.println("BF sélectionné donc inutile");
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


    public static void main(String[] args) throws Exception {

        // Visit these directory to see the list of available files on Commetud.
        final String mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/paris.mapgr";
        final String pathName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // TODO: Read the graph.
        final Graph graph = reader.read();

        // Create the drawing:
        final Drawing drawing = createDrawing();

        // TODO: Draw the graph on the drawing.
        drawing.drawGraph(graph);

        // TODO: Create a PathReader.
        final PathReader pathReader = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
;

        // TODO: Read the path.
        final Path path = pathReader.readPath(graph);

        // TODO: Draw the path.
        drawing.drawPath(path);

        // Lancement des tests auto
        Path solutionBF =  ShortestTest(graph,path,13880,32864,"BF",false);
        Path solutionDj =  ShortestTest(graph,path,13880,32864,"DJ",false);
        System.out.println(comparePath(solutionBF,solutionDj,false));


        
    }

}
