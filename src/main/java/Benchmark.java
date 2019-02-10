
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Benchmark {

    private static ArrayList<Integer> start = new ArrayList<Integer>();
    private static ArrayList<Integer> dest = new ArrayList<>();
    private static ArrayList<Integer> dist = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to HiaslMaps TM! Benchmark");
        System.out.println("#########################################################################");
        System.out.println("Please enter the path to a map you want to read from.");
        String mapPath = scanner.nextLine();
        System.out.println("Please enter the path to the .que file.");
        String quePath = scanner.nextLine();
        System.out.println("Please enter the path to the .sol file");
        String solPath = scanner.nextLine();
        scanner.close();
        BufferedReader reader = new BufferedReader(new FileReader(quePath));
        String input1;
        while ((input1 = reader.readLine()) != null && input1.length() != 0) {
            String[] numbers = input1.split(" ");
            start.add(Integer.parseInt(numbers[0]));
            dest.add(Integer.parseInt(numbers[1]));

        }
        reader.close();
        System.out.println("doing Benchmark stuff now");
        Graph graph = new Graph(mapPath);

        var dijkstra = new Dijkstra(graph);
        for (int i = 0; i < dest.size(); i++) {
            long startTime = System.nanoTime();
            dijkstra.dijkstra(start.get(i), dest.get(i));
            long endTime = System.nanoTime();
            long finalTime = endTime - startTime;
            System.out.printf("Time to calc dijkstra in seconds: %.2f %n", finalTime / 1.0e9);
            dist.add(dijkstra.distanceTo(dest.get(i)));

        }

        for (int i : dist) {
            System.out.println(i);
        }

        reader = new BufferedReader(new FileReader(solPath));

        boolean flag = true;
        for (int i : dist) {
            if (Integer.parseInt(reader.readLine()) != i) {
                flag = false;
                break;
            }
        }
        reader.close();

        if (flag) System.out.println("Just like in the .sol file");
        else System.out.println("something's not so right");
    }
}
