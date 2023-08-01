import java.util.List;

interface Graph { // Graph class ADT
    // Initialize the graph with some number of vertices

    void init(int n);

    // Return the number of vertices
    int nodeCount();

    // Return the current number of edges
    int edgeCount();

    // Adds a new edge from node v to node w
    void addEdge(int v, int w);

    // Removes the edge from the graph.
    void removeEdge(int v, int w);

    // Returns true if the graph has the edge
    boolean hasEdge(int v, int w);

    // Returns an array containing the indices of the neighbors of v
    List<Node> neighbors(int v);

    // Implement Topological sort using Stack
    List<Node> topologicalSortStack() throws CycleDetected;

    // Implement Topological sort using Queue
    List<Node> topologicalSortQueue() throws CycleDetected;
}
