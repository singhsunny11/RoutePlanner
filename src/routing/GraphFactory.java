package routing;

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
        int removedCount = 0;
        Iterator<Node> nodeIterator = nodes.values().iterator();
        while (nodeIterator.hasNext()) {
            Node node = nodeIterator.next();
            if (node.numEdges() == 0) {
                nodeIterator.remove();
                removedCount++;
            }
        }
        return removedCount;
    }

    @Override
    public int removeUntraversableEdges(RoutingAlgorithm ra, TravelType tt) {
      return 0;
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
