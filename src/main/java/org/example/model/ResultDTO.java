package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ResultDTO {

    public List<PerGraph> results = new ArrayList<>();

    public static class PerGraph {
        public int id; 
        public String name; 
        public int vertices;
        public int edges;
        public Algo prim;
        public Algo kruskal;
        public boolean equalCost;
        public boolean connected;
    }


    public static class Algo {
        public long mstCost; 
        public List<EdgeTriple> mstEdges = new ArrayList<>();
        public long timeMs; 
        public Ops ops = new Ops(); 
        public long opCount() { 
            return ops.pqPushes + ops.pqPops + ops.edgeConsidered + ops.edgeChecks + ops.unions + ops.finds;
        }
    }


    public static class EdgeTriple { public int u; public int v; public int w; }


    public static class Ops {
        public long pqPushes; 
        public long pqPops; 
        public long edgeConsidered;
        public long edgeChecks;
        public long unions; 
        public long finds;
    }
}
