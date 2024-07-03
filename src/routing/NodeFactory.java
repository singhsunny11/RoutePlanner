package routing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NodeFactory implements Node{
    private long id;
    private Coordinate coordinate;
    private List<Edge> edges;

    public NodeFactory(long id, Coordinate coordinate){
        this.id = id;
        this.coordinate = coordinate;
        this.edges= new ArrayList<>();
    }

    @Override
    public Coordinate getCoordinate() {
       return coordinate;
    }

    @Override
    public Edge getEdge(int idx) {
        if (idx < 0 || idx >= edges.size()) {
            throw new IndexOutOfBoundsException("Invalid edge index: " + idx);
        }
        return edges.get(idx);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Iterator<Edge> iterator() {
       return edges.iterator();
    }

    @Override
    public int numEdges() {
       return edges.size();
    }

    @Override
    public void addEdge(Edge e) {
        edges.add(e);
    }

    @Override
    public void removeEdge(int i) {
       edges.remove(i);
    }
    
}
