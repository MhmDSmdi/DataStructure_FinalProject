import java.util.ArrayList;

public class GraphSparse extends Graph {

    public GraphSparse(ArrayList<Edge> edges, int numberOfVertices) {
        this.edges = edges;
        this.numberOfVertices = numberOfVertices;
    }

    @Override
    protected void fillEdges() { }

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
            if (a.getVertex1() == v && !visited[a.getVertex2()])
                DFS(a.getVertex2(), visited);
            if (a.getVertex2() == v && !visited[a.getVertex1()])
                DFS(a.getVertex1(), visited);
        }
    }

    @Override
    protected int countCycle(int v1, int v2) {
        boolean exist = false;
        int count= 0;
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        for (int i = 0 ; i < edges.size() ; i++) {
            if (!exist && (edges.get(i).getVertex1() == v1 && edges.get(i).getVertex2() == v2) || (edges.get(i).getVertex1() == v2 && edges.get(i).getVertex2() == v1))
                exist = true;
            if(edges.get(i).getVertex1() == v1)
                list1.add(edges.get(i).getVertex2());
            if(edges.get(i).getVertex2() == v1)
                list1.add(edges.get(i).getVertex1());
            if(edges.get(i).getVertex1() == v2)
                list2.add(edges.get(i).getVertex2());
            if(edges.get(i).getVertex2() == v2)
                list2.add(edges.get(i).getVertex1());
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
            if(a.getVertex1() == v || a.getVertex2() == v)
                degree++;
        return degree;
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
        int index = 0;
        for (int i = 0 ; i < edges.size() ; i++) {
            if ((edges.get(i).getVertex1() == v1 && edges.get(i).getVertex2() == v2) || (edges.get(i).getVertex1() == v2 && edges.get(i).getVertex2() == v1)) {
                index = i;
                break;
            }
        }
        edges.remove(index);
        System.out.println(v1 + " and " + v2 + "  Was Removed");
    }

    public void print() {
        for (Edge a : edges)
            System.out.println("V1 = " + a.getVertex1() + "    V2 = " + a.getVertex2());
    }

    public static void main(String[] args) {
        InputHandler a = new InputHandler("test1.txt");
        GraphSparse b = new GraphSparse(a.fetchAdjacencySparseMatrix(), a.getInputSize());
        b.identifyingCommunities(5,1);
    }
}
