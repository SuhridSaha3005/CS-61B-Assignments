import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private K key;
    private V value;
    private BSTMap<K, V> left;
    private BSTMap<K, V> right;
    private Set<K> keySet;

    public BSTMap() {
        keySet = new HashSet<>();
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        key = null;
        value = null;
        left = null;
        right =  null;
        keySet = new HashSet<>();
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return get(this, key);
    }

    private V get(BSTMap<K, V> bst, K key) {
        if (bst == null || bst.key == null) {
            return null;
        }
        int cmp = key.compareTo(bst.key);
        if (cmp == 0) {
            return bst.value;
        } else if (cmp < 0) {
            return get(bst.left, key);
        } else {
            return get(bst.right, key);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return keySet.size();
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        keySet.add(key);
        put(this, key, value);
    }

    private void put(BSTMap<K, V> bst, K key, V value) {
        if (bst.key == null) {
            bst.key = key;
            bst.value = value;
        } else {
            int cmp = key.compareTo(bst.key);
            if (cmp == 0) {
                bst.value = value;
            } else if (cmp < 0) {
                if (bst.left == null) {
                    bst.left = new BSTMap<>();
                }
                put(bst.left, key, value);
            } else {
                if (bst.right == null) {
                    bst.right = new BSTMap<>();
                }
                put(bst.right, key, value);
            }
        }
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /* Returns an iterator over the set of keys */
    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }

    private String orderedKeyString() {
        if (key == null) {
            return "";
        }
        return left.orderedKeyString() + key.toString() + " " + right.orderedKeyString();
    }

    public void printInOrder() {
        System.out.println(this.orderedKeyString());
    }
}
