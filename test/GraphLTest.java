import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GraphLTest {
    @Test
    public void topologicalStack() throws CycleDetected {
        Node v0 = new Node(0);
        Node v1 = new Node(1);
        Node v2 = new Node(2);
        Node v3 = new Node(3);

        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(v0,v1,v2,v3));

        ArrayList<Node>[] neighbors = new ArrayList[4];
        neighbors[0] = new ArrayList<>(Arrays.asList(v1, v2));
        neighbors[1] = new ArrayList<>(Collections.singletonList(v3));
        neighbors[2] = new ArrayList<>(Collections.singletonList(v3));
        neighbors[3] = new ArrayList<>();

        //System.out.println(neighbors[0]);
        //System.out.println(neighbors[1]);
        //System.out.println(neighbors[2]);
        //System.out.println(neighbors[3]);

        GraphL g = new GraphL(neighbors, nodes);
        List<Node> sort = g.topologicalSortStack();
        assertEquals(4, sort.size());
        assertEquals(v0, sort.get(0));
        assertEquals(v2, sort.get(1)); // these could potentially be switched
        assertEquals(v1, sort.get(2)); // these could potentially be switched
        assertEquals(v3, sort.get(3));
    }

    @Test
    public void topologicalQueue() throws CycleDetected {
        Node v0 = new Node(0);
        Node v1 = new Node(1);
        Node v2 = new Node(2);
        Node v3 = new Node(3);

        ArrayList<Node> nodes = new ArrayList<>(Arrays.asList(v0,v1,v2,v3));

        ArrayList<Node>[] neighbors = new ArrayList[4];
        neighbors[0] = new ArrayList<>(Arrays.asList(v1, v2));
        neighbors[1] = new ArrayList<>(Collections.singletonList(v3));
        neighbors[2] = new ArrayList<>(Collections.singletonList(v3));
        neighbors[3] = new ArrayList<>();

        //System.out.println(neighbors[0]);
        //System.out.println(neighbors[1]);
        //System.out.println(neighbors[2]);
        //System.out.println(neighbors[3]);

        GraphL g = new GraphL(neighbors, nodes);
        List<Node> sort = g.topologicalSortQueue();
        assertEquals(4, sort.size());
        assertEquals(v0, sort.get(0));
        assertEquals(v2, sort.get(2)); // these could potentially be switched
        assertEquals(v1, sort.get(1)); // these could potentially be switched
        assertEquals(v3, sort.get(3));
    }



}