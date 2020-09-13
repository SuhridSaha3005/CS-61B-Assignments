/** Circular Array List with instance variables items (array), size, nextFirst, nextLast
 *  nextFirst returns the index "before" first, nextLast returns the index "after" last. */

public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Constructor for the empty ArrayDeque. 8 given as default items.length. */
    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    /** Remainder function, that returns a mod b and is between 0 and b - 1. */
    private int remainder(int dividend, int divisor) {
        int rem = dividend % divisor;
        if (rem >= 0) {
            return rem;
        } else {
            return rem + divisor;
        }
    }

    /** Returns the minimum of 2 integers */
    private int min(int x, int y) {
        if (x < y) {
            return x;
        } else {
            return y;
        }
    }

    /** Resizes an 'items' of ArrayDeque to array of length 'capacity'.
     *  This is mainly for allowing us to extend array size. */
    private void resize(int capacity) {
        int front = remainder(nextFirst + 1, items.length);
        int frontItems = min(items.length - front, size);
        int backBegin = remainder(front + frontItems, items.length);
        int backItems = size - frontItems;
        T[] a = (T []) new Object[capacity];
        System.arraycopy(items, front, a, 0, frontItems);
        System.arraycopy(items, backBegin, a, frontItems, size - frontItems);
        items = a;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    /** Add 'item' to 'front' of ArrayDeque. */
    public void addFirst(T item) {
        if (size == items.length) {
            this.resize(4 * size);
        }
        items[nextFirst] = item;
        nextFirst = remainder(nextFirst - 1, items.length);
        size += 1;
        nextLast = remainder(nextFirst + size + 1, items.length);
    }

    /** Add 'item' to 'end' of ArrayDeque. */
    public void addLast(T item) {
        if (size == items.length) {
            this.resize(4 * size);
        }
        items[nextLast] = item;
        nextLast = remainder(nextLast + 1, items.length);
        size += 1;
        nextFirst = remainder(nextLast - size - 1, items.length);
    }

    /** Checks if some ArrayDeque is an empty array list or not. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the size of an array list, that is, the number of items. */
    public int size() {
        return size;
    }

    /** Since our array is circular, this converts the user's index (0,1,2...) to their
     * corresponding indices in the circular array list. */
    private int circularIndex(int index) {
        return remainder(nextFirst + index + 1, items.length);
    }

    /** Removes the front item of an array list. */
    public T removeFirst() {
        if (items.length >= 16 && 4 * size <= items.length) {
            this.resize(4 * (size - 1));
        }
        int firstIndex = this.circularIndex(0);
        T firstItem = items[firstIndex];
        items[firstIndex] = null;
        if (size > 0) {
            size -= 1;
        }
        nextFirst = remainder(nextFirst + 1, items.length);
        nextLast = remainder(nextFirst + size + 1, items.length);
        return firstItem;
    }

    /** Removes the end item of an array list. */
    public T removeLast() {
        if (items.length >= 16 && 4 * size <= items.length) {
            this.resize(4 * (size - 1));
        }
        int lastIndex = this.circularIndex(size - 1);
        T lastItem = items[lastIndex];
        items[lastIndex] = null;
        if (size > 0) {
            size -= 1;
        }
        nextLast = remainder(nextLast - 1, items.length);
        nextFirst = remainder(nextLast - size - 1, items.length);
        return lastItem;
    }

    /** Returns the item placed at 'index' in array list. */
    public T get(int index) {
        return items[circularIndex(index)];
    }

    /** Prints out the items of the array list, separated by space. Then prints out a new line. */
    public void printDeque() {
        for (int i = 0; i < size - 1; i += 1) {
            System.out.print(this.get(i));
            System.out.print(" ");
        }
        System.out.print(this.get(size - 1));
        System.out.println("\n");
    }
}
