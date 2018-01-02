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
        GNode root = adjacencyList[v];
        while(root != null) {
            if(!visited[root.getVertexNumber()])
                DFS(root.getVertexNumber(), visited);
            root = root.getLink();
        }
    }

    private void deleteEdge(int v1, int v2) {
        GNode root = adjacencyList[v1];
        GNode prvRoot = null;
        if (root.getVertexNumber() == v2) {
            adjacencyList[v1] = root.getLink();
        }
        else
            while (root != null) {
                if (root.getVertexNumber() == v2) {
                    prvRoot.setLink(root.getLink());
                    root.setLink(null);
                    break;
                }
                else {
                    prvRoot = root;
                    root = root.getLink();
                }
            }

        root = adjacencyList[v2];
        prvRoot = null;
        if (root.getVertexNumber() == v1) {
            adjacencyList[v2] = root.getLink();
        }
        else
            while (root != null) {
                if (root.getVertexNumber() == v1) {
                    prvRoot.setLink(root.getLink());
                    root.setLink(null);
                    break;
                }
                else {
                    prvRoot = root;
                    root = root.getLink();
                }
            }

        System.out.println(v1 + " and " + v2 + "  Was Removed");
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
        cost = new ArrayList<>();
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

    public void identifyingCommunities(int sortType) {
        Sort sort = new Sort();
        while (DFS()) {
            fetchCostOfEdges();
            sort.doSort(cost, sortType, 0);
            for (Edge a : cost)
                System.out.print(a.getCost() +"|(" +a.getVertex1() + " : " + a.getVertex2() + ")" + " - ");
            System.out.println();
            deleteEdge(cost.get(0).getVertex1(), cost.get(0).getVertex2());
        }
    }

    public void identifyingCommunities(int sortType, int n) {
        Sort sort = new Sort();
        while (DFS()) {
            fetchCostOfEdges();
            sort.doSort(cost, sortType, n);
            for (Edge a : cost)
                System.out.print(a.getCost() +"|(" +a.getVertex1() + " : " + a.getVertex2() + ")" + " - ");
            System.out.println();
            deleteEdge(cost.get(0).getVertex1(), cost.get(0).getVertex2());
        }
    }

    public static void main(String[] args) {
        InputHandler a = new InputHandler("test1.txt");
        GraphList b = new GraphList(a.fetchAdjacencyList(), a.getInputSize());
        System.out.println();
        System.out.println();
        b.identifyingCommunities(5, 30);
    }
}
