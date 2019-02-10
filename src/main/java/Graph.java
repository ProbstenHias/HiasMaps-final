import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Graph {


    private int[] source;
    private int[] destination;
    private int[] weight;

    /**
     * One dimensional array that saves an offset for each node in the graph.
     * The index stands for the node in the graph.
     * The value saved in the array represents the first appearance of the node as a source for another node.
     * <p>
     * This results in an array with the size array[nodeCount]
     */
    private int[] offset;


    private double[] latitude;
    private double[] longitude;


    private int nodeCount;

    private int edgeCount;

    /**
     * overloaded constructor for the class that immediately creates the graph
     *
     * @param path path of the map file
     */
    public Graph(String path) {
        long startTime = System.nanoTime();
        try {
            readFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        createOffset();
        long endTime = System.nanoTime();
        long finalTime = endTime - startTime;
        System.out.printf("Time to read graph in seconds: %.2f %n", finalTime / 1.0e9);
    }


    public void readFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            for (int i = 0; i < 5; i++) {
                reader.readLine();
            }
            this.nodeCount = Integer.parseInt(reader.readLine());
            latitude = new double[nodeCount];
            longitude = new double[nodeCount];
            this.edgeCount = Integer.parseInt(reader.readLine());
            source = new int[edgeCount];
            destination = new int[edgeCount];
            weight = new int[edgeCount];

            for (int i = 0; i < nodeCount; i++) {
                String[] numbers = reader.readLine().split(" ");
                latitude[i] = Double.parseDouble(numbers[2]);
                longitude[i] = Double.parseDouble(numbers[3]);
            }
            for (int i = 0; i < edgeCount; i++) {
                String[] numbers = reader.readLine().split(" ");
                source[i] = Integer.parseInt(numbers[0]);
                destination[i] = Integer.parseInt(numbers[1]);
                weight[i] = Integer.parseInt(numbers[2]);
            }

        }
    }

    public int getOffsetOf(int node) {
        return offset[node];
    }

    public int getWeigtOf(int edge) {
        return this.weight[edge];
    }

    public int getNodeCount() {
        return this.nodeCount;
    }

    public int getEdgeCount() {
        return this.edgeCount;
    }

    public double getLatOf(int node) {
        return latitude[node];
    }

    public double getLonOf(int node) {
        return longitude[node];
    }


    public void createOffset() {
        this.offset = new int[this.nodeCount];
        offset[0] = 0;
        int current = 1;
        int start = 0;
        while (source[start] == 0) {
            start++;
        }
        //works again!!!
        for (int i = start; i < source.length; i++) {
            if (current >= offset.length)
                break;
            if (offset[current] == 0 && source[i] == current) {
                offset[current] = i;
                current++;
            } else {
                if (offset[current] == 0 && source[i] > current) {
                    offset[current] = -1;
                    current++;
                }
            }
        }
    }

    /**
     * returns all outgoing edges of a node
     *
     * @param node
     * @return
     */
    public ArrayList<int[]> edgesOfNode(int node) {
        ArrayList<int[]> list = new ArrayList<>();
        int offset = this.offset[node];
        if (offset == -1) {
            return list;
        }
        while (offset < this.edgeCount && source[offset] == node) {
            list.add(new int[]{
                    destination[offset], weight[offset]
            });
            offset++;
        }
        return list;
    }

    public int getSourceOf(int offset) {
        return source[offset];
    }

    public int getDestinationOf(int offset) {
        return destination[offset];
    }

    public String getNextNode(double x, double y) {
        double dist = Double.MAX_VALUE;
        int next = Integer.MAX_VALUE;
        for (int i = 0; i < nodeCount; i++) {
            double temp = Math.hypot(latitude[i] - x, longitude[i] - y);
            if (temp < dist) {
                dist = temp;
                next = i;
            }

        }
        return next + " " + latitude[next] + " " + longitude[next];
    }

}
