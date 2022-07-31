package queue;

import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    public void enqueue(Object element) {
        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    public Object element() {
        return elementImpl();
    }

    protected abstract Object elementImpl();

    public Object dequeue() {
        Object result = element();
        size--;
        remove();
        return result;
    }

    public abstract void remove();

    protected abstract void initNext();

    protected abstract Object next();

    public int countIf(Predicate<Object> predicate) {
        int cnt = 0;
        initNext();
        for (int i = 0; i < size; i++) {
            if(predicate.test(next())) {
                cnt++;
            }
        }
        return cnt;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
