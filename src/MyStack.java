import java.util.ArrayList;

public class MyStack <T> {
    private ArrayList<T> arrayList;

    public MyStack() {
        arrayList = new ArrayList<>();
    }

    public void push(T item) {
        arrayList.add(item);
    }

    public T pop() {
        T item = arrayList.get(arrayList.size() - 1);
        arrayList.remove(arrayList.size() - 1);
        return item;
    }

    public boolean isEmpty() {
        if (arrayList.size() == 0)
            return true;
        else
            return false;
    }
}
