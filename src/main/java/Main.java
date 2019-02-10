
import java.util.Scanner;

/**
 * @author Marius Haller
 * @author Oleksandr Mitrofanov
 * @author Matthias Weilinger
 * @version 0.1
 */
public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to HiaslMaps TM!");
            System.out.println("#########################################################################");
            System.out.println("Please enter the path to a map you want to read from.");
            String path = scanner.nextLine();
            System.out.println("Reading...");
            Graph graph = new Graph(path);
            whitespace();
            Dijkstra dijkstra = new Dijkstra(graph);
            while (true) {
                System.out.println("Please enter a starting node");
                int start = scanner.nextInt();
                System.out.println("Please enter a destination node");
                int dest = scanner.nextInt();
                whitespace();
                System.out.println("Calculation route...");
                long startTime = System.nanoTime();
                dijkstra.dijkstra(start, dest);
                long endTime = System.nanoTime();
                long finalTime = endTime - startTime;
                System.out.printf("Time to calc dijkstra in seconds: %.2f %n", finalTime / 1.0e9);
                System.out.println("Distance from " + start + " to " + dest + ":  " + dijkstra.distanceTo(dest));
                whitespace();
            }
        }

    }

    private static void whitespace() {
        System.out.println();
        System.out.println();
        System.out.println();
    }


}