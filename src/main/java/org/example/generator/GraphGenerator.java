package org.example.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GraphGenerator {

    static class Graph {
        int id;
        List<String> nodes;
        List<Edge> edges;

        Graph(int id, List<String> nodes, List<Edge> edges) {
            this.id = id;
            this.nodes = nodes;
            this.edges = edges;
        }
    }

    static class Edge {
        String from;
        String to;
        double weight;

        Edge(String from, String to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        int id = 1;

        List<Graph> small = new ArrayList<>();
        List<Graph> medium = new ArrayList<>();
        List<Graph> large = new ArrayList<>();
        List<Graph> extraLarge = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            small.add(generateGraph(id++, randomSize(5, 30), random));
        }
        saveToJson(small, "graphs_small.json");
        printStats("SMALL", small);

        for (int i = 0; i < 10; i++) {
            medium.add(generateGraph(id++, randomSize(30, 300), random));
        }
        saveToJson(medium, "graphs_medium.json");
        printStats("MEDIUM", medium);

        for (int i = 0; i < 10; i++) {
            large.add(generateGraph(id++, randomSize(300, 1000), random));
        }
        saveToJson(large, "graphs_large.json");
        printStats("LARGE", large);

        for (int i = 0; i < 3; i++) {
            extraLarge.add(generateGraph(id++, randomSize(1000, 2000), random));
        }
        saveToJson(extraLarge, "graphs_extra_large.json");
        printStats("EXTRA LARGE", extraLarge);

        System.out.println("\n All JSON files generated successfully!");
    }

    private static Graph generateGraph(int id, int vertexCount, Random random) {
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            nodes.add("N" + i);
        }

        List<Edge> edges = new ArrayList<>();
        int maxEdges = Math.max(vertexCount * 3, vertexCount + 1);
        Set<String> added = new HashSet<>();

        while (edges.size() < maxEdges) {
            int a = random.nextInt(vertexCount);
            int b = random.nextInt(vertexCount);
            if (a == b) continue;

            String key = a < b ? a + "-" + b : b + "-" + a;
            if (added.contains(key)) continue;

            added.add(key);
            double weight = 1 + random.nextInt(100);
            edges.add(new Edge("N" + a, "N" + b, weight));
        }

        return new Graph(id, nodes, edges);
    }

    private static void saveToJson(List<Graph> graphs, String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            Map<String, Object> root = new HashMap<>();
            root.put("graphs", graphs);
            gson.toJson(root, writer);
            System.out.println("📁 Saved " + filename + " (" + graphs.size() + " graphs)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int randomSize(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    private static void printStats(String name, List<Graph> graphs) {
        System.out.println("\n🔹 " + name + " GRAPHS:");
        for (Graph g : graphs) {
            System.out.printf("   Graph #%d → vertices: %d, edges: %d%n",
                    g.id, g.nodes.size(), g.edges.size());
        }
    }
}
