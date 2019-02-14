import java.util.Iterator;

import static java.util.Arrays.fill;

public class AStar {


    private int[] prev;
    private boolean[] finished;
    private int[] distance;
    private Heap heap;
    private Graph graph;

    public AStar(Graph graph) {
        this.graph = graph;
        heap = new Heap(graph.getNodeCount());
        prev = new int[graph.getNodeCount()];
        finished = new boolean[graph.getNodeCount()];
        distance = new int[graph.getNodeCount()];
    }

    public static void main(String[] args) {
        AStar aStar = new AStar(new Graph("GraphFiles/germany.fmi"));
        aStar.aStar(1, 215);
        System.out.println(aStar.calcDistance(1, 2));
        System.out.println(aStar.distanceTo(2));
    }

    @SuppressWarnings("Duplicates")
    public void aStar(int start, int destination) {
        if (!heap.isEmpty()) heap.flush();
        heap.setEmpty(false);
        fill(finished, false);
        fill(prev, -1); // fill with Infinite
        fill(distance, -1);
        this.heap.insert(start, 0); // add the start node to the heap
        distance[start] = 0;

        //dijkstra algorithm
        while (heap.getSize() > 0) {
            int finishedNode = heap.deleteMin(); // get node with shortest path
            if (finishedNode == destination) return;
            finished[finishedNode] = true;
            int offset = graph.getOffsetOf(finishedNode);
            if (offset == -1) continue;
            while (offset < graph.getEdgeCount() && graph.getSourceOf(offset) == finishedNode) {
                int dest = graph.getDestinationOf(offset);
                int weight = graph.getWeigtOf(offset);
                offset++;

                if (finished[dest]) continue;
                int heur = calcDistance(dest, destination);
                if (distance[dest] == -1) {
                    heap.insert(dest, distance[finishedNode] + weight + heur);
                    prev[dest] = finishedNode;
                    distance[dest] = distance[finishedNode] + weight;
                } else {

                    if (distance[finishedNode] + weight < distance[dest]) {
                        heap.decreaseKey(dest, distance[finishedNode] + weight + heur);
                        prev[dest] = finishedNode;
                        distance[dest] = distance[finishedNode] + weight;
                    }
                }

            }

        }
    }

    public int calcDistance(int x, int y) {
        return (int) ((Math.hypot(graph.getLatOf(x) - graph.getLatOf(y), graph.getLonOf(y) - graph.getLonOf(y)) * 1e6 / 2.2));
    }

    public int distanceTo(int destination) {
        return distance[destination];
    }

    public Iterator<Integer> pathTo(int destination) {
        return new Iterator<>() {
            int current = destination;

            @Override
            public boolean hasNext() {
                return current != -1;
            }

            @Override
            public Integer next() {
                int tmp = current;
                current = prev[current];
                return tmp;

            }
        };
    }
}
