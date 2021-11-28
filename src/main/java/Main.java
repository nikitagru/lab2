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

        for (int i = 0; i < graph.getxCount(); i++) {
            try {
                combination.addAll(Objects.requireNonNull(bfs(graph)));
            } catch (NullPointerException ignored) {

            }
        }

        return combination;
    }

    private static List<Node> createCombonation(Graph graph, Deque<Node> nodeDeque) {
        List<Node> combination = new ArrayList<>();

        Node drain = nodeDeque.pollLast();
        Node drainPrev = nodeDeque.stream().filter(x ->
                graph.getEdges().stream().anyMatch(y -> y.getSecond().equals(drain) && y.getFirst().equals(x))
        ).findFirst().get();

        Node xNode = graph.getNodes().stream().filter(x ->
                graph.getEdges().stream().anyMatch(y -> y.getSecond().equals(drainPrev) && y.getFirst().equals(x))
        ).findFirst().get();

        combination.add(xNode);
        combination.add(drainPrev);

        updateGraph(graph, new Node[] {xNode, drainPrev});

        return combination;
    }

    private static void updateGraph(Graph graph, Node[] nodes) {
        Edge edgeFromSource = graph.getEdges().stream().filter(x -> x.getFirst().getName().equals("s")
                && x.getSecond().equals(nodes[0])).findFirst().get();
        Edge edgeToDrain = graph.getEdges().stream().filter(x -> x.getFirst().equals(nodes[1])
                && x.getSecond().getName().equals("t")).findFirst().get();

        graph.getEdges().remove(edgeFromSource);
        graph.getEdges().remove(edgeToDrain);
    }

    private static List<Node> bfs(Graph graph) {
        Deque<Node> nodeDeque = new ArrayDeque<>();
        Node source = graph.getNodes().stream().filter(x -> x.getName().equals("s")).findFirst().get();

        nodeDeque.addLast(source);
        source.setVisited(true);

        while (!nodeDeque.isEmpty()) {
            Node node = nodeDeque.pollFirst();

            List<Edge> edgesFromNode = graph.getEdges().stream().filter(x -> x.getFirst().equals(node))
                    .collect(Collectors.toList());

            for (Edge edgeToNeighbour : edgesFromNode) {
                Node neighbour = edgeToNeighbour.getSecond();
                if (!neighbour.isVisited()) {
                    nodeDeque.addLast(neighbour);
                    neighbour.setVisited(true);

                    if (neighbour.getName().equals("t")) {
                        return createCombonation(graph, nodeDeque);
                    }
                }
            }
        }
        return null;
    }
}
