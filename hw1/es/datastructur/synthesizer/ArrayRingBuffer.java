package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;

    /* Index for the next enqueue. */
    private int next;

    /* Variable for the fillCount. */
    private int fillCount;

    /* Array for storing the buffer data. */
    private final T[] items;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        items = (T[]) new Object[capacity];
        first = 0;
        next = 0;
        fillCount = 0;
    }

    /** Returns size of the ring buffer */

    @Override
    public int capacity() {
        return items.length;
    }

    /** Returns number of items currently in ring buffer */

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */

    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        items[next] = x;
        next = (next + 1) % items.length;
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T firstItem = items[first];
        items[first] = null;
        first = (first + 1) % items.length;
        fillCount -= 1;
        return firstItem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        return items[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new BufferIterator();
    }

    private class BufferIterator implements Iterator<T> {
        private int bufferPos;

        BufferIterator() {
            bufferPos = 0;
        }

        @Override
        public boolean hasNext() {
            return bufferPos < fillCount;
        }

        @Override
        public T next() {
            T returnItem = items[(first + bufferPos) % items.length];
            bufferPos += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> o = (ArrayRingBuffer<T>) other;
        if (this.fillCount() != o.fillCount()) {
            return false;
        }
        Iterator<T> iterator1 = this.iterator();
        Iterator<T> iterator2 = o.iterator();
        while (iterator1.hasNext()) {
            if (!iterator1.next().equals(iterator2.next())) {
                return false;
            }
        }
        return true;
    }
}
