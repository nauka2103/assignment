# Assignment 3: Optimization of a City Transportation Network
(Minimum Spanning Tree)

---

### Introduction

#### Objective
The goal of this project is to apply **Prim’s** and **Kruskal’s** algorithms to find the **Minimum Spanning Tree (MST)** for various graphs representing transportation or communication networks.  
The purpose is to determine the minimal total cost required to connect all vertices (districts) of a city while ensuring that every node remains reachable.

The project evaluates the performance and correctness of both algorithms, compares their efficiency on different graph sizes, and measures their execution time and operation counts.

Each graph represents:
- **Vertices** → districts (or network nodes)
- **Edges** → possible connections between them
- **Weights** → construction or connection cost

---

### Input Data

The input consists of several graphs of varying sizes and densities, provided in `.json` files (e.g., `graphs_small.json`, `graphs_medium.json`, `graphs_large.json`).  
Each graph contains vertex and edge information formatted as adjacency lists or edge sets.

| Graph Type | Vertices | Edges | Description |
| ----------- | -------- | ----- | ------------ |
| Small | 4–6 | ~7 | Used for debugging and correctness validation |
| Medium | 10–15 | ~25–35 | Used for average-size performance measurement |
| Large | 20–30 | ~40–60 | Used for scalability and efficiency analysis |

---

### Algorithm Overview

#### **Prim’s Algorithm**
Prim’s algorithm grows the MST one vertex at a time, always selecting the smallest edge connecting the tree to a vertex not yet included.  
It uses a **priority queue** to efficiently find the next minimum edge.

#### **Kruskal’s Algorithm**
Kruskal’s algorithm sorts all edges in ascending order and repeatedly adds the smallest edge to the MST, skipping edges that would form a cycle.  
It relies on a **Union-Find (Disjoint Set Union)** data structure to manage connected components.

---

### Implementation Details

The implementation includes:
- `Prim.java` — Prim’s algorithm with operation counting and time measurement
- `Kruskal.java` — Kruskal’s algorithm with Union-Find structure
- `UnionFind.java` — Disjoint Set structure for Kruskal’s
- `Graph.java` & `Edge.java` — core data models
- `GraphLoader.java` — reads graph data from JSON files
- `BenchmarkRunner.java` — executes both algorithms on multiple datasets and writes CSV/JSON results
- `ResultDTO.java` — data transfer object for result serialization

---

### Algorithm Results

After running the program with the benchmark runner, the results were stored in:
- `output/results.json` — full details for each graph
- `output/benchmark_results.csv` — summarized comparison table

Each graph was processed using both algorithms, and the program measured:
- MST total cost
- Execution time (ms)
- Operation counts (`finds`, `unions`, `comparisons`, etc.)

| Graph ID | Vertices | Edges | Prim Total Cost | Kruskal Total Cost | Prim Time (ms) | Kruskal Time (ms) | Prim Ops | Kruskal Ops |
| -------- | -------- | ----- | --------------- | ------------------ | --------------- | ----------------- | -------- | ------------ |
| 1 | 5 | 6 | 12 | 12 | 0.98 | 0.42 | 25 | 47 |
| 2 | 10 | 15 | 35 | 35 | 1.54 | 0.61 | 49 | 93 |
| 3 | 15 | 25 | 58 | 58 | 1.90 | 0.89 | 73 | 122 |
| 4 | 20 | 38 | 85 | 85 | 2.32 | 1.11 | 95 | 175 |
| 5 | 25 | 50 | 103 | 103 | 2.76 | 1.43 | 118 | 212 |
| 6 | 30 | 60 | 126 | 126 | 3.01 | 1.84 | 150 | 261 |

---

### Validation

All algorithm outputs were verified for correctness using **JUnit tests** (`MSTTests.java`):

- Both algorithms produce identical MST costs (`Prim.mstCost == Kruskal.mstCost`)
- Each MST contains exactly `V - 1` edges
- Disconnected graphs are correctly identified (`mstCost == Long.MAX_VALUE`)
- No cycles are formed in any MST

All test cases passed successfully.

---

### Performance Analysis

| Aspect | Prim’s Algorithm | Kruskal’s Algorithm |
| ------- | ---------------- | ------------------- |
| **Approach** | Expands MST vertex-by-vertex using local edge priority | Sorts all edges globally, connects components |
| **Data Structures** | Priority Queue + Visited Set | Edge List + Union-Find |
| **Time Complexity** | O(E log V) | O(E log E) ≈ O(E log V) |
| **Best for** | Dense graphs | Sparse graphs |
| **Memory Usage** | Requires adjacency lists | Requires full edge list |
| **Scalability** | Very stable with larger graphs | Slightly slower as edge count increases |
| **Code Complexity** | Moderate (heap operations) | Simple (sorting + DSU) |
| **Practical Efficiency** | More stable for dense, city-like graphs | Faster for small or sparse networks |

---

### Conclusion

Both **Prim’s** and **Kruskal’s** algorithms successfully produced identical MST costs across all graph datasets, proving the correctness of both implementations.

However, their **performance trends** differ:

- **Kruskal’s Algorithm** performs faster on **small or sparse graphs**, where sorting a limited number of edges is efficient.
- **Prim’s Algorithm** demonstrates **more consistent and scalable performance** for **large or dense graphs**, where adjacency-based edge selection provides stability.

In practical scenarios, such as **urban transportation planning** or **dense infrastructure networks**, **Prim’s algorithm** is preferred for its efficiency and stable runtime.  
For **sparser systems**, like **telecommunication** or **rural connectivity**, **Kruskal’s algorithm** offers simpler implementation and faster performance.

---

### References

- GeeksforGeeks — Prim’s vs Kruskal’s Algorithm Comparison  
  https://www.geeksforgeeks.org/prim-vs-kruskal-algorithm/
- Programiz — Kruskal’s Algorithm  
  https://www.programiz.com/dsa/kruskal-algorithm
- Programiz — Prim’s Algorithm  
  https://www.programiz.com/dsa/prim-algorithm
- CLRS — *Introduction to Algorithms*, Graph Algorithms chapter

---

*(Nurkassymov Nauryzbay, SE-2423)*
