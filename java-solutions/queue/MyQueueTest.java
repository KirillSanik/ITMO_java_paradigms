package queue;

public class MyQueueTest {

    public static void fill(Queue queue) {
        for(int i = 0; i < 12; i++) {
            queue.enqueue(i);
        }
    }

    public static void dump(Queue queue) {
        while (!queue.isEmpty()) {
            System.out.println("Size: " + queue.size() +
                    " Elem: " + queue.element() +
                    " " + queue.dequeue());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Queue myQueue = new LinkedQueue();
        fill(myQueue);
        dump(myQueue);
        myQueue = new ArrayQueue();
        fill(myQueue);
        dump(myQueue);
    }
}
