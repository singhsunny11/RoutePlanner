package routing;

public class EdgeFactory implements Edge{

    private Node start;
    private Node end;
    private boolean forwardCar;
    private boolean backwardCar;
    private boolean forwardBike;
    private boolean backwardBike;
    private boolean forwardWalk;
    private boolean backwardWalk;
    private double length;

   public EdgeFactory(Node start, Node end, boolean forwardCar, boolean backwardCar,
                    boolean forwardBike, boolean backwardBike, boolean forwardWalk, boolean backwardWalk) {
        this.start = start;
        this.end = end;
        this.forwardCar = forwardCar;
        this.backwardCar = backwardCar;
        this.forwardBike = forwardBike;
        this.backwardBike = backwardBike;
        this.forwardWalk = forwardWalk;
        this.backwardWalk = backwardWalk;
        this.length = Coordinate.distance(start.getCoordinate().getLatitude(), start.getCoordinate().getLongitude(),
                                          end.getCoordinate().getLatitude(), end.getCoordinate().getLongitude());
    } 

    @Override
    public boolean allowsTravelType(TravelType tt, Direction dir) {
       switch(tt){
        case CAR:
                return dir == Direction.FORWARD ? forwardCar : backwardCar;
        case BIKE:
                return dir == Direction.FORWARD ? forwardBike : backwardBike;
        case FOOT:
                return dir == Direction.FORWARD ? forwardWalk : backwardWalk;
            default:
                return false;
       }
    }

    @Override
    public Node getEnd() {
       return end;
    }

    @Override
    public double getLength() {
       return length;
    }

    @Override
    public Node getStart() {
       return start;
    }
    
}
