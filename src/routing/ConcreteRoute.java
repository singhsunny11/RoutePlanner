package routing;

import java.util.Iterator;
import java.util.List;

public class ConcreteRoute extends RouteBase{

    private List<RouteLeg> legs;
    private TravelType travelType;

    public ConcreteRoute(List<RouteLeg> legs, TravelType travelType) {
        this.legs = legs;
        this.travelType = travelType;
    }

    @Override
    public double distance() {
        return legs.stream().mapToDouble(RouteLeg::getDistance).sum();
    }

    @Override
    public Node getEndNode() {
        return legs.get(legs.size() - 1).getEndNode();
    }

    @Override
    public Node getStartNode() {
        return legs.get(0).getStartNode();
    }

    @Override
    public TravelType getTravelType() {
        return travelType;
    }

    @Override
    public Iterator<RouteLeg> iterator() {
        return legs.iterator();
    }

    @Override
    public int size() {
        return legs.size();
    }
    
}
