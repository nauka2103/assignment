package org.example;

import org.example.algorithms.Kruskal;
import org.example.algorithms.Prim;
import org.example.io.JsonIO;
import org.example.model.Graph;
import org.example.model.ResultDTO;
import org.example.visual.GraphVisualizer;

import java.awt.*;
import java.nio.file.Path;

public class MainVisualizeMST {
    public static void main(String[] args) throws Exception {
        String input = args.length > 0 ? args[0] : "graphs_small.json";
        String outputDir = "output/";

        var items = JsonIO.readAnyGraphs(Path.of(input));

        int count = Math.min(5, items.size());
        for (int i = 0; i < count; i++) {
            Graph g = items.get(i).graph;

            // Run MST algorithms
            ResultDTO.Algo prim = Prim.run(g);
            ResultDTO.Algo kruskal = Kruskal.run(g);

            // Save images
            String primFile = outputDir + "graph_" + (i + 1) + "_prim.png";
            String kruskalFile = outputDir + "graph_" + (i + 1) + "_kruskal.png";

            GraphVisualizer.saveGraphImage(g, prim, Color.GREEN, primFile);
            GraphVisualizer.saveGraphImage(g, kruskal, Color.BLUE, kruskalFile);

            System.out.println("Saved " + primFile + " and " + kruskalFile);
        }

        System.out.println("✅ Done. Saved MST visualizations for " + count + " graphs.");
    }
}
