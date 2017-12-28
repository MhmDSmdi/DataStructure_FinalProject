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
        for (int i = 0 ; i < graph.length ; i++) {
            for (int j = 0; j < graph.length; j++) {
                    System.out.print(" " + graph[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public int countCycle(int v1, int v2) {
        int count = 0;
        if (adjacencyMatrix[v1][v2] == 1) {
            for (int i = 0 ; i < numberOfVertices ; i++) {
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
        for (int i = 0 ; i < numberOfVertices ; i++){
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

    public void fetchCostOfEdges() {
        for (int i = 0 ; i < numberOfVertices ; i++)
            for (int j = 0 + i ; j < numberOfVertices ; j++)
                if (adjacencyMatrix[i][j] == 1){
                    int cost = calculateCost(countCycle(i, j), degreeOfVertex(i), degreeOfVertex(j));
                    Edge temp = new Edge(i, j, cost);
                    this.cost.add(temp);
                }
    }


    public static void main(String[] args) {
        InputHandler a = new InputHandler("graph.txt");
        GraphArray b = new GraphArray(a.fetchAdjacencyMatrix(), a.getInputSize());
        b.fetchCostOfEdges();
        System.out.println();
        for (Edge r : b.cost)
            System.out.println(r.getCost());

    }


}
