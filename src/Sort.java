import java.sql.Time;
import java.util.ArrayList;

public class Sort {

    private ArrayList<Edge> helperArray;

    private ArrayList<Edge> quick(ArrayList<Edge> inputArray) {
        quick(0, inputArray.size() - 1, inputArray);
        return inputArray;
    }

    private void quick(int low, int high, ArrayList<Edge> inputArray) {
        int i = low, j = high;
        double pivot = inputArray.get((low + high) / 2).getCost();
        while (i <= j) {
            while (inputArray.get(i).getCost() < pivot) i++;
            while (inputArray.get(j).getCost()  > pivot) j--;
            if (i <= j) {
                swap(i, j, inputArray);
                i++;
                j--;
            }
        }
        if (low < j)
            quick(low, j, inputArray);
        if (i < high)
            quick(i, high, inputArray);
    }
    private void swap(int i, int j, ArrayList<Edge> a) {
        Edge temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    private ArrayList insertion(ArrayList<Edge> inputArray) {
        for (int j = 1; j < inputArray.size() ; j++) {
            Edge key = inputArray.get(j);
            int i = j - 1;
            while ( (i > -1) && ( inputArray.get(i).getCost() > key.getCost() ) ) {
                inputArray.set(i + 1, inputArray.get(i));
                i--;
            }
            inputArray.set(i + 1, key);
        }
        return inputArray;
    }

    private ArrayList insertion(ArrayList<Edge> inputArray, int low, int high) {
        for (int j = low + 1; j <= high ; j++) {
            Edge key = inputArray.get(j);
            int i = j - 1;
            while ( (i > -1) && ( inputArray.get(i).getCost() > key.getCost() ) ) {
                inputArray.set(i + 1, inputArray.get(i));
                i--;
            }
            inputArray.set(i + 1, key);
        }
        return inputArray;
    }

    private ArrayList mergeSort(ArrayList inputArray) {
        helperArray = new ArrayList<Edge>();
        for (int i = 0 ; i < inputArray.size() ; i++)
           helperArray.add(null);
        mergeSort(0, inputArray.size() - 1, inputArray);
        return inputArray;
    }

    private void mergeSort(int low, int high, ArrayList<Edge> inputArray) {
        if (low < high) {
            int middle = (high + low) / 2;
            mergeSort(low, middle, inputArray);
            mergeSort(middle + 1, high, inputArray);
            merge(low, middle, high, inputArray);
        }
    }

    private void merge(int low, int middle, int high, ArrayList<Edge> inputArray) {
        for (int i = low; i <= high; i++)
            helperArray.set(i, inputArray.get(i));
        int i = low;
        int j = middle + 1;
        int k = low;
        while (i <= middle && j <= high) {
            if (helperArray.get(i).getCost() <= helperArray.get(j).getCost()) {
                inputArray.set(k, helperArray.get(i));
                i++;
            } else {
                inputArray.set(k, helperArray.get(j));
                j++;
            }
            k++;
        }
        while (i <= middle) {
            inputArray.set(k, helperArray.get(i));
            k++;
            i++;
        }
    }

    private ArrayList bubble(ArrayList<Edge> inputArray) {
        int temp = 0;
        for (int i = 0; i < inputArray.size(); i++) {
            for (int j = 1; j < (inputArray.size() - i); j++) {
                if (inputArray.get(j - 1).getCost() > inputArray.get(j).getCost()) {
                    swap(j, j - 1, inputArray);
                }
            }
        }
        return inputArray;
    }

    private ArrayList bubble(ArrayList<Edge> inputArray, int low, int high) {
        int temp = 0;
        for (int i = low; i <= high; i++) {
            for (int j = low + 1 ; j <= (high - i); j++) {
                if (inputArray.get(j - 1).getCost() > inputArray.get(j).getCost()) {
                    swap(j, j - 1, inputArray);
                }
            }
        }
        return inputArray;
    }
    private ArrayList optimumSort(ArrayList inputArray, int sortType, int n) {
        optimum(inputArray, sortType, n, inputArray.size() - 1 , 0);
        return inputArray;
    }


    /*
    0 for BUBBLE
    1 for INSERTION
    */
    private ArrayList optimum(ArrayList<Edge> inputArray, int sortType, int n, int high, int low) {
        if (high - low + 1 > n) {
            int i = low, j = high;
            double pivot = inputArray.get((low + high) / 2).getCost();
            while (i <= j) {
                while (inputArray.get(i).getCost() < pivot) i++;
                while (inputArray.get(j).getCost()  > pivot) j--;
                if (i <= j) {
                    swap(i, j, inputArray);
                    i++;
                    j--;
                }
            }
            if (low < j)
                optimum(inputArray, sortType, n, j, low);
            if (i < high)
                optimum(inputArray, sortType, n, high, i);
        }
        else {
            if (sortType == 0)
                bubble(inputArray, low, high);
            else
                insertion(inputArray, low, high);
        }
        return inputArray;
    }

    public ArrayList<Edge> doSort(ArrayList<Edge> inputArray, int type, int n) {
        switch (type){
            case 0:
                return quick(inputArray);
            case 1:
                return insertion(inputArray);
            case 2:
                return mergeSort(inputArray);
            case 3:
                return bubble(inputArray);
            case 4:
                return optimumSort(inputArray, 0, n);
            case 5:
                return optimumSort(inputArray, 1, n);
        }
        return null;
    }

    private ArrayList<Vertex> quickVertex(ArrayList<Vertex> inputArray) {
        quickVertex(0, inputArray.size() - 1, inputArray);
        return inputArray;
    }
    private void quickVertex(int low, int high, ArrayList<Vertex> inputArray) {
        int i = low, j = high;
        int pivot = inputArray.get((low + high) / 2).getVertexNumber();
        while (i <= j) {
            while (inputArray.get(i).getVertexNumber() < pivot) i++;
            while (inputArray.get(j).getVertexNumber()  > pivot) j--;
            if (i <= j) {
                swapVertex(i, j, inputArray);
                i++;
                j--;
            }
        }
        if (low < j)
            quickVertex(low, j, inputArray);
        if (i < high)
            quickVertex(i, high, inputArray);
    }
    private void swapVertex(int i, int j, ArrayList<Vertex> a) {
        Vertex temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    private void findBestN() {
        InputHandler inputHandler = new InputHandler("test1.txt");
        GraphList graphList = new GraphList(inputHandler.fetchAdjacencyList(), inputHandler.getEdges(), inputHandler.getVertices(), inputHandler.getInputSize());
        System.out.println("fetched");
        graphList.fetchCostOfEdges();
        System.out.println("numVertex" + graphList.numberOfVertices + "   average: " +  graphList.averageDegreeOfVertex + "    numEdge" +  graphList.numberOfEdges);
        ArrayList<Edge> temp = graphList.edges;
        TimeHandler.startTime();
        optimumSort(temp, 0, 29);
        System.out.println("For N = " + 29 + "  Time is: " + TimeHandler.fetchTime());
    }

    public static void main(String[] args) {
        Sort a = new Sort();
        ArrayList<Edge> list = new ArrayList<>();
        list.add(new Edge(null, null, 1.3));
        list.add(new Edge(null, null, 5.12));
        list.add(new Edge(null, null, 1.1));
        list.add(new Edge(null, null, 0.546));
        list.add(new Edge(null, null, 2.2));
        list.add(new Edge(null, null, 2.6546));
        list.add(new Edge(null, null, 8.2));
        a.insertion(list);
        for (Edge d : list)
            System.out.println(d.getCost());
    }
}
