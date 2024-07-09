package routing;

import java.util.Iterator;
import java.util.List;

public class ConcreteRouteLeg extends RouteLegBase {

    private List<Node> nodes;
    private double distance;



    public ConcreteRouteLeg(List<Node> nodes, double distance) {
        this.nodes = nodes;
        this.distance = distance;
    }

    @Override
    public double getDistance() {
        return distance;
    }

    @Override
    public Node getEndNode() {
        return nodes.get(nodes.size() - 1);
    }

    @Override
    public Node getStartNode() {
        return nodes.get(0);
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }

    @Override
    public int size() {
        return nodes.size();
    }
    
    
}
