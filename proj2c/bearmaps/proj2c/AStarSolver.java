package bearmaps.proj2c;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.introcs.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private final LinkedList<Vertex> solution;
    private final AStarGraph<Vertex> graph;
    private final Vertex goal;
    private final ExtrinsicMinPQ<Vertex> fringe;
    private final HashMap<Vertex, Double> distTo;
    private final HashMap<Vertex, Vertex> edgeTo;
    private double elapsedTime;
    private int numStates;
    private static final double infinity = Double.POSITIVE_INFINITY;
    private final double maxTime;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        graph = input;
        goal = end;
        maxTime = timeout;
        fringe = new ArrayHeapMinPQ<>();
        fringe.add(start, graph.estimatedDistanceToGoal(start, goal));
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        distTo.put(start, 0.0);
        Vertex p;
        elapsedTime = sw.elapsedTime();
        numStates = 0;
        solution = new LinkedList<>();
        while (fringe.size() != 0 && fringe.getSmallest() != end && elapsedTime < maxTime) {
            p = fringe.removeSmallest();
            numStates += 1;
            for (WeightedEdge<Vertex> edge : graph.neighbors(p)) {
                if (!distTo.containsKey(edge.to())) {
                    distTo.put(edge.to(), infinity);
                }
                relax(edge);
            }
            elapsedTime = sw.elapsedTime();
        }
        if (fringe.size() != 0 && elapsedTime < maxTime) {
            solution.addFirst(goal);
            while (solution.getFirst() != start) {
                solution.addFirst(edgeTo.get(solution.getFirst()));
            }
        }
    }

    private void relax(WeightedEdge<Vertex> edge) {
        Vertex p = edge.from();
        Vertex q = edge.to();
        double w = edge.weight();
        if (distTo.get(p) + w < distTo.get(q)) {
            distTo.replace(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (fringe.contains(q)) {
                fringe.changePriority(q, distTo.get(q) + graph.estimatedDistanceToGoal(q, goal));
            } else {
                fringe.add(q, distTo.get(q) + graph.estimatedDistanceToGoal(q, goal));
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        if (elapsedTime >= maxTime) {
            return SolverOutcome.TIMEOUT;
        } else if (fringe.size() == 0) {
            return SolverOutcome.UNSOLVABLE;
        } else {
            return SolverOutcome.SOLVED;
        }
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return distTo.get(fringe.getSmallest());
    }

    @Override
    public int numStatesExplored() {
        return numStates;
    }

    @Override
    public double explorationTime() {
        return elapsedTime;
    }
}
