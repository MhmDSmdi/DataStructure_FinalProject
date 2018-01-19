import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class GraphList extends Graph {
    private GNode[] adjacencyList;
    private Boolean[] visited;

    public GraphList(GNode[] adjacencyList, ArrayList<Edge> edges, Vertex[] vertices ,int numberOfVertices) {
        this.adjacencyList = adjacencyList;
        this.numberOfVertices = numberOfVertices;
        this.edges = edges;
        this.vertices = vertices;
        visited = new Boolean[numberOfVertices];
        numberOfEdges = edges.size();
        averageDegreeOfVertex = numberOfEdges / this.numberOfVertices ;
    }

    public GraphList() {}

    public void printList() {
        for (int i = 1 ; i < adjacencyList.length ; i++) {
            GNode root = adjacencyList[i];
            System.out.print("Parent " + i + " : ");
            while (root != null) {
                /*if (!root.isDeleted())*/
                System.out.print(root.getVertexNumber() + " - ");
                root = root.getLink();
            }
            System.out.println();
        }
    }

    private boolean isConnected(int v1, int v2) {
        GNode root = adjacencyList[v1];
        while(root != null){
            if (root.getVertexNumber() == v2/* && !root.isDeleted()*/)
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
            //System.out.println("Cycle Path between " + v1 + " and " + v2);
            while (root != null) {
                if (/*!root.isDeleted() && */isConnected(v2, root.getVertexNumber())) {
                    count++;
                    //System.out.println("* " + root.getVertexNumber());
                }
                root = root.getLink();
            }
            return count;
        }
        else
            return 0;
    }

    public int degreeOfVertex(int v) {
       /* int count = 0;
        GNode root = adjacencyList[v];
        while (root != null) {
            *//*if (!root.isDeleted())*//*
                count++;
            root = root.getLink();
        }*/
        return vertices[v].getDegree();
    }

    @Override
    protected boolean DFS() {
        for (int i = 1 ; i < numberOfVertices ; i++)
            visited[i] = false;
        DFS(1, visited);

        for (int i = 1 ; i < numberOfVertices ; i++) {
            if (!visited[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void DFS(int v, Boolean[] visited) {
       /* visited[v] = true;
        GNode root = adjacencyList[v];
        while(root != null) {
            if(!visited[root.getVertexNumber()])
                DFS(root.getVertexNumber(), visited);
            root = root.getLink();

        }*/
        MyStack<Integer> s = new MyStack<>();
        s.push(v);
        while (!s.isEmpty()) {
            int vertex = s.pop();
            if (!visited[vertex]) {
                visited[vertex] = true;
                GNode root = adjacencyList[vertex];
                while(root != null) {
                    s.push(root.getVertexNumber());
                    root = root.getLink();
                }
            }
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
        //System.out.println("(" + count + ")" + v1 + " and " + v2 + "  Was Removed" + "  with Cost: " + edges.get(0).getCost() +  "  List Size: " + edges.size());
        vertices[v1].minusDegree();
        vertices[v2].minusDegree();
        edges.remove(0);
        count++;
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

    private boolean isExist(int v1, int v2) {
        for (Edge a : edges) {
            if ((v1 == a.getVertex1().getVertexNumber() && v2 == a.getVertex2().getVertexNumber()) || (v1 == a.getVertex2().getVertexNumber() && v2 == a.getVertex1().getVertexNumber()))
                return true;
        }
        return false;
    }

    @Override
    protected void fetchCostOfEdges() {
        for (Edge a : edges) {
            int cost = calculateCost(countCycle(a.getVertex1().getVertexNumber(), a.getVertex2().getVertexNumber()), degreeOfVertex(a.getVertex1().getVertexNumber()), degreeOfVertex(a.getVertex2().getVertexNumber()));
            a.setCost(cost);
        }
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public static void main(String[] args) {
        InputHandler a = new InputHandler("g1.txt");
        GraphList b = new GraphList(a.fetchAdjacencyList(), a.getEdges(), a.getVertices(), a.getInputSize());
        //b.printList();
        System.out.println(a.time);
        System.out.println();
        System.out.println();
        b.identifyingCommunities(5,30);
        System.out.println();
        System.out.println();
        for (Edge d : b.edges)
            System.out.println(d.getVertex1().getVertexNumber() + " and " + d.getVertex2().getVertexNumber());
       // b.DFS();
        /*System.out.println(b.isConnected(2,4));
        System.out.println(b.countCycle(5,2));
        b.fetchCostOfEdges();
        b.deleteEdge(2,4);
        System.out.println(b.countCycle(5,2));*/

    }
}
