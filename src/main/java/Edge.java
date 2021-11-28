public class Edge {
    private Node first;
    private Node second;

    public Edge(Node first, Node second) {
        this.first = first;
        this.second = second;
    }

    public Node getFirst() {
        return first;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    public Node getSecond() {
        return second;
    }

    public void setSecond(Node second) {
        this.second = second;
    }
}
