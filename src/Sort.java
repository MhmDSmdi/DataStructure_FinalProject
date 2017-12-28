import java.util.ArrayList;

public class Sort {

    private ArrayList<Edge> helperArray;

    public ArrayList<Edge> quick(ArrayList<Edge> inputArray) {
        quick(0, inputArray.size() - 1, inputArray);
        return inputArray;
    }
    public void quick(int low, int high, ArrayList<Edge> inputArray) {
        int i = low, j = high;
        int pivot = inputArray.get(0).getCost();
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

    public ArrayList insertion(ArrayList<Edge> inputArray) {
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

    public ArrayList mergeSort(ArrayList inputArray) {
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
        for (int i = low; i <= high; i++) {
            helperArray.set(i, inputArray.get(i));
        }
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


    public ArrayList bubble(ArrayList<Edge> inputArray) {
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
/*
    public static ArrayList optimum(ArrayList inputArray) {

    }
*/


    public static void main(String[] args) {
        InputHandler a = new InputHandler("test1.txt");
        GraphList b = new GraphList(a.fetchAdjacencyList(), a.getInputSize());
        b.fetchCostOfEdges();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        Sort e = new Sort();
        e.bubble(b.getCost());
        for (Edge d : b.getCost())
            System.out.print("- " + d.getCost() + " -");
    }
}
