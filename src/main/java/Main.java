import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("in.txt");
        Graph graph = new Graph(file);
        List<Node> resultCombination = fordFalkerson(graph);
        System.out.println();
    }

    private static List<Node> fordFalkerson(Graph graph) {
        List<Node> combination = new ArrayList<>();

        Pair result = bfs(graph);
        boolean isFindRoute = (boolean) result.getFirst();

        while (isFindRoute) {
            updateGraph(graph, (HashMap<Node, Node>) result.getSecond());
            result = bfs(graph);
            isFindRoute = (boolean) result.getFirst();
        }

        graph.deleteSourceAndDrainEdges(true);

        Pair finalRoute = modifiedBFS(graph);

        List<Edge> lastNodes = findLastEdges(graph, (HashMap<Node, Node>) finalRoute.getSecond());

        if ((boolean) finalRoute.getFirst()) {
            graph.deleteSourceAndDrainEdges(false);
        }

        printResult(graph, lastNodes);

        return combination;
    }

    private static List<Edge> findLastEdges(Graph graph, HashMap<Node, Node> previous) {
        Node drain = graph.getNodes().stream().filter(x -> x.getName().equals("t")).findFirst().get();
        List<Edge> result = new ArrayList<>();
        Node prev = drain;

        while (true) {
            Node node = previous.get(prev);

            Node finalPrev = prev;

            if (node.getProportion() != null && !node.getProportion().equals(Proportion.Y)) {
                Edge edge = graph.getEdges().stream().filter(x -> x.getFirst().equals(node) && x.getSecond().equals(finalPrev)).findFirst().get();

                result.add(edge);
            } else if (finalPrev.getProportion() != null && node.getProportion() != null) {
                graph.getEdges().stream().filter(x -> x.getFirst().equals(finalPrev) && x.getSecond().equals(node)).findFirst().get().setReversed(false);
            }

            if (node.getName().equals("s")) {
                break;
            }

            prev = node;
        }

        return result;
    }

    private static void updateGraph(Graph graph, HashMap<Node, Node> previous) {
        Node drain = graph.getNodes().stream().filter(x -> x.getName().equals("t")).findFirst().get();
        Node nodeToDrain = previous.get(drain);
        Node nodeXToY = previous.get(nodeToDrain);
        Node fromSToX = previous.get(nodeXToY);

        List<Edge> edges = graph.getEdges().stream().filter(x -> (x.getFirst().equals(fromSToX) && x.getSecond().equals(nodeXToY))
                                                                || (x.getFirst().equals(nodeXToY) && x.getSecond().equals(nodeToDrain))
                                                                || (x.getFirst().equals(nodeToDrain) && x.getSecond().equals(drain))).collect(Collectors.toList());

        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).setReversed(true);
        }
    }

    private static Pair bfs(Graph graph) {
        Deque<Node> nodeDeque = new ArrayDeque<>();
        Node source = graph.getNodes().stream().filter(x -> x.getName().equals("s")).findFirst().get();

        HashMap<Node, Node> previous = new HashMap<>();

        nodeDeque.addLast(source);
        source.setVisited(true);

        while (!nodeDeque.isEmpty()) {
            Node node = nodeDeque.pollFirst();

            List<Edge> edgesFromNode = graph.getEdges().stream().filter(x -> x.getFirst().equals(node) && !x.isReversed())
                    .collect(Collectors.toList());

            for (Edge edgeToNeighbour : edgesFromNode) {
                Node neighbour = edgeToNeighbour.getSecond();
                if (!previous.containsKey(neighbour)) {
                    previous.put(neighbour, node);
                }
//                if (!neighbour.isVisited()) {
                    nodeDeque.addLast(neighbour);
                    neighbour.setVisited(true);

                    if (neighbour.getName().equals("t")) {
                        return new Pair(true, previous);
                    }
//                }
            }
        }
        return new Pair(false, null);
    }

    private static Pair modifiedBFS(Graph graph) {
        Deque<Node> nodeDeque = new ArrayDeque<>();
        Node source = graph.getNodes().stream().filter(x -> x.getName().equals("s")).findFirst().get();

        HashMap<Node, Node> previous = new HashMap<>();

        nodeDeque.addLast(source);
        source.setVisited(true);

        while (!nodeDeque.isEmpty()) {
            Node node = nodeDeque.pollFirst();

            List<Edge> edgesFromNode = graph.getEdges().stream().filter(x -> (x.getFirst().equals(node) && !x.isReversed())
                    || (x.getSecond().equals(node) && x.isReversed()))
                    .collect(Collectors.toList());

            for (Edge edgeToNeighbour : edgesFromNode) {
                Node neighbour;
                if (edgeToNeighbour.isReversed()) {
                    neighbour = edgeToNeighbour.getFirst();
                } else {
                    neighbour = edgeToNeighbour.getSecond();
                }
                if (!previous.containsKey(neighbour)) {
                    previous.put(neighbour, node);
                }
//                if (!neighbour.isVisited()) {
                    nodeDeque.addLast(neighbour);
                    neighbour.setVisited(true);

                    if (neighbour.getName().equals("t")) {
                        return new Pair(true, previous);
                    }
//                }
            }
        }
        return new Pair(false, null);
    }

    private static void printResult(Graph graph, List<Edge> complementaryEdges) {
        List<Edge> finalEdges = graph.getEdges().stream().filter(x -> x.getFirst().getProportion().equals(Proportion.X) && x.isReversed())
                .collect(Collectors.toList());

        

        finalEdges.addAll(complementaryEdges);
        System.out.println();
    }
}
