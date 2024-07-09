package routing;

public class NodeDistance implements Comparable<NodeDistance> {
    private Node node;
    private double distance;

    public NodeDistance(Node node, double distance) {
        this.node = node;
        this.distance = distance;
    }

    public Node getNode() {
        return node;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public int compareTo(NodeDistance other) {
        return Double.compare(this.distance, other.distance);
    }
}
