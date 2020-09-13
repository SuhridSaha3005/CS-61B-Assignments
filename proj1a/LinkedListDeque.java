/** Double Linked List with variables ItemNode and size. */

public class LinkedListDeque<ItemType> {
    private ItemNode sentinel;
    private int size;

    /** Subordinate class ItemNode with variables item, prev, next. */
    public class ItemNode {
        public ItemType item;
        public ItemNode prev;
        public ItemNode next;

        /** Constructor for ItemNode class. */
        public ItemNode(ItemNode p, ItemType x, ItemNode n) {
            prev = p;
            item = x;
            next = n;
        }
    }

    /** Constructor for empty LinkedListDeque. Note: sentinel's prev and next point to itself. */
    public LinkedListDeque() {
        size = 0;
        sentinel = new ItemNode(null, null, null);
        ItemNode sentinelNode = this.sentinel;
        sentinel.prev = sentinelNode;
        sentinel.next = sentinelNode;
    }

    /** Add 'item' to the front of the linked list. */
    public void addFirst(ItemType item) {
        size += 1;
        sentinel.next = new ItemNode(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }

    /** Add 'item' to the end of the linked list. */
    public void addLast(ItemType item) {
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
    public ItemType removeFirst() {
        size -= 1;
        ItemType firstItem = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        return firstItem;
    }

    /** Removes the last item from linked list. */
    public ItemType removeLast() {
        size -= 1;
        ItemType lastItem = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        return lastItem;
    }

    /** Below method returns the item placed at 'index' in linked list. */
    public ItemType get(int index) {
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
    private ItemType getRecursiveHelper(ItemNode n, int i) {
        if (i == 0) {
            return n.item;
        } else {
            return getRecursiveHelper(n.next, i - 1);
        }
    }

    /** getRecursive(), the recursive version of the iterative get(), just calls its helper method. */
    public ItemType getRecursive (int index) {
        return this.getRecursiveHelper(sentinel.next, index);
    }

    /** Prints out the items of the linked list, separated by space. Then prints out a new line. */
    public void printDeque() {
        for (int i = 0; i < size - 1; i += 1) {
            System.out.print(this.get(i));
            System.out.print("");
        }
        System.out.print(this.get(size - 1));
        System.out.println("\n");
    }
}
