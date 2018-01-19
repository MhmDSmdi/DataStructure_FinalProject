public class Main {
    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler("test2.txt");
        GraphSparse graphSparse;
        GraphList graphList;

        while (true) {
            int input = inputHandler.getCommand();
            switch (input) {
                case 1:
                    graphList = new GraphList(inputHandler.fetchAdjacencyList(), inputHandler.getEdges(), inputHandler.getVertices(), inputHandler.getInputSize());
                    graphList.identifyingCommunities(0);
                    break;
                case 2:
                    graphSparse = new GraphSparse(inputHandler.fetchAdjacencySparseMatrix(), inputHandler.getVertices(), inputHandler.getInputSize());
                    graphSparse.identifyingCommunities(0);
                    break;
                case 3:
                    graphList = new GraphList(inputHandler.fetchAdjacencyList(), inputHandler.getEdges(), inputHandler.getVertices(), inputHandler.getInputSize());
                    graphList.identifyingCommunities(1);
                    break;
                case 4:
                    graphSparse = new GraphSparse(inputHandler.fetchAdjacencySparseMatrix(), inputHandler.getVertices(), inputHandler.getInputSize());
                    graphSparse.identifyingCommunities(1);
                    break;
                case 5:
                    graphList = new GraphList(inputHandler.fetchAdjacencyList(), inputHandler.getEdges(), inputHandler.getVertices(), inputHandler.getInputSize());
                    graphList.identifyingCommunities(2);
                    break;
                case 6:
                    graphSparse = new GraphSparse(inputHandler.fetchAdjacencySparseMatrix(), inputHandler.getVertices(), inputHandler.getInputSize());
                    graphSparse.identifyingCommunities(2);
                    break;
                case 7:
                    graphList = new GraphList(inputHandler.fetchAdjacencyList(), inputHandler.getEdges(), inputHandler.getVertices(), inputHandler.getInputSize());
                    graphList.identifyingCommunities(3);
                    break;
                case 8:
                    graphSparse = new GraphSparse(inputHandler.fetchAdjacencySparseMatrix(), inputHandler.getVertices(), inputHandler.getInputSize());
                    graphSparse.identifyingCommunities(3);
                    break;
                case 9:
                    graphList = new GraphList(inputHandler.fetchAdjacencyList(), inputHandler.getEdges(), inputHandler.getVertices(), inputHandler.getInputSize());
                    graphList.identifyingCommunities(5 ,InputHandler.n);
                    break;
                case 10:
                    graphSparse = new GraphSparse(inputHandler.fetchAdjacencySparseMatrix(), inputHandler.getVertices(), inputHandler.getInputSize());
                    graphSparse.identifyingCommunities(5 ,InputHandler.n);
                    break;

                case 11:
                    graphList = new GraphList(inputHandler.fetchAdjacencyList(), inputHandler.getEdges(), inputHandler.getVertices(), inputHandler.getInputSize());
                    graphList.identifyingCommunities(4 ,InputHandler.n);
                    break;
                case 12:
                    graphSparse = new GraphSparse(inputHandler.fetchAdjacencySparseMatrix(), inputHandler.getVertices(), inputHandler.getInputSize());
                    graphSparse.identifyingCommunities(4 ,InputHandler.n);
                    break;
            }
        }
    }
}
