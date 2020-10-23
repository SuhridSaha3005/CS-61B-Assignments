package bearmaps;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private T[] minHeap;
    private final HashMap<T, Double> hashHeap;
    private final HashMap<T, Integer> positions;
    private int size;

    /* Constructor to create empty MinPQ */
    public ArrayHeapMinPQ() {
        minHeap = (T[]) new Object[1];
        hashHeap = new HashMap<>();
        positions = new HashMap<>();
        size = 0;
    }

    /* Helper method: Returns index of parent of key in position k */
    private int parent(int k) {
        return k / 2;
    }

    /* Helper method: Returns index of left Child of key in position k */
    private int leftChild(int k) {
        return 2 * k;
    }

    /* Helper method: Returns index of right Child of key in position k */
    private int rightChild(int k) {
        return 2 * k + 1;
    }

    /* Helper method: Returns priority value of key */
    private double priority(T item) {
        return hashHeap.get(item);
    }

    /* Helper method: Compares priorities of keys in positions i and j */
    private int compare (int i, int j) {
        return Double.compare(priority(minHeap[i]), priority(minHeap[j]));
    }

    /* Helper method: Swaps keys in positions i and j */
    private void swap (int i, int j) {
        positions.put(minHeap[i], j);
        positions.put(minHeap[j], i);
        T temp = minHeap[i];
        minHeap[i] = minHeap[j];
        minHeap[j] = temp;
    }

    /* Resizes minHeap to length 'capacity' */
    private void resize (int capacity) {
        T[] temp = (T[]) new Object[capacity];
        System.arraycopy(minHeap, 1, temp, 1, size);
        minHeap = temp;
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            return;
        }
        if (size >= minHeap.length - 1) {
            resize(4 * minHeap.length);
        }
        minHeap[size + 1] = item;
        hashHeap.put(item, priority);
        positions.put(item, size + 1);
        swim(size + 1);
        size += 1;
    }

    /* Helper method to 'swim' a node to the correct position */
    private void swim(int k) {
        if (k == 1) {
            return;
        }
        if (compare(k, parent(k)) < 0) {
            swap(k, parent(k));
            swim(parent(k));
        }
    }

    /* Helper method to 'sink' a node to correct position */
    private void sink(int k) {
        if (leftChild(k) > size) {
            return;
        }
        if (rightChild(k) > size || compare(leftChild(k), rightChild(k)) < 0) {
            if (compare(leftChild(k), k) < 0) {
                swap(k, leftChild(k));
                sink(leftChild(k));
            }
        } else {
            if (compare(rightChild(k), k) < 0) {
                swap(k, rightChild(k));
                sink(rightChild(k));
            }
        }
    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return hashHeap.containsKey(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return minHeap[1];
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T min = minHeap[1];
        swap(1, size);
        hashHeap.remove(min);
        positions.remove(min);
        size -= 1;
        sink(1);
        if (4 * size < minHeap.length) {
            resize(2 * size + 1);
        }
        return min;
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return size;
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        double oldPriority = priority(item);
        if (priority == oldPriority) {
            return;
        }
        hashHeap.put(item, priority);
        if (priority > oldPriority) {
            sink(positions.get(item));
        } else {
            swim(positions.get(item));
        }
    }
}
