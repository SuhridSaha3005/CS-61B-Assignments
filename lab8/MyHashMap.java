import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int initialSize = 16;
    private double loadFactor = 0.75;
    private List<Node>[] hashData;
    private Set<K> keySet;
    private List<Node> nodes;
    private int size;

    private class Node {
        private final K key;
        private V value;
        public Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    public MyHashMap() {
        hashData = (List<Node>[]) new ArrayList[initialSize];
        keySet = new HashSet<>();
        nodes = new LinkedList<>();
        size = 0;
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        hashData = (List<Node>[]) new ArrayList[initialSize];
        keySet = new HashSet<>();
        nodes = new LinkedList<>();
        size = 0;
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        hashData = (List<Node>[]) new ArrayList[initialSize];
        keySet = new HashSet<>();
        nodes = new LinkedList<>();
        size = 0;
    }

    private int keyPos(K key, int len) {
        return Math.floorMod(key.hashCode(), len);
    }

    private List<Node> bucket(K key) {
        return hashData[keyPos(key, hashData.length)];
    }

    private void resize(int capacity) {
        List<Node>[] temp = (List<Node>[]) new ArrayList[capacity];
        for (Node n : nodes) {
            if (temp[keyPos(n.key, capacity)] == null) {
                temp[keyPos(n.key, capacity)] = new ArrayList<>();
            }
            temp[keyPos(n.key, capacity)].add(n);
        }
        hashData = temp;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        hashData = (List<Node>[]) new ArrayList[initialSize];
        keySet = new HashSet<>();
        nodes = new LinkedList<>();
        size = 0;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (bucket(key) == null) {
            return false;
        }
        for (Node n : bucket(key)) {
            if (key.equals(n.key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (bucket(key) == null) {
            return null;
        }
        for (Node n : bucket(key)) {
            if (key.equals(n.key)) {
                return n.value;
            }
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        double N = size;
        double M = hashData.length;
        double load = N / M;
        if (load >= loadFactor) {
            resize(10 * hashData.length);
        }
        int keyCount = 0;
        if (bucket(key) == null) {
            hashData[keyPos(key, hashData.length)] = new ArrayList<>();
        }
        for (Node n : bucket(key)) {
            if (key.equals(n.key)) {
                n.value = value;
                keyCount += 1;
            }
        }
        if (keyCount == 0) {
            Node n = new Node(key, value);
            bucket(key).add(n);
            keySet.add(key);
            nodes.add(n);
            size += 1;
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }
}
