import java.util.ArrayList;

public class GraphArray extends Graph {
    private int[][] adjacencyMatrix;

    public GraphArray(int[][] adjacencyMatrix, int numberOfVertices) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.numberOfVertices = numberOfVertices;
        edges = new ArrayList<>();
        fillEdges();
        numberOfEdges = edges.size();
        averageDegreeOfVertex = numberOfEdges / this.numberOfVertices ;
    }

    @Override
    protected void fillEdges() {
        for (int i = 1 ; i < numberOfVertices ; i++)
            for (int j = 0 + i ; j < numberOfVertices ; j++)
                if (adjacencyMatrix[i][j] == 1){
                    Edge temp = new Edge(i, j, 0);
                    this.edges.add(temp);
                }
    }

    @Override
    protected boolean DFS() {
        Boolean[] visited = new Boolean[numberOfVertices];
        for (int i = 1 ; i < numberOfVertices ; i++)
            visited[i] = false;
        DFS(1, visited);

        for (int i = 1 ; i < numberOfVertices ; i++) {
            if (!visited[i])
                return false;
        }
        return true;
    }

    @Override
    protected void DFS(int v, Boolean[] visited) {
        visited[v] = true;
        for (int i = 1 ; i < numberOfVertices ; i++) {
            if (adjacencyMatrix[v][i] == 1 && !visited[i])
                DFS(i, visited);
        }
    }

    @Override
    protected int countCycle(int v1, int v2) {
        int count = 0;
        if (adjacencyMatrix[v1][v2] == 1) {
            for (int i = 1 ; i < numberOfVertices ; i++) {
                if (adjacencyMatrix[i][v1] == 1 && adjacencyMatrix[i][v2] == 1)
                    count++;
            }
            return count;
        }
        else
            return 0;
    }

    @Override
    protected int degreeOfVertex(int v) {
        int count = 0;
        for (int i = 1 ; i < numberOfVertices ; i++){
            if (adjacencyMatrix[v][i] == 1)
                count++;
        }
        return count;
    }

    @Override
    protected void fetchCostOfEdges() {
        for (Edge a : edges) {
            int cost = calculateCost(countCycle(a.getVertex1(), a.getVertex2()), degreeOfVertex(a.getVertex1()), degreeOfVertex(a.getVertex2()));
            a.setCost(cost);
        }
    }

    @Override
    protected void deleteEdge(int v1, int v2) {
        adjacencyMatrix[v1][v2] = adjacencyMatrix[v2][v1] = 0;
        edges.remove(0);
        System.out.println(v1 + " and " + v2 + "  Was Removed");
    }

    public static void main(String[] args) {
        InputHandler a = new InputHandler("graph.txt");
        GraphArray b = new GraphArray(a.fetchAdjacencyMatrix(), a.getInputSize());
        System.out.println();
        System.out.println();
        b.identifyingCommunities(1);
    }
}
