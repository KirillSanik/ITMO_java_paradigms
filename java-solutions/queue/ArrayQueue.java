package queue;

import java.util.Arrays;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[5];
    private int head = 0;
    private int tail = 0;
    private int headNext;

    protected void enqueueImpl(Object element) {
        ensureCapacity(size + 1);
        tail %= elements.length;
        elements[tail++] = element;
    }

    public Object elementImpl() {
        return elements[head % elements.length];
    }

    public void remove() {
        head = head % elements.length + 1;
    }

    protected void initNext() {
        headNext = head;
    }

    protected Object next() {
        headNext++;
        return elements[(headNext - 1) % elements.length];
    }

    public void clear() {
        head = 0;
        tail = 0;
        size = 0;
    }

    private void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            if (head >= tail) {
                Object[] temp = Arrays.copyOf(elements, (size + 1 - head) + tail + capacity);
                System.arraycopy(elements, head, temp, tail + capacity + 1, size - head);
                head = tail + capacity + 1;
                elements = temp;
            } else {
                elements = Arrays.copyOf(elements, 2 * capacity);
            }
        }
    }

    public int count(Object element) {
        int cnt = 0;
        initNext();
        for (int j = 0; j < size; j++) {
            if (element.equals(next())) {
                cnt++;
            }
        }
        return cnt;
    }
}
