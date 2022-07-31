package queue;

import java.util.Arrays;

public class ArrayQueueADT {
    private int size = 0;
    private Object[] elements = new Object[5];
    private int head = 0;
    private int tail = 0;

    public static void enqueue(ArrayQueueADT queue, Object element) {
        ensureCapacity(queue, ++queue.size);
        queue.tail %= queue.elements.length;
        queue.elements[queue.tail++] = element;
    }

    public static Object element(ArrayQueueADT queue) {
        return queue.elements[queue.head % queue.elements.length];
    }

    public static Object dequeue(ArrayQueueADT queue) {
        queue.head %= queue.elements.length;
        Object element = queue.elements[queue.head++];
        queue.size--;
        return element;
    }

    public static void clear(ArrayQueueADT queue) {
        queue.head = 0;
        queue.tail = 0;
        queue.size = 0;
    }

    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity > queue.elements.length) {
            if (queue.head >= queue.tail) {
                Object[] temp = Arrays.copyOf(queue.elements, (queue.size - queue.head) + queue.tail + capacity);
                System.arraycopy(queue.elements, queue.head, temp, queue.tail + capacity + 1, queue.size - queue.head - 1);
                queue.head = queue.tail + capacity + 1;
                queue.elements = temp;
            } else {
                queue.elements = Arrays.copyOf(queue.elements, 2 * capacity);
            }
        }
    }

    public static int count(ArrayQueueADT queue, Object element) {
        int cnt = 0;
        for (int i = queue.head, j = 0; j < queue.size; i++, j++) {
            if (element.equals(queue.elements[i % queue.elements.length])) {
                cnt++;
            }
        }
        return cnt;
    }

    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }
}
