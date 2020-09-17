public interface Deque<T> {
    /** Add 'item' to 'front' of ArrayDeque. */
    void addFirst(T item);

    /** Add 'item' to 'end' of ArrayDeque. */
    void addLast(T item);

    /** Checks if some ArrayDeque is an empty array list or not. */
    default boolean isEmpty() {
        return size() == 0;
    }

    /** Returns the size of an array list, that is, the number of items. */
    int size();

    /** Removes the front item of an array list. */
    T removeFirst();

    /** Removes the end item of an array list. */
    T removeLast();

    /** Returns the item placed at 'index' in array list. */
    T get(int index);

    /** Prints out the items of the array list, separated by space. Then prints out a new line. */
    void printDeque();
}
