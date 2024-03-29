import java.util.ArrayList;
import java.util.List;

public class Node {
    private String name;
    private List<Node> neighbours = new ArrayList<>();
    private Proportion proportion;
    private boolean visited = false;

    public Node(String name, Proportion proportion) {
        this.name = name;
        this.proportion = proportion;
    }

    public Node(String name) {
        this.name = name;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Proportion getProportion() {
        return proportion;
    }

    public void setProportion(Proportion proportion) {
        this.proportion = proportion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbour(Node node) {
        neighbours.add(node);
    }
}
