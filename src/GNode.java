public class GNode {
    private GNode link;
    private int vertexNumber;

    public GNode(GNode link, int vertexNumber) {
        this.link = link;
        this.vertexNumber = vertexNumber;
    }

    public GNode getLink() {
        return link;
    }

    public void setLink(GNode link) {
        this.link = link;
    }

    public int getVertexNumber() {
        return vertexNumber;
    }

    public void setVertexNumber(int vertexNumber) {
        this.vertexNumber = vertexNumber;
    }
}
