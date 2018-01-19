import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputHandler {
    private ArrayList<Edge> edges;
    private Vertex[] vertices;
    private String fileAddress;
    private int inputSize;
    private String command;
    public String time;
    public static int n;

    public InputHandler(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public InputHandler() {
        System.out.print("Please enter file'address:  ");
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        fileAddress = scanner.nextLine().trim();
    }

    public int getInputSize() {
        return inputSize;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    private boolean isExist(int v1, int v2) {
        for (Edge a : edges) {
            if ((v1 == a.getVertex1().getVertexNumber() && v2 == a.getVertex2().getVertexNumber()) || (v1 == a.getVertex2().getVertexNumber() && v2 == a.getVertex1().getVertexNumber()))
                return true;
        }
        return false;
    }

    /*public int[][] fetchAdjacencyMatrix() {
        time = System.currentTimeMillis();
        int[][] adjacencyMatrix = {};
        try {
            FileInputStream fi = new FileInputStream(fileAddress);
            Scanner scanner = new Scanner(fi);
            String temp;
            ArrayList<Integer> vertexData = new ArrayList<>();
            ArrayList<Integer> recentVertex = new ArrayList<>();
            while(scanner.hasNext()) {
                temp = scanner.nextLine();
                String[] splited = temp.split(",");
                vertexData.add(Integer.parseInt(splited[0]));
                vertexData.add(Integer.parseInt(splited[1]));
                if (!recentVertex.contains(Integer.parseInt(splited[0])))
                    recentVertex.add(Integer.parseInt(splited[0]));
                if (!recentVertex.contains(Integer.parseInt(splited[1])))
                    recentVertex.add(Integer.parseInt(splited[1]));
            }

            inputSize = recentVertex.size() + 1;
            adjacencyMatrix = new int[inputSize][inputSize];

            for (int i = 0 ; i < vertexData.size() - 1 ; i += 2) {
                adjacencyMatrix[vertexData.get(i)][vertexData.get(i + 1)] = 1;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        time = System.currentTimeMillis() - time;
        return adjacencyMatrix;
    }*/

    private int getMax() {
        int max = 0;
        try {
            FileInputStream fi = new FileInputStream(fileAddress);
            Scanner scanner = new Scanner(fi);
            String temp;
            while(scanner.hasNext()) {
                temp = scanner.nextLine();
                String[] splited = temp.split(",");
                if (max < Integer.parseInt(splited[0].trim()))
                    max = Integer.parseInt(splited[0].trim());
                else if (max < Integer.parseInt(splited[1].trim()))
                    max = Integer.parseInt(splited[1].trim());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return max;
    }

    public GNode[] fetchAdjacencyList() {
        TimeHandler.startTime();
        edges = new ArrayList<>();
        GNode[] gNodes = {};
        inputSize = getMax() + 1;
        vertices = new Vertex[inputSize];
        vertices[0] = new Vertex(0);
        try {
            FileInputStream fi = new FileInputStream(fileAddress);
            Scanner scanner = new Scanner(fi);
            String temp;
            ArrayList<Integer> vertexData = new ArrayList<>();
            while(scanner.hasNext()) {
                temp = scanner.nextLine();
                String[] splited = temp.split(",");
                vertexData.add(Integer.parseInt(splited[0]));
                vertexData.add(Integer.parseInt(splited[1]));
            }
            gNodes = new GNode[inputSize];
            for (int i = 0 ; i < vertexData.size() ; i += 2) {
                if (vertices[vertexData.get(i)] == null)
                    vertices[vertexData.get(i)] = new Vertex(vertexData.get(i));
                if (vertices[vertexData.get(i + 1)] == null)
                    vertices[vertexData.get(i + 1)] = new Vertex(vertexData.get(i + 1));
                GNode a = new GNode(gNodes[vertexData.get(i)], vertexData.get(i + 1));
                if(!isExist(vertexData.get(i), vertexData.get(i + 1))) {
                    Edge edge = new Edge(vertices[vertexData.get(i)], vertices[vertexData.get(i + 1)], 0);
                    vertices[vertexData.get(i)].plusDegree();
                    vertices[vertexData.get(i + 1)].plusDegree();
                    edges.add(edge);
                }
                gNodes[vertexData.get(i)] = a;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        time = TimeHandler.fetchTime();
        System.out.println("Storing Time : " + time);
        return gNodes;
    }

    public ArrayList<Edge> fetchAdjacencySparseMatrix() {
        TimeHandler.startTime();
        edges = new ArrayList<>();
        inputSize = getMax() + 1;
        vertices = new Vertex[inputSize];
        vertices[0] = new Vertex(0);
        try {
            FileInputStream fi = new FileInputStream(fileAddress);
            Scanner scanner = new Scanner(fi);
            String temp;
            ArrayList<Integer> vertexData = new ArrayList<>();
            while(scanner.hasNext()) {
                temp = scanner.nextLine();
                String[] splited = temp.split(",");
                vertexData.add(Integer.parseInt(splited[0]));
                vertexData.add(Integer.parseInt(splited[1]));
            }
            for (int i = 0 ; i < vertexData.size() ; i += 2) {
                if (vertices[vertexData.get(i)] == null)
                    vertices[vertexData.get(i)] = new Vertex(vertexData.get(i));
                if (vertices[vertexData.get(i + 1)] == null)
                    vertices[vertexData.get(i + 1)] = new Vertex(vertexData.get(i + 1));
                if(!isExist(vertexData.get(i), vertexData.get(i + 1))) {
                    Edge edge = new Edge(vertices[vertexData.get(i)], vertices[vertexData.get(i + 1)], 0);
                    vertices[vertexData.get(i)].plusDegree();
                    vertices[vertexData.get(i + 1)].plusDegree();
                    edges.add(edge);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        time = TimeHandler.fetchTime();
        System.out.println("Storing Time : " + time);
        return edges;
    }

    public int getCommand() {
        System.out.println("Enter your Command: ");
        Scanner scanner = new Scanner(System.in);
        command = scanner.nextLine();
        switch (command.trim().toLowerCase()) {
            case "run linkedlist quick":
                return 1;
            case "run matrix quick":
                return 2;
            case "run linkedlist insertion":
                return 3;
            case "run matrix insertion":
                return 4;
            case "run linkedlist merge":
                return 5;
            case "run matrix merge":
                return 6;
            case "run linkedlist bubble":
                return 7;
            case "run matrix bubble":
                return 8;
            case "run linkedlist optimum insertion":
                System.out.print("Enetr N: ");
                n = scanner.nextInt();
                System.out.println();
                return 9;
            case "run matrix optimum insertion":
                System.out.print("Enetr N: ");
                n = scanner.nextInt();
                System.out.println();
                return 10;
            case "run linkedlist optimum bubble":
                System.out.print("Enetr N: ");
                n = scanner.nextInt();
                System.out.println();
                return 11;
            case "run matrix optimum bubble":
                System.out.print("Enetr N: ");
                n = scanner.nextInt();
                System.out.println();
                return 12;
        }
        return -1;
    }
}
