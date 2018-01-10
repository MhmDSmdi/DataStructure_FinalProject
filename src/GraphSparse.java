import java.util.ArrayList;

public class GraphSparse extends Graph {

    public GraphSparse(ArrayList<Edge> edges, int numberOfVertices) {
        this.edges = edges;
        this.numberOfVertices = numberOfVertices;
    }

    @Override
    protected void fillEdges() {}

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
        for (Edge a : edges) {
            if (a.getVertex1().getVertexNumber() == v && !visited[a.getVertex2().getVertexNumber()])
                DFS(a.getVertex2().getVertexNumber(), visited);
            if (a.getVertex2().getVertexNumber() == v && !visited[a.getVertex1().getVertexNumber()])
                DFS(a.getVertex1().getVertexNumber(), visited);
        }
    }

    @Override
    protected int countCycle(int v1, int v2) {
        boolean exist = false;
        int count= 0;
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        for (int i = 0 ; i < edges.size() ; i++) {
            if (!exist && (edges.get(i).getVertex1().getVertexNumber() == v1 && edges.get(i).getVertex2().getVertexNumber() == v2) || (edges.get(i).getVertex1().getVertexNumber() == v2 && edges.get(i).getVertex2().getVertexNumber() == v1))
                exist = true;
            if(edges.get(i).getVertex1().getVertexNumber() == v1)
                list1.add(edges.get(i).getVertex2().getVertexNumber());
            if(edges.get(i).getVertex2().getVertexNumber() == v1)
                list1.add(edges.get(i).getVertex1().getVertexNumber());
            if(edges.get(i).getVertex1().getVertexNumber() == v2)
                list2.add(edges.get(i).getVertex2().getVertexNumber());
            if(edges.get(i).getVertex2().getVertexNumber() == v2)
                list2.add(edges.get(i).getVertex1().getVertexNumber());
        }
        if (!exist)
            return 0;
        else {
            for (int i = 0 ; i < list1.size() ; i++)
                for (int j = 0 ; j < list2.size() ; j++)
                    if (list1.get(i).equals(list2.get(j)))
                        count++;
            return count;
        }
    }

    @Override
    protected int degreeOfVertex(int v) {
        int degree = 0;
        for (Edge a : edges)
            if(a.getVertex1().getVertexNumber() == v || a.getVertex2().getVertexNumber() == v)
                degree++;
        return degree;
    }

    @Override
    protected void fetchCostOfEdges() {
        for (Edge a : edges) {
            int cost = calculateCost(countCycle(a.getVertex1().getVertexNumber(), a.getVertex2().getVertexNumber()), degreeOfVertex(a.getVertex1().getVertexNumber()), degreeOfVertex(a.getVertex2().getVertexNumber()));
            a.setCost(cost);
        }
    }

    @Override
    protected void deleteEdge(int v1, int v2) {
        int index = 0;
        for (int i = 0 ; i < edges.size() ; i++) {
            if ((edges.get(i).getVertex1().getVertexNumber() == v1 && edges.get(i).getVertex2().getVertexNumber() == v2) || (edges.get(i).getVertex1().getVertexNumber() == v2 && edges.get(i).getVertex2().getVertexNumber() == v1)) {
                index = i;
                break;
            }
        }
        edges.remove(index);
        System.out.println(v1 + " and " + v2 + "  Was Removed");
    }

    @Override
    protected void write2File() {

    }

    public void print() {
        for (Edge a : edges)
            System.out.println("V1 = " + a.getVertex1().getVertexNumber() + "    V2 = " + a.getVertex2().getVertexNumber());
    }

    public static void main(String[] args) {
        InputHandler a = new InputHandler("graph.txt");
        GraphSparse b = new GraphSparse(a.fetchAdjacencySparseMatrix(), a.getInputSize());
        System.out.println(a.time);
        b.identifyingCommunities(5,1);
        for (Edge d : b.edges)
            System.out.println(d.getVertex1().getVertexNumber() + " and " + d.getVertex2().getVertexNumber());
    }
}
