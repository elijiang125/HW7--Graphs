import java.util.*;

class CycleDetected extends Exception{};

public class GraphL implements Graph {
    // An Array of nodes where each item points to a list of adjacent nodes
    private List<Node>[] nodeArray; //the whole picture
    // A List of nodes that will be referenced in the nodeArray
    private List<Node> nodes; //just a list of nodes that are there
    // Number of edges in the graph
    private int numEdge;

    public GraphL(int n){
        this.nodeArray = new ArrayList[n];
        this.nodes = new ArrayList<>();
        init(n);
        this.numEdge = 0;
    }

    // TODO: find numEdge value for existing lists
    public GraphL(List<Node>[] nodeArray, List<Node> nodes){
        this.nodeArray = nodeArray;
        this.nodes = nodes;
    }

    // Implement the missing functions here:
    // TODO: initialize graph with n amount of vertices
    // DONE
    public void init(int n) {
        for (int i = 0; i < n; i++) {
            this.nodeArray[i] = new ArrayList<Node>();
        }
    }

    // Hint: may need a reset function for the Runner class (not in the Interface)
    //TODO: reset on something
    //NOTE: Need help
    public void reset(){
        for (int i = 0; i < nodeArray.length; i++) {
            nodeArray[i].clear();
        }
        numEdge = 0;
    }

    // TODO: return number of vertices
    //DONE
    public int nodeCount() {
        return nodeArray.length;
    }

    // TODO: return number of edges
    public int edgeCount() {
        int edges = 0;
        //so we need to take nodeArray, and count all nodes in there as edges
        for (int i = 0; i < nodeArray.length; i++) {
            for (int j = 0; j < nodeArray[i].size(); j++) {
                edges++;
            }
        }
        this.numEdge = edges; //update numEdge
        return edges;
    }

