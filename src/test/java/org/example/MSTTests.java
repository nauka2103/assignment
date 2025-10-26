package org.example;

import org.example.algorithms.Kruskal;
import org.example.algorithms.Prim;
import org.example.io.JsonIO;
import org.example.model.Edge;
import org.example.model.Graph;
import org.example.model.ResultDTO;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MSTTests {
    @Test
    void dataset_algorithms_agree_and_tree_shape() throws Exception {
        var items = JsonIO.readAnyGraphs(Path.of("graphs_small.json"));


        for (var it : items) {
            Graph g = it.graph;
            ResultDTO.Algo p = Prim.run(g);
            ResultDTO.Algo k = Kruskal.run(g);

            assertEquals(p.mstCost, k.mstCost,
                    "Prim vs Kruskal cost on graph id=" + it.id);

            if (p.mstCost != Long.MAX_VALUE) {
                assertEquals(g.V - 1, p.mstEdges.size(),
                        "Prim edges count (id=" + it.id + ")");
                assertEquals(g.V - 1, k.mstEdges.size(),
                        "Kruskal edges count (id=" + it.id + ")");
            }
        }
    }

    @Test
    void disconnected_detected() {
        Graph g = new Graph(4, List.of(
                new Edge(0, 1, 1),
                new Edge(2, 3, 1)
        ));
        var p = Prim.run(g);
        var k = Kruskal.run(g);
        assertEquals(Long.MAX_VALUE, p.mstCost);
        assertEquals(Long.MAX_VALUE, k.mstCost);
    }
}
