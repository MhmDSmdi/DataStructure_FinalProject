import java.util.ArrayList;

public class GraphList extends Graph {
    private GNode[] adjacencyList;

    public GraphList(GNode[] adjacencyList, int numberOfVertices) {
        this.adjacencyList = adjacencyList;
        this.numberOfVertices = numberOfVertices;
        edges = new ArrayList<>();
        fillEdges();
        numberOfEdges = edges.size();
        averageDegreeOfVertex = numberOfEdges / this.numberOfVertices ;
    }

    public void printList() {
        for (int i = 1 ; i < adjacencyList.length ; i++) {
            GNode root = adjacencyList[i];
            System.out.print("Parent " + i + " : ");
            while (root != null) {
                System.out.print(  root.getVertexNumber() + " - ");
                root = root.getLink();
            }
            System.out.println();
        }
    }

    public ArrayList<Edge> getEdges() {
        return edges;
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
        GNode root = adjacencyList[v];
        while(root != null) {
            if(!visited[root.getVertexNumber()])
                DFS(root.getVertexNumber(), visited);
            root = root.getLink();
        }
    }

    @Override
    protected void deleteEdge(int v1, int v2) {
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
        edges.remove(0);
        System.out.println(v1 + " and " + v2 + "  Was Removed");
    }

    private boolean isExist(int v1, int v2) {
        for (Edge a : edges) {
            if ((v1 == a.getVertex1() && v2 == a.getVertex2()) || (v1 == a.getVertex2() && v2 == a.getVertex1()))
                return true;
        }
        return false;
    }

    @Override
    protected void fillEdges() {
        GNode root;
        for (int i = 0 ; i < numberOfVertices ; i++){
            root = adjacencyList[i];
            while (root != null) {
                if (!isExist(i, root.getVertexNumber())) {
                    Edge temp = new Edge(i, root.getVertexNumber(), 0);
                    this.edges.add(temp);
                }
                root = root.getLink();
            }
        }
    }

    @Override
    protected void fetchCostOfEdges() {
        for (Edge a : edges) {
            int cost = calculateCost(countCycle(a.getVertex1(), a.getVertex2()), degreeOfVertex(a.getVertex1()), degreeOfVertex(a.getVertex2()));
            a.setCost(cost);
        }
    }

    public static void main(String[] args) {
        InputHandler a = new InputHandler("graph.txt");
        GraphList b = new GraphList(a.fetchAdjacencyList(), a.getInputSize());
        System.out.println();
        System.out.println();
        b.identifyingCommunities(1);
    }
}
