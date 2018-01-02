import java.util.ArrayList;

public abstract class Graph {

    protected int numberOfVertices;
    protected ArrayList<Edge> edges;
    protected int numberOfEdges;
    protected float averageDegreeOfVertex;

    protected abstract void fillEdges();
    protected abstract boolean DFS();
    protected abstract void DFS(int v, Boolean[] visited);
    protected abstract int countCycle(int v1, int v2);
    protected abstract int degreeOfVertex(int v);
    protected abstract void fetchCostOfEdges();
    protected abstract void deleteEdge(int v1, int v2);

    protected void identifyingCommunities(int sortType) {
        Sort sort = new Sort();
        while (DFS()) {
            fetchCostOfEdges();
            sort.doSort(edges, sortType, 0);
            deleteEdge(edges.get(0).getVertex1(), edges.get(0).getVertex2());
            edges.remove(0);
        }
    }
    protected void identifyingCommunities(int sortType, int n) {
        Sort sort = new Sort();
        while (DFS()) {
            fetchCostOfEdges();
            sort.doSort(edges, sortType, n);
            deleteEdge(edges.get(0).getVertex1(), edges.get(0).getVertex2());
            edges.remove(0);
        }
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

}
