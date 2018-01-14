import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GraphSparse extends Graph {

    private Boolean[] visited;

    public GraphSparse(ArrayList<Edge> edges, Vertex[] vertices, int numberOfVertices) {
        this.edges = edges;
        this.numberOfVertices = numberOfVertices;
        this.vertices = vertices;
        visited = new Boolean[numberOfVertices];
        numberOfEdges = edges.size();
        averageDegreeOfVertex = numberOfEdges / this.numberOfVertices ;
    }

    @Override
    protected void fillEdges() {}

    @Override
    protected boolean DFS() {
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
        return vertices[v].getDegree();
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
        vertices[v1].minusDegree();
        vertices[v2].minusDegree();
        System.out.println(v1 + " and " + v2 + "  Was Removed");
    }

    @Override
    protected void write2File() {
        try {
            boolean index = visited[1];
            FileOutputStream fo = new FileOutputStream("resault.txt");
            for (int i = 1 ; i < visited.length ; i++) {
                String line = "#" + i + " : " + ((visited[i] == index) ? "A" : "B");
                byte[] byteArray = line.getBytes();
                fo.write(byteArray);
                fo.write("\n".getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        for (Edge a : edges)
            System.out.println("V1 = " + a.getVertex1().getVertexNumber() + "    V2 = " + a.getVertex2().getVertexNumber());
    }

    public static void main(String[] args) {
        InputHandler a = new InputHandler("g1.txt");
        GraphSparse b = new GraphSparse(a.fetchAdjacencySparseMatrix(), a.getVertices(), a.getInputSize());
        System.out.println(a.time);
        b.identifyingCommunities(5,1);
        for (Edge d : b.edges)
            System.out.println(d.getVertex1().getVertexNumber() + " and " + d.getVertex2().getVertexNumber());

      /*  for (Vertex d : b.vertices)
            System.out.println(d.getVertexNumber() + " degree's : " + d.getDegree());*/
    }
}
