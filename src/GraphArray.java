import java.util.ArrayList;

public class GraphArray {
    private int[][] adjacencyMatrix;
    private ArrayList<Edge> cost;
    private int numberOfVertices;

    public GraphArray(int[][] adjacencyMatrix, int numberOfVertices) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.numberOfVertices = numberOfVertices;
        cost = new ArrayList<>();
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public void printGraph(int[][] graph) {
        for (int i = 1 ; i < graph.length ; i++) {
            for (int j = 1; j < graph.length; j++) {
                    System.out.print(" " + graph[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private boolean DFS() {
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

    private void DFS(int v, Boolean[] visited) {
        visited[v] = true;
        for (int i = 1 ; i < numberOfVertices ; i++) {
            if (adjacencyMatrix[v][i] == 1 && !visited[i])
                DFS(i, visited);
        }
    }

    public int countCycle(int v1, int v2) {
        int count = 0;
        if (adjacencyMatrix[v1][v2] == 1) {
            for (int i = 1 ; i < numberOfVertices ; i++) {
                if (adjacencyMatrix[i][v1] == 1 && adjacencyMatrix[i][v2] == 1) {
                    count++;
                }
            }
            return count;
        }
        else
            return 0;
    }

    public int degreeOfVertex(int v) {
        int count = 0;
        for (int i = 1 ; i < numberOfVertices ; i++){
            if (adjacencyMatrix[v][i] == 1)
                count++;
        }
        return count;
    }

    private int calculateCost(int cycles, int degreeV1, int degreeV2) {
        int temp;
        try {
            temp = ((cycles + 1) / (Math.min(degreeV1 - 1, degreeV2 - 1)));
        }catch (ArithmeticException e) {
            temp = Integer.MAX_VALUE;
        }

        return temp;
    }

    private void fetchCostOfEdges() {
        cost = new ArrayList<>();
        for (int i = 1 ; i < numberOfVertices ; i++)
            for (int j = 0 + i ; j < numberOfVertices ; j++)
                if (adjacencyMatrix[i][j] == 1){
                    int cost = calculateCost(countCycle(i, j), degreeOfVertex(i), degreeOfVertex(j));
                    //System.out.println("i: " + i + " and j: " + j + " has Cost : " + cost);
                    Edge temp = new Edge(i, j, cost);
                    this.cost.add(temp);
                }
    }

    public void deleteEdge(int v1, int v2) {
        adjacencyMatrix[v1][v2] = adjacencyMatrix[v2][v1] = 0;
        System.out.println(v1 + " and " + v2 + "  Was Removed");
    }

    public void identifyingCommunities(int sortType) {
        Sort sort = new Sort();
        while (DFS()) {
            fetchCostOfEdges();
            sort.doSort(cost, sortType, 0);
            /*for (Edge a : cost)
                System.out.print(a.getCost() +"|(" +a.getVertex1() + " : " + a.getVertex2() + ")" + " - ");*/
            //System.out.println();
            deleteEdge(cost.get(0).getVertex1(), cost.get(0).getVertex2());
        }
        System.out.println("ERROR");
    }

    public void identifyingCommunities(int sortType, int n) {
        Sort sort = new Sort();
        while (DFS()) {
            fetchCostOfEdges();
            sort.doSort(cost, sortType, n);
            /*for (Edge a : cost)
                System.out.print(a.getCost() +"|(" +a.getVertex1() + " : " + a.getVertex2() + ")" + " - ");*/
            //System.out.println();
            deleteEdge(cost.get(0).getVertex1(), cost.get(0).getVertex2());
        }
        System.out.println("ERROR");
    }

    public static void main(String[] args) {
        InputHandler a = new InputHandler("test1.txt");
        GraphArray b = new GraphArray(a.fetchAdjacencyMatrix(), a.getInputSize());
       // b.printGraph(b.adjacencyMatrix);
        System.out.println();
        System.out.println();
        b.identifyingCommunities(5, 23);
    }
}
