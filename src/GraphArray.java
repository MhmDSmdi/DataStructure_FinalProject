public class GraphArray {
    private int[][] adjacencyMatrix;
    private int numberOfVertices = 5;
    private int count = 0;

    public GraphArray(int[][] adjacencyMatrix, int numberOfVertices) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.numberOfVertices = numberOfVertices;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public void printGraph() {
        for (int i = 0 ; i < adjacencyMatrix.length ; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                    System.out.print(" " + adjacencyMatrix[i][j] + " ");
            }
            System.out.println("");
        }
    }

    /*private void DFS(int graph[][], boolean marked[], int cycleLength, int vert, int start) {
        marked[vert] = true;
        if (cycleLength == 0) {
            marked[vert] = false;
            if (graph[vert][start] == 1) {
                count++;
                return;
            } else
                return;
        }
        for (int i = 0; i < numberOfVertices; i++)
            if (!marked[i] && graph[vert][i] == 1)
                DFS(graph, marked, cycleLength - 1, i, start);
        marked[vert] = false;
    }

    public int countCycles(int graph[][], int cycleLength) {
        boolean marked[] = new boolean[numberOfVertices];
        count = 0;
        for (int i = 0; i < numberOfVertices - (cycleLength - 1); i++) {
            DFS(graph, marked, cycleLength - 1, i, i);
            marked[i] = true;
        }
        return count / 2;
    }*/

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

    public static void main(String[] args) {
        InputHandler a = new InputHandler("graph.txt");
        GraphArray b = new GraphArray(a.fetchAdjacencyMatrix(), a.getInputSize());
        System.out.println(b.countCycle(1, 4));
    }


}
