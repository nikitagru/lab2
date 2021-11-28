import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private int xCount;

    public Graph (File file) throws IOException {
        BufferedReader bfReader = new BufferedReader(new FileReader(file));

        String[] sizes = bfReader.readLine().split(" ");

        xCount = Integer.parseInt(sizes[0]);

        String[] numbers;
        String line = bfReader.readLine();
        line = bfReader.readLine();

        StringBuffer sb = new StringBuffer();

        while (line != null) {
            sb.append(line + " ");
            line = bfReader.readLine();
        }

        String numbersString = sb.toString();
        numbersString = numbersString.replaceAll("\n", " ");
        numbersString = numbersString.replaceAll("32767", "");
        numbers = numbersString.split(" ");

        int nodeCounter = 1;

        for (int i = 0; i < numbers.length; i++) {
            if (nodeCounter == xCount + 1) {
                break;
            }
            int link = Integer.parseInt(numbers[i]);
            int gap = Integer.parseInt(numbers[i + 1]) - link;
            if (gap == 0 && nodeCounter == xCount) {
                gap = 1;
            }
            for (int j = 0; j < gap; j++) {
                if (getNodeByName(String.valueOf(nodeCounter), Proportion.X) == null) {
                    Node node = new Node(String.valueOf(nodeCounter), Proportion.X);
                    nodes.add(node);
                }
                int neighbourNum = Integer.parseInt(numbers[Integer.parseInt(numbers[i]) - 1 + j]);

                if (getNodeByName(String.valueOf(neighbourNum), Proportion.Y) == null && neighbourNum != 0) {
                    Node node = new Node(numbers[Integer.parseInt(numbers[i]) - 1 + j], Proportion.Y);
                    getNodeByName(String.valueOf(nodeCounter), Proportion.X).addNeighbour(node);
                    node.addNeighbour(getNodeByName(String.valueOf(nodeCounter), Proportion.X));
                    nodes.add(node);
                } else {
                    Node existingNodeX = getNodeByName(String.valueOf(nodeCounter), Proportion.X);
                    Node existingNodeY = getNodeByName(String.valueOf(neighbourNum), Proportion.Y);

                    existingNodeX.addNeighbour(existingNodeY);
                    existingNodeY.addNeighbour(existingNodeX);
                }

                Edge edge = new Edge(getNodeByName(String.valueOf(nodeCounter), Proportion.X),
                        getNodeByName(String.valueOf(neighbourNum), Proportion.Y));
                edges.add(edge);
            }

            nodeCounter++;
        }

        addSourceAndDrain();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getxCount() {
        return xCount;
    }

    public void setxCount(int xCount) {
        this.xCount = xCount;
    }

    private Node getNodeByName(String name, Proportion proportion) {
        return nodes.stream().filter(x -> x.getName().equals(name) && x.getProportion().equals(proportion)).findFirst().orElse(null);
    }

    private void addSourceAndDrain() {
        List<Node> xNodes = new ArrayList<>();
        List<Node> yNodes = new ArrayList<>();

        for (Node node : nodes) {
            if (node.getProportion().equals(Proportion.X)) {
                xNodes.add(node);
            } else if (node.getProportion().equals(Proportion.Y)){
                yNodes.add(node);
            }
        }

        Node source = new Node("s");
        Node drain = new Node("t");
        nodes.add(source);
        nodes.add(drain);

        for (int i = 0; i < xNodes.size(); i++) {
            Edge edge = new Edge(source, xNodes.get(i));
            edges.add(edge);
            source.addNeighbour(xNodes.get(i));
        }

        for (int i = 0; i < yNodes.size(); i++) {
            Edge edge = new Edge(yNodes.get(i), drain);
            edges.add(edge);
            drain.addNeighbour(yNodes.get(i));
        }
    }
}
