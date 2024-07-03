package routing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Factory {

	/**
	 * Create a graph from the description in a .nae file.
	 *
	 * @param fileName
	 *            A path to an NAE file.
	 *
	 * @return The graph as described in the .nae file.
	 *
	 * @throws IOException
	 *             If an Input/Output error occurs.
	 */
	public static Graph createGraphFromMap(String fileName) throws IOException {
		Map<Long, Node> nodes = new HashMap<>();
        List<EdgeFactory> edges = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
			String line;
			while((line=br.readLine())!=null){
				String[] parts = line.split(" ");
				if(parts[0].equals("N")){
					long id = Long.parseLong(parts[1]);
					double latitude = Double.parseDouble(parts[2]);
					double longitude = Double.parseDouble(parts[3]);
					nodes.put(id, new NodeFactory(id, new Coordinate(latitude, longitude)));
				}
				else if (parts[0].equals("E")){
					long source_id = Long.parseLong(parts[1]);
					long destination_id = Long.parseLong(parts[2]);
					boolean forward_car = parts[3].equals("1");
					boolean backward_car = parts[4].equals("1");
					boolean forward_bike = parts[5].equals("1");
					boolean backward_bike = parts[6].equals("1"); 
					boolean forward_walk = parts[6].equals("1");
					boolean backward_walk = parts[7].equals("1");
					//double  length = 1.0;

					Node source = nodes.get(source_id);
                    Node dest = nodes.get(destination_id);
					if (source != null && dest != null) {
						EdgeFactory toEdge = new EdgeFactory(source, dest, forward_car, backward_car, forward_bike, backward_bike, forward_walk, backward_walk);
                        EdgeFactory fromEdge = new EdgeFactory(dest, source, backward_car, forward_car, backward_bike, forward_bike, backward_walk, forward_walk);
                        source.addEdge(toEdge);
                        dest.addEdge(fromEdge);
                        edges.add(toEdge);
                        edges.add(fromEdge);
                    }
				}
			}
		}catch (IOException e) {
				throw new IOException("Error reading file", e);
			}


		// TODO: Implement me.
		return new GraphFactory(nodes,edges);
	}

	/**
	 * Return a node finder algorithm for the graph g. The graph argument allows
	 * the node finder to build internal data structures.
	 *
	 * @param g
	 *            The graph the nodes are looked up in.
	 * @return A node finder algorithm for that graph.
	 */
	public static NodeFinder createNodeFinder(Graph g) {
		// TODO: Implement me.
		return null;
	}

	/**
	 * == BONUS ==
	 *
	 * Compute the overlay graph (or junction graph).
	 *
	 * Note: This is part of a bonus exercise, not of the regular project.
	 *
	 * @return The overlay graph for the given graph g.
	 */
	public static Graph createOverlayGraph(Graph g) {
		// TODO: Implement me.
		return null;
	}

	/**
	 * Return a routing algorithm for the graph g. This allows to inspect the
	 * graph and choose from different routing strategies if appropriate.
	 *
	 * @param g
	 *            The graph the routing is performed on.
	 * @return A routing algorithm suitable for that graph.
	 */
	public static RoutingAlgorithm createRoutingAlgorithm(Graph g) {
		// TODO: Implement me.
		return null;
	}

}
