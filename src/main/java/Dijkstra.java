import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Marius Haller
 * @author Oleksandr Mitrofanov
 * @author Matthias Weilinger
 * @version 0.1
 */
public class Dijkstra {
    private int start;
    private int[] prev;
    private boolean[] finished;
    private Heap heap;
    private Graph graph;

    public Dijkstra(Graph graph) {
        this.graph = graph;
        heap = new Heap(graph.getNodeCount()); //create priority queue
        finished = new boolean[graph.getNodeCount()];
        prev = new int[graph.getNodeCount()];
    }


    @SuppressWarnings("Duplicates")
    public void dijkstra(int start) {
        if (!heap.isEmpty()) heap.flush();
        heap.setEmpty(false);
        Arrays.fill(finished, false);
        this.start = start;
        this.heap.insert(start, 0); // add the start node to the heap

        //dijkstra algorithm
        while (heap.getSize() > 0) {
            int finishedNode = heap.deleteMin(); // get node with shortest path
            int finishedValue = heap.getValueOf(finishedNode);
            finished[finishedNode] = true;
            int offset = graph.getOffsetOf(finishedNode);
            if (offset == -1) continue;
            while (offset < graph.getEdgeCount() && graph.getSourceOf(offset) == finishedNode) {
                int destination = graph.getDestinationOf(offset);
                int weight = graph.getWeigtOf(offset);
                offset++;

                //dijkstra
                if (finished[destination]) continue;
                if (heap.getValueOf(destination) == -1) {
                    heap.insert(destination, finishedValue + weight);
                } else {
                    if (heap.getValueOf(finishedNode) + weight < heap.getValueOf(destination)) {
                        heap.decreaseKey(destination, finishedValue + weight);
                    }
                }

            }

        }
    }

    @SuppressWarnings("Duplicates")
    public void dijkstra(int start, int destination) {
        if (!heap.isEmpty()) heap.flush();
        heap.setEmpty(false);
        Arrays.fill(finished, false);
        this.start = start;
        Arrays.fill(prev, -1); // fill with Infinite
        this.heap.insert(start, 0); // add the start node to the heap

        //dijkstra algorithm
        while (heap.getSize() > 0) {
            int finishedNode = heap.deleteMin(); // get node with shortest path
            if (finishedNode == destination) return;
            int finishedValue = heap.getValueOf(finishedNode);
            finished[finishedNode] = true;
            int offset = graph.getOffsetOf(finishedNode);
            if (offset == -1) continue;
            while (offset < graph.getEdgeCount() && graph.getSourceOf(offset) == finishedNode) {
                int dest = graph.getDestinationOf(offset);
                int weight = graph.getWeigtOf(offset);
                offset++;

                //dijkstra
                if (finished[dest]) continue;
                if (heap.getValueOf(dest) == -1) {
                    heap.insert(dest, finishedValue + weight);
                    prev[dest] = finishedNode;
                } else {
                    if (heap.getValueOf(finishedNode) + weight < heap.getValueOf(dest)) {
                        heap.decreaseKey(dest, finishedValue + weight);
                        prev[dest] = finishedNode;
                    }
                }

            }

        }
    }

    public int distanceTo(int destination) {
        return heap.getValueOf(destination);
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
