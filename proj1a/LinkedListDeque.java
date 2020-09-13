/** Double Linked List with variables ItemNode and size. */

public class LinkedListDeque<T> {
    private ItemNode sentinel;
    private int size;

    /** Subordinate class ItemNode with variables item, prev, next. */
    private class ItemNode {
        private T item;
        private ItemNode prev;
        private ItemNode next;

        /** Constructor for ItemNode class. */
        private ItemNode(ItemNode p, T x, ItemNode n) {
            prev = p;
            item = x;
            next = n;
        }
    }

    /** Constructor for empty LinkedListDeque. */
    public LinkedListDeque() {
        size = 0;
        sentinel = new ItemNode(null, null, null);
        ItemNode sentinelNode = this.sentinel;
        sentinel.prev = sentinelNode;
        sentinel.next = sentinelNode;
    }

    /** Add 'item' to the front of the linked list. */
    public void addFirst(T item) {
        size += 1;
        sentinel.next = new ItemNode(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }

    /** Add 'item' to the end of the linked list. */
    public void addLast(T item) {
        size += 1;
        sentinel.prev = new ItemNode(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
    }

    /** Below method Checks if LinkedList is empty or not. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Below method Returns length/size of linked list. */
    public int size() {
        return size;
    }

    /** Removes the first item from linked list. */
    public T removeFirst() {
        if (size == 0) {
            size -= 1;
        }
        T firstItem = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return firstItem;
    }

    /** Removes the last item from linked list. */
    public T removeLast() {
        if (size == 0) {
            size -= 1;
        }
        T lastItem = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return lastItem;
    }

    /** Below method returns the item placed at 'index' in linked list. */
    public T get(int index) {
        ItemNode x = sentinel.next;
        int i = 0;
        while (i < index) {
            x = x.next;
            i += 1;
        }
        return x.item;
    }

    /** Helper method for getRecursive(). Note: the helper is recursive.
     * The actual getRecursive() just calls the helper. */
    private T getRecursiveHelper(ItemNode n, int i) {
        if (i == 0) {
            return n.item;
        } else {
            return getRecursiveHelper(n.next, i - 1);
        }
    }

    /** The recursive version of the iterative get(), just calls its helper method. */
    public T getRecursive(int index) {
        return this.getRecursiveHelper(sentinel.next, index);
    }

    /** Prints out the items of the linked list, separated by space. then a new line. */
    public void printDeque() {
        for (int i = 0; i < size - 1; i += 1) {
            System.out.print(this.get(i));
            System.out.print(" ");
        }
        System.out.print(this.get(size - 1));
        System.out.println("\n");
    }
}
