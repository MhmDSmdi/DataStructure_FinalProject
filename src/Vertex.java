public class Vertex {
    private int vertexNumber;
    private String communityName;
    private int degree;

    public Vertex(int vertexNumber) {
        this.vertexNumber = vertexNumber;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getVertexNumber() {
        return vertexNumber;
    }

    public void setVertexNumber(int vertexNumber) {
        this.vertexNumber = vertexNumber;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public void plusDegree() {
        degree++;
    }

    public void minusDegree() {
        degree--;
    }
}
