package org.example.io;

import com.google.gson.*;
import org.example.model.Edge;
import org.example.model.Graph;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;

public class GraphLoader {

    public static class LoadedGraph {
        public int id;
        public String name;
        public Graph graph;
    }

    public static List<LoadedGraph> loadFromJson(Path path) throws Exception {
        JsonObject root = JsonParser.parseReader(new FileReader(path.toFile())).getAsJsonObject();
        JsonArray arr = root.getAsJsonArray("graphs");
        List<LoadedGraph> result = new ArrayList<>();

        for (JsonElement el : arr) {
            JsonObject g = el.getAsJsonObject();
            int id = g.get("id").getAsInt();
            JsonArray nodes = g.getAsJsonArray("nodes");
            JsonArray edges = g.getAsJsonArray("edges");

            int n = nodes.size();
            List<Edge> edgeList = new ArrayList<>();

            for (JsonElement eEl : edges) {
                JsonObject e = eEl.getAsJsonObject();
                String from = e.get("from").getAsString().substring(1); // "N5" → 5
                String to = e.get("to").getAsString().substring(1);
                int w = (int) e.get("weight").getAsDouble();
                edgeList.add(new Edge(Integer.parseInt(from), Integer.parseInt(to), w));
            }

            LoadedGraph lg = new LoadedGraph();
            lg.id = id;
            lg.name = "graph_" + id;
            lg.graph = new Graph(n, edgeList);
            result.add(lg);
        }

        return result;
    }
}
