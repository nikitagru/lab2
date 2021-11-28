import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    public Graph (File file) throws IOException {
        BufferedReader bfReader = new BufferedReader(new FileReader(file));

        String[] sizes = bfReader.readLine().split(" ");

        int xCount = Integer.parseInt(sizes[0]);
        int yCount = Integer.parseInt(sizes[1]);

        int count = Integer.parseInt(bfReader.readLine());

        String[] numbers;
        String line = bfReader.readLine();

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
            for (int j = 0; j < gap; j = j + 2) {
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
                        getNodeByName(String.valueOf(neighbourNum), Proportion.Y),
                        Integer.parseInt(numbers[Integer.parseInt(numbers[i]) + j]));
                edges.add(edge);
            }

            nodeCounter++;

            if (nodeCounter == 3) {
                System.out.println();
            }
        }
    }

    private Node getNodeByName(String name, Proportion proportion) {
        return nodes.stream().filter(x -> x.getName().equals(name) && x.getProportion().equals(proportion)).findFirst().orElse(null);
    }
}
