class Node implements Comparable<Node> {
    static Node min (Node a, Node b) {
        return a.compareTo(b) < 0 ? a : b;
    }
    private final int name;
    private int value;
    private boolean visited;

    Node(int name) {
        this.name = name;
        this.visited = false;
    }

    int getName() {
        return name;
    }
    int getValue () { return value; }
    boolean getVisited() { return visited; }

    void setVisited() { this.visited = true; }

    void setUnvisited() {
        this.visited = false;
    }

    void setValue (int value) { this.value = value; }

    public int compareTo(Node o) {
        return Integer.compare(value, o.value);
    }

    public String toString() {
        return name + "";
    }

    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node that = (Node) o;
            return name == that.getName();
        } else return false;
    }
}