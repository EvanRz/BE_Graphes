package org.insa.graphs.algorithm.utils;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class AlgoTest {
    Graph graph;
    Path path;
    public void initGraph(String mapName, String pathName) throws Exception
        {
            // Create a graph reader.
            final GraphReader reader = new BinaryGraphReader(
                    new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
            final Graph graph = reader.read();
            final PathReader pathReader = new BinaryPathReader(
                    new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
            final Path path = pathReader.readPath(graph);
            this.graph = graph;
            this.path = path;
        }
    public AlgoTest(String mapName) {
    }

    public void ShortestTest(int idorigin,int iddest){
            Node origin = this.graph.get(idorigin);
            Node destination = this.graph.get(iddest);

        }


}
