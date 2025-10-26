package org.example.algorithms;

import org.example.model.Edge;
import org.example.model.Graph;
import org.example.model.ResultDTO;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Prim {
    public static ResultDTO.Algo run(Graph g) {
        long t0 = System.nanoTime();
        ResultDTO.Algo out = new ResultDTO.Algo();
        if (g.V == 0) { out.mstCost = 0; out.timeMs = 0; return out; }

        boolean[] in = new boolean[g.V];

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        in[0] = true;
        for (Edge e : g.adj.get(0)) {
            pq.add(new int[]{e.w, e.u, e.v});
            out.ops.pqPushes++; out.ops.edgeConsidered++;
        }

        int taken = 0;
        long total = 0;

        while (!pq.isEmpty() && taken < g.V - 1) {
            int[] cur = pq.poll(); out.ops.pqPops++;
            int w = cur[0], u = cur[1], v = cur[2];

            if (in[v]) continue;

            in[v] = true;
            taken++;
            total += w;

            ResultDTO.EdgeTriple et = new ResultDTO.EdgeTriple();
            et.u = u; et.v = v; et.w = w;
            out.mstEdges.add(et);

            for (Edge e : g.adj.get(v)) {
                if (!in[e.v]) {
                    pq.add(new int[]{e.w, e.u, e.v});
                    out.ops.pqPushes++; out.ops.edgeConsidered++;
                }
            }
        }

        out.mstCost = (taken == g.V - 1) ? total : Long.MAX_VALUE;
        out.timeMs = (System.nanoTime() - t0) / 1_000_000;
        return out;
    }
}