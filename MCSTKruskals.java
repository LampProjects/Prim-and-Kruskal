// Java program for Kruskal's algorithm

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MCSTKruskals
{

    public MCSTKruskals(int[][] graph, String[] header)
    {
        kruskals(graph, header);

    }
    // Defines edge structure
    static class Edge
    {
        int src, dest, weight;

        public Edge(int src, int dest, int weight)
        {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    // Defines subset element structure
    static class Subset
    {
        int parent, rank;

        public Subset(int parent, int rank)
        {
            this.parent = parent;
            this.rank = rank;
        }
    }

    // Function to find the MST
    private static void kruskals(int[][] graph, String[] vertexHeaders)
    {
        int V = graph.length;
        List<Edge> edges = new ArrayList<>();

        // Populate the list of edges from the adjacency matrix
        for (int src = 0; src < V; src++)
        {
            for (int dest = src + 1; dest < V; dest++)
            {
                if (graph[src][dest] != 0)
                {
                    edges.add(new Edge(src, dest, graph[src][dest]));
                }
            }
        }

        // Sort the edges in non-decreasing order
        edges.sort(Comparator.comparingInt(e -> e.weight));

        int noOfEdges = 0;
        int j = 0;
        Edge[] results = new Edge[V - 1];
        Subset[] subsets = new Subset[V];

        // Create V subsets with single elements
        for (int i = 0; i < V; i++)
        {
            subsets[i] = new Subset(i, 0);
        }

        while (noOfEdges < V - 1)
        {
            Edge nextEdge = edges.get(j);
            int x = findRoot(subsets, nextEdge.src);
            int y = findRoot(subsets, nextEdge.dest);

            if (x != y)
            {
                results[noOfEdges] = nextEdge;
                union(subsets, x, y);
                noOfEdges++;
            }

            j++;
        }

        // Print the MST
        System.out.println("Following are the edges of the constructed MST:");
        int minCost = 0;
        for (Edge edge : results)
        {
            System.out.println(
                    vertexHeaders[edge.src] + " -- " + vertexHeaders[edge.dest] + " == " + edge.weight
            );
            minCost += edge.weight;
        }
        System.out.println("Total cost of MST: " + minCost);
    }

    // Function to unite two disjoint sets
    private static void union(Subset[] subsets, int x, int y)
    {
        int rootX = findRoot(subsets, x);
        int rootY = findRoot(subsets, y);

        if (subsets[rootX].rank < subsets[rootY].rank)
        {
            subsets[rootX].parent = rootY;
        } else if (subsets[rootX].rank > subsets[rootY].rank)
        {
            subsets[rootY].parent = rootX;
        } else {
            subsets[rootY].parent = rootX;
            subsets[rootX].rank++;
        }
    }

    // Function to find parent of a set
    private static int findRoot(Subset[] subsets, int i)
    {
        if (subsets[i].parent == i)
            return subsets[i].parent;

        subsets[i].parent
                = findRoot(subsets, subsets[i].parent);
        return subsets[i].parent;
    }
}

