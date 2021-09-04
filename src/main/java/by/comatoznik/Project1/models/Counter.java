package by.comatoznik.Project1.models;

public class Counter {
    private int count;

    public Counter() {
        count = -1;
    }

    public Counter(int i) {
        count = i;
    }

    public int increment() {
        return ++count;
    }

    public int decrement() {
        return --count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
