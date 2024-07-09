package routing;

import java.util.*;

public class RouteAlgorithmImplementation implements RoutingAlgorithm {

    @Override
    public Route computeRoute(Graph g, List<Node> nodes, TravelType tt) throws NoSuchRouteException {
        if (nodes == null || nodes.size() < 2) {
            throw new IllegalArgumentException("At least two nodes are required to compute a route.");
        }

        List<RouteLeg> routeLegs = new ArrayList<>();
        for (int i = 0; i < nodes.size() - 1; i++) {
            Node startNode = nodes.get(i);
            Node endNode = nodes.get(i + 1);

            if (startNode == null) {
                throw new IllegalArgumentException("Start node at index " + i + " is null.");
            }
            if (endNode == null) {
                throw new IllegalArgumentException("End node at index " + (i + 1) + " is null.");
            }

            RouteLeg leg = computeRouteLeg(g, startNode, endNode, tt);
            routeLegs.add(leg);
        }

        return new ConcreteRoute(routeLegs, tt);

    }

    @Override
    public RouteLeg computeRouteLeg(Graph g, long startId, long endId, TravelType tt) throws NoSuchRouteException {
        Node startNode = g.getNode(startId);
        Node endNode = g.getNode(endId);
        if (startNode == null || endNode == null) {
            throw new NoSuchRouteException("Start or end node not found.");
        }

        return computeRouteLeg(g, startNode, endNode, tt);
    }

    @Override
    public RouteLeg computeRouteLeg(Graph g, Node startNode, Node endNode, TravelType tt) throws NoSuchRouteException {
        if (startNode == null || endNode == null) {
            throw new IllegalArgumentException("Start or end node cannot be null.");
        }
    
        if (startNode.equals(endNode)) {
            return new ConcreteRouteLeg(Collections.singletonList(startNode), 0.0);
        }
    
        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>();
        Map<Node, Node> predecessors = new HashMap<>();
        Map<Node, Double> distances = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        for (Node node : g) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }
        distances.put(startNode, 0.0);
        priorityQueue.add(new NodeDistance(startNode, 0.0));
    
        while (!priorityQueue.isEmpty()) {
            NodeDistance current = priorityQueue.poll();
            Node currentNode = current.getNode();
    
            if (!visited.add(currentNode)) {
                continue;
            }
    
            if (currentNode.equals(endNode)) {
                break;
            }
    
            for (Edge edge : currentNode) {
                Node neighbor = edge.getEnd();
                if (visited.contains(neighbor)) {
                    continue;
                }
    
                boolean canTraverse = tt == TravelType.ANY || edge.allowsTravelType(tt, Direction.FORWARD);
                if (!canTraverse) {
                    continue;
                }
    
                double newDist = distances.get(currentNode) + edge.getLength();
    
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    priorityQueue.add(new NodeDistance(neighbor, newDist));
                    predecessors.put(neighbor, currentNode);
                }
            }
        }
        if (!predecessors.containsKey(endNode)) {
            throw new NoSuchRouteException("No route found from start to end node.");
        }
    
        List<Node> path = new ArrayList<>();
        Node currentNode = endNode;
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = predecessors.get(currentNode);
        }
        Collections.reverse(path);
        double totalDistance = distances.get(endNode);
        return new ConcreteRouteLeg(path, totalDistance);

    }

   @Override
    public boolean isBidirectional() {
        return false;
    } 
}