    // TODO: adds a new edge from node v to node w
    public void addEdge(int v, int w) {
        boolean wExist = false;
        Node temp = null;
        //let's see if Node w exists or not
        for (int j = 0; j < nodes.size(); j++) {
            if (nodes.get(j).getName() == w) {
                wExist = true;
                temp = nodes.get(j);
            }
        }

        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getName() == v && wExist) {
                //if the number is the same and Node W EXISTS
                nodeArray[i].add(temp);
            }
            else if (nodes.get(i).getName() == v && !wExist){
                //if the number is the same and Node W is NEW
                nodeArray[i].add(new Node(w));
            }
        }
    }

    // TODO: removes the edge from the graph
    //NOTE: are we removing w from v?
    //i'm just gonna assume that the nodeArray is in increasing order
    public void removeEdge(int v, int w) {
        if (v > nodeArray.length) {
            System.out.println("Cannot delete");
            //number value is greater than size, so it doesn't exist
        }
        List<Node> deletednodeFromList = nodeArray[v];
        //we're just going to ASSUME FOR NOW

        if (hasEdge(v, w)) {
            for (int i = 0; i < deletednodeFromList.size(); i++)
                if (deletednodeFromList.get(i).getName() == w) {
                    nodeArray[v].remove(i);
                }
            //System.out.println(deletednodeFromList);
        }
    }

    // TODO: returns true if graph contains the edge
    //Again, assuming nodeArray is in increasing order
    public boolean hasEdge(int v, int w) {
        boolean result = false;
        List<Node> list = nodeArray[v];
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName() == w) {
                result = true;
                break;
            }
        }
        //System.out.println(result);
        return result;
    }

    // TODO: returns list containing indices of neighbors of v
    public List<Node> neighbors(int v) {
        return nodeArray[v];
    }

    // TODO: Implement topological sort with stack.
    /*
    Potential way to implement:
    1. For each node in the nodes, you can perform DFS
     */
    // Hint: think of how you can use the value of the Node (not the name)
    public List<Node> topologicalSortStack() throws CycleDetected {
        //Here's how to do it
        //1. Find first value (1 or A) (from nodes variable)
        //2. go into nodeArray and find the next node
        //3. When you reach the end and there are no nodes in the list, then add it onto the stack
        //    a. Go back to previous nodes to see if there are any possibilities
        //    b. When root node is done, set it to the next closest node

        int index = 0; //tracking the index
        Stack<Node> stack = new Stack<Node>(); //stack
        List<Node> result = new ArrayList<Node>(); //list to return, do that at the end


        Node rootNode = nodes.get(index); //keep track of rootNode
        Node node = nodes.get(index); //keep track of node you're on
        Node prevNode = nodes.get(index); //keep track of prevNode by doing prevNode = node
        List<Node> nodesList = neighbors(index); //get node's adjacency list

        while (stack.size() != nodes.size()) { //until the stack finds all nodes
            if (stack.size() == nodes.size() - 1) {
                stack.push(rootNode);

                System.out.println("Pushed Node " + index);
            }

            else if (nodesList.size() == 0 && node == rootNode) {
                //if there's nothing, and we're back in the root node, do the following:
                // 1. Set node as visited in private variable
                // 2. Add node to the stack
                // 3. Use a for loop to find the next number that's not visited
                //      a. Set the rootNode and node as that node not visited
                //      b. Update its index and nodesList

                System.out.println("Hit root Node, proceeding to next value.");
                nodes.get(index).setVisited();
                stack.push(rootNode);

                for (int i = 0; i < nodes.size(); i++) {
                    if (!nodes.get(i).getVisited()) {
                        rootNode = nodes.get(i);
                        node = nodes.get(i);
                        index = i;
                        nodesList = neighbors(index);
                        System.out.println("Found " + index + " as the new root.");
                        break;
                    }
                }
            }
            else if (nodesList.size() > 0) {
                //if there's at least on the list, go to the first one
                prevNode = node;
                index = nodesList.get(0).getName();
                node = nodes.get(index);
                nodesList = neighbors(index);

                System.out.println("Found at least one node, going to Node " + index);
            }
            else if(nodesList.size() == 0) {
                //if the size of list is zero, do the following things
                // 1. Set node as visited in private variable
                // 2. add node to the stack
                // 3. Remove edge in prevNode and the current node
                // 4. Go back to the root node as the node
                // 5. update its index and nodesList

                if(!stack.contains(node)) {
                    //if node not in Stack, add it to the stack
                    nodes.get(index).setVisited();
                    System.out.println("Pushing Node " + node.getName());
                    stack.push(node);
                    removeEdge(prevNode.getName(), node.getName());
                    index = rootNode.getName();
                    nodesList = neighbors(index);
                    node = rootNode;

                    System.out.println("List empty, going back to Node " + index);
                }
                else {
                    //if already added, just remove the edge
                    removeEdge(prevNode.getName(), node.getName());
                    index = rootNode.getName();
                    nodesList = neighbors(index);
                    node = rootNode;

                    System.out.println("Node already in stack, moving to " + index);
                }
            }

        }

        for (int i = 0; i < nodes.size(); i++) {
            Node popped = stack.pop();
            result.add(popped);
        }
        return result;

    }



    // TODO: Implement topological sort with queue.
    /*
    Potential way to implement:
    1. Go through the edges and set the value of the node to the number of incoming edges and unvisited.
    2. Push nodes that have 0 incoming edges into the queue.
    3. Implement BFS
        -Everytime you process a node, decrease it's value.
     */
    public List<Node> topologicalSortQueue() throws CycleDetected {
        int[] inDegree = new int[nodeCount()];
        ArrayList<Node> list = new ArrayList<>();

        for (int i = 0; i < nodeCount(); i++) {
            ArrayList<Node> temp = (ArrayList<Node>) nodeArray[i];
            for (Node node: temp) {
                inDegree[node.getName()]++;
            }
        }

        Queue<Node> result = new LinkedList<>();

        for (int i = 0; i < nodeCount(); i++) {
            if(inDegree[i] == 0) {
                result.add(new Node(i));
            }
        }

        int count = 0;

        Vector<Node>order = new Vector<>();

        while (!result.isEmpty()) {
            Node temp = result.poll();
            order.add(temp);

            for (Node node: nodeArray[temp.getName()]) {
                if (--inDegree[node.getName()] == 0) {
                    result.add(node);
                }
            }
            count++;
        }

        if (count != nodeCount()) {
            throw new CycleDetected();
        }

        for (Node i: order) {
            list.add(i);
        }

        return list;

    }

    public void deleteAllAssociated(int num) {
        //TODO: Delete all nodes associated with that number
        for (int i = 0; i < nodeArray.length; i++) {
            for (int j = 0; j < nodeArray[i].size(); j++) {
                if (nodeArray[i].get(j).getName() == num) {
                    removeEdge(nodeArray[i].get(j).getName(), num);
                    System.out.println("Removed edge in " + i + "th position of the array, and "
                    + j + "th position of the list. It is removing " + num);
                }
            }
        }
    }

}