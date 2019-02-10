
/**
 * @author Marius Haller
 * @author Oleksandr Mitrofanov
 * @author Matthias Weilinger
 * @version 0.2
 */
public interface IHeap {
    /**
     * Deletes the minimal node of the heap.
     * Returns the node number and it's value
     * Replaces it with last node in array and makes a new heap
     *
     * @return node number and value
     */
    int deleteMin();

    /**
     * Method can decrease the value of a node
     * shifts the node up in the minheap until it is in correct position
     *
     * @param node  node that gets decreased
     * @param value new distance to start node
     */
    void decreaseKey(int node, int value);

    /**
     * returns the parent index of an index in the heap
     * if index is root it returns 0
     *
     * @param i index to check on
     * @return index of the parent
     */
    static int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * returns the left child index of an index in the heap
     *
     * @param i index to check on
     * @return index of left child
     */
    static int left(int i) {
        return (2 * i + 1);
    }

    /**
     * returns the right child index of an index in the heap
     *
     * @param i index to check on
     * @return index of right child
     */
    static int right(int i) {
        return (2 * i + 2);
    }

    /**
     * shifts up an entry in the min heap as far as necessary
     * is used in decreaseKey method
     * is used if new entry is added
     *
     * @param i index of the node in the heap to shift up
     */
    void siftup(int i);

    /**
     * shifts down an entry in the min heap as far as necessary
     *
     * @param i index of the node in the heap to shift down
     */
    void siftdown(int i);

    /**
     * swaps to entries in the heap
     * also swaps their keyValuePlace entries
     *
     * @param x entry number one
     * @param y entry number two
     */
    void swap(int x, int y);

    /**
     * inserts a new node in the heap
     * places it in the right position
     *
     * @param vertex   name of node to insert
     * @param distance value of node to insert
     */
    void insert(int vertex, int distance);

    /**
     * returns the number of entries in the heap
     *
     * @return number of entries in the heap
     */
    int getSize();

    boolean isLeaf(int index);

    double getLeafCount();

    boolean hasRight(int index);

}
