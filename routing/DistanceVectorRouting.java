import java.util.*;

class Router {
    String name;

    // Direct neighbor and link cost
    Map<String, Integer> neighbors = new HashMap<>();

    // Routing table: destination -> total cost
    Map<String, Integer> routingTable = new HashMap<>();

    public Router(String name) {
        this.name = name;
        routingTable.put(name, 0); // cost to itself = 0
    }

    void addNeighbor(String neighborName, int cost) {
        neighbors.put(neighborName, cost);
        routingTable.put(neighborName, cost);
    }

    // Distance Vector update (one iteration)
    void update(Map<String, Router> network) {
        for (String neighborName : neighbors.keySet()) {
            Router neighborRouter = network.get(neighborName);

            for (var entry : neighborRouter.routingTable.entrySet()) {
                String destination = entry.getKey();
                int neighborToDestCost = entry.getValue();
                int newCost = neighbors.get(neighborName) + neighborToDestCost;

                // Update if destination not known or we found a cheaper path
                if (!routingTable.containsKey(destination) || newCost < routingTable.get(destination)) {
                    routingTable.put(destination, newCost);
                }
            }
        }
    }

    void printRoutingTable() {
        System.out.println("Routing table for " + name + ": " + routingTable);
    }
}

public class DistanceVectorRouting{
    public static void main(String[] args) {
        Router A = new Router("A");
        Router B = new Router("B");
        Router C = new Router("C");

        // Define network connections
        A.addNeighbor("B", 2);
        B.addNeighbor("A", 2);
        B.addNeighbor("C", 3);
        C.addNeighbor("B", 3);

        Map<String, Router> network = Map.of(
                "A", A,
                "B", B,
                "C", C
        );

        // Simulate multiple rounds of updates until convergence
        for (int round = 1; round <= 5; round++) {
            for (Router router : network.values()) {
                router.update(network);
            }
        }

        // Display final routing tables
        A.printRoutingTable();
        B.printRoutingTable();
        C.printRoutingTable();
    }
}
