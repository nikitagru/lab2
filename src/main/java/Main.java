import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("in.txt");
        Graph graph = new Graph(file);
        System.out.println();
    }

    private static void fordFalkerson(Graph graph) {
        List<Node> combination = new ArrayList<>();

        for (int i = 0; i < graph.getxCount(); i++) {
            combination.addAll(bfs(graph));
        }
    }

    private static List<Node> createCombonation(Graph graph, Deque<Node> nodeDeque) {
        List<Node> combination = new ArrayList<>();

        Node drain = nodeDeque.pollLast();
        Node drainPrev = nodeDeque.stream().filter(x ->
                graph.getEdges().stream().anyMatch(y -> y.getSecond().equals(drain) && y.getFirst().equals(x))
        ).findFirst().get();

        Node xNode = nodeDeque.stream().filter(x ->
                graph.getEdges().stream().anyMatch(y -> y.getSecond().equals(drain) && y.getFirst().equals(x))
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
            Node node = nodeDeque.getFirst();

            for (Node neighbour : node.getNeighbours()) {
                if (!neighbour.isVisited()) {
                    nodeDeque.addLast(neighbour);
                    neighbour.setVisited(true);

                    if (neighbour.getName().equals("t")) {
                        return createCombonation(graph, nodeDeque);
                    }
                }
            }
        }
    }
}
