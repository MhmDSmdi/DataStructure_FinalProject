import java.util.ArrayList;

public class GraphList {
    private GNode[] adjacencyList;
    private int numberOfVertices;
    private ArrayList<Edge> cost;

    public GraphList(GNode[] adjacencyList, int numberOfVertices) {
        this.adjacencyList = adjacencyList;
        this.numberOfVertices = numberOfVertices;
        cost = new ArrayList<>();
    }

    public ArrayList<Edge> getCost() {
        return cost;
    }

    private boolean isConnected(int v1, int v2) {
        GNode root = adjacencyList[v1];
        while(root != null){
            if (root.getVertexNumber() == v2)
                return true;
            else
                root = root.getLink();
        }
        return false;
    }

    public int countCycle(int v1, int v2) {
        int count = 0;
        if (isConnected(v1, v2)) {
            GNode root = adjacencyList[v1];
            while (root != null) {
                if (isConnected(v2, root.getVertexNumber()))
                    count++;
                root = root.getLink();
            }
            return count;
        }
        else
            return 0;
    }

    public int degreeOfVertex(int v) {
        int count = 0;
        GNode root = adjacencyList[v];
        while (root != null) {
            count++;
            root = root.getLink();
        }
        return count;
    }

    public boolean isExist(int v1, int v2) {
        for (Edge a : cost) {
            if ((v1 == a.getVertex1() && v2 == a.getVertex2()) || (v1 == a.getVertex2() && v2 == a.getVertex1()))
                return true;
        }
        return false;
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
        GNode root;
        for (int i = 0 ; i < numberOfVertices ; i++){
            root = adjacencyList[i];
            while (root != null) {
                if (!isExist(i, root.getVertexNumber())) {
                    int cost = calculateCost(countCycle(i, root.getVertexNumber()), degreeOfVertex(i), degreeOfVertex(root.getVertexNumber()));
                    Edge temp = new Edge(i, root.getVertexNumber(), cost);
                    this.cost.add(temp);
                }
                root = root.getLink();
            }
        }
    }

    public static void main(String[] args) {
        InputHandler a = new InputHandler("graph.txt");
        GraphList b = new GraphList(a.fetchAdjacencyList(), a.getInputSize());
        b.fetchCostOfEdges();
        for (Edge f : b.cost)
            System.out.println(f.getCost());
    }
}
