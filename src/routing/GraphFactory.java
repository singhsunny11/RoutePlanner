package routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GraphFactory implements Graph
{
    private Map<Long, Node> nodes;
    private List<EdgeFactory> edges;

    public GraphFactory(Map<Long, Node> nodes, List<EdgeFactory> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    @Override
    public Node getNode(long id) {
        return nodes.get(id);
    }

    @Override
    public Coordinate getNWCoordinate() {
        double max_latitude = Double.NEGATIVE_INFINITY;
        double max_longitude = Double.NEGATIVE_INFINITY;
        for(Node node : nodes.values()){
            Coordinate coordinate = node.getCoordinate();
          if (coordinate.getLatitude()>max_latitude)
          max_latitude = coordinate.getLatitude();
          if(coordinate.getLongitude()>max_longitude)
          max_longitude = coordinate.getLongitude();
        }
        return new Coordinate(max_latitude, max_longitude);
    }

    @Override
    public Coordinate getSECoordinate() {
       double min_latitude = Double.POSITIVE_INFINITY;
       double min_longitude = Double.POSITIVE_INFINITY;
       for(Node node : nodes.values()){
        Coordinate coordinate = node.getCoordinate();
          if (coordinate.getLatitude()>min_latitude)
          min_latitude = coordinate.getLatitude();
          if(coordinate.getLongitude()>min_longitude)
          min_longitude = coordinate.getLongitude();
       }
       return new Coordinate(min_latitude, min_longitude);
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.values().iterator();
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public int numNodes() {
        return nodes.size();
    }

    @Override
    public int removeIsolatedNodes() {
        int removed = 0;
        Iterator<Map.Entry<Long, Node>> it = nodes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, Node> entry = it.next();
            Node node = entry.getValue();
            if (node.numEdges() == 0) {
                it.remove();
                removed++;
            }
        }
        return removed;
    }

    @Override
    public int removeUntraversableEdges(RoutingAlgorithm ra, TravelType tt) {
        int removed = 0;
        boolean isBidirectional = ra.isBidirectional();
        List<Edge> edgesToRemove = new ArrayList<>();
        for (Edge edge : edges) {
            boolean forwardAllowed = edge.allowsTravelType(tt, Direction.FORWARD);
            boolean backwardAllowed = edge.allowsTravelType(tt, Direction.BACKWARD);
    
            if (isBidirectional) {
                if (!forwardAllowed && !backwardAllowed) {
                    edgesToRemove.add(edge);
                }
            } else {
                if (!forwardAllowed) {
                    edgesToRemove.add(edge);
                }
            }
        }
        for (Edge edge : edgesToRemove) {
            edges.remove(edge);
            removed++;
        }
        for (Node node : nodes.values()) {
            for (int i = 0; i < node.numEdges(); ) {
                Edge edge = node.getEdge(i);
                if (edgesToRemove.contains(edge)) {
                    node.removeEdge(i);
                } else {
                    i++;
                }
            }
        }
        return removed;
    }
    

    @Override
    public boolean isOverlayGraph() {
        return false;
    }

    @Override
    public Node getNodeInUnderlyingGraph(long id) {
        return null;
    }
    
}
