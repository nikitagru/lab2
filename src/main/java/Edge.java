public class Edge {
    private Node first;
    private Node second;
    private boolean reversed = false;

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

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }
}
