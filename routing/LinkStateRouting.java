import java.util.*;

public class LinkStateRouting {

    // Utility function to add undirected edges
    static void addLink(List<List<int[]>> graph, int from, int to, int cost) {
        graph.get(from).add(new int[]{to, cost});
        graph.get(to).add(new int[]{from, cost});
    }

    // Dijkstra’s algorithm using a priority queue
    static void computeShortestPaths(List<List<int[]>> graph, int source) {
        int n = graph.size();
        int[] distance = new int[n];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentNode = current[0];
            int currentDist = current[1];

            if (currentDist > distance[currentNode]) continue;

            for (int[] neighbor : graph.get(currentNode)) {
                int nextNode = neighbor[0];
                int edgeWeight = neighbor[1];
                int newDist = currentDist + edgeWeight;

                if (newDist < distance[nextNode]) {
                    distance[nextNode] = newDist;
                    pq.offer(new int[]{nextNode, newDist});
                }
            }
        }

        // Print routing table for this router
        System.out.println("Routing table for router " + (char) ('A' + source));
        for (int i = 0; i < n; i++) {
            System.out.println("To " + (char) ('A' + i) + " = " + distance[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int V = 4; // Number of routers
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) graph.add(new ArrayList<>());

        // Define network topology
        addLink(graph, 0, 1, 2); // A-B
        addLink(graph, 0, 2, 1); // A-C
        addLink(graph, 0, 3, 4); // A-D
        addLink(graph, 1, 3, 3); // B-D
        addLink(graph, 2, 3, 1); // C-D

        // Each router computes its own routing table
        for (int src = 0; src < V; src++) {
            computeShortestPaths(graph, src);
        }
    }
}
