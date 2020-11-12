package bearmaps.proj2d;

import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.PointSet;
import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.proj2c.streetmap.StreetMapGraph;
import bearmaps.proj2c.streetmap.Node;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private final PointSet connectedPoints;
    private final HashMap<Point, Node> nodePointMap;
    private final TrieSet trieNames;
    private final HashMap<String, List<Node>> nameMap;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        trieNames = new TrieSet();
        List<Point> points = new ArrayList<>();
        nodePointMap = new HashMap<>();
        nameMap = new HashMap<>();
        Point p;
        String s;
        List<Node> lst;
        for (Node vertex : nodes) {
            s = vertex.name();
            trieNames.add(s);
            if (nameMap.containsKey(s)) {
                lst = nameMap.get(s);
                lst.add(vertex);
                nameMap.replace(s, lst);
            } else {
                lst = new ArrayList<>();
                lst.add(vertex);
                nameMap.put(s, lst);
            }
            if (!neighbors(vertex.id()).isEmpty()) {
                p = new Point(vertex.lon(), vertex.lat());
                points.add(p);
                nodePointMap.put(p, vertex);
            }
        }
        connectedPoints = new WeirdPointSet(points);
    }

    private static class TrieSet {
        private final TrieNode root;

        private static class TrieNode {
            public boolean isKey;
            public HashMap<Character, TrieNode> next;

            public TrieNode(boolean isKey, HashMap<Character, TrieNode> next) {
                this.isKey = isKey;
                this.next = next;
            }
        }

        public TrieSet() {
            root = new TrieNode(false, new HashMap<>());
        }

        public void add(String s) {
            add(root, s);
        }

        private void add(TrieNode n, String s) {
            if (s == null || s.equals("")) {
                return;
            }
            char first = s.charAt(0);
            String rest = s.substring(1);
            boolean isKey = rest.equals("");
            TrieNode restNode;
            if (n.next.containsKey(first)) {
                restNode = n.next.get(first);
                restNode.isKey = isKey;
            } else {
                restNode = new TrieNode(isKey, new HashMap<>());
            }
            add(restNode, rest);
            n.next.put(first, restNode);
        }

        private List<String> collect(TrieNode n) {
            List<String> lst = new ArrayList<>();
            for (Map.Entry<Character, TrieNode> entry: n.next.entrySet()) {
                colHelp(Character.toString(entry.getKey()), lst, entry.getValue());
            }
            return lst;
        }

        private void colHelp(String s, List<String> lst, TrieNode n) {
            if (n.isKey) {
                lst.add(s);
            }
            for (Map.Entry<Character, TrieNode> entry: n.next.entrySet()) {
                colHelp(s + Character.toString(entry.getKey()), lst, entry.getValue());
            }
        }

        public List<String> containsPrefix(String prefix) {
            return containsPrefix(root, prefix);
        }

        private List<String> containsPrefix(TrieNode n, String prefix) {
            if (prefix == null || prefix.equals("")) {
                return collect(n);
            }
            List<String> lst = new ArrayList<>();
            char first = prefix.charAt(0);
            String rest = prefix.substring(1);
            if (n.next.containsKey(first)) {
                for (String s : containsPrefix(n.next.get(first), rest)) {
                    lst.add(Character.toString(first) + s);
                }
            }
            return lst;
        }
    }

    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point closestPoint = connectedPoints.nearest(lon, lat);
        Node closestNode = nodePointMap.get(closestPoint);
        return closestNode.id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        return trieNames.containsPrefix(cleanString(prefix));
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Node> locations = nameMap.get(cleanString(locationName));
        List<Map<String, Object>> lst = new ArrayList<>();
        HashMap<String, Object> locationMap;
        for (Node location : locations) {
            locationMap = new HashMap<>();
            locationMap.put("lat", location.lat());
            locationMap.put("lon", location.lon());
            locationMap.put("name", location.name());
            locationMap.put("id", location.id());
            lst.add(locationMap);
        }
        return lst;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
