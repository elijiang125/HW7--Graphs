import java.util.*;
import java.io.*;

// This class acts a main function
public class Runner {
    public static GraphL setUpGraph(String file) throws IOException{
        try{
            int vertices = 0;
            GraphL graph = new GraphL(1);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            System.out.print(file + " loaded. ");
            String line;
            int count = 0;
            while((line = reader.readLine()) != null){
                String text = line.toLowerCase();
                if(count == 0){
                    vertices = Integer.parseInt(text);
                    graph = new GraphL(vertices);
                    count++;
                    continue;
                }
                String[] words = text.split("\\s+"); // splits by whitespace
                int source = Integer.parseInt(words[0]);
                int dest = Integer.parseInt(words[1]);
                graph.addEdge(source, dest);
            }
            System.out.println("Graph was created.");
            return graph;
        } catch (IOException e){
            throw e;
        }
    }

    public static void main(String[] args) throws IOException, CycleDetected {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter file name: ");
            String file = input.nextLine();
            GraphL g = setUpGraph(file);
            String answer = "y";
            while (answer.equals("y")) {
                System.out.println("Enter what graph traversal you'd like to do: ");
                System.out.println("0: to quit");
                System.out.println("1. Topological sort with stack");
                System.out.println("2. Topological sort with queue");
                int choice = Integer.parseInt(input.nextLine());
                if (choice == 0) {
                    System.out.println("buh bye");
                    break;
                } else if (choice == 1){
                    System.out.println(g.topologicalSortStack());
                } else if (choice == 2){
                    System.out.println(g.topologicalSortQueue());
                }else {
                    System.out.println("Invalid choice.");
                }
                g.reset();
            }
            input.close();
        } catch (IOException | CycleDetected e) {
            throw e;
        }
    }
}
