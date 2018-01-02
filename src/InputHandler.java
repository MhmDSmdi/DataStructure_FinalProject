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
            /*for (int i = 0 ; i < vertexNum ; i++) {
                for (int j = 0; j < vertexNum; j++) {
                    if(adjacencyMatrix[i][j] != 0)
                        System.out.println(i + " and " + j);
                }
            }*/
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

    public static void main(String[] args) {
        InputHandler a = new InputHandler("test1.txt");
        a.fetchAdjacencyList();
    }

}
