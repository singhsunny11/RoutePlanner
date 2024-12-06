# Route Planner
## Overview
This project implements a Route Planner in Java, designed to calculate the shortest route between specified coordinates. The planner supports various modes of transportation and can also plan routes with intermediate coordinates that the route must visit. The system interacts with a front-end interface, which is used for visualizing the route and debugging purposes.

## Key Features
- Shortest Route Calculation: Computes the shortest path between two or more specified coordinates.
- Intermediate Stops: Supports routing through intermediate coordinates.
- OpenStreetMap Integration: Uses OpenStreetMap (OSM) node identifiers to represent the nodes in the navigation graph.

## Requirements
- Java: The route planner is implemented in Java.
- OpenStreetMap (OSM): The project utilizes OSM identifiers for the navigation graph.
- NAE File Format: The navigation graph is encoded as a NAE file, which you must download and extract into your project’s root directory.
- Front-End Interface: The project includes a front-end component for route visualization and debugging (though minor bugs might still exist in the prototype).

