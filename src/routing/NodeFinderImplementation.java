package routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class NodeFinderImplementation implements NodeFinder {

    private static final double CELL_SIZE = 0.01;
    private Map<String, List<Node>> grid = new HashMap<>();
    private Coordinate nwCoordinate;
    private Coordinate seCoordinate;

    public NodeFinderImplementation(Graph graph) {
        this.nwCoordinate = graph.getNWCoordinate();
        this.seCoordinate = graph.getSECoordinate();
        initializeGrid(graph);
    }

    private void initializeGrid(Graph graph) {
        for (Node node : graph) {
            addNodeToGrid(node);
        }
    }

    private void addNodeToGrid(Node node) {
        String cellKey = getCellKey(node.getCoordinate());
        grid.computeIfAbsent(cellKey, k -> new ArrayList<>()).add(node);
    }

    private String getCellKey(Coordinate coordinate) {
        int latIndex = (int) ((coordinate.getLatitude() - seCoordinate.getLatitude()) / CELL_SIZE);
        int lonIndex = (int) ((coordinate.getLongitude() - nwCoordinate.getLongitude()) / CELL_SIZE);
        return latIndex + "_" + lonIndex;
    }

    private List<String> getAdjacentCellKeys(Coordinate coordinate) {
        int latIndex = (int) ((coordinate.getLatitude() - seCoordinate.getLatitude()) / CELL_SIZE);
        int lonIndex = (int) ((coordinate.getLongitude() - nwCoordinate.getLongitude()) / CELL_SIZE);
        List<String> cellKeys = new ArrayList<>();
        for (int i=-1; i<=1; i++) {
            for (int j=-1; j<=1; j++) {
                cellKeys.add((latIndex + i) + "_" + (lonIndex + j));
            }
        }
        return cellKeys;
    }


    @Override
    public Node getNodeForCoordinates(Coordinate c) {
        Node candidate = findCandidate(c);
        if (candidate == null) return null;

        double radius = c.getDistance(candidate.getCoordinate());
        CoordinateBox boundingBox = c.computeBoundingBox(radius);

        return findClosestInBoundingBox(c, boundingBox, candidate);  
    }
     private Node findCandidate(Coordinate c) {
        Queue<String> cellsToSearch = new LinkedList<>(getAdjacentCellKeys(c));
        Set<String> visitedCells = new HashSet<>();

        while (!cellsToSearch.isEmpty()) {
            String cellKey = cellsToSearch.poll();
            if (!visitedCells.contains(cellKey)) {
                visitedCells.add(cellKey);
                List<Node> nodes = grid.get(cellKey);
                if (nodes != null && !nodes.isEmpty()) {
                    Node candidate = nodes.get(0);
                    double minDistance = c.getDistance(candidate.getCoordinate());

                    for (Node node : nodes) {
                        double distance = c.getDistance(node.getCoordinate());
                        if (distance < minDistance) {
                            candidate = node;
                            minDistance = distance;
                        }
                    }
                    return candidate;
                }
            }
        }
        return null;
    }

    private Node findClosestInBoundingBox(Coordinate c, CoordinateBox boundingBox, Node candidate) {
        List<String> cellsToSearch = getBoundingBoxCellKeys(boundingBox);
        double minDistance = c.getDistance(candidate.getCoordinate());
        Node closestNode = candidate;

        for (String cellKey : cellsToSearch) {
            List<Node> nodes = grid.get(cellKey);
            if (nodes != null) {
                for (Node node : nodes) {
                    double distance = c.getDistance(node.getCoordinate());
                    if (distance < minDistance) {
                        closestNode = node;
                        minDistance = distance;
                    }
                }
            }
        }

        return closestNode;
    }

    private List<String> getBoundingBoxCellKeys(CoordinateBox boundingBox) {
        List<String> cellKeys = new ArrayList<>();
        int minLatIndex = (int) ((boundingBox.getLowerBound().getLatitude() - seCoordinate.getLatitude()) / CELL_SIZE);
        int maxLatIndex = (int) ((boundingBox.getUpperBound().getLatitude() - seCoordinate.getLatitude()) / CELL_SIZE);
        int minLonIndex = (int) ((boundingBox.getLowerBound().getLongitude() - nwCoordinate.getLongitude()) / CELL_SIZE);
        int maxLonIndex = (int) ((boundingBox.getUpperBound().getLongitude() - nwCoordinate.getLongitude()) / CELL_SIZE);

        for (int i = minLatIndex; i <= maxLatIndex; i++) {
            for (int j = minLonIndex; j <= maxLonIndex; j++) {
                cellKeys.add(i + "_" + j);
            }
        }

        return cellKeys;
    }
}
