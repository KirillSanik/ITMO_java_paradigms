package queue;

import java.util.*;

/*
Model: a[head] ... a[tail]
Invarint: for head ... tail: a[i] != null

Let immutable(n): for i = tail ... head: a'[i] == a[i]

Pred: element != null
Post: tail' = tail + 1 && a[tail'] = element && immutable(tail)
    enqueue(element)

Pred: true
Post: Result = a[head] && immutable(n)
    element()

Pred: true
Post: head' = head + 1 && Result = a[head]
    dequeue()

Pred: true
Post: size = 0 && tail = 0 && head = 0
    clear()

Pred: true
Post: for i = head ... tail: Result = cnt(a[i] == element) && immutable(n)
    count(element)

Pred: true
Post: Result = tail - head && immutable(head - tail)
    size()

Pred: true
Post: Result = size == 0 && immutable(head - tail)
    isEmpty()
 */

public class ArrayQueueModule {
    private static int size = 0;
    private static Object[] elements = new Object[5];
    private static int head = 0;
    private static int tail = 0;

    public static void enqueue(Object element) {
        ensureCapacity(++size);
        tail %= elements.length;
        elements[tail++] = element;
    }

    public static Object element() {
        return elements[head % elements.length];
    }

    public static Object dequeue() {
        head %= elements.length;
        Object element = elements[head++];
        size--;
        return element;
    }

    public static void clear() {
        head = 0;
        tail = 0;
        size = 0;
    }

    private static void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            if (head >= tail) {
                Object[] temp = Arrays.copyOf(elements,
                        (size() - head) + tail + capacity);
                System.arraycopy(elements, head, temp,
                        tail + capacity + 1, size() - head - 1);
                head = tail + capacity + 1;
                elements = temp;
            } else {
                elements = Arrays.copyOf(elements, 2 * capacity);
            }
        }
    }

    public static int count(Object element) {
        int cnt = 0;
        for (int i = head, j = 0; j < size; i++, j++) {
            if (element.equals(elements[i % elements.length])) {
                cnt++;
            }
        }
        return cnt;
    }

    public static int size() {
        return size;
    }

    public static boolean isEmpty() {
        return size() == 0;
    }
}
