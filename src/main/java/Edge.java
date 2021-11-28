public class Edge {
    private Node[] nodes = new Node[2];
    private int weight;

    public Edge(Node first, Node second, int weight) {
        nodes[0] = first;
        nodes[1] = second;
        this.weight = weight;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }
}
