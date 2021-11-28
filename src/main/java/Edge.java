public class Edge {
    private Node[] nodes = new Node[2];

    public Edge(Node first, Node second) {
        nodes[0] = first;
        nodes[1] = second;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }
}
