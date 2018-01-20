import java.sql.Time;
import java.util.ArrayList;

public abstract class Graph {

    protected int numberOfVertices;
    protected ArrayList<Edge> edges;
    protected Vertex[] vertices;
    protected int numberOfEdges;
    protected float averageDegreeOfVertex;
    protected int count = 0;
    protected abstract boolean DFS();
    protected abstract void DFS(int v, Boolean[] visited);
    protected abstract int countCycle(int v1, int v2);
    protected abstract int degreeOfVertex(int v);
    protected abstract void fetchCostOfEdges();
    protected abstract void deleteEdge(int v1, int v2);
    protected abstract void write2File();

    protected void identifyingCommunities(int sortType) {
        TimeHandler.startTime();
        Sort sort = new Sort();
        while (DFS()) {
            fetchCostOfEdges();
            sort.doSort(edges, sortType, 0);
            deleteEdge(edges.get(0).getVertex1().getVertexNumber(), edges.get(0).getVertex2().getVertexNumber());
        }
        System.out.println("AlgorithmTime : " + TimeHandler.fetchTime());
        write2File();
    }

    protected void identifyingCommunities(int sortType, int n) {
        TimeHandler.startTime();
        Sort sort = new Sort();
        while (DFS()) {
            fetchCostOfEdges();
            sort.doSort(edges, sortType, n);
            deleteEdge(edges.get(0).getVertex1().getVertexNumber(), edges.get(0).getVertex2().getVertexNumber());
        }
        System.out.println("AlgorithmTime : " + TimeHandler.fetchTime());
        write2File();
    }

    protected int calculateCost(int cycles, int degreeV1, int degreeV2) {
        int temp;
        try {
            temp = ((cycles + 1) / (Math.min(degreeV1 - 1, degreeV2 - 1)));
        }catch (ArithmeticException e) {
            temp = Integer.MAX_VALUE;
        }
        return temp;
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public void setNumberOfVertices(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public void setVertices(Vertex[] vertices) {
        this.vertices = vertices;
    }

    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    public void setNumberOfEdges(int numberOfEdges) {
        this.numberOfEdges = numberOfEdges;
    }

    public float getAverageDegreeOfVertex() {
        return averageDegreeOfVertex;
    }

    public void setAverageDegreeOfVertex(float averageDegreeOfVertex) {
        this.averageDegreeOfVertex = averageDegreeOfVertex;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
