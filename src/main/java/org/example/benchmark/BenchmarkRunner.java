package org.example.benchmark;

import org.example.algorithms.Kruskal;
import org.example.algorithms.Prim;
import org.example.io.GraphLoader;
import org.example.model.ResultDTO;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Path smallGraphs = Path.of("graphs_small.json");
        Path mediumGraphs = Path.of("graphs_medium.json");

        List<GraphLoader.LoadedGraph> small = GraphLoader.loadFromJson(smallGraphs);
        List<GraphLoader.LoadedGraph> medium = GraphLoader.loadFromJson(mediumGraphs);

        var graphs = small.subList(0, Math.min(14, small.size()));
        graphs.addAll(medium.subList(0, Math.min(14, medium.size())));

        try (FileWriter csv = new FileWriter("benchmark_results.csv")) {
            csv.write("GraphID,Vertices,Edges,Algorithm,MSTCost,TimeMs,OpsTotal\n");

            for (var it : graphs) {
                var g = it.graph;

                ResultDTO.Algo prim = Prim.run(g);
                ResultDTO.Algo kruskal = Kruskal.run(g);

                csv.write(String.format("%d,%d,%d,Prim,%d,%d,%d\n",
                        it.id, g.V, g.E(), prim.mstCost, prim.timeMs, prim.opCount()));

                csv.write(String.format("%d,%d,%d,Kruskal,%d,%d,%d\n",
                        it.id, g.V, g.E(), kruskal.mstCost, kruskal.timeMs, kruskal.opCount()));
            }
        }

        System.out.println("✅ Benchmark completed → benchmark_results.csv");
    }
}
