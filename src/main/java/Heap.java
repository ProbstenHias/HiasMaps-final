
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Heap implements IHeap {
    private int[] value;
    private int[] place;
    private int[] heap;
    private int size;
    private boolean empty = true;

    public Heap(int maxEntries) {
        size = 0;
        value = new int[maxEntries];
        Arrays.fill(value, -1);
        place = new int[maxEntries];
        heap = new int[maxEntries];
    }

    @Override
    public int deleteMin() throws NoSuchElementException {
        if (size > 0) {
            int tmp = heap[0];
            swap(0, size - 1);
            size--;
            if (size > 1) {
                siftdown(0);
            }
            return tmp;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void decreaseKey(int node, int value) {
        if (this.value[node] > value) {
            this.value[node] = value;
            siftup(place[node]);
        }
    }

    @Override
    public void siftup(int i) {
        int index = i;
        int node = heap[i];
        while (index > 0) {
            int node2 = heap[(IHeap.parent(index))];
            if (value[node] >= value[node2]) break;
            heap[index] = node2;
            place[node2] = index;
            index = IHeap.parent(index);
        }
        heap[index] = node;
        place[node] = index;
    }

    @Override
    public void siftdown(int i) {
        int node = heap[i];
        int index = i;
        double end = getLeafCount();
        while (index < end) {
            if (hasRight(index)) {
                if (value[heap[IHeap.left(index)]] < value[heap[IHeap.right(index)]]) {
                    place[heap[IHeap.left(index)]] = index;
                    heap[index] = heap[IHeap.left(index)];
                    index = IHeap.left(index);
                } else {
                    place[heap[IHeap.right(index)]] = index;
                    heap[index] = heap[IHeap.right(index)];
                    index = IHeap.right(index);
                }

            } else {
                place[heap[IHeap.left(index)]] = index;
                heap[index] = heap[IHeap.left(index)];
                index = IHeap.left(index);

            }
        }
        place[node] = index;
        heap[index] = node;
        siftup(index);
    }

    @Override
    public void swap(int x, int y) {
        int tmp1 = heap[x];
        heap[x] = heap[y];
        heap[y] = tmp1;
        int tmp2 = place[heap[x]];
        place[heap[x]] = place[heap[y]];
        place[heap[y]] = tmp2;

    }

    @Override
    public void insert(int vertex, int distance) {
        heap[size] = vertex;
        value[vertex] = distance;
        place[vertex] = size;
        size++;
        siftup(size - 1);
    }


    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isLeaf(int index) {
        return false;
    }


    @Override
    public double getLeafCount() {
        return Math.floor(size / 2.0);
    }

    @Override
    public boolean hasRight(int index) {
        return IHeap.right(index) < size;
    }


    public int getValueOf(int node) {
        return value[node];
    }

    public void flush() {
        Arrays.fill(value, -1);
        this.size = 0;
        this.empty = true;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
