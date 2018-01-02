import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputHandler {
    private String fileAddress;
    private int inputSize;

    public InputHandler(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public int getInputSize() {
        return inputSize;
    }

    public int[][] fetchAdjacencyMatrix() {
        int[][] adjacencyMatrix = {};
        try {
            FileInputStream fi = new FileInputStream(fileAddress);
            Scanner scanner = new Scanner(fi);
            String temp;
            ArrayList<Integer> vertexData = new ArrayList<>();
            ArrayList<Integer> recentVertex = new ArrayList<>();
            while(scanner.hasNext()) {
                temp = scanner.nextLine();
                String[] splited = temp.split("\t");
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

        return adjacencyMatrix;
    }

    public GNode[] fetchAdjacencyList() {
        GNode[] gNodes = {};
        try {
            FileInputStream fi = new FileInputStream(fileAddress);
            Scanner scanner = new Scanner(fi);
            String temp;
            ArrayList<Integer> vertexData = new ArrayList<>();
            ArrayList<Integer> recentVertex = new ArrayList<>();
            while(scanner.hasNext()) {
                temp = scanner.nextLine();
                String[] splited = temp.split("\t");
                vertexData.add(Integer.parseInt(splited[0]));
                vertexData.add(Integer.parseInt(splited[1]));
                if (!recentVertex.contains(Integer.parseInt(splited[0])))
                    recentVertex.add(Integer.parseInt(splited[0]));
                if (!recentVertex.contains(Integer.parseInt(splited[1])))
                    recentVertex.add(Integer.parseInt(splited[1]));
            }
            inputSize = recentVertex.size() + 1;
            gNodes = new GNode[inputSize];

            for (int i = 0 ; i < vertexData.size() ; i += 2) {
                GNode a = new GNode(gNodes[vertexData.get(i)], vertexData.get(i + 1));
                gNodes[vertexData.get(i)] = a;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gNodes;
    }

    public ArrayList<Edge> fetchAdjacencySparseMatrix() {
        ArrayList<Edge> adjacencySparseList = new ArrayList<>();;
        ArrayList<Integer> recentVertex = new ArrayList<>();
        try {
            FileInputStream fi = new FileInputStream(fileAddress);
            Scanner scanner = new Scanner(fi);
            String temp;
            ArrayList<Integer> vertexData = new ArrayList<>();
            while(scanner.hasNext()) {
                temp = scanner.nextLine();
                String[] splited = temp.split("\t");
                vertexData.add(Integer.parseInt(splited[0]));
                vertexData.add(Integer.parseInt(splited[1]));
                if (!recentVertex.contains(Integer.parseInt(splited[0])))
                    recentVertex.add(Integer.parseInt(splited[0]));
                if (!recentVertex.contains(Integer.parseInt(splited[1])))
                    recentVertex.add(Integer.parseInt(splited[1]));
            }

            inputSize = recentVertex.size() + 1;

            for (int i = 0 ; i < vertexData.size() - 1 ; i += 2) {
                Edge edge = new Edge(vertexData.get(i), vertexData.get(i + 1), 0);
                boolean flag = false;
                for (Edge a : adjacencySparseList) {
                    if ((a.getVertex1() == vertexData.get(i) && a.getVertex2() == vertexData.get(i + 1)) || (a.getVertex2() == vertexData.get(i) && a.getVertex1() == vertexData.get(i + 1))) {
                        flag = true;
                        break;
                    }
                }
                if (!flag)
                    adjacencySparseList.add(edge);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return adjacencySparseList;
    }

    public static void main(String[] args) {
        InputHandler a = new InputHandler("graph.txt");
        ArrayList<Edge> d = a.fetchAdjacencySparseMatrix();
        for (Edge f : d)
            System.out.println("v1: " + f.getVertex1() + "    v2: " + f.getVertex2());
    }

}
